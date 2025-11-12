// src/Pages/HomePage.jsx
import React, { useState } from "react";
import Header from "../components/Layout/Header"; // ✅ CORREGIDO: ../ en lugar de ./
import CategoriesBar from "../components/Layout/CategoriesBar"; // ✅ CORREGIDO
import ProductGrid from "../components/Layout/ProductGrid"; // ✅ CORREGIDO
import { useProducts } from "../hooks/useProducts"; // ✅ CORREGIDO

const HomePage = () => {
  const [selectedCategory, setSelectedCategory] = useState(null);
  const { products, loading, error } = useProducts(selectedCategory);

  return (
    <div className="min-h-screen bg-gray-50">
      <Header />
      <CategoriesBar 
        selectedCategory={selectedCategory}
        onCategorySelect={setSelectedCategory}
      />
      
      <main className="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
        {selectedCategory && (
          <div className="mb-6">
            <h1 className="text-3xl font-bold text-gray-900">
              {selectedCategory.name}
            </h1>
            <p className="text-gray-600 mt-2">{selectedCategory.description}</p>
          </div>
        )}
        
        <ProductGrid products={products} loading={loading} />
      </main>
    </div>
  );
};

export default HomePage;

// // pages/HomePage.jsx
// import React from "react";
// import Header from "../components/Layout/Header";
// import ProductGrid from "../components/Layout/ProductGrid";
// import { useProducts } from "../hooks/useProducts";

// const HomePage = () => {
//   const { products, loading, error } = useProducts();

//   return (
//     <div className="min-h-screen bg-gray-50">
//       <Header />
//       <main>
//         {error && <div className="text-red-500 text-center p-4">{error}</div>}
//         <ProductGrid products={products} loading={loading} />
//       </main>
//     </div>
//   );
// };

// export default HomePage;