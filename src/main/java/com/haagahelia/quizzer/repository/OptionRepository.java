package com.haagahelia.quizzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haagahelia.quizzer.domain.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {

}