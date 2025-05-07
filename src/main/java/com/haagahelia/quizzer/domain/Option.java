package com.haagahelia.quizzer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long option_id;
    private String text;
    private boolean correct;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Option() {
    }

    public Option(String text, boolean correct, Question question) {
        this.text = text;
        this.correct = correct;
        this.question = question;
    }

    public Long getOption_id() {
        return option_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Option [option_id=" + option_id + ", text=" + text + ", iscorrect=" + correct + ", question="
                + question + "]";
    }

}
