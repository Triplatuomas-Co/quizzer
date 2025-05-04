import { Routes, Route, Link } from "react-router-dom";
import "./App.css";
import QuizList from "./components/QuizList";

function App() {
  return (
    <div className="app-container">
      <nav className="navbar">
        <Link to="/">Quizzes</Link>
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
      </Routes>
    </div>
  );
}

export default App;
