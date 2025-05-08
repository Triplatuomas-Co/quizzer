import React, { useEffect, useState } from "react";
import { AllCommunityModule, ColDef, ModuleRegistry } from 'ag-grid-community'; 
import { AgGridReact } from 'ag-grid-react'; 
import { Quiz } from "../types";



ModuleRegistry.registerModules([AllCommunityModule]);

export default function QuizList() {
  const [quizzes, setQuizzes] = useState<Quiz[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [columnDefs] = useState<ColDef<Quiz>[]>([
    { field: "title", filter: true},
    { field: "description", filter: true },
    { field: "course", filter: true, },
    { field: "category", filter: true, valueGetter: (params) => params.data?.category.title ?? "Unknown"  },
    { field: "addedOn", filter: true},
    { headerName: "Actions"},
]);

  useEffect(() => {
    // Fetch quizzes from the backend API
    fetch("http://localhost:8080/api/quiz/published-list")
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
    <div className="ag-theme-alpine" style={{ margin: "auto", height: 600, width: '80%' }}>
      <h2>Quizzes</h2>
      <AgGridReact 
        rowData={quizzes}
        columnDefs={columnDefs}
        defaultColDef={{
          resizable: true,
          flex: 1,
          minWidth: 100,
        }}
        rowHeight={35}
        headerHeight={50}
        />
    </div>
  );
}