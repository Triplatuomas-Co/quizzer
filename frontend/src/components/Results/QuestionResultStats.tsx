import React from 'react';
import { Question } from '../../types';
import { TableRow, TableCell, Typography, Box } from '@mui/material';

interface QuestionResultStatsProps {
  question: Question;
}

const QuestionResultStats: React.FC<QuestionResultStatsProps> = ({ question }) => {
  const correctPercentage = question.answerCount > 0
    ? Math.round((question.correctAnswerCount / question.answerCount) * 100)
    : 0;

  const getDifficultyColor = (difficulty: number) => {
    switch (difficulty) {
      case 1: return '#4caf50'; // Easy - Green    
      case 2: return '#ff9800'; // Medium - Orange
      case 3: return '#f44336'; // Hard - Red
      default: return '#4caf50'; // Default - Gray
    }
  };

  return (
    <TableRow hover>
      <TableCell>
        <Typography variant="body2">{question.title}</Typography>
      </TableCell>
      <TableCell>
        <Box sx={{ 
          display: 'inline-block',
          px: 1,
          py: 0.5,
          borderRadius: 1,
          bgcolor: getDifficultyColor(question.difficulty),
          color: 'white'
        }}>
          {question.difficulty === 1 ? 'Easy' : question.difficulty === 2 ? 'Medium' : 'Hard'}
        </Box>
      </TableCell>
      <TableCell>
        <Typography variant="body2">{question.answerCount}</Typography>
      </TableCell>
      <TableCell>
        <Typography variant="body2">{correctPercentage}%</Typography>
      </TableCell>
      <TableCell>
        <Typography variant="body2" color="success.main">
          {question.correctAnswerCount}
        </Typography>
      </TableCell>
      <TableCell>
        <Typography variant="body2" color="error.main">
          {question.answerCount - question.correctAnswerCount}
        </Typography>
      </TableCell>
    </TableRow>
  );
};

export default QuestionResultStats;
