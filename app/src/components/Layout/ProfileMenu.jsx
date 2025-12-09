import React from 'react';

import { useAuth } from "../../context/AuthContext";

const ProfileMenu = ({ isOpen, onClose }) => {
  const { user, logout } = useAuth();

  if (!isOpen || !user) return null;

  return (

    <div
      className="fixed inset-0 z-50"
      onClick={onClose}
    >

      <div
        className="bg-white w-80 h-full p-6 shadow-2xl absolute right-0"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="flex justify-between items-center mb-6 border-b pb-2">
          <h2 className="text-2xl font-bold text-[#0D99FF]">Mi Cuenta</h2>
          <button
            onClick={onClose}
            className="text-gray-500 hover:text-red-500 transition-colors p-1 rounded-full"
          >
            <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>

        {/* Cabecera perfik */}
        <div className="flex items-center space-x-4 mb-8 p-3 bg-blue-50/50 rounded-lg">
          <div className="w-12 h-12 rounded-full bg-white flex items-center justify-center text-xl font-bold text-[#0D99FF] border-2 border-[#0D99FF]">
            {user.avatar || user.name.charAt(0)}
          </div>
          <div>
            <p className="font-bold text-gray-800">Hola, {user.name.split(' ')[0]}</p>
            <p className="text-sm text-gray-500 truncate w-40">{user.email}</p>
          </div>
        </div>


        {/* opciones */}
        <ul className="space-y-2 mb-6">
          {/* Enlaces con iconos */}
          <MenuItem href="/pedidos" icon="📦" text="Mis Pedidos" />
          <MenuItem href="/perfil" icon="⚙️" text="Configuración de Cuenta" />
          <MenuItem href="/favoritos" icon="❤️" text="Lista de Deseos" />
        </ul>

        {/* soporte */}
        <h3 className="text-sm font-semibold text-gray-500 uppercase tracking-wider mb-3 border-t pt-4">
          Ayuda y Legal
        </h3>
        <ul className="space-y-2 mb-8">
          <MenuItem href="/faq" icon="❓" text="Preguntas Frecuentes (FAQ)" />
          <MenuItem href="/contacto" icon="💬" text="Contactar Soporte" />
          <MenuItem href="/terminos" icon="📜" text="Términos y Condiciones" />
        </ul>

        {/* Cerrar sesión funcional */}
        <div className="mt-auto border-t pt-4">
          <button
            onClick={() => {
              logout();
              onClose();
            }}
            className="w-full text-red-600 border border-red-600 py-2 rounded-lg font-semibold hover:bg-red-50 transition-colors"
          >
            Cerrar Sesión
          </button>
        </div>

      </div>
    </div>
  );
};

const MenuItem = ({ href, icon, text }) => (
  <li className="hover:bg-blue-50 rounded-lg transition-colors">
    <a href={href} className="flex items-center space-x-3 p-2 text-gray-700">
      <span className="text-lg">{icon}</span>
      <span className="font-medium">{text}</span>
    </a>
  </li>
);

export default ProfileMenu;