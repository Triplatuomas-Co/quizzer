package com.haagahelia.quizzer.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.haagahelia.quizzer.domain.Category;
import com.haagahelia.quizzer.domain.Option;
import com.haagahelia.quizzer.domain.Question;
import com.haagahelia.quizzer.domain.Quiz;
import com.haagahelia.quizzer.domain.Review;
import com.haagahelia.quizzer.domain.Teacher;
import com.haagahelia.quizzer.dto.CategoryDTO;
import com.haagahelia.quizzer.dto.OptionDto;
import com.haagahelia.quizzer.dto.QuestionDto;
import com.haagahelia.quizzer.dto.QuizDto;
import com.haagahelia.quizzer.dto.ReviewDTO;
import com.haagahelia.quizzer.repository.CategoryRepository;
import com.haagahelia.quizzer.repository.QuestionRepository;
import com.haagahelia.quizzer.repository.QuizRepository;
import com.haagahelia.quizzer.repository.ReviewRepository;
import com.haagahelia.quizzer.repository.TeacherRepository;

import jakarta.annotation.PostConstruct;

@Service
public class QuizService {

    private final ReviewRepository reviewRepository;
    private final QuizRepository quizRepository;
    private final TeacherRepository teacherRepository;
    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;

    // Constants for the template teacher
    // Should be removed once Spring Security is implemented
    private static final String TEMPLATE_TEACHER_USERNAME = "template_teacher";
    private static final String TEMPLATE_TEACHER_FIRSTNAME = "Template";
    private static final String TEMPLATE_TEACHER_LASTNAME = "Teacher";

    public QuizService(QuizRepository quizRepository, TeacherRepository teacherRepository,
            QuestionRepository questionRepository,
            CategoryRepository categoryRepository, ReviewRepository reviewRepository) {
        this.quizRepository = quizRepository;
        this.teacherRepository = teacherRepository;
        this.questionRepository = questionRepository;
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
    // @Transactional
    // public QuizDto createQuiz(QuizDto quizDto) {

    // // Teacher ID is optional; if not provided, use the template teacher
    // // for testing purposes. This should be removed once Spring Security is
    // // implemented.
    // Teacher teacher = quizDto.getTeacherId() != null
    // ? teacherRepository.findById(quizDto.getTeacherId())
    // .orElseThrow(() -> new ResponseStatusException(
    // HttpStatus.BAD_REQUEST, "Teacher not found: " + quizDto.getTeacherId()))
    // : getTemplateTeacher();

    // // create category -object from the DTO
    // Category category =
    // categoryRepository.findById(quizDto.getCategory().getId())
    // .orElseThrow(() -> new ResponseStatusException(
    // HttpStatus.BAD_REQUEST, "Category not found: " +
    // quizDto.getCategory().getId()));

    // // Create a new Quiz entity from the DTO
    // Quiz quiz = new Quiz(
    // category, // Use the category
    // teacher, // Use the template teacher if no teacher ID is provided
    // quizDto.getDifficulty(),
    // quizDto.getTitle(),
    // quizDto.getDescription(),
    // quizDto.isPublished());

    // // if the quiz has a list of question IDs, fetch the questions and set them
    // in
    // // the quiz
    // if (quizDto.getQuestions() != null) {

    // for (QuestionDto qDto : quizDto.getQuestions()) {
    // Question question = new Question(
    // qDto.getTitle(), qDto.getDescription(), qDto.getDifficulty(), quiz);

    // if (qDto.getOptions() != null) {
    // for (OptionDto oDto : qDto.getOptions()) {
    // Option option = new Option(
    // oDto.getText(), question);
    // question.getOptions().add(option);
    // }
    // }
    // quiz.getQuestions().add(question);
    // }
    // }

    // // Save the quiz and return DTO
    // Quiz saved = quizRepository.save(quiz);
    // return toDto(saved);
    // }

    public QuizDto toDto(Quiz quiz) {
        // Create a CategoryDTO from the Quiz's category
        CategoryDTO categoryDto = quiz.getCategory() != null ? toCategoryDTO(quiz.getCategory()) : null;

        // Create a list of QuestionDto from the Quiz's questions using service method
        List<QuestionDto> questionDtos = getListOfQuestionDTOsFromQuiz(quiz);

        // using constructor to create QuizDto object and return it
        return new QuizDto(quiz.getQuiz_id(), categoryDto, quiz.getTeacher().getTeacher_id(),
                quiz.getDificulty(),
                quiz.getTitle(), quiz.getDescription(),
                quiz.isIspublished(), questionDtos);
    }

    public Quiz toEntity(QuizDto quizDto) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDto.getTitle());
        quiz.setDescription(quizDto.getDescription());
        quiz.setDificulty(quizDto.getDifficulty());
        quiz.setIspublished(quizDto.isPublished());

