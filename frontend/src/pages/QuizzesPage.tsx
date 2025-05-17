import QuizList from "../components/QuizList";
import { Quiz } from "../types";
import { useFetch } from "../hooks/useFetch";
import { AllCommunityModule, ModuleRegistry } from "ag-grid-community";
import { useParams } from "react-router-dom";

ModuleRegistry.registerModules([AllCommunityModule]);

export default function QuizzesPage() {
  const { category } = useParams<{ category: string }>(); // Get the category from the URL parameters

  const baseUrl = import.meta.env.VITE_API_BASE_URL as string;
  let endpoint = `${baseUrl}/published-list`;
  if (category) {
    endpoint = `${baseUrl}/published-list/${category}`;
  }
  const { data: quizzes, loading, error } = useFetch<Quiz[]>(endpoint);
  
  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="app-container">
      <h1>{category ? `Quizzes in ${category}` : "Quizzes"}</h1>
      <QuizList quizzes={quizzes ?? []} />
    </div>
  );
}