package com.haagahelia.quizzer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.haagahelia.quizzer.domain.Option;
import com.haagahelia.quizzer.domain.Quiz;
import com.haagahelia.quizzer.domain.Review;
import com.haagahelia.quizzer.domain.Teacher;
import com.haagahelia.quizzer.dto.CategoryDTO;
import com.haagahelia.quizzer.dto.QuestionDto;
import com.haagahelia.quizzer.dto.QuizDto;
import com.haagahelia.quizzer.dto.ReviewDTO;
import com.haagahelia.quizzer.repository.OptionRepository;
import com.haagahelia.quizzer.repository.QuizRepository;
import com.haagahelia.quizzer.repository.ReviewRepository;
import com.haagahelia.quizzer.repository.TeacherRepository;
import com.haagahelia.quizzer.service.QuizService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/quiz") // Base URL for the Quiz API
public class QuizRestController {

        private final QuizRepository quizRepository;
        private final TeacherRepository teacherRepository;
        private final QuizService quizService;
        private final ReviewRepository reviewRepository;
        private final OptionRepository optionRepository;

        // Constants for the template teacher for testing purposes.
        private static final String TEMPLATE_TEACHER_USERNAME = "template_teacher";
        private static final String TEMPLATE_TEACHER_FIRSTNAME = "Template";
        private static final String TEMPLATE_TEACHER_LASTNAME = "Teacher";

