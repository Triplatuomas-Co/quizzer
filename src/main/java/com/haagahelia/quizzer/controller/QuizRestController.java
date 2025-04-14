package com.haagahelia.quizzer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.haagahelia.quizzer.domain.Quiz;
import com.haagahelia.quizzer.domain.Question;
import com.haagahelia.quizzer.domain.Option;
import com.haagahelia.quizzer.domain.Teacher;
import com.haagahelia.quizzer.repository.QuizRepository;
import com.haagahelia.quizzer.repository.TeacherRepository;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/quiz") // Base URL for the Quiz API
public class QuizRestController {

    private final QuizRepository quizRepository;
    private final TeacherRepository teacherRepository;
    
    // Constants for the template teacher for testing purposes.
    private static final String TEMPLATE_TEACHER_USERNAME = "template_teacher";
    private static final String TEMPLATE_TEACHER_FIRSTNAME = "Template";
    private static final String TEMPLATE_TEACHER_LASTNAME = "Teacher";

    @Autowired
    public QuizRestController(QuizRepository quizRepository, 
                              TeacherRepository teacherRepository) {
        this.quizRepository = quizRepository;
        this.teacherRepository = teacherRepository;
    }
    
    // Initialize the template teacher.
    @PostConstruct
    public void initTemplateTeacher() {
        if (teacherRepository.findByUsername(TEMPLATE_TEACHER_USERNAME) == null) {
            Teacher templateTeacher = new Teacher();
            templateTeacher.setFirstName(TEMPLATE_TEACHER_FIRSTNAME);
            templateTeacher.setLastName(TEMPLATE_TEACHER_LASTNAME);
            templateTeacher.setUsername(TEMPLATE_TEACHER_USERNAME);
            teacherRepository.save(templateTeacher);
        }
    }
    
    // Helper method to retrieve the template teacher.
    private Teacher getTemplateTeacher() {
        Teacher teacher = teacherRepository.findByUsername(TEMPLATE_TEACHER_USERNAME);
        if (teacher == null) {
            throw new RuntimeException("Template teacher was not initialized properly.");
        }
        return teacher;
    }
    
    // **** CREATE ****
    // Accepts a JSON payload to add a new Quiz with nested questions and options.
    // POST http://localhost:8080/api/quiz
    // Example JSON payload:
    // {
    //     "category": "General",
    //     "dificulty": 1,
    //     "title": "Sample Quiz",
    //     "description": "This quiz tests basic Java concepts.",
    //     "ispublished": false,
    //     "questions": [
    //         {
    //         "title": "What is Java?",
    //         "description": "Select the correct answer.",
    //         "options": [
    //             {
    //             "text": "Programming Language",
    //             "iscorrect": true
    //             },
    //             {
    //             "text": "A type of coffee",
    //             "iscorrect": false
    //             }
    //         ]
    //         }
    //     ]
    // }
    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        if (quiz.getTeacher() == null) {
            quiz.setTeacher(getTemplateTeacher());
        }
        if (quiz.getQuestions() != null) {
            for (Question question : quiz.getQuestions()) {
                question.setQuiz(quiz);
                if (question.getOptions() != null) {
                    for (Option option : question.getOptions()) {
                        option.setQuestion(question);
                    }
                }
            }
        }
        return quizRepository.save(quiz);
    }
    
    // **** READ ****
    // Returns the list of all quizzes for the template teacher.
    @GetMapping("/list")
    public List<Quiz> getQuizList() {
        Teacher teacher = getTemplateTeacher();
        return quizRepository.findByTeacher(teacher);
    }
    
    // Returns a single quiz by its ID.
    @GetMapping("/view/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        Optional<Quiz> quizOpt = quizRepository.findById(id);
        return quizOpt.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    // You can add update and delete endpoints similarly using @PutMapping and @DeleteMapping.
}
