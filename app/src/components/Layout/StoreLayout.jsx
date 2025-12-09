import React from 'react';
import { Outlet } from 'react-router-dom';
import Header from './Header';
import Footer from './Footer';

const StoreLayout = () => {
  return (
    <div className="flex flex-col min-h-screen">
      {/* El Header y Footer SOLO viven aquí */}
      <Header />
      
      <main className="flex-grow">
        {/* <Outlet /> representa la página que estemos viendo (Home, Producto, Carrito) */}
        <Outlet />
      </main>

      <Footer />
    </div>
  );
};

export default StoreLayout;