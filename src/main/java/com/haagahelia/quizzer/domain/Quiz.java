package com.haagahelia.quizzer.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long quiz_id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    private int dificulty;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
    private List<Question> questions = new ArrayList<>();
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
    private List<Review> reviews = new ArrayList<>();
    private String title;
    private String description;
    private boolean ispublished = false;

    public Quiz() {
    }

    public Quiz(Category category, Teacher teacher, int dificulty, String title,
            String description, boolean ispublished) {
        this.category = category;
        this.teacher = teacher;
        this.dificulty = dificulty;
        this.title = title;
        this.description = description;
        this.ispublished = ispublished;
    }

    public Long getQuiz_id() {
        return quiz_id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getDificulty() {
        return dificulty;
    }

    public void setDificulty(int dificulty) {
        this.dificulty = dificulty;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
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

    public boolean isIspublished() {
        return ispublished;
    }

    public void setIspublished(boolean ispublished) {
        this.ispublished = ispublished;
    }

    @Override
    public String toString() {
        return "Quiz [id=" + quiz_id + ", dificulty=" + dificulty
                + ", title=" + title + ", description=" + description + ", ispublished="
                + ispublished + "]";
    }

}
