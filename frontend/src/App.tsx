import { Routes, Route, Link } from "react-router-dom";
import "./App.css";
import 'ag-grid-community/styles/ag-grid.css';
import "ag-grid-community/styles/ag-theme-alpine.css";
import CategoriesPage from "./pages/CategoriesPage";
import QuizzesPage from "./pages/QuizzesPage";


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
        <Route path="/categories/:categoryTitle/quizzes" element={<QuizzesPage />} />
      </Routes>
    </div>
  );
}


