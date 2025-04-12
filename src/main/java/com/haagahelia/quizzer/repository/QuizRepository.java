package com.haagahelia.quizzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haagahelia.quizzer.domain.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> { 

}
