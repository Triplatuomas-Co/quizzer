import { Routes, Route, Link } from "react-router-dom";
import "./App.css";
import QuizList from "./components/QuizList";
import QuizCategories from "./components/QuizCategories";
import 'ag-grid-community/styles/ag-grid.css';
import "ag-grid-community/styles/ag-theme-alpine.css";

function App() {
  return (
    <div className="app-container">
      <nav className="navbar">
        <Link to="/">Quizzes</Link>
        <Link to="/categories">Categories</Link>
      </nav>

      <Routes>
        <Route
          path="/"
          element={
            <>
              <h1>Quizzes</h1>
              <QuizList />
            </>
          }
        />
        <Route
          path="/categories"
          element={
            <>
              <h1>Quiz Categories</h1>
              <QuizCategories />
            </>
          }
        />
      </Routes>
    </div>
  );
}

export default App;
