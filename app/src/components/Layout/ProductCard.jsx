import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useWishlist } from "../../context/WishlistContext";
import { useAuth } from "../../context/AuthContext";
import LoginModal from "./LoginModal";

const ProductCard = ({ product }) => {
  const { toggleWishlist, isInWishlist } = useWishlist();
  const { user } = useAuth();
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);

  const isLiked = isInWishlist(product.id);

  const handleHeartClick = (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (!user) { setShowModal(true); return; }
    toggleWishlist(product);
  };

  const handleConfirmLogin = () => {
    setShowModal(false);
    navigate('/login');
  };

  return (
    <>
      <LoginModal isOpen={showModal} onClose={() => setShowModal(false)} onConfirm={handleConfirmLogin} />
      <div className="group relative bg-white rounded-2xl transition-all duration-300 hover:shadow-[0_20px_40px_-15px_rgba(0,0,0,0.1)] hover:-translate-y-1 border border-gray-100 flex flex-col h-full overflow-hidden">

        {/* Botón Favorito */}
        <button
          onClick={handleHeartClick}
          className="absolute top-4 right-4 z-20 p-2 rounded-full bg-white/80 backdrop-blur-sm shadow-sm hover:bg-white transition-all hover:text-red-500 text-gray-400"
        >
          {isLiked ? '❤️' : '🤍'}
        </button>

        <Link to={`/productos/${product.id}`} className="flex flex-col flex-grow">
          <div className="bg-[#F3F4F6] h-60 w-full flex items-center justify-center p-6 relative overflow-hidden">
            <div className="absolute bg-white/50 w-40 h-40 rounded-full blur-2xl opacity-0 group-hover:opacity-100 transition-opacity duration-500"></div>

            <img
              src={product.image}
              alt={product.name}
              className="max-h-full max-w-full object-contain relative z-10 mix-blend-multiply group-hover:scale-110 transition-transform duration-500"
            />
          </div>

          {/* 2. CONTENIDO */}
          <div className="p-5 flex flex-col flex-grow">

            {/* Categoría */}
            <p className="text-[10px] font-bold tracking-widest text-[#0D99FF] uppercase mb-2">
              {product.category || 'Salud'}
            </p>

            {/* Título */}
            <h3 className="font-bold text-gray-800 text-lg leading-tight mb-2 line-clamp-2">
              {product.name}
            </h3>

            {/* Descripción corta */}
            <p className="text-sm text-gray-500 line-clamp-2 mb-4">
              {product.description}
            </p>

            {/* Footer: Precio y Acción */}
            <div className="mt-auto flex items-center justify-between pt-4 border-t border-gray-50">
              <div>
                <span className="block text-xs text-gray-400 font-medium">Precio</span>
                <span className="text-xl font-bold text-gray-900">
                  ${product.price.toLocaleString('es-CL')}
                </span>
              </div>

              {/* Botón Circular Azul */}
              <button className="bg-[#0D99FF] h-10 w-10 rounded-full flex items-center justify-center text-white shadow-lg shadow-blue-200 group-hover:bg-[#007ACC] group-hover:scale-110 transition-all duration-300">
                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                  <path fillRule="evenodd" d="M10 3a1 1 0 011 1v5h5a1 1 0 110 2h-5v5a1 1 0 11-2 0v-5H4a1 1 0 110-2h5V4a1 1 0 011-1z" clipRule="evenodd" />
                </svg>
              </button>
            </div>
          </div>
        </Link>
      </div>
    </>
  );
};

export default ProductCard;