import { Quiz } from "../types";
import { Link, useNavigate } from "react-router-dom";
import { useQuizReviews } from "../hooks/useQuizReviews"; // Import the hook
import { Typography } from "@mui/material"; // Assuming you might want to use MUI for consistency

interface QuizCardProps {
  quiz: Quiz;
}

export default function QuizCard({ quiz }: QuizCardProps) {
  const navigate = useNavigate();
  const { reviewCount, loading: reviewsLoading } = useQuizReviews(quiz.id.toString());

  const handleTakeQuiz = () => {
    navigate(`/quiz/${quiz.id}`);
  };

  const handleSeeResults = () => {
    console.log("Quiz ID:", quiz.id); // Debug log
    navigate(`/quiz/${quiz.id}/results`);
  };

  return (
    <div className="quiz-card" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '1rem', padding: '1rem', border: '1px solid #ccc' }}>
      <div style={{ flexGrow: 1 }}>
        <h2>{quiz.title}</h2>
        <p>{quiz.description}</p>
        <p>
          <strong>Category:</strong> {quiz.category.title}
        </p>
        {/* Displaying quiz.difficulty if it exists on your Quiz type */}
        {quiz.difficulty && (
          <p>
            <strong>Difficulty:</strong> {quiz.difficulty} 
          </p>
        )}
        <button onClick={handleTakeQuiz}>Take Quiz</button>
        <button onClick={handleSeeResults} style={{ marginLeft: '8px' }}>See Results</button>
        <Link to={`/quiz/${quiz.id}/reviews`} style={{ marginLeft: '8px' }}>
          <button>See reviews</button>
        </Link>
      </div>
      <div style={{ textAlign: 'right', minWidth: '100px', marginLeft: '1rem' }}>
        {reviewsLoading ? (
          <Typography variant="caption">Loading reviews...</Typography>
        ) : (
          <Typography variant="body2">
            Reviews: {reviewCount}
          </Typography>
        )}
      </div>
    </div>
  );
}
