import React from 'react';
import ProductGrid from '../components/Layout/ProductGrid';
import { useWishlist } from '../context/WishlistContext';
import { Link } from 'react-router-dom';

const WishlistPage = () => {
  const { wishlist } = useWishlist();

  if (wishlist.length === 0) {
    return (
      <div className="min-h-screen bg-[#F9FAFB] flex items-center justify-center px-4">
         <div className="text-center">
            <div className="text-6xl mb-4 animate-pulse">❤️</div>
            <h2 className="text-2xl font-bold text-gray-800 mb-2">Tu lista de deseos está vacía</h2>
            <p className="text-gray-500 mb-6">Guarda lo que te gusta para no perderlo de vista.</p>
            <Link to="/" className="inline-block px-8 py-3 bg-white border border-gray-200 text-gray-700 font-bold rounded-full hover:bg-gray-50 transition-colors shadow-sm">
              Explorar productos
            </Link>
         </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#F9FAFB] py-10 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto">
        <div className="flex items-center gap-3 mb-8">
            <span className="text-3xl">❤️</span>
            <h1 className="text-3xl font-bold text-gray-900">Mis Favoritos</h1>
        </div>
        
        <ProductGrid products={wishlist} />
      </div>
    </div>
  );
};

export default WishlistPage;