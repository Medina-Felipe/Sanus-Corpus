import React, { useState, useEffect } from "react";
import CategoriesBar from "../components/Layout/CategoriesBar"; 
import ProductGrid from "../components/Layout/ProductGrid"; 
import { useProducts } from "../hooks/useProducts";

const HomePage = () => {
  const [selectedCategory, setSelectedCategory] = useState(null);
  const { products, loading, error } = useProducts(selectedCategory); 

  useEffect(() => {
    const handleReset = () => setSelectedCategory(null);
    window.addEventListener("resetCategory", handleReset);
    return () => window.removeEventListener("resetCategory", handleReset);
  }, []); 

  return (
    <div className="min-h-screen bg-[#F9FAFB]">
      
      <CategoriesBar 
        selectedCategory={selectedCategory}
        onCategorySelect={setSelectedCategory}
      />
      
      <main className="max-w-7xl mx-auto py-10 px-4 sm:px-6 lg:px-8">
        
        {/* Encabezado */}
        <div className="text-center mb-10">
            <h1 className="text-3xl font-bold text-gray-900 tracking-tight">
              {selectedCategory ? selectedCategory.name : "Nuestra Colección"}
            </h1>
            <div className="h-1 w-20 bg-[#0D99FF] mx-auto mt-4 rounded-full"></div>
        </div>
        
        {error && <div className="text-red-500 text-center">Error: {error}</div>}
        
        <ProductGrid products={products} loading={loading} />
      </main>
    </div>
  );
};

export default HomePage;