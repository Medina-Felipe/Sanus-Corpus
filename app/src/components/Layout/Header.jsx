import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useCart } from '../../hooks/useCart';
import { useAuth } from '../../context/AuthContext';
import ProfileMenu from './ProfileMenu';

import logo from "../../Images/Logo.png";
import icon1 from "../../Images/IconoCarrito.png";
import icon2 from "../../Images/IconoPersona.png";
import icon3 from "../../Images/IconoTresLineas.png";

const Header = () => {
  const navigate = useNavigate();
  const [searchQuery, setSearchQuery] = useState('');
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const { totals } = useCart();
  const { user } = useAuth();

  const handleLogoClick = () => {
    navigate("/");
    window.dispatchEvent(new Event("resetCategory"));
  };

  const handleSearch = (e) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      navigate(`/search/${encodeURIComponent(searchQuery.trim())}`);
    }
  };

  const handleProfileClick = () => {
    if (user) setIsMenuOpen(true);
    else navigate('/login');
  };

  return (
    <>
      <header className="bg-[#eff6ff] border-b border-blue-100/50 sticky top-0 z-50 shadow-sm">
        <div className="max-w-7xl mx-auto px-4 py-3">

          {/* CONTENEDOR PRINCIPAL */}
          <div className="flex items-center justify-between">

            {/* 1. LOGO */}
            <div
              className="flex-shrink-0 cursor-pointer hover:opacity-80 transition-opacity mr-2"
              onClick={handleLogoClick}
            >              <img
                src={logo}
                alt="Sanus Corpus"
                className="h-8 md:h-12 w-auto object-contain mix-blend-multiply"
              />
            </div>

            {/* 2. BUSCADOR */}
            <form
              onSubmit={handleSearch}
              className="hidden md:flex flex-1 max-w-xl mx-8 relative shadow-sm rounded-lg"
            >
              <input
                type="text"
                placeholder="Busca productos, marcas y más..."
                className="w-full pl-4 pr-12 py-2.5 rounded-lg border border-gray-200 focus:border-[#0D99FF] focus:ring-2 focus:ring-[#0D99FF]/20 outline-none text-gray-700 bg-white placeholder-gray-400"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />

            </form>

            {/* 3. ÍCONOS  */}
            <div className="flex items-center gap-2 md:gap-4 flex-shrink-0">

              {/* Carrito */}
              <button
                className="relative p-2 rounded-full hover:bg-blue-100/50 transition-colors"
                onClick={() => navigate('/carrito')}
              >
                {/* Icono más pequeño en celular*/}
                <img src={icon1} alt="Carrito" className="h-6 w-6 md:h-8 md:w-8 object-contain opacity-80" />
                {totals.itemCount > 0 && (
                  <span className="absolute -top-1 -right-1 bg-[#0D99FF] text-white text-[10px] font-bold h-4 w-4 md:h-5 md:w-5 flex items-center justify-center rounded-full border-2 border-white">
                    {totals.itemCount}
                  </span>
                )}
              </button>

              {/* Perfil */}
              <button
                className="p-2 rounded-full hover:bg-blue-100/50 transition-colors"
                onClick={handleProfileClick}
              >
                {user && user.avatar ? (
                  <img src={user.avatar} alt="Avatar" className="h-6 w-6 md:h-8 md:w-8 rounded-full object-cover border border-blue-200" />
                ) : (
                  <img src={icon2} alt="Perfil" className="h-6 w-6 md:h-8 md:w-8 object-contain opacity-80" />
                )}
              </button>
            </div>
          </div>

          {/* --- FILA 2: BUSCADOR MÓVIL --- */}
          <div className="mt-3 md:hidden">
            <form onSubmit={handleSearch} className="relative w-full">
              <input
                type="text"
                placeholder="¿Qué buscas hoy?"
                className="w-full pl-4 pr-10 py-2 rounded-lg border border-gray-200 focus:border-[#0D99FF] focus:ring-1 focus:ring-[#0D99FF] outline-none text-sm bg-white shadow-sm"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
              />
              <button type="submit" className="absolute right-0 top-0 bottom-0 px-3 flex items-center justify-center text-[#0D99FF]">
                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
              </button>
            </form>
          </div>

        </div>
      </header>

      <ProfileMenu isOpen={isMenuOpen} onClose={() => setIsMenuOpen(false)} />
    </>
  );
};

export default Header;