        // Handle category conversion from DTO to Entity
        if (quizDto.getCategory() != null) {
            if (quizDto.getCategory().getId() > 0L) {
                Category category = categoryRepository.findById(quizDto.getCategory().getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Category not found with id: " + quizDto.getCategory().getId()));
                quiz.setCategory(category);
            } else if (quizDto.getCategory().getTitle() != null) {
                Category category = categoryRepository.findByTitle(quizDto.getCategory().getTitle());
                if (category == null) {
                    category = new Category();
                    category.setTitle(quizDto.getCategory().getTitle());
                    category.setDescription(quizDto.getCategory().getDescription());
                    category = categoryRepository.save(category);
                }
                quiz.setCategory(category);
            }
        }
        return quiz;
    }

    // to CategoryDTOs method to convert all categories to list of CategoryDTOs
    public List<CategoryDTO> toCategoryDTOs() {
        return getAllCategoryDTOs();
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

    // Method for updating review
    public void updateReview(Long id, ReviewDTO reviewDTO) {

        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Review not found: " + id));
        review.setNickname(reviewDTO.getNickname());
        review.setRating(reviewDTO.getRating());
        review.setReview(reviewDTO.getReview());
        reviewRepository.save(review);
    }

    // Method to delete review
    public ResponseEntity<String> deleteReview(Review review) {
        reviewRepository.delete(review);
        return ResponseEntity.ok("Review deleted successfully.");
    }
    

    // Method to update total answer count and validate if quiz is published
    public ResponseEntity<?> updateQuestionAnsweredTimes(Option option) {
        Quiz quiz = option.getQuestion().getQuiz();
        if (!quiz.isIspublished()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Quiz is not published, cannot answer to question.");
        }
        Question question = option.getQuestion();
        question.setAnswerCount(option.getQuestion().getAnswerCount() + 1);
        question.setCorrectAnswerCount(option.getQuestion().getCorrectAnswerCount() + 1);
        questionRepository.save(question);

        return ResponseEntity.ok(option);
    }

    // **** CATEGORY SERVICE METHODS ****

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategoryDTOs() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(this::toCategoryDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createCategory(CategoryDTO categoryDTO) {
        // Check if category with the same title already exists
        if (categoryRepository.findByTitle(categoryDTO.getTitle()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Category with title '" + categoryDTO.getTitle() + "' already exists.");
        }
        Category category = toCategoryEntity(categoryDTO);
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category not found with id: " + categoryId));

        // Check if the category is used by any quizzes
        List<Quiz> quizzesUsingCategory = quizRepository.findByCategory(category);
        if (!quizzesUsingCategory.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot delete category '" + category.getTitle() + "' as it is currently in use by "
                            + quizzesUsingCategory.size() + " quiz(zes).");
        }

        categoryRepository.delete(category);
    }

    private CategoryDTO toCategoryDTO(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDTO(category.getCategory_id(), category.getTitle(), category.getDescription());
    }

    private Category toCategoryEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }
        Category category = new Category();
        category.setTitle(categoryDTO.getTitle());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }

    // **** QUESTION SERVICE METHODS ****

    public List<QuestionDto> getListOfQuestionDTOsFromQuiz(Quiz quiz) {

        List<QuestionDto> questionDtos = quiz.getQuestions().stream()
                .map(q -> {
                    // map nested options → OptionDto
                    List<OptionDto> optionDtos = q.getOptions().stream()
                            .map(o -> {
                                OptionDto oDto = new OptionDto(o.getOption_id(), o.getText());
                                return oDto;
                            })
                            .toList();
                    // create QuestionDto object using constructor
                    return new QuestionDto(q.getQuestion_id(), q.getTitle(),
                            q.getDifficulty(),
                            q.getDescription(), optionDtos, q.getAnswerCount(), q.getCorrectAnswerCount());
                })
                .toList();
        return questionDtos;
    }
}
