package com.haagahelia.quizzer.dto;

import java.sql.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// object representing a review of a quiz and its DTO (Data Transfer Object)
public class ReviewDTO {

    private Long review_id;
    @NotBlank(message = "Nickname is required")
    private String nickname;

    @Min(value = 1, message = "Rating must be at least 1")
    @Min(value = 5, message = "Rating must be at most 5")
    private int rating;
    @Size(max = 1000, message = "Review must be less than 1000 characters")
    private String review;
    private Date created_date;
    @NotBlank(message = "Quiz ID is required")
    private Long quiz_id;

    public ReviewDTO() {
    }

    public ReviewDTO(Long review_id, String nickname, int rating, String review, Date created_date, Long quiz_id) {
        this.review_id = review_id;
        this.nickname = nickname;
        this.rating = rating;
        this.review = review;
        this.created_date = created_date;
        this.quiz_id = quiz_id;
    }

    public Long getReview_id() {
        return review_id;
    }

    public void setReview_id(Long review_id) {
        this.review_id = review_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Long getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(Long quiz_id) {
        this.quiz_id = quiz_id;
    }

    @Override
    public String toString() {
        return "ReviewDTO [review_id=" + review_id + ", nickname=" + nickname + ", rating=" + rating + ", review="
                + review + ", created_date=" + created_date + ", quiz_id=" + quiz_id + "]";
    }

}
