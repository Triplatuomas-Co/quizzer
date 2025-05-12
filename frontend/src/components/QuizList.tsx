import { Quiz } from "../types";
import QuizCard from "./QuizCard";

interface QuizTableProps {
  quizzes: Quiz[];
}

export default function QuizList({ quizzes }: QuizTableProps) {
  return (
    <div className="quiz-card-container">
      {quizzes.map((quiz) => (
        <QuizCard 
        key={quiz.id} 
        quiz={quiz} 
        />
      ))}
    </div>
  );
}