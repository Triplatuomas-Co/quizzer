package com.haagahelia.quizzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haagahelia.quizzer.domain.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // Add method to find a teacher by username
    Teacher findByUsername(String username);
}
