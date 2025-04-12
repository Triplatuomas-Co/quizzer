package com.haagahelia.quizzer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.haagahelia.quizzer.domain.Option;
import com.haagahelia.quizzer.domain.Question;
import com.haagahelia.quizzer.domain.Quiz;
import com.haagahelia.quizzer.domain.Teacher;
import com.haagahelia.quizzer.repository.TeacherRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
class QuizzerApplicationTests {

	@Autowired
	private TeacherRepository teacherRepository;

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	void testTeacherWithQuizAndQuestion() {
		// Luodaan opettaja
		Teacher teacher = new Teacher("John", "Doe", new java.sql.Date(System.currentTimeMillis()));

		// Luodaan quiz ja liitetään se opettajaan
		Quiz quiz = new Quiz("Category 1", teacher, 1, "Quiz Title", "Quiz Description", false);
		teacher.getQuizzes().add(quiz);

		// Luodaan kysymys ja liitetään se quiz:iin
		Question question = new Question("Question 1", "Description 1", quiz);
		quiz.getQuestions().add(question);

		// Luodaan vaihtoehto ja liitetään se kysymykseen
		Option option = new Option("Option 1", true, question);
		question.getOptions().add(option);

		// Tallennetaan opettaja (cascade tallentaa myös quizin, kysymyksen ja
		// vaihtoehdon)
		teacherRepository.save(teacher);

		// Haetaan opettaja tietokannasta ja varmistetaan, että kaikki liittyvät tiedot
		// tallentuivat
		Teacher savedTeacher = teacherRepository.findAll().get(0);
		assertEquals("John", savedTeacher.getFirstName());
		assertEquals(1, savedTeacher.getQuizzes().size());
		assertEquals("Quiz Title", savedTeacher.getQuizzes().get(0).getTitle());
		assertEquals(1, savedTeacher.getQuizzes().get(0).getQuestions().size());
		assertEquals("Question 1", savedTeacher.getQuizzes().get(0).getQuestions().get(0).getTitle());
		assertEquals(1, savedTeacher.getQuizzes().get(0).getQuestions().get(0).getOptions().size());
		assertEquals("Option 1", savedTeacher.getQuizzes().get(0).getQuestions().get(0).getOptions().get(0).getText());
	}
}
