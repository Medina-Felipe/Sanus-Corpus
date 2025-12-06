import React, { useState } from "react";
import CategoriesBar from "../components/Layout/CategoriesBar"; 
import ProductGrid from "../components/Layout/ProductGrid"; 
import { useProducts } from "../hooks/useProducts";

const HomePage = () => {
  const [selectedCategory, setSelectedCategory] = useState(null);
  
  const { products, loading, error } = useProducts(selectedCategory); 

  return (
    <div className="min-h-screen bg-gray-50">
      <CategoriesBar 
        selectedCategory={selectedCategory}
        onCategorySelect={setSelectedCategory}
      />
      
      <main className="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
        

        <div className="mb-6">
            <h1 className="text-3xl font-bold text-gray-900">
              {selectedCategory ? selectedCategory.name : "Productos Destacados"}
            </h1>
            <p className="text-gray-600 mt-2">
              {selectedCategory ? selectedCategory.description : "Explora nuestra selección más popular y recomendada."}
            </p>
        </div>
        
        {error && <div className="text-red-500">Error: {error}</div>}
        <ProductGrid products={products} loading={loading} />
      </main>
    </div>
  );
};

export default HomePage;