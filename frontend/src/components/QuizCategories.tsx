import React, { useEffect, useState } from "react";
import { AllCommunityModule, ColDef, ModuleRegistry } from 'ag-grid-community'; 
import { AgGridReact } from 'ag-grid-react'; 
import { Category } from "../types";


export default function QuizCategories() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const [columnDefs] = useState<ColDef<Category>[]>([
    { field: "title", filter: true, flex: 1 },
    { field: "description", filter: true, flex: 5 },
  ]);

  useEffect(() => {
    fetch("http://localhost:8080/api/quiz/categories")
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to fetch quizzes");
      }
      return response.json();
    })
      .then((data) => {
        setCategories(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
      
  }, []);

  if (error) return <div>Error: {error}</div>;
  if (loading) return <div>Loading...</div>;

  return (
    <div className="ag-theme-alpine" style={{ margin: "auto", height: 600, width: '80%' }}>
      <h1>Categories</h1>
      <AgGridReact
        rowData={categories}
        columnDefs={columnDefs}
        rowHeight={35}
        headerHeight={50}
      />
    </div>
  );
}
