import { Question as QuizQuestion, Option as QuizOption, QuizWithQuestions } from "../types";
import {
  Card,
  CardContent,
  Radio,
  RadioGroup,
  FormControlLabel,
  Button,
  Typography,
  Box
} from "@mui/material";

interface QuestionDisplayProps {
  question: QuizQuestion;
  selectedAnswer: number | undefined;
  isAnswered: boolean;
  onAnswerSelect: (questionId: number, optionId: number) => void;
  onSubmit: (questionId: number) => void;
  quiz: QuizWithQuestions;
}

export default function QuestionDisplay({
  question,
  selectedAnswer,
  isAnswered,
  onAnswerSelect,
  onSubmit,
  quiz
}: QuestionDisplayProps) {
  const getDifficultyText = (difficulty: number): string => {
    switch (difficulty) {
      case 1: return "Easy";
      case 2: return "Normal";
      case 3: return "Hard";
      default: return "Normal";
    }
  };

  return (
    <Card
      sx={{
        mb: 3,
        boxShadow: 2,
        '&:hover': {
          boxShadow: 4
        }
      }}
    >
      <CardContent>
        <Typography variant="h6" gutterBottom>
          {question.title}
        </Typography>

        {question.description && (
          <Typography 
            variant="body1" 
            color="text.secondary" 
            sx={{ 
              whiteSpace: 'pre-wrap',
              textAlign: 'left',
              lineHeight: 1.6,
              py: 1,
              wordBreak: 'break-word',
              overflowWrap: 'break-word',
              mb: 2 // Retaining the margin bottom from the previous state
            }}
          >
            {question.description}
          </Typography>
        )}

        <Typography variant="subtitle2" color="text.secondary" sx={{ mb: 2 }}>
        Question {quiz.questions.findIndex(q => q.id === question.id) + 1} of {quiz.questions.length} –
        Difficulty: {getDifficultyText(question.difficulty)}
        </Typography>

        <RadioGroup
          name={`question-${question.id}`}
          value={selectedAnswer?.toString() || ''}
          onChange={(e) => {
            const selectedValue = parseInt(e.target.value, 10);
            onAnswerSelect(question.id, selectedValue);
          }}
        >
          {question.options.map((option: QuizOption) => (
            <FormControlLabel
              key={`${question.id}-${option.id}`}
              value={option.id.toString()}
              control={<Radio />}
              label={option.text}
              disabled={isAnswered}
              sx={{
                mb: 1,
                '&:hover': {
                  backgroundColor: 'action.hover',
                  borderRadius: 1
                },
                textAlign: 'left',
                width: '100%'
              }}
            />
          ))}
        </RadioGroup>

        <Box sx={{ mt: 2, display: 'flex', justifyContent: 'flex-end' }}>
          <Button
            variant="contained"
            onClick={() => onSubmit(question.id)}
            disabled={isAnswered}
            sx={{
              minWidth: 120,
              bgcolor: isAnswered ? 'success.main' : 'primary.main',
              '&:hover': {
                bgcolor: isAnswered ? 'success.dark' : 'primary.dark',
              }
            }}
          >
            {isAnswered ? 'Answered' : 'Submit Answer'}
          </Button>
        </Box>
      </CardContent>
    </Card>
  );
}