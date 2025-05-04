import React, { useEffect, useState } from "react";

interface Quiz {
  id: number;
  title: string;
  description: string;
}

const QuizList: React.FC = () => {
  const [quizzes, setQuizzes] = useState<Quiz[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    // Fetch quizzes from the backend API
    fetch('http://localhost:8080/api/quiz/list')
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to fetch quizzes");
        }
        return response.json();
      })
      .then((data) => {
        setQuizzes(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h2>Quiz List</h2>
      <ul>
        {quizzes.map((quiz) => (
          <li key={quiz.id}>
            <strong>{quiz.title}</strong>: {quiz.description}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default QuizList;
