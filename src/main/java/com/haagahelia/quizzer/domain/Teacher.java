package com.haagahelia.quizzer.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long teacher_id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String username; // Added for authentication
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
    private List<Quiz> quizzes;

    public Teacher() {
        this.quizzes = new ArrayList<>();
    }

    public Teacher(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.quizzes = new ArrayList<>();
    }

    // Special constructor for template teacher with ID 0
    public Teacher(Long id, String firstName, String lastName, String username) {
        this.teacher_id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.quizzes = new ArrayList<>();
    }

    public Long getTeacher_id() {
        return teacher_id;
    }

    // Added setter for teacher_id for the template teacher
    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and setters for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    @Override
    public String toString() {
        return "Teacher [teacher_id=" + teacher_id + ", firstName=" + firstName + ", lastName=" + lastName
                + ", dateOfBirth=" + dateOfBirth + ", username=" + username + "]";
    }
}
