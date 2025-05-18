import { QuizWithQuestions } from "../types";
import { useAnswerSubmission } from "../hooks/useAnswerSubmission";
import QuestionDisplay from "./QuestionDisplay";
import { Box, Typography, Divider } from "@mui/material";

interface QuestionCardProps {
  quiz: QuizWithQuestions;
}

export default function QuestionCard({ quiz }: QuestionCardProps) {
  const {
    selectedAnswers,
    answeredQuestions,
    handleAnswerSubmit,
    handleAnswerSelect
  } = useAnswerSubmission({ quizId: quiz.id });

  return (
    <Box sx={{ maxWidth: 800, mx: 'auto', p: 2 }}>
      <Typography variant="h4" gutterBottom sx={{ textAlign: 'left' }}>
        {quiz.title}
      </Typography>
      <Typography 
        variant="body1" 
        color="text.secondary" 
        sx={{ 
          textAlign: 'left',
          whiteSpace: 'pre-wrap',
          lineHeight: 1.6,
          wordBreak: 'break-word',
          overflowWrap: 'break-word',
          mb: 1
        }}
      >
        {quiz.description}
      </Typography>
      <Typography variant="body2" color="text.secondary" sx={{ textAlign: 'left' }}>
        Questions: {quiz.questions.length}  - 
          Category: {quiz.category.title}
      </Typography>
      <Divider sx={{ my: 3 }} />

      {quiz.questions.map((question) => (
        <QuestionDisplay
          key={question.id}
          question={question}
          selectedAnswer={selectedAnswers[question.id]}
          isAnswered={answeredQuestions[question.id]}
          onAnswerSelect={handleAnswerSelect}
          onSubmit={handleAnswerSubmit}
          quiz={quiz}
        />
      ))}
    </Box>
  );
}
