package com.haagahelia.quizzer.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.haagahelia.quizzer.domain.Option;
import com.haagahelia.quizzer.domain.Question;
import com.haagahelia.quizzer.domain.Quiz;
import com.haagahelia.quizzer.domain.Teacher;
import com.haagahelia.quizzer.repository.OptionRepository;
import com.haagahelia.quizzer.repository.QuestionRepository;
import com.haagahelia.quizzer.repository.QuizRepository;
import com.haagahelia.quizzer.repository.TeacherRepository;

import jakarta.annotation.PostConstruct;



@Controller
public class QuizController {

    private final QuizRepository quizRepository;
    private final TeacherRepository teacherRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    
    // Constants for the template teacher
    // Should be removed once Spring Security is implemented
    private static final String TEMPLATE_TEACHER_USERNAME = "template_teacher";
    private static final String TEMPLATE_TEACHER_FIRSTNAME = "Template";
    private static final String TEMPLATE_TEACHER_LASTNAME = "Teacher";

    @Autowired
    public QuizController(QuizRepository quizRepository, 
                          TeacherRepository teacherRepository,
                          QuestionRepository questionRepository,
                          OptionRepository optionRepository) {
        this.quizRepository = quizRepository;
        this.teacherRepository = teacherRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
    }
    
    // Initialize the template teacher after the controller is constructed.
    // THIS IS FOR TESTING PURPOSES ONLY AND SHOULD BE REMOVED IN PRODUCTION.
    @PostConstruct
    public void initTemplateTeacher() {
        if (teacherRepository.findByUsername(TEMPLATE_TEACHER_USERNAME) == null) {
            Teacher templateTeacher = new Teacher();
            templateTeacher.setFirstName(TEMPLATE_TEACHER_FIRSTNAME);
            templateTeacher.setLastName(TEMPLATE_TEACHER_LASTNAME);
            templateTeacher.setUsername(TEMPLATE_TEACHER_USERNAME);
            // Set other required fields as needed (for example, dateOfBirth)
            teacherRepository.save(templateTeacher);
        }
    }
    
    // Helper method to get the template teacher by username.
    // THIS IS FOR TESTING PURPOSES ONLY AND SHOULD BE REMOVED IN PRODUCTION.
    private Teacher getTemplateTeacher() {
        Teacher teacher = teacherRepository.findByUsername(TEMPLATE_TEACHER_USERNAME);
        if (teacher == null) {
            throw new RuntimeException("Template teacher was not initialized properly.");
        }
        return teacher;
    }
    
    // **** CREATE ****
    // Process form data to add a new quiz with questions and options all at once.
    @PostMapping("/addquiz")
    public String postQuiz(@ModelAttribute Quiz quiz) {
        // Set template teacher if not provided by form data.
        if (quiz.getTeacher() == null) {
            quiz.setTeacher(getTemplateTeacher());
        }
        
        // For each question in the quiz, set the back-reference to the quiz.
        if (quiz.getQuestions() != null) {
            for (Question question : quiz.getQuestions()) {
                question.setQuiz(quiz);
                // For each option in the question, set the back-reference to the question.
                if (question.getOptions() != null) {
                    for (Option option : question.getOptions()) {
                        option.setQuestion(question);
                    }
                }
            }
        }
        quizRepository.save(quiz);
        return "redirect:/quiz/list";
    }
    
    // List all quizzes for the template teacher.
    @GetMapping("/quiz/list")
    public String quizList(Model model) {
        Teacher teacher = getTemplateTeacher();
        model.addAttribute("quizzes", quizRepository.findByTeacher(teacher));
        return "quizlist";
    }
    
    // View a specific quiz (including questions and options)
    @GetMapping("/quiz/view/{id}")
    public String viewQuiz(Model model, @PathVariable Long id) {
        Optional<Quiz> quizOpt = quizRepository.findById(id);
        if (quizOpt.isPresent()) {
            model.addAttribute("quiz", quizOpt.get());
            return "quizview";
        }
        return "redirect:/quiz/list";
    }

