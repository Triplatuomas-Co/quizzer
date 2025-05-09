import QuizTable from "../components/QuizTable";
import { Quiz } from "../types";
import { useFetch } from "../hooks/useFetch";
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community'; 


ModuleRegistry.registerModules([AllCommunityModule]);

export default function QuizzesPage() {
  const { data: quizzes, loading, error } = useFetch<Quiz>("http://localhost:8080/api/quiz/published-list");

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h1>Quizzes</h1>
      <QuizTable quizzes={quizzes} />
    </div>
  );
}