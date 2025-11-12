// src/components/Layout/Header.jsx
import React from "react";
import { useNavigate } from "react-router-dom";
import logo from "../../Images/Logo.png";
import icon1 from "../../Images/IconoCarrito.png";
import icon2 from "../../Images/IconoPersona.png";
import icon3 from "../../Images/IconoTresLineas.png";

const Header = () => {
  const navigate = useNavigate();

  const handleLogoClick = () => {
    navigate("/"); // ✅ Ahora funcionará
  };

  return (
    <header className="bg-gradient-to-r from-[#E8F7FF] to-[#D1F0FF] w-full p-4 flex items-center justify-between shadow-sm">
      {/* Logo clickeable */}
      <div 
        className="flex items-center cursor-pointer hover:opacity-80 transition-opacity"
        onClick={handleLogoClick}
      >
        <img src={logo} alt="Logo" className="h-16 w-auto" />
      </div>

      {/* Resto del código igual */}
      <div className="flex-1 mx-6 relative max-w-2xl">
        <input
          type="text"
          placeholder="Buscar productos..."
          className="w-full p-3 pl-12 rounded-full border-2 border-[#0D99FF]/30 focus:outline-none focus:border-[#0D99FF] focus:ring-4 focus:ring-[#0D99FF]/20 bg-white/80 text-gray-700 placeholder-gray-500 text-lg shadow-sm"
        />
        <div className="absolute left-4 top-1/2 transform -translate-y-1/2 text-[#0D99FF]">
          <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
        </div>
      </div>

      <div className="flex items-center space-x-3">
        <button className="p-2 rounded-lg hover:bg-[#0D99FF]/10 transition-all duration-300">
          <img src={icon3} alt="Menú" className="h-8 w-8" />
        </button>
        <button className="p-2 rounded-full hover:bg-[#0D99FF]/10 transition-all duration-300">
          <img src={icon2} alt="Perfil" className="h-8 w-8" />
        </button>
        <button className="p-2 rounded-lg hover:bg-[#0D99FF]/10 transition-all duration-300 relative">
          <img src={icon1} alt="Carrito" className="h-8 w-8" />
          <span className="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center font-bold">
            0
          </span>
        </button>
      </div>
    </header>
  );
};

export default Header;