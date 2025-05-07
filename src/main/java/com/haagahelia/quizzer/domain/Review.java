package com.haagahelia.quizzer.domain;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// Object representing a review of a quiz
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long review_id;
    private String nickname;
    private int rating;
    private String review;
    private Date created_date = new Date(System.currentTimeMillis());
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Review() {
    }

    public Review(String nickname, int rating, String review) {
        this.nickname = nickname;
        this.rating = rating;
        this.review = review;
    }

    public Long getReview_id() {
        return review_id;
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

    @Override
    public String toString() {
        return "Review [review_id=" + review_id + ", nickname=" + nickname + ", rating=" + rating + ", review=" + review
                + ", created_date=" + created_date + "]";
    }

}