    @GetMapping("/")
    // Show the index page with a list of quizzes for the template teacher.
    public String index(Model model) {
        Teacher teacher = getTemplateTeacher();
        model.addAttribute("quizzes", quizRepository.findByTeacher(teacher));
        return "index";
    }

    @GetMapping("/quiz/add")
    // Show the form to add a new quiz
    public String showAddQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "addquiz";
    }

    @PostMapping("/quiz/save")
    // Process the form data to save a new quiz
    public String saveQuiz(@ModelAttribute Quiz quiz) {
        if (quiz.getTeacher() == null) {
            quiz.setTeacher(getTemplateTeacher());
        }
        quizRepository.save(quiz);
        return "redirect:/";
    }

    @GetMapping("/quiz/{id}/addquestion")
    // Show the form to add a new question to a specific quiz
    public String showAddQuestionForm(@PathVariable Long id, Model model) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        Question question = new Question();
        question.setQuiz(quiz);
        model.addAttribute("question", question);
        return "addquestion";
    }

    @PostMapping("/quiz/{id}/savequestion")
    // Process the form data to save a new question to a specific quiz
    public String saveQuestion(@PathVariable Long id, @ModelAttribute Question question) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(); 
        question.setQuiz(quiz);
        question.getOptions().forEach(option -> option.setQuestion(question));
        questionRepository.save(question);
        return "redirect:/quiz/view/" + id;
    }

    @GetMapping("/quiz/edit/{id}")
    // Show the form to edit an existing quiz
    public String showEditQuizForm(@PathVariable Long id, Model model) {
        Optional<Quiz> quizOpt = quizRepository.findById(id);
        if (quizOpt.isPresent()) {
            model.addAttribute("quiz", quizOpt.get());
            return "editquiz";
        }
        return "redirect:/quiz/list";
    }

    @PostMapping("/quiz/update/{id}")
    // Process the form data to update an existing quiz
    public String updateQuiz(@PathVariable Long id, @ModelAttribute Quiz quiz) {
        Optional<Quiz> existingQuizOpt = quizRepository.findById(id);
        if (existingQuizOpt.isPresent()) {
            Quiz existingQuiz = existingQuizOpt.get();
            
            // Update basic quiz information
            existingQuiz.setTitle(quiz.getTitle());
            existingQuiz.setDescription(quiz.getDescription());
            
            // Save the updated quiz
            quizRepository.save(existingQuiz);
        }
        return "redirect:/quiz/list";
    }

    @GetMapping("/question/{id}/edit")
    // Show the form to edit an existing question
    public String editQuestion(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow();
        model.addAttribute("question", question);
        return "editquestion";
    }

    @PostMapping("/question/{id}/update")
    // Process the form data to update an existing question
    public String updateQuestion(@PathVariable Long id, @ModelAttribute Question question) {
        Question existingQuestion = questionRepository.findById(id).orElseThrow();
        
        // Set the quiz back-reference to the existing question
        existingQuestion.setTitle(question.getTitle());
        existingQuestion.setDescription(question.getDescription());
        existingQuestion.getOptions().clear();
    
        // Set the options for the existing question
        question.getOptions().forEach(option -> {
            option.setQuestion(existingQuestion);
            existingQuestion.getOptions().add(option);
        });
    
        // Save the updated question
        questionRepository.save(existingQuestion);
        return "redirect:/quiz/view/" + existingQuestion.getQuiz().getQuiz_id();
    }

    @GetMapping("/quiz/delete/{id}")
    // Show the confirmation page for deleting a quiz
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        try {
            Quiz quiz = quizRepository.findById(id).orElseThrow();
            model.addAttribute("quiz", quiz);
            return "deletequiz";
        } catch (Exception e) {
            return "redirect:/quiz/list";
        }
    }

    @PostMapping("/quiz/delete/{id}")
    // Process the deletion of a quiz
    public String deleteQuiz(@PathVariable Long id) {
        try {
            Quiz quiz = quizRepository.findById(id).orElseThrow();
            quizRepository.delete(quiz);
        } catch (Exception e) {
            // Quiz not found, just redirect
        }
        return "redirect:/quiz/list";
    }

}
