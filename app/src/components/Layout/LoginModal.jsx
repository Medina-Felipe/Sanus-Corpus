import React from 'react';

const LoginModal = ({ isOpen, onClose, onConfirm }) => {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/50 backdrop-blur-sm transition-opacity">
      {/* Caja del Modal */}
      <div className="bg-white rounded-xl shadow-2xl w-full max-w-sm transform transition-all scale-100 p-6 text-center">
        
        {/* Ícono de Candado o Alerta */}
        <div className="mx-auto flex items-center justify-center h-12 w-12 rounded-full bg-blue-100 mb-4">
          <span className="text-2xl">🔒</span>
        </div>

        {/* Texto */}
        <h3 className="text-lg font-bold text-gray-900 mb-2">
          Inicia sesión para continuar
        </h3>
        <p className="text-sm text-gray-500 mb-6">
          Necesitas una cuenta para guardar tus productos favoritos y verlos después.
        </p>

        {/* Botones */}
        <div className="flex flex-col gap-3">
          <button
            onClick={onConfirm}
            className="w-full inline-flex justify-center rounded-lg border border-transparent shadow-sm px-4 py-2 bg-[#0D99FF] text-base font-medium text-white hover:bg-[#0A7ACC] focus:outline-none sm:text-sm"
          >
            Ir a Iniciar Sesión
          </button>
          
          <button
            onClick={onClose}
            className="w-full inline-flex justify-center rounded-lg border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none sm:text-sm"
          >
            Cancelar
          </button>
        </div>
      </div>
    </div>
  );
};

export default LoginModal;