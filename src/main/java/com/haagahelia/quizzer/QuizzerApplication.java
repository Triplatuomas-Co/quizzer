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

	public static void main(String[] args) {
		SpringApplication.run(QuizzerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Teacher teacher = new Teacher("John", "Doe", new java.sql.Date(System.currentTimeMillis()));
		teacherRepository.save(teacher);
		Category category = new Category("Science", "Science quizzes");
		categoryRepository.save(category);

		for (int i = 0; i < 10; i++) {
			Quiz quiz = new Quiz(category, teacher, i, "Quiz Title " + i, "Description for quiz " + i, true);
			teacher.getQuizzes().add(quiz);

			Question question = new Question("Question " + i, "Description for question " + i, 1, quiz);
			quiz.getQuestions().add(question);

			Option option = new Option("Option " + i, i % 2 == 0, question);
			question.getOptions().add(option);

			quizRepository.save(quiz);
		}
	}
}
