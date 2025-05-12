// src/main/java/com/haagahelia/quizzer/dto/OptionDto.java
package com.haagahelia.quizzer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating or returning an Option.
 */
public class OptionDto {
    private Long id;

    @NotBlank(message = "Option text is required")
    @Size(max = 200, message = "Option text must be less than 200 characters")
    private String text;

    public OptionDto() {
    }

    public OptionDto(Long id, String text) {
        this.id = id;
        this.text = text;
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

}
