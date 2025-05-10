import QuizTable from "../components/QuizTable";
import { Quiz } from "../types";
import { useFetch } from "../hooks/useFetch";
import { AllCommunityModule, ModuleRegistry } from "ag-grid-community";
import { useParams } from "react-router-dom";

ModuleRegistry.registerModules([AllCommunityModule]);

export default function QuizzesPage() {
  const { category } = useParams(); // Get the category from the URL parameters

  let endpoint = "http://localhost:8080/api/quiz/published-list"; 

  if (category) {
    endpoint = "http://localhost:8080/api/quiz/published-list/${category}"; // If a category is provided, use it to filter quizzes
  }

  const { data: quizzes, loading, error } = useFetch<Quiz>(endpoint);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h1>Quizzes</h1>
      <QuizTable quizzes={quizzes} />
    </div>
  );
}
