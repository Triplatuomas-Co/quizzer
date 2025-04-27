// src/main/java/com/haagahelia/quizzer/dto/QuestionDto.java
package com.haagahelia.quizzer.dto;

import jakarta.validation.Valid;
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

    @Valid
    @NotEmpty(message = "Each question needs at least one option")
    private List<OptionDto> options = new ArrayList<>();

    public QuestionDto() {}

    public QuestionDto(Long id, String title, String description, List<OptionDto> options) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.options = options != null ? options : new ArrayList<>();
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

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }
}
