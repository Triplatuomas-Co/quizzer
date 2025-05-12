import { Category } from "../types";

interface CategoryCardProps {
  category: Category;
  onClick?: (categoryTitle: string) => void;
}

export default function CategoryCard({ category, onClick }: CategoryCardProps) {
  return (
    <div className="quiz-card">
      <h2>{category.title}</h2>
      <p>{category.description}</p>
      {onClick && (
        <button onClick={() => onClick(category.title)}>
          View Quizzes From This Category
        </button>
      )}
    </div>
  );
}
