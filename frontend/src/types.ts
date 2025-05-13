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