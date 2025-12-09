import { mockProducts } from '../data/products';


export const productService = {
  
  getAll: async () => {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve([...mockProducts]);
      }, 300);
    });
  },

  getById: async (id) => {
    return new Promise((resolve) => {
      setTimeout(() => {
        const product = mockProducts.find(p => p.id === parseInt(id));
        resolve(product);
      }, 300);
    });
  }
};