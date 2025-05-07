// src/main/java/com/haagahelia/quizzer/dto/QuestionDto.java
package com.haagahelia.quizzer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for creating or returning a Question
 * Includes nested OptionDto list for creation.
 */
public class QuestionDto {
    private Long id;

    @NotBlank(message = "Question title is required")
    @Size(max = 200, message = "Title must be less than 200 characters")
    private String title;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @Min(value = 1, message = "Rating must be at least 1")
    @Min(value = 5, message = "Rating must be at most 5")
    private int difficulty;

    @Valid
    @NotEmpty(message = "Each question needs at least one option")
    private List<OptionDto> options = new ArrayList<>();
    private int answerCount;
    private int correctAnswerCount;

    public QuestionDto() {
    }

    public QuestionDto(Long id, String title, int difficulty, String description, List<OptionDto> options,
            int answerCount,
            int correctAnswerCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.options = options != null ? options : new ArrayList<>();
        this.answerCount = answerCount;
        this.correctAnswerCount = correctAnswerCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public void setCorrectAnswerCount(int correctAnswerCount) {
        this.correctAnswerCount = correctAnswerCount;
    }
}
