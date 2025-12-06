import { useState, useEffect } from 'react';
import { mockProducts } from '../data/products'; 

export const useProducts = (selectedCategory = null) => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);
        
        setTimeout(() => {
          let filteredProducts = mockProducts;
          
          if (selectedCategory) {
            filteredProducts = mockProducts.filter(
              product => product.category === selectedCategory.slug
            );
          }
          
          setProducts(filteredProducts);
          setLoading(false);
        }, 500);
        
      } catch (err) {
        console.error('Error fetching products:', err);
        setProducts(mockProducts); 
        setLoading(false);
      }
    };

    fetchProducts();
  }, [selectedCategory]);

  return { products, loading, error };
};