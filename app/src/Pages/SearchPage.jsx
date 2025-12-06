import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import ProductGrid from '../components/Layout/ProductGrid';
import { mockProducts } from '../data/products';

const SearchPage = () => {
  const { query } = useParams();
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(true);
  
  const searchTerm = query ? decodeURIComponent(query) : '';

  useEffect(() => {
    setLoading(true);
    const timer = setTimeout(() => {
      if (searchTerm) {
        const filtered = mockProducts.filter(product =>
          product.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
          product.description.toLowerCase().includes(searchTerm.toLowerCase())
        );
        setResults(filtered);
      } else {
        setResults([]);
      }
      setLoading(false);
    }, 400);

    return () => clearTimeout(timer);
  }, [searchTerm]);

  return (
    <div className="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
      <h1 className="text-3xl font-bold text-gray-900 mb-6">
        {searchTerm 
          ? `Resultados para: "${searchTerm}"` 
          : 'Ingresa un término de búsqueda.'
        }
      </h1>
      
      {loading ? (
        <p>Buscando productos...</p>
      ) : results.length > 0 ? (
        <ProductGrid products={results} loading={false} />
      ) : searchTerm && (
        <p className="text-gray-600">No se encontraron productos que coincidan con su búsqueda.</p>
      )}
    </div>
  );
};

export default SearchPage;