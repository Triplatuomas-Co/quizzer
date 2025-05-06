import React, { useEffect, useState } from "react";

type Category = {
  title: string;
  description: string;
};

const QuizCategories: React.FC = () => {
  const [categories, setCategories] = useState<Category[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetch("http://localhost:8080/api/quiz/categories")
      .then((response) => response.json())
      .then((data) => {
        const allCategories = data.map((quiz: { category: string }) => ({
          title: quiz.category,
        }));
        setCategories(allCategories);
      })
      .catch(() => setError("Failed to fetch categories"));
  }, []);

  if (error) return <div>Error: {error}</div>;

  return (
    <div>
      <h2>Quiz Categories</h2>
      <ul>
        {categories.map((category, index) => (
          <li key={index}>{category.title}</li>
        ))}
      </ul>
    </div>
  );
};

export default QuizCategories;
