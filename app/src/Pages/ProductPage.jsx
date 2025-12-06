import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { mockProducts } from '../data/products';
import { useCart } from '../hooks/useCart'; 

export default function ProductPage() {
  const { slug } = useParams();
  const navigate = useNavigate();
  const { product, loading, error } = useProductDetail(slug);
  const { addItem } = useCart();

  useEffect(() => {
    setLoading(true);
    setTimeout(() => {
      const foundProduct = mockProducts.find(p => p.slug === slug);
      setProduct(foundProduct);
      setLoading(false);
    }, 300);
  }, [slug]);

  if (loading) {
    return (
      <div className="max-w-7xl mx-auto py-10 px-4">
        Cargando detalles del producto...
      </div>
    );
  }

  if (!product) {
    return (
      <div className="max-w-7xl mx-auto py-10 px-4">
        <h1 className="text-4xl font-bold text-red-600">Producto No Encontrado</h1>
      </div>
    );
  }

  const handleAddToCart = () => {
    addItem(product, 1);
    alert(`¡${product.name} añadido al carrito!`);
  };

  // Vista de detalles del producto
  return (
    <div className="max-w-7xl mx-auto py-10 px-4 sm:px-6 lg:px-8">
      <div className="bg-white shadow-xl rounded-lg overflow-hidden md:flex">

        {/* Imagen del Producto */}
        <div className="md:w-1/2 p-6 flex items-center justify-center bg-gray-50">
          <img
            src={product.image}
            alt={product.name}
            className="max-h-96 object-contain rounded-lg"
          />
        </div>

        {/* Información del Producto */}
        <div className="md:w-1/2 p-8 space-y-6">
          <h1 className="text-4xl font-extrabold text-gray-900">{product.name}</h1>

          <div className="text-3xl font-bold text-[#FF0D4B]">
            ${product.price}
          </div>

          <p className="text-gray-700 leading-relaxed text-lg">
            {product.description}
          </p>

          <div className="pt-4 border-t border-gray-200">
            <h3 className="text-xl font-semibold mb-2 text-[#0D99FF]">Detalles Adicionales</h3>
            <ul className="list-disc list-inside text-gray-600 space-y-1">
              <li>**Categoría:** {product.category}</li>
              <li>**ID:** {product.id}</li>
              <li>**Stock:** Disponible</li>
            </ul>
          </div>

          <button
            onClick={handleAddToCart}
            className="w-full sm:w-auto px-8 py-3 bg-[#0D99FF] text-white font-semibold rounded-full shadow-lg hover:bg-[#0A7ACC] transition-colors"
          >
            Añadir al Carrito
          </button>
        </div>
      </div>
    </div>
  );
}