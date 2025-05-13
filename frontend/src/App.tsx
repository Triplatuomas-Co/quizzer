import { Routes, Route, Link } from "react-router-dom";
import "./App.css";
import "ag-grid-community/styles/ag-grid.css";
import "ag-grid-community/styles/ag-theme-alpine.css";
import CategoriesPage from "./pages/CategoriesPage";
import QuizzesPage from "./pages/QuizzesPage";
import QuizAnswerPage from "./pages/QuizAnswerPage";
import ReviewPage from "./pages/ReviewPage";
import ReviewListPage from "./pages/ReviewListPage";
import EditReviewPage from "./pages/EditReviewPage";
import ResultsPage from "./pages/ResultsPage";

export default function App() {
  return (
    <div className="app-container">
      <nav className="navbar">
        <Link to="/">Quizzes</Link>
        <Link to="/categories">Categories</Link>
      </nav>

      <Routes>
        <Route path="/" element={<QuizzesPage />} />
        <Route path="/categories" element={<CategoriesPage />} />
        <Route path="/quizzes/:category" element={<QuizzesPage />} />
        <Route path="/quiz/:id" element={<QuizAnswerPage />} />
        <Route path="/quiz/:id/review" element={<ReviewPage />} />
        <Route path="/quiz/:id/reviews" element={<ReviewListPage />} />
        <Route path="/editreview/:id" element={<EditReviewPage />} />
        <Route path="/quiz/:id/results" element={<ResultsPage />} />
      </Routes>
    </div>
  );
}
