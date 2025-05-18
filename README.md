# Quizzer

Quizzer is a lightweight web-based quiz application designed to help users test their knowledge in various subjects. Users can create custom quizzes, take quizzes created by others, and track their performance over time. The project is built using modern web technologies with a focus on usability, responsiveness, and simplicity. It aims to be an educational tool suitable for both self-learners and classroom environments.

## Team members

- [Aku Ihamuotila](https://github.com/akuihamuotila)
- [Jani Könönen](https://github.com/janikononen)
- [Tuomas Jaakkola](https://github.com/tuojaakkola)
- [Tuomas Laalo](https://github.com/TuomasLaalo)
- [Tuomas Leinonen](https://github.com/Leinonen96)

## Additional information

This project is developed as part of the "Introduction to software development projects" course. Contributions follow agile methodologies and GitHub flow practices.

## Product Backlog

[Link](https://github.com/orgs/Triplatuomas-Co/projects/1/views/1)

## Live Backend (Rahti)

[Link](https://quizzer-git-quizzer-postgres.2.rahtiapp.fi/)

## Live Frontend (Netlify)

[Link](https://quizzer-triplatuomas-co.netlify.app/)

## Flinga

[Link](https://edu.flinga.fi/s/ENKEQKR)

## Swagger UI

[Link](https://quizzer-git-quizzer-postgres.2.rahtiapp.fi/swagger-ui/index.html)

## User Instructions

## Frontend

Node version 22.13.0

**MacOS & Windows**  
Terminal commands:

```bash
cd frontend
```

```bash
npm install
```

```bash
npm run dev
```

## Backend

Java version 17.0.12

**MacOS & Windows**  
Terminal commands:

```bash
cd quizzer
```

```bash
./mvnw spring-boot:run
```

### Backend - Test

Java version 17.0.12

**MacOS & Windows**  
Terminal commands:

```bash
cd quizzer
```

```bash
./mvnw test
```

## Entity Relationship Diagram

```mermaid
erDiagram
    TEACHER ||--o{ QUIZ : creates
    CATEGORY ||--o{ QUIZ : has
    QUIZ ||--o{ QUESTION : contains
    QUESTION ||--o{ OPTION : has
    QUIZ ||--o{ REVIEW : has

    TEACHER {
        Long teacher_id
        String firstName
        String lastName
        Date dateOfBirth
        String username
    }

    QUIZ {
        Long quiz_id
        int difficulty
        String title
        String description
        boolean ispublished
        Long teacher_id
        Long category_id
    }

    CATEGORY {
        Long category_id
        String title
        String description
    }

    QUESTION {
        Long question_id
        int difficulty
        String title
        String description
        int answerCount
        int correctAnswerCount
        Long quiz_id
    }

    OPTION {
        Long option_id
        String text
        boolean correct
        Long question_id
    }

    REVIEW {
        Long review_id
        String nickname
        int rating
        String review
        Date created_date
        Long quiz_id
    }
```
