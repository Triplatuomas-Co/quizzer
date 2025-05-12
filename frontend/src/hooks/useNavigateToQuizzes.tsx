import { useNavigate } from "react-router-dom";

export function useNavigateToQuizzes() {
  const navigate = useNavigate();

  const onNavigateToQuizzes = (categoryTitle: string) => {
    navigate(`/quizzes/${categoryTitle}`); 
  };

  return { onNavigateToQuizzes };
}