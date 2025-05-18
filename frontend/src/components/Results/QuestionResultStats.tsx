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
      case 2: return '#ffeb3b'; // Medium - Yellow/Amber (Material UI yellow[600] or amber[500])
      case 3: return '#ff9800'; // Normal - Orange
      case 4: return '#f44336'; // Hard - Red
      case 5: return '#b71c1c'; // Very Hard - Dark Red (Material UI red[900])
      default: return '#9e9e9e'; // Default - Gray (for unexpected values)
    }
  };

  const getDifficultyText = (difficulty: number): string => {
    switch (difficulty) {
      case 1: return 'Easy';
      case 2: return 'Medium';
      case 3: return 'Normal';
      case 4: return 'Hard';
      case 5: return 'Very Hard';
      default: return 'Unknown';
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
          color: question.difficulty === 2 ? 'black' : 'white' // Yellow might need black text
        }}>
          {getDifficultyText(question.difficulty)}
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
