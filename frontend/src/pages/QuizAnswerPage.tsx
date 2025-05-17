import { useParams } from "react-router-dom";
import { useFetch } from "../hooks/useFetch";
import { QuizWithQuestions } from "../types";
import QuestionCard from "../components/QuestionCard";
import { Container, CircularProgress, Alert, Paper } from '@mui/material';

export default function QuizAnswerPage() {
  const { id } = useParams();
  const baseUrl = import.meta.env.VITE_API_BASE_URL as string;
  const { data: quiz, loading, error } = useFetch<QuizWithQuestions>(
    `${baseUrl}/${id}`
  );

  if (loading) return (
    <Container maxWidth="lg" sx={{ mt: 4, display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '200px' }}>
      <CircularProgress />
    </Container>
  );

  if (error) return (
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      <Alert severity="error" sx={{ mb: 2 }}>
        Error: {error}
      </Alert>
    </Container>
  );

  if (!quiz) return (
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      <Alert severity="warning" sx={{ mb: 2 }}>
        Quiz not found
      </Alert>
    </Container>
  );

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper elevation={2} sx={{ p: 3, borderRadius: 2 }}>
        <QuestionCard quiz={quiz} />
      </Paper>
    </Container>
  );
} 