import { Category } from "../types";
import { useNavigateToQuizzes } from "../hooks/useNavigateToQuizzes";
import CategoryCard from "./CategoryCard";

interface CategoryTableProps {
  categories: Category[];
}

export default function CategoryList({ categories }: CategoryTableProps) {
  const { onNavigateToQuizzes } = useNavigateToQuizzes();

  return (
    <div className="quiz-card-container">
      {categories.map((category) => (
        <CategoryCard
          key={category.title}
          category={category}
          onClick={onNavigateToQuizzes}
        />
      ))}
    </div>
  );
}