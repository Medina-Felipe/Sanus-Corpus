import React from "react";
import { Link } from "react-router-dom";

const ProductCard = ({ product }) => {
  return (
    <div className="bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow p-4">

      <Link to={`/productos/${product.slug}`} className="block">
        <img
          src={product.image}
          alt={product.name}
          className="w-full h-48 object-cover rounded-md mb-4"
        />
      </Link>

      <h3 className="font-semibold text-lg mb-2">{product.name}</h3>
      <p className="text-gray-600 text-sm mb-2">{product.description}</p>

      <div className="flex justify-between items-center">
        <span className="text-2xl font-bold text-[#0D99FF]">
          ${product.price.toLocaleString('es-CL')}
        </span>

        <button className="bg-[#0D99FF] text-white py-1 px-3 rounded-md text-sm hover:bg-[#0A7ACC] transition-colors">
          Comprar
        </button>
      </div>
    </div>
  );
};

export default ProductCard;