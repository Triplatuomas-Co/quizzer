package com.haagahelia.quizzer.controller;

import com.haagahelia.quizzer.domain.Category;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for QuizController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuizControllerIntegrationTest {

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

    /**
     * Tests GET / shows empty list when no quizzes exist.
     */
    @Test
    void indexShowsEmptyListWhenNoQuizzesExist() throws Exception {
        mockMvc.perform(get("/").accept(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists("quizzes"))
            .andExpect(model().attribute("quizzes", hasSize(0)));
    }

    /**
     * Tests GET /quiz/list shows quizzes for the template teacher.
     */
    @Test
    @Transactional
    void quizListShowsQuizzesForTemplateTeacher() throws Exception {
        Category cat = new Category("Cat1", "D1");
        categoryRepository.save(cat);
        Quiz quiz1 = new Quiz(cat, templateTeacher, 1, "Q1", "D1", false);
        Quiz quiz2 = new Quiz(cat, templateTeacher, 2, "Q2", "D2", true);
        quizRepository.save(quiz1);
        quizRepository.save(quiz2);

        mockMvc.perform(get("/quiz/list").accept(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists("quizzes"))
            .andExpect(model().attribute("quizzes", hasSize(2)));
    }

    /**
     * Tests GET /quiz/view/{id} shows quiz details when quiz exists.
     */
    @Test
    @Transactional
    void viewQuizShowsQuizWhenExists() throws Exception {
        Category cat = new Category("Cat2", "D2");
        categoryRepository.save(cat);
        Quiz quiz = new Quiz(cat, templateTeacher, 1, "VQ", "Desc", true);
        quizRepository.save(quiz);

        mockMvc.perform(get("/quiz/view/" + quiz.getQuiz_id()).accept(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(view().name("quizview"))
            .andExpect(model().attributeExists("quiz"))
            .andExpect(model().attribute("quiz", is(quiz)));
    }

    /**
     * Tests GET /quiz/view/{id} redirects when quiz is missing.
     */
    @Test
    void viewQuizRedirectsWhenMissing() throws Exception {
        mockMvc.perform(get("/quiz/view/9999").accept(MediaType.TEXT_HTML))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));
    }

    /**
     * Tests GET /quiz/add provides necessary model attributes for the form.
     */
    @Test
    void showAddQuizFormProvidesModelAttributes() throws Exception {
        mockMvc.perform(get("/quiz/add").accept(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(view().name("addquiz"))
            .andExpect(model().attributeExists("quiz"))
            .andExpect(model().attributeExists("allCategories"));
    }
}