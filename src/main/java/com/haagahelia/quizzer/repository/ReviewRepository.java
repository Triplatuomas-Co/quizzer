package com.haagahelia.quizzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haagahelia.quizzer.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
