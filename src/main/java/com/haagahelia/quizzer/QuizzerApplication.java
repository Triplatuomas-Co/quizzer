package com.haagahelia.quizzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.haagahelia.quizzer.domain.Category;
import com.haagahelia.quizzer.domain.Option;
import com.haagahelia.quizzer.domain.Question;
import com.haagahelia.quizzer.domain.Quiz;
import com.haagahelia.quizzer.domain.Teacher;
import com.haagahelia.quizzer.repository.CategoryRepository;
import com.haagahelia.quizzer.repository.QuizRepository;
import com.haagahelia.quizzer.repository.TeacherRepository;

@SpringBootApplication
public class QuizzerApplication implements CommandLineRunner {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // Define constants consistent with QuizController for the template teacher
    private static final String TEMPLATE_TEACHER_USERNAME = "template_teacher";
    private static final String TEMPLATE_TEACHER_FIRSTNAME = "Template";
    private static final String TEMPLATE_TEACHER_LASTNAME = "Teacher";

    public static void main(String[] args) {
        SpringApplication.run(QuizzerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Try to find the template teacher, or create if not exists
        // Teacher templateTeacher =
        // teacherRepository.findByUsername(TEMPLATE_TEACHER_USERNAME);
        // if (templateTeacher == null) {
        // templateTeacher = new Teacher();
        // templateTeacher.setFirstName(TEMPLATE_TEACHER_FIRSTNAME);
        // templateTeacher.setLastName(TEMPLATE_TEACHER_LASTNAME);
        // templateTeacher.setUsername(TEMPLATE_TEACHER_USERNAME);
        // teacherRepository.save(templateTeacher);
        // }

        // Category category = categoryRepository.findByTitle("Science");
        // if (category == null) {
        // category = new Category("Science", "Science quizzes");
        // categoryRepository.save(category);
        // }

        // // Check if quizzes for this teacher already exist to avoid duplicates on
        // // restart
        // if (quizRepository.findByTeacher(templateTeacher).isEmpty()) {
        // for (int i = 0; i < 10; i++) {
        // Quiz quiz = new Quiz(category, templateTeacher, i, "Sample Quiz Title " + i,
        // "Description for sample quiz " + i, true);

        // Question question = new Question("Sample Question " + i, "Description for
        // sample question " + i, 1,
        // quiz);

        // Option option = new Option("Sample Option " + i, i % 2 == 0, question);

        // question.setQuiz(quiz);
        // if (quiz.getQuestions().stream().noneMatch(q ->
        // q.getTitle().equals(question.getTitle()))) {
        // quiz.getQuestions().add(question);
        // }
        // option.setQuestion(question);
        // if (question.getOptions().stream().noneMatch(o ->
        // o.getText().equals(option.getText()))) {
        // question.getOptions().add(option);
        // }

        // quizRepository.save(quiz);
        // }
        // }
    }
}
