import React from 'react';
import { Question } from '../../types';
import QuestionResultStats from './QuestionResultStats';
import { 
  Table, 
  TableBody, 
  TableCell, 
  TableContainer, 
  TableHead, 
  TableRow, 
  Paper,
  Typography
} from '@mui/material';

interface ResultsListProps {
  questions: Question[];
}

const ResultsList: React.FC<ResultsListProps> = ({ questions }) => {
  return (
    <TableContainer component={Paper} sx={{ boxShadow: 1 }}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>
              <Typography variant="subtitle1" fontWeight="bold">Question</Typography>
            </TableCell>
            <TableCell>
              <Typography variant="subtitle1" fontWeight="bold">Difficulty</Typography>
            </TableCell>
            <TableCell>
              <Typography variant="subtitle1" fontWeight="bold">Total Answers</Typography>
            </TableCell>
            <TableCell>
              <Typography variant="subtitle1" fontWeight="bold">Correct answers %</Typography>
            </TableCell>
            <TableCell>
              <Typography variant="subtitle1" fontWeight="bold">Correct</Typography>
            </TableCell>
            <TableCell>
              <Typography variant="subtitle1" fontWeight="bold">Wrong</Typography>
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {questions.map((question) => (
            <QuestionResultStats key={question.id} question={question} />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default ResultsList;
