export interface Category {
  category_id: number;
  title: string;
  description: string;
}

export interface Quiz {
  id: number;
  category: Category;
  teacher_id: number;
  difficulty: number;
  title: string;
  description: string;
  isPublished: boolean;
  course?: string;
  addedOn?: string;
  questions?: Question[];
}

export interface Question {
  id: number;
  title: string;
  description: string;
  difficulty: number;
  options: Option[];
  answerCount: number;
  correctAnswerCount: number;
}

export interface Option {
  id: number;
  text: string;
  correct: boolean;
}

export interface QuizWithQuestions extends Quiz {
  questions: Question[];
}

// Updated Review type to match a typical Review DTO structure
export interface Review { // Changed from type alias to interface for consistency
  review_id: number;    // Typically a number ID from the database
  nickname: string;
  rating: number;
  review: string;         // The main text/comment of the review
  created_date: string; // Or Date, depending on how it's serialized
  quiz_id: number;      // ID of the quiz this review belongs to
  // userId?: string;    // Optional: if you track the user who wrote it via a separate user ID
}