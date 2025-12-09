import React from 'react';
import { Link } from "react-router-dom";
const Footer = () => {
  return (
    <footer className="bg-gray-800 text-white mt-8">
            {/* Link secreto para desarrolladores */}

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
          
          {/* Columna 1: Contacto */}
          <div>
            <h4 className="font-bold text-lg mb-4 text-[#0D99FF]">Contacto</h4>
            <ul className="space-y-2 text-sm">
              <li>Dirección: Calle Falsa 123</li>
              <li>Teléfono: +56 9 1234 5678</li>
              <li>Email: info@sanuscorpus.cl</li>
            </ul>
          </div>

          {/* Columna 2: Información */}
          <div>
            <h4 className="font-bold text-lg mb-4 text-[#0D99FF]">Información</h4>
            <ul className="space-y-2 text-sm">
              <li><a href="#" className="hover:underline">Acerca de Nosotros</a></li>
              <li><a href="#" className="hover:underline">Términos y Condiciones</a></li>
              <li><a href="#" className="hover:underline">Política de Privacidad</a></li>
            </ul>
          </div>

          {/* Columna 3: Categorías Rápidas */}
          <div>
            <h4 className="font-bold text-lg mb-4 text-[#0D99FF]">Categorías</h4>
            <ul className="space-y-2 text-sm">
              <li><a href="#" className="hover:underline">Dermocosmética</a></li>
              <li><a href="#" className="hover:underline">Medicamentos</a></li>
              <li><a href="#" className="hover:underline">Vitaminas</a></li>
            </ul>
          </div>

          {/* Columna 4: Síguenos */}
          <div>
            <h4 className="font-bold text-lg mb-4 text-[#0D99FF]">Síguenos</h4>
            <div className="flex space-x-4">
              <a href="#" className="hover:text-[#0D99FF]">
                <svg className="w-6 h-6" fill="currentColor" viewBox="0 0 24 24">...</svg>
              </a>
              <a href="#" className="hover:text-[#0D99FF]">
                <svg className="w-6 h-6" fill="currentColor" viewBox="0 0 24 24">...</svg>
              </a>
            </div>
          </div>

        </div>
        

        <div className="mt-8 pt-6 border-t border-gray-700 text-center text-sm text-gray-400">
          © {new Date().getFullYear()} SANUS-CORPUS. Todos los derechos reservados.
        </div>
      </div>
<div className="bg-gray-900 py-2 text-center">
    <Link to="/admin" className="text-gray-600 text-xs hover:text-white">
        🔒 Acceso Admin (Solo Devs)
    </Link>
</div>
    </footer>
  );
};

export default Footer;