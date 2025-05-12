import { Quiz } from "../types";

interface QuizCardProps {
  quiz: Quiz;
}

export default function QuizCard({ quiz }: QuizCardProps) {
  return (
    <div className="quiz-card">
      <h2>{quiz.title}</h2>
      <p>{quiz.description}</p>
      <p><strong>Category:</strong> {quiz.category.title}</p>
      <button> Take Quiz</button>
      <button> See Results</button>
      <button> See reviews</button>
    </div>
  );
}
