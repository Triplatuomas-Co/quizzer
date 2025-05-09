import { AgGridReact } from "ag-grid-react";
import { ColDef } from "ag-grid-community";
import { Quiz } from "../types";

interface QuizTableProps {
  quizzes: Quiz[];
}

export default function QuizTable({ quizzes }: QuizTableProps) {
  const columnDefs: ColDef<Quiz>[] = [
    { field: "title", filter: true },
    { field: "description", filter: true },
    { field: "course", filter: true },
    { field: "category", filter: true, valueGetter: (params) => params.data?.category.title ?? "Unknown" },
    { field: "addedOn", filter: true },
    { headerName: "Actions" },
  ];

  return (
    <div className="ag-theme-alpine" style={{ margin: "auto", height: 600, width: "80%" }}>
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