        public QuizRestController(QuizRepository quizRepository, TeacherRepository teacherRepository,
                        QuizService quizService, ReviewRepository reviewRepository, OptionRepository optionRepository) {
                this.quizRepository = quizRepository;
                this.teacherRepository = teacherRepository;
                this.quizService = quizService;
                this.reviewRepository = reviewRepository;
                this.optionRepository = optionRepository;
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
        // Accepts a JSON payload to add a new Quiz with nested questions and options.
        // POST http://localhost:8080/api/quiz
        // Example JSON payload:
        // {
        // "category": "General",
        // "difficulty": 1,
        // "title": "Sample Quiz",
        // "description": "This quiz tests basic Java concepts.",
        // "ispublished": false,
        // "questions": [
        // {
        // "title": "What is Java?",
        // "description": "Select the correct answer.",
        // "options": [
        // {
        // "text": "Programming Language",
        // "iscorrect": true
        // },
        // {
        // "text": "A type of coffee",
        // "iscorrect": false
        // }
        // ]
        // }
        // ]
        // }

        // YOU CAN UNCOMMENT THIS METHOD TO ENABLE QUIZ CREATION

        // @Tag(name = "Quizzes", description = "Operations related to quizzes")
        // @Operation(summary = "Create a new quiz")
        // @ApiResponses(value = {
        // @ApiResponse(responseCode = "201", description = "Quiz created
        // successfully"),
        // @ApiResponse(responseCode = "400", description = "Invalid input data")
        // })
        // @PostMapping
        // public ResponseEntity<QuizDto> createQuiz(@Valid @RequestBody QuizDto
        // quizDto) {
        // QuizDto created = quizService.createQuiz(quizDto);
        // return ResponseEntity.status(HttpStatus.CREATED).body(created);
        // }

        @Tag(name = "Quizzes", description = "Operations related to quizzes")
        @Operation(summary = "Get all quizzes for the template teacher")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Quizzes retrieved successfully")
        })
        @GetMapping("/list")
        public List<Quiz> getQuizList() {
                Teacher teacher = getTemplateTeacher();
                return quizRepository.findByTeacher(teacher);
        }

        @Tag(name = "Quizzes", description = "Operations related to quizzes")
        @Operation(summary = "Get all quizzes that are published")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Quizzes retrieved successfully")
        })
        @GetMapping("/published-list")
        public List<QuizDto> getAllPublishedQuizzes() {
                return quizRepository.findByIspublished(true).stream().map(quiz -> quizService.toDto(quiz)).toList();
        }

        @Tag(name = "Quizzes", description = "Operations related to quizzes")
        @Operation(summary = "Get a quiz by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Quiz retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "Quiz not found")
        })
        @GetMapping("/{id}")
        public ResponseEntity<QuizDto> getQuizById(@PathVariable Long id) {
                Quiz quiz = quizRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Quiz not found with id: " + id));
                return ResponseEntity.ok(quizService.toDto(quiz));
        }

        @Tag(name = "Quizzes", description = "Operations related to quizzes")
        @Operation(summary = "Get all published quizzes by category", description = "Returns a list of published quizzes filtered by category name.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Quizzes retrieved successfully")
        })
        @GetMapping("/published-list/{category}")
        public List<QuizDto> getAllPublishedQuizzesByCategory(@PathVariable String category) {
                return quizRepository.findByIspublishedAndCategory_Title(true, category).stream()
                                .map(quiz -> quizService.toDto(quiz))
                                .toList();
        }

        @Tag(name = "Question", description = "Operations related to Questions")
        @Operation(summary = "Get all Questions of Quiz", description = "Returns a list of questions from given quiz-id.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Questions retrieved successfully")
        })
        @GetMapping("/questions/{id}")
        public List<QuestionDto> getAllQuestionsFromQuiz(@PathVariable Long id) {
                Quiz quiz = quizRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Quiz not found with id: " + id));
                return quizService.getListOfQuestionDTOsFromQuiz(quiz);
        }

        @Tag(name = "Category", description = "Operations related to categories")
        @Operation(summary = "Get all categories", description = "Returns a list of categories.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
        })
        @GetMapping("/categories")
        public List<CategoryDTO> getAllCategories() {
                return quizService.toCategoryDTOs();
        }

        @Tag(name = "Review", description = "Operations related to reviews")
        @Operation(summary = "Get all reviews", description = "Returns a list of reviews from given quiz-id.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully")
        })
        @GetMapping("/{id}/reviews")
        public List<ReviewDTO> getAllReviewsFromQuiz(@PathVariable Long id) {
                return quizService.getListOfReviewDTOsFromQuiz(id);
        }

        @Tag(name = "Review", description = "Operations related to reviews")
        @Operation(summary = "Create Review", description = "Creates a new review for a quiz.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Review created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data")
        })

        @PostMapping("/review")
        public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewDTO reviewDTO,
                        BindingResult bindingResult) {
                if (bindingResult.hasErrors()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        bindingResult.getAllErrors().get(0).getDefaultMessage());
                }
                quizService.toReview(reviewDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(reviewDTO);
        }

        @Tag(name = "Review", description = "Operations related to reviews")
        @Operation(summary = "Get Review", description = "Returns single review by id.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Review retrieved successfully")
        })
        @GetMapping("/review/{id}")
        public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
                Review review = reviewRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Review not found with id: " + id));

                ReviewDTO dto = new ReviewDTO(
                                review.getReview_id(),
                                review.getNickname(),
                                review.getRating(),
                                review.getReview(),
                                review.getCreated_date(),
                                review.getQuiz().getQuiz_id());

                return ResponseEntity.ok(dto);
        }

        @Tag(name = "Review", description = "Operations related to reviews")
        @Operation(summary = "Update Review", description = "Updates review of quiz.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Review updated successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data")
        })
        @PutMapping("/review/{id}")
        public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id,
                        @Valid @RequestBody ReviewDTO reviewDTO, BindingResult bindingResult) {
                if (bindingResult.hasErrors()) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                        bindingResult.getAllErrors().get(0).getDefaultMessage());
                }
                quizService.updateReview(id, reviewDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(reviewDTO);
        }

        @Tag(name = "Review", description = "Operations related to reviews")
        @Operation(summary = "Delete Review", description = "Delete review of quiz.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Review deleted successfully")
        })
        @DeleteMapping("/review/{id}")
        public ResponseEntity<String> deleteReview(@PathVariable Long id) {
                Review review = reviewRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Quiz not found with id: " + id));
                return quizService.deleteReview(review);
        }

        @Tag(name = "Option ", description = "Operations related to Optoions")
        @Operation(summary = "Manages functionality for answering to questions", description = "Adds total answered times +1 to a Question and updates the correct answer count if the option boolean value is true. Returns Option -object for given optionId")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Updated successfully")
        })
        @PutMapping("/update-answered-times")
        public ResponseEntity<?> updateQuestionAnsweredTimes(@RequestBody Long optionId) {
                Option option = optionRepository.findById(optionId)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                "Option not found with id: " + optionId));

                return quizService.updateQuestionAnsweredTimes(option);
        }
        // You can add update and delete endpoints similarly using @PutMapping and
        // @DeleteMapping.
}
