package com.haagahelia.quizzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haagahelia.quizzer.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByTitle(String title);
}