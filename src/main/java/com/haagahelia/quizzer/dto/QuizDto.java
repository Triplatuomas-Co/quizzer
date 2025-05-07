package com.haagahelia.quizzer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for creating or returning a Quiz.
 * Questions are now optional.
 */
public class QuizDto {
    private Long id;

    @NotBlank(message = "Category is required")
    private CategoryDTO category;

    /** If omitted, service will fall back to template teacher */
    private Long teacherId;

    @NotNull(message = "Difficulty level is required")
    private Integer difficulty;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @JsonProperty("isPublished")
    private boolean published;

    @Valid
    private List<QuestionDto> questions = new ArrayList<>();

    @Valid
    private List<ReviewDTO> reviews = new ArrayList<>();

    public QuizDto() {
    }

    public QuizDto(Long id,
            CategoryDTO category,
            Long teacherId,
            Integer difficulty,
            String title,
            String description,
            boolean published,
            List<QuestionDto> questions) {
        this.id = id;
        this.category = category;
        this.teacherId = teacherId;
        this.difficulty = difficulty;
        this.title = title;
        this.description = description;
        this.published = published;
        this.questions = questions != null ? questions : new ArrayList<>();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions != null ? questions : new ArrayList<>();
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
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

    @JsonProperty("isPublished")
    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}