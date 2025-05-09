import { AgGridReact } from "ag-grid-react";
import { ColDef } from "ag-grid-community";
import { Category } from "../types";

interface CategoryTableProps {
  categories: Category[];
}

export default function CategoryTable({ categories }: CategoryTableProps) {
  const columnDefs: ColDef<Category>[] = [
    { field: "title", filter: true, flex: 1 },
    { field: "description", filter: true, flex: 5 },
  ];

  return (
    <div className="ag-theme-alpine" style={{ margin: "auto", height: 600, width: "80%" }}>
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