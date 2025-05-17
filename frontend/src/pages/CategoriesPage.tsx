import CategoryList from "../components/CategoryList";
import { useFetch } from "../hooks/useFetch";
import { Category } from "../types";
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community'; 

ModuleRegistry.registerModules([AllCommunityModule]);

export default function CategoriesPage() {
  const baseUrl = import.meta.env.VITE_API_BASE_URL as string;
  const { data: categories, loading, error } = useFetch<Category[]>(`${baseUrl}/categories`);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    
    <div className="app-container">
      <h1>Categories</h1>
      <CategoryList categories={categories ?? []} />
    </div>
  );
}