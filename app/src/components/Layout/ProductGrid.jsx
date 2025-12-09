import React from "react";
import ProductCard from "./ProductCard";

const ProductGrid = ({ products, loading }) => {
  if (loading) return (
    <div className="flex justify-center py-20">
       <div className="animate-spin rounded-full h-10 w-10 border-t-2 border-b-2 border-[#0D99FF]"></div>
    </div>
  );
  
  if (!products || products.length === 0) return (
     <div className="text-center py-20 opacity-50">
        <p>No hay productos disponibles.</p>
     </div>
  );

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-8">
      {products.map(product => (
        <ProductCard key={product.id} product={product} />
      ))}
    </div>
  );
};

export default ProductGrid;