import { Quiz } from "../types";
import { useNavigate } from "react-router-dom";

interface QuizCardProps {
  quiz: Quiz;
}

export default function QuizCard({ quiz }: QuizCardProps) {
  const navigate = useNavigate();

  const handleTakeQuiz = () => {
    navigate(`/quiz/${quiz.id}`);
  };

  return (
    <div className="quiz-card">
      <h2>{quiz.title}</h2>
      <p>{quiz.description}</p>
      <p><strong>Category:</strong> {quiz.category.title}</p>
      <button onClick={handleTakeQuiz}>Take Quiz</button>
      <button>See Results</button>
      <button>See reviews</button>
    </div>
  );
}