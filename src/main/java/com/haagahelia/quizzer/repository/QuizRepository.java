package com.haagahelia.quizzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haagahelia.quizzer.domain.Quiz;
import com.haagahelia.quizzer.domain.Teacher;

public interface QuizRepository extends JpaRepository<Quiz, Long> { 
    List<Quiz> findByTeacher(Teacher teacher);
}
