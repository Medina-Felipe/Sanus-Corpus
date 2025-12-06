import { useState, useMemo } from 'react';

const initialCartItems = [
  { id: 101, name: "Crema Hidratante Facial", slug: "crema-hidratante", price: 5999, quantity: 2, image: "/src/Images/placeholder-crema.jpg" },
  { id: 102, name: "Suplemento Vitamina C", slug: "vitamina-c", price: 2999, quantity: 1, image: "/src/Images/placeholder-vitaminac.jpg" },
];

export const useCart = () => {
  const [cartItems, setCartItems] = useState(() => initialCartItems);

  const totals = useMemo(() => {
    const subtotal = cartItems.reduce((acc, item) => acc + (item.price * item.quantity), 0);
    
    const taxRate = 0.19;
    const tax = subtotal * taxRate;

    
    const shippingThreshold = 20000; 
    const shippingCost = 3500; 
    
    const shipping = subtotal > shippingThreshold ? 0 : shippingCost;

    // Total final
    const total = subtotal + tax + shipping;

    return {
      subtotal: Math.round(subtotal),
      tax: Math.round(tax),
      shipping: Math.round(shipping),
      total: Math.round(total),
      itemCount: cartItems.reduce((acc, item) => acc + item.quantity, 0),
    };
  }, [cartItems]);

  const updateQuantity = (id, newQuantity) => {
    const qty = parseInt(newQuantity);
    if (qty < 1) return; 

    setCartItems(prevItems => 
      prevItems.map(item => 
        item.id === id ? { ...item, quantity: qty } : item
      )
    );
  };

  const removeItem = (id) => {
    setCartItems(prevItems => prevItems.filter(item => item.id !== id));
  };
  
  const addItem = (product, quantity = 1) => {
    const existingItem = cartItems.find(item => item.id === product.id);
    if (existingItem) {
      updateQuantity(product.id, existingItem.quantity + quantity);
    } else {
      const newItem = {
        ...product,
        quantity: quantity
      };
      setCartItems(prevItems => [...prevItems, newItem]);
    }
  };

  return { cartItems, totals, updateQuantity, removeItem, addItem };
};