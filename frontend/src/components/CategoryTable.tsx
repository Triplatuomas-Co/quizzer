import { AgGridReact } from "ag-grid-react";
import { ColDef, ICellRendererParams } from "ag-grid-community";
import { Category } from "../types";
import { Link } from "react-router-dom";

interface CategoryTableProps {
  categories: Category[];
}

export default function CategoryTable({ categories }: CategoryTableProps) {
  const columnDefs: ColDef<Category>[] = [
    { field: "title", filter: true, flex: 1 },
    { field: "description", filter: true, flex: 5 },
    {
      headerName: "View Quizzes by Category",
      field: "viewQuizzes",
      cellRenderer: (params: ICellRendererParams) => {
        const category = params.data.title;
        return <Link to={`/categories/${category}/quizzes`}>View Quizzes</Link>;
      },
    },
  ];

  return (
    <div
      className="ag-theme-alpine"
      style={{ margin: "auto", height: 600, width: "80%" }}
    >
      <AgGridReact
        rowData={categories}
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
