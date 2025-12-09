import { useState, useEffect } from 'react';
import { productService } from '../services/productServices'; 

export const useProducts = (selectedCategory) => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProducts = async () => {
      setLoading(true);
      setError(null);
      
      try {
        const data = await productService.getAll();
        
        if (selectedCategory) {
          const filteredData = data.filter(product => {
             
             if (selectedCategory.category && product.category === selectedCategory.category) return true;

             return product.category?.toLowerCase().includes(selectedCategory.name?.toLowerCase());
          });
          
          setProducts(filteredData);
        } else {
          setProducts(data);
        }

      } catch (err) {
        console.error(err);
        setError("Error al cargar los productos. Intente nuevamente.");
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, [selectedCategory]); 

  return { products, loading, error };
};