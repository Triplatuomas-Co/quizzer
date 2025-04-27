package com.haagahelia.quizzer.service;

import com.haagahelia.quizzer.domain.*;
import com.haagahelia.quizzer.dto.*;
import com.haagahelia.quizzer.repository.*;

import jakarta.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final TeacherRepository teacherRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    
    // Constants for the template teacher
    // Should be removed once Spring Security is implemented
    private static final String TEMPLATE_TEACHER_USERNAME = "template_teacher";
    private static final String TEMPLATE_TEACHER_FIRSTNAME = "Template";
    private static final String TEMPLATE_TEACHER_LASTNAME = "Teacher";

    public QuizService(QuizRepository quizRepository,TeacherRepository teacherRepository, 
                       QuestionRepository questionRepository, OptionRepository optionRepository) {
        this.quizRepository = quizRepository;
        this.teacherRepository = teacherRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
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
    @Transactional
    public QuizDto createQuiz(QuizDto quizDto) {
        
        // Teacher ID is optional; if not provided, use the template teacher
        // for testing purposes. This should be removed once Spring Security is implemented.
        Teacher teacher = quizDto.getTeacherId() != null
            ? teacherRepository.findById(quizDto.getTeacherId())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Teacher not found: " + quizDto.getTeacherId()))
            : getTemplateTeacher();

        // Create a new Quiz entity from the DTO
        Quiz quiz = new Quiz(
            quizDto.getCategory(), 
            teacher, // Use the template teacher if no teacher ID is provided
            quizDto.getDifficulty(), 
            quizDto.getTitle(), 
            quizDto.getDescription(), 
            quizDto.isPublished());


            // if the quiz has a list of question IDs, fetch the questions and set them in the quiz
            if (quizDto.getQuestions() != null) {
                for (QuestionDto qDto : quizDto.getQuestions()) {
                    Question question = new Question();
                    question.setTitle(qDto.getTitle());
                    question.setDescription(qDto.getDescription());
                    question.setQuiz(quiz);
        
                    if (qDto.getOptions() != null) {
                        for (OptionDto oDto : qDto.getOptions()) {
                            Option option = new Option();
                            option.setText(oDto.getText());
                            option.setIscorrect(oDto.getCorrect());
                            option.setQuestion(question);
                            question.getOptions().add(option);
                        }
                    }
        
                    quiz.getQuestions().add(question);
                }
            }
        
            // Save the quiz and return DTO
            Quiz saved = quizRepository.save(quiz);
            return toDto(saved);
        }


        private QuizDto toDto(Quiz quiz) {
            QuizDto dto = new QuizDto();
            dto.setId(quiz.getQuiz_id());
            dto.setCategory(quiz.getCategory());
            dto.setTeacherId(quiz.getTeacher().getTeacher_id());
            dto.setDifficulty(quiz.getDificulty());
            dto.setTitle(quiz.getTitle());
            dto.setDescription(quiz.getDescription());
            dto.setPublished(quiz.isIspublished());
        
            // map nested questions → QuestionDto
            List<QuestionDto> questionDtos = quiz.getQuestions().stream()
                .map(q -> {
                    QuestionDto qDto = new QuestionDto();
                    qDto.setId(q.getQuestion_id());
                    qDto.setTitle(q.getTitle());
                    qDto.setDescription(q.getDescription());
        
                    // map nested options → OptionDto
                    List<OptionDto> optionDtos = q.getOptions().stream()
                        .map(o -> {
                            OptionDto oDto = new OptionDto();
                            oDto.setId(o.getOption_id());
                            oDto.setText(o.getText());
                            oDto.setCorrect(o.isIscorrect());
                            return oDto;
                        })
                        .collect(Collectors.toList());
        
                    qDto.setOptions(optionDtos);
                    return qDto;
                })
                .collect(Collectors.toList());
        
            dto.setQuestions(questionDtos);
            return dto;
        }
}
