package com.haagahelia.quizzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haagahelia.quizzer.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
