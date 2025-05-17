package com.haagahelia.quizzer.controller;

import com.haagahelia.quizzer.domain.Category;
import com.haagahelia.quizzer.domain.Option;
import com.haagahelia.quizzer.domain.Question;
import com.haagahelia.quizzer.domain.Quiz;
import com.haagahelia.quizzer.domain.Teacher;
import com.haagahelia.quizzer.repository.CategoryRepository;
import com.haagahelia.quizzer.repository.OptionRepository;
import com.haagahelia.quizzer.repository.QuestionRepository;
import com.haagahelia.quizzer.repository.QuizRepository;
import com.haagahelia.quizzer.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for QuizRestController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuizRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    private Teacher templateTeacher;

    @BeforeEach
    void setUp() {
        optionRepository.deleteAll();
        questionRepository.deleteAll();
        quizRepository.deleteAll();
        categoryRepository.deleteAll();
        teacherRepository.deleteAll();

        templateTeacher = new Teacher("Template", "Teacher", new Date(System.currentTimeMillis()));
        templateTeacher.setUsername("template_teacher");
        teacherRepository.save(templateTeacher);
    }

    // --------------------------------------
    // Exercise 10: GET /api/quiz/published-list
    // --------------------------------------

    /**
     * Tests GET /api/quiz/published-list returns empty list when no quizzes exist.
     */
    @Test
    @Transactional
    void getAllQuizzesReturnsEmptyListWhenNoQuizzesExist() throws Exception {
        mockMvc.perform(get("/api/quiz/published-list")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * Tests GET /api/quiz/published-list returns published quizzes.
     */
    @Test
    @Transactional
    void getAllQuizzesReturnsListOfQuizzesWhenPublishedQuizzesExist() throws Exception {
        Category cat = new Category("TestCat", "Desc");
        categoryRepository.save(cat);

        Quiz q1 = new Quiz(cat, templateTeacher, 1, "Quiz1", "Desc1", true);
        Quiz q2 = new Quiz(cat, templateTeacher, 2, "Quiz2", "Desc2", true);
        quizRepository.save(q1);
        quizRepository.save(q2);

        mockMvc.perform(get("/api/quiz/published-list")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].title", is("Quiz1")))
            .andExpect(jsonPath("$[1].title", is("Quiz2")));
    }

    /**
     * Tests GET /api/quiz/published-list does not return unpublished quizzes.
     */
    @Test
    @Transactional
    void getAllQuizzesDoesNotReturnUnpublishedQuizzes() throws Exception {
        Category cat = new Category("TestCat2", "Desc2");
        categoryRepository.save(cat);

        Quiz published = new Quiz(cat, templateTeacher, 1, "PubQuiz", "desc", true);
        Quiz unpublished = new Quiz(cat, templateTeacher, 1, "UnpubQuiz", "desc", false);
        quizRepository.save(published);
        quizRepository.save(unpublished);

        mockMvc.perform(get("/api/quiz/published-list")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].title", is("PubQuiz")));
    }

    // --------------------------------------
    // Exercise 11: GET /api/quiz/questions/{id}
    // --------------------------------------

    /**
     * Tests GET /api/quiz/questions/{id} returns empty list if quiz has no questions.
     */
    @Test
    @Transactional
    void getQuestionsByQuizIdReturnsEmptyListWhenQuizDoesNotHaveQuestions() throws Exception {
        Category cat = new Category("Cat3", "Desc3");
        categoryRepository.save(cat);

        Quiz quiz = new Quiz(cat, templateTeacher, 1, "NoQQuiz", "desc", true);
        quizRepository.save(quiz);

        mockMvc.perform(get("/api/quiz/questions/" + quiz.getQuiz_id())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * Tests GET /api/quiz/questions/{id} returns questions with their options.
     */
    @Test
    @Transactional
    void getQuestionsByQuizIdReturnsListOfQuestionsWhenQuizHasQuestions() throws Exception {
        Category cat = new Category("Cat4", "Desc4");
        categoryRepository.save(cat);

        Quiz quiz = new Quiz(cat, templateTeacher, 1, "QQuiz", "desc", true);
        if (quiz.getQuestions() == null) {
            quiz.setQuestions(new ArrayList<>());
        }
        quizRepository.save(quiz);

        Question question1 = new Question("Q1", "DescQ1", 1, quiz);
        Question question2 = new Question("Q2", "DescQ2", 2, quiz);

        quiz.getQuestions().add(question1);
        quiz.getQuestions().add(question2);
        
        questionRepository.saveAll(List.of(question1, question2));

        Option opt1 = new Option("Opt1", true, question1);
        Option opt2 = new Option("Opt2", false, question1);

        if (question1.getOptions() == null) { question1.setOptions(new ArrayList<>()); }
        question1.getOptions().add(opt1);
        question1.getOptions().add(opt2);

        Option opt3 = new Option("Opt3", false, question2);
        if (question2.getOptions() == null) { question2.setOptions(new ArrayList<>());
        }
        question2.getOptions().add(opt3);

        quizRepository.save(quiz);

        mockMvc.perform(get("/api/quiz/questions/" + quiz.getQuiz_id())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].options", hasSize(2)))
            .andExpect(jsonPath("$[1].options", hasSize(1)));
    }

    /**
     * Tests GET /api/quiz/questions/{id} returns 404 for a non-existent quiz.
     */
    @Test
    void getQuestionsByQuizIdReturnsErrorWhenQuizDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/quiz/questions/9999")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    // --------------------------------------
    // Exercise 12: PUT /api/quiz/update-answered-times
    // --------------------------------------

    /**
     * Tests PUT /api/quiz/update-answered-times increments counters for a correct answer on a published quiz.
     */
    @Test
    @Transactional
    void createAnswerSavesAnswerForPublishedQuiz() throws Exception {
        Category cat = new Category("CatA", "Desc");
        categoryRepository.save(cat);
        Quiz quiz = new Quiz(cat, templateTeacher, 1, "AnsPub", "D", true);
        quizRepository.save(quiz);
        Question q = new Question("Q", "D", 1, quiz);
        questionRepository.save(q);
        Option correct = new Option("Yes", true, q);
        optionRepository.save(correct);

        mockMvc.perform(put("/api/quiz/update-answered-times")
                .contentType(MediaType.APPLICATION_JSON)
                .content(correct.getOption_id().toString()))
            .andExpect(status().isOk());

        Question updated = questionRepository.findById(q.getQuestion_id()).get();
        assertEquals(1, updated.getAnswerCount());
        assertEquals(1, updated.getCorrectAnswerCount());
    }

    /**
     * Tests PUT /api/quiz/update-answered-times returns 400 if optionId is missing.
     */
    @Test
    void createAnswerDoesNotSaveAnswerWithoutAnswerOption() throws Exception {
        mockMvc.perform(put("/api/quiz/update-answered-times")
                .contentType(MediaType.APPLICATION_JSON)
                .content("null"))
            .andExpect(status().isBadRequest());
    }

    /**
     * Tests PUT /api/quiz/update-answered-times returns 404 for a non-existent optionId.
     */
    @Test
    void createAnswerDoesNotSaveAnswerForNonExistingAnswerOption() throws Exception {
        mockMvc.perform(put("/api/quiz/update-answered-times")
                .contentType(MediaType.APPLICATION_JSON)
                .content("99999"))
            .andExpect(status().isNotFound());
    }

    /**
     * Tests PUT /api/quiz/update-answered-times returns 403 if quiz is not published.
     */
    @Test
    @Transactional
    void createAnswerDoesNotSaveAnswerForNonPublishedQuiz() throws Exception {
        Category cat = new Category("CatA2", "Desc");
        categoryRepository.save(cat);
        Quiz quiz = new Quiz(cat, templateTeacher, 1, "AnsUnpub", "D", false);
        quizRepository.save(quiz);
        Question q = new Question("Q", "D", 1, quiz);
        questionRepository.save(q);
        Option opt = new Option("Yes", true, q);
        optionRepository.save(opt);

        mockMvc.perform(put("/api/quiz/update-answered-times")
                .contentType(MediaType.APPLICATION_JSON)
                .content(opt.getOption_id().toString()))
            .andExpect(status().isForbidden());
    }

    // --------------------------------------
    // Exercise 13: GET /api/quiz/list (additional endpoint)
    // --------------------------------------

    /**
     * Tests GET /api/quiz/list returns empty list if no quizzes exist for the teacher.
     */
    @Test
    @Transactional
    void getQuizListReturnsEmptyWhenNoQuizzesExist() throws Exception {
        mockMvc.perform(get("/api/quiz/list")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * Tests GET /api/quiz/list returns quizzes only for the specific (template) teacher.
     */
    @Test
    @Transactional
    void getQuizListReturnsQuizzesForTemplateTeacher() throws Exception {
        Category cat = new Category("Cat5", "Desc5");
        categoryRepository.save(cat);

        Quiz quiz1 = new Quiz(cat, templateTeacher, 1, "TQuiz1", "desc", false);
        Quiz quiz2 = new Quiz(cat, templateTeacher, 2, "TQuiz2", "desc", true);
        quizRepository.save(quiz1);
        quizRepository.save(quiz2);

        Teacher other = new Teacher("Other", "Teacher", new Date(System.currentTimeMillis()));
        other.setUsername("other_teacher");
        teacherRepository.save(other);
        Quiz otherQuiz = new Quiz(cat, other, 1, "OQuiz", "desc", true);
        quizRepository.save(otherQuiz);

        mockMvc.perform(get("/api/quiz/list")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }
}
