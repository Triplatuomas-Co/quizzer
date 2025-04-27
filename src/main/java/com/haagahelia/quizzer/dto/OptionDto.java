// src/main/java/com/haagahelia/quizzer/dto/OptionDto.java
package com.haagahelia.quizzer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating or returning an Option.
 */
public class OptionDto {
    private Long id;

    @NotBlank(message = "Option text is required")
    @Size(max = 200, message = "Option text must be less than 200 characters")
    private String text;

    @NotNull(message = "Correctness is required")
    private Boolean correct;

    public OptionDto() {}

    public OptionDto(Long id, String text, Boolean correct) {
        this.id = id;
        this.text = text;
        this.correct = correct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
