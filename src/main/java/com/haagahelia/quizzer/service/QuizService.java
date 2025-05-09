package com.haagahelia.quizzer.service;

import com.haagahelia.quizzer.domain.*;
import com.haagahelia.quizzer.dto.*;
import com.haagahelia.quizzer.repository.*;

import jakarta.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class QuizService {

    private final ReviewRepository reviewRepository;
    private final QuizRepository quizRepository;
    private final TeacherRepository teacherRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final CategoryRepository categoryRepository;

    // Constants for the template teacher
    // Should be removed once Spring Security is implemented
    private static final String TEMPLATE_TEACHER_USERNAME = "template_teacher";
    private static final String TEMPLATE_TEACHER_FIRSTNAME = "Template";
    private static final String TEMPLATE_TEACHER_LASTNAME = "Teacher";

    public QuizService(QuizRepository quizRepository, TeacherRepository teacherRepository,
            QuestionRepository questionRepository, OptionRepository optionRepository,
            CategoryRepository categoryRepository, ReviewRepository reviewRepository) {
        this.quizRepository = quizRepository;
        this.teacherRepository = teacherRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.categoryRepository = categoryRepository;
        this.reviewRepository = reviewRepository;
    }

    // Initialize the template teacher.
    @PostConstruct
    public void initTemplateTeacher() {

        if (teacherRepository.findByUsername(TEMPLATE_TEACHER_USERNAME) == null) {
            Teacher templateTeacher = new Teacher();
            templateTeacher.setFirstName(TEMPLATE_TEACHER_FIRSTNAME);
            templateTeacher.setLastName(TEMPLATE_TEACHER_LASTNAME);
            templateTeacher.setUsername(TEMPLATE_TEACHER_USERNAME);
            teacherRepository.save(templateTeacher);
        }
    }

    // Helper method to retrieve the template teacher.
    private Teacher getTemplateTeacher() {

        Teacher teacher = teacherRepository.findByUsername(TEMPLATE_TEACHER_USERNAME);
        if (teacher == null) {
            throw new RuntimeException("Template teacher was not initialized properly.");
        }
        return teacher;
    }

    // **** CREATE ****
    @Transactional
    public QuizDto createQuiz(QuizDto quizDto) {

        // Teacher ID is optional; if not provided, use the template teacher
        // for testing purposes. This should be removed once Spring Security is
        // implemented.
        Teacher teacher = quizDto.getTeacherId() != null
                ? teacherRepository.findById(quizDto.getTeacherId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, "Teacher not found: " + quizDto.getTeacherId()))
                : getTemplateTeacher();

        // create category -object from the DTO
        Category category = categoryRepository.findById(quizDto.getCategory().getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Category not found: " + quizDto.getCategory().getId()));

        // Create a new Quiz entity from the DTO
        Quiz quiz = new Quiz(
                category, // Use the category
                teacher, // Use the template teacher if no teacher ID is provided
                quizDto.getDifficulty(),
                quizDto.getTitle(),
                quizDto.getDescription(),
                quizDto.isPublished());

        // if the quiz has a list of question IDs, fetch the questions and set them in
        // the quiz
        if (quizDto.getQuestions() != null) {

            for (QuestionDto qDto : quizDto.getQuestions()) {
                Question question = new Question(
                        qDto.getTitle(), qDto.getDescription(), qDto.getDifficulty(), quiz);

                if (qDto.getOptions() != null) {
                    for (OptionDto oDto : qDto.getOptions()) {
                        Option option = new Option(
                                oDto.getText(), oDto.getCorrect(), question);
                        question.getOptions().add(option);
                    }
                }
                quiz.getQuestions().add(question);
            }
        }

        // Save the quiz and return DTO
        Quiz saved = quizRepository.save(quiz);
        return toDto(saved);
    }

    public QuizDto toDto(Quiz quiz) {
        // Create a CategoryDTO from the Quiz's category
        CategoryDTO categoryDto = new CategoryDTO(quiz.getCategory().getCategory_id(),
                quiz.getCategory().getTitle(), quiz.getCategory().getDescription());

        // map nested questions → QuestionDto
        List<QuestionDto> questionDtos = quiz.getQuestions().stream()
                .map(q -> {
                    // map nested options → OptionDto
                    List<OptionDto> optionDtos = q.getOptions().stream()
                            .map(o -> {
                                OptionDto oDto = new OptionDto(o.getOption_id(), o.getText(), o.getCorrect());
                                return oDto;
                            })
                            .toList();
                    // create QuestionDto object using constructor
                    return new QuestionDto(q.getQuestion_id(), q.getTitle(),
                            q.getDifficulty(),
                            q.getDescription(), optionDtos, q.getAnswerCount(), q.getCorrectAnswerCount());
                })
                .toList();
        // using constructor to create QuizDto object and return it
        return new QuizDto(quiz.getQuiz_id(), categoryDto, quiz.getTeacher().getTeacher_id(),
                quiz.getDificulty(),
                quiz.getTitle(), quiz.getDescription(),
                quiz.isIspublished(), questionDtos);
    }

    // to CategoryDTOs method to convert all categories to list of CategoryDTOs
    public List<CategoryDTO> toCategoryDTOs() {

        return categoryRepository.findAll().stream()
                .map(c -> new CategoryDTO(c.getCategory_id(), c.getTitle(), c.getDescription()))
                .toList();
    }

    // to CategoryDTO method to get list of reviews from quiz
    public List<ReviewDTO> getListOfReviewDTOsFromQuiz(Long id) {

        List<Review> reviews = quizRepository.findById(id).get().getReviews();
        return reviews.stream().map(r -> new ReviewDTO(r.getReview_id(), r.getNickname(), r.getRating(),
                r.getReview(), r.getCreated_date(), r.getQuiz().getQuiz_id()))
                .toList();
    }

    // toReview method to convert ReviewDTO to Review object and save it to the quiz
    public void toReview(ReviewDTO rDTO) {

        Quiz quiz = quizRepository.findById(rDTO.getQuiz_id()).get();
        Review review = new Review(rDTO.getNickname(), rDTO.getRating(), rDTO.getReview(),
                quiz);
        quiz.getReviews().add(review);
        quizRepository.save(quiz);
    }

    // Method to update total answer count and correct answer count for a question
    // if boolean true
    public ResponseEntity<String> addAnswerCount(Long questionId, boolean isCorrect) {

        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Question not found: " + questionId));
        question.setAnswerCount(question.getAnswerCount() + 1);
        if (isCorrect) {
            question.setCorrectAnswerCount(question.getCorrectAnswerCount() + 1);
        }
        questionRepository.save(question);
        return ResponseEntity.ok("Answer count updated successfully.");
    }

    // Method for updating review
    public void updateReview(Long id, ReviewDTO reviewDTO) {

        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Review not found: " + id));
        review.setNickname(reviewDTO.getNickname());
        review.setRating(reviewDTO.getRating());
        review.setReview(reviewDTO.getReview());
        reviewRepository.save(review);
    }
}
