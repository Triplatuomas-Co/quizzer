package com.haagahelia.quizzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haagahelia.quizzer.domain.Quiz;
import com.haagahelia.quizzer.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByQuiz(Quiz quiz);
}
