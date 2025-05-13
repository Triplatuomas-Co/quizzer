import React from 'react';
import { Question } from '../../types';
import ResultsList from './ResultsList';
import { Card, CardContent, Typography, Box } from '@mui/material';

interface QuizResultsCardProps {
  quizName: string;
  questions: Question[];
}

const QuizResultsCard: React.FC<QuizResultsCardProps> = ({ quizName, questions }) => {
  const totalAnswers = questions.reduce((sum, question) => sum + question.answerCount, 0);

  return (
    <Card sx={{ 
      maxWidth: 1200, 
      mx: 'auto', 
      bgcolor: 'background.paper',
      boxShadow: 2,
      borderRadius: 2
    }}>
      <CardContent>
        <Typography variant="h4" component="h2" gutterBottom sx={{ color: 'text.primary' }}>
          Results of "{quizName}"
        </Typography>
        <Box sx={{ mb: 3 }}>
          <Typography variant="body1" color="text.secondary">
            {totalAnswers} answers to {questions.length} questions
          </Typography>
        </Box>
        <ResultsList questions={questions} />
      </CardContent>
    </Card>
  );
};

export default QuizResultsCard;
