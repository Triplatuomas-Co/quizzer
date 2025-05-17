import React from "react";
import { useParams } from "react-router-dom";
import { QuizWithQuestions } from "../types";
import QuizResultsCard from "../components/Results/QuizResultsCard";
import { Container, CircularProgress, Alert, Paper } from '@mui/material';
import { useFetch } from "../hooks/useFetch";

const ResultsPage: React.FC = () => {
  const { id } = useParams();
  const baseUrl = import.meta.env.VITE_API_BASE_URL as string;
  const { data: quizData, loading, error } = useFetch<QuizWithQuestions>(`${baseUrl}/${id}`);

  if (loading) return (
    <Container maxWidth="lg" sx={{ mt: 4, display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '200px' }}>
      <CircularProgress />
    </Container>
  );
  
  if (error) return (
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      <Alert severity="error" sx={{ mb: 2 }}>
        {error}
      </Alert>
    </Container>
  );
  
  if (!quizData) return (
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      <Alert severity="warning" sx={{ mb: 2 }}>
        No quiz data available.
      </Alert>
    </Container>
  );

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper elevation={2} sx={{ p: 3, borderRadius: 2 }}>
        <QuizResultsCard quizName={quizData.title} questions={quizData.questions} />
      </Paper>
    </Container>
  );
};

export default ResultsPage;
