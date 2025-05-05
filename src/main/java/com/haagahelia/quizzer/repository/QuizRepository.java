package com.haagahelia.quizzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haagahelia.quizzer.domain.Quiz;
import com.haagahelia.quizzer.domain.Teacher;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // Custom query to find quizzes by teacher
    List<Quiz> findByTeacher(Teacher teacher);

    // Custom query to find quizzes by published status
    List<Quiz> findByIspublished(boolean ispublished);

    // Custom query to find quizzes by category title and published status
    List<Quiz> findByIspublishedAndCategory_Title(boolean ispublished, String categoryTitle);
}
