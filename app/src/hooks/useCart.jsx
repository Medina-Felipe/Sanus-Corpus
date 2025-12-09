import React, { createContext, useState, useContext, useMemo } from 'react';

const CartContext = createContext();

const initialCartItems = [
  { id: 101, name: "Crema Hidratante Facial", slug: "crema-hidratante", price: 5999, quantity: 2, image: "/src/Images/placeholder-crema.jpg" },
  { id: 102, name: "Suplemento Vitamina C", slug: "vitamina-c", price: 2999, quantity: 1, image: "/src/Images/placeholder-vitaminac.jpg" },
];

export const CartProvider = ({ children }) => {
  const [cartItems, setCartItems] = useState(() => initialCartItems);

  // Cálculos de totales
  const totals = useMemo(() => {
    const subtotal = cartItems.reduce((acc, item) => acc + (item.price * item.quantity), 0);
    const taxRate = 0.19;
    const tax = subtotal * taxRate;
    const shippingThreshold = 20000; 
    const shippingCost = 3500; 
    const shipping = subtotal > shippingThreshold ? 0 : shippingCost;
    const total = subtotal + tax + shipping;

    return {
      subtotal: Math.round(subtotal),
      tax: Math.round(tax),
      shipping: Math.round(shipping),
      total: Math.round(total),
      itemCount: cartItems.reduce((acc, item) => acc + item.quantity, 0),
    };
  }, [cartItems]);

  // Funciones
  const updateQuantity = (id, newQuantity) => {
    const qty = parseInt(newQuantity);
    if (qty < 1) return; 
    setCartItems(prevItems => 
      prevItems.map(item => item.id === id ? { ...item, quantity: qty } : item)
    );
  };

  const removeItem = (id) => {
    setCartItems(prevItems => prevItems.filter(item => item.id !== id));
  };
  
  // addToCart
  const addToCart = (product, quantity = 1) => {
    setCartItems(prevItems => {
      const existingItem = prevItems.find(item => item.id === product.id);
      if (existingItem) {
        // Si ya existe, sumamos la cantidad
        return prevItems.map(item => 
            item.id === product.id 
            ? { ...item, quantity: item.quantity + quantity } 
            : item
        );
      } else {
        // Si no existe, lo agregamos
        return [...prevItems, { ...product, quantity }];
      }
    });
  };

  // 3. Entregamos todo a la app
  return (
    <CartContext.Provider value={{ cartItems, totals, updateQuantity, removeItem, addToCart }}>
      {children}
    </CartContext.Provider>
  );
};

// 4. Hook para usar el carrito en cualquier parte
export const useCart = () => {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error("useCart debe ser usado dentro de un CartProvider");
  }
  return context;
};