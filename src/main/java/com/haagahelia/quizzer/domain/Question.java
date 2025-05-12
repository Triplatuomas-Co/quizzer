package com.haagahelia.quizzer.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long question_id;
    private int difficulty;
    private String title;

    @Column(length = 10000)
    private String description;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question", orphanRemoval = true)
    private List<Option> options;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    private int answerCount = 0;
    private int correctAnswerCount = 0;

    public Question() {
        this.options = new ArrayList<>();
    }

    public Question(String title, String description, int difficulty, Quiz quiz) {
        this.options = new ArrayList<>();
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.quiz = quiz;
    }

    public Long getQuestion_id() {
        return question_id;
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

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
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

    @Override
    public String toString() {
        return "Question [question_id=" + question_id + ", title=" + title + ", description=" + description
                + ", quiz=" + quiz + "]";
    }
}
