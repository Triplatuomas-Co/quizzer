import { useParams } from "react-router-dom";
import { useFetch } from "../hooks/useFetch";
import { QuizWithQuestions } from "../types";
import QuestionCard from "../components/QuestionCard";

export default function QuizAnswerPage() {
  const { id } = useParams();
  const { data: quiz, loading, error } = useFetch<QuizWithQuestions>(`http://localhost:8080/api/quiz/${id}`);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!quiz) return <div>Quiz not found</div>;

  return (
    <div className="app-container">
      <h1>{quiz.title}</h1>
      <p>{quiz.description}</p>
      <QuestionCard quiz={quiz} />
    </div>
  );
} 