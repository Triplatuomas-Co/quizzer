package com.haagahelia.quizzer.dto;

import java.sql.Date;

// object representing a review of a quiz and its DTO (Data Transfer Object)
public class ReviewDTO {

    private Long review_id;
    private String nickname;
    private int rating;
    private String review;
    private Date created_date;

    public ReviewDTO() {
    }

    public ReviewDTO(Long review_id, String nickname, int rating, String review, Date created_date) {
        this.review_id = review_id;
        this.nickname = nickname;
        this.rating = rating;
        this.review = review;
        this.created_date = created_date;
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

    @Override
    public String toString() {
        return "ReviewDTO [review_id=" + review_id + ", nickname=" + nickname + ", rating=" + rating + ", review="
                + review + ", created_date=" + created_date + "]";
    }

}
