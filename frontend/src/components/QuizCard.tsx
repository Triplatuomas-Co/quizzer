import { Quiz } from "../types";
import { Link, useNavigate } from "react-router-dom";

interface QuizCardProps {
  quiz: Quiz;
}

export default function QuizCard({ quiz }: QuizCardProps) {
  const navigate = useNavigate();

  const handleTakeQuiz = () => {
    navigate(`/quiz/${quiz.id}`);
  };

  const handleSeeResults = () => {
    console.log("Quiz ID:", quiz.id); // Debug log
    navigate(`/quiz/${quiz.id}/results`);
  };

  return (
    <div className="quiz-card">
      <h2>{quiz.title}</h2>
      <p>{quiz.description}</p>
      <p>
        <strong>Category:</strong> {quiz.category.title}
      </p>
      <button onClick={handleTakeQuiz}>Take Quiz</button>
      <button onClick={handleSeeResults}>See Results</button>
      <Link to={`/quiz/${quiz.id}/reviews`}>
        <button>See reviews</button>
      </Link>
    </div>
  );
}
