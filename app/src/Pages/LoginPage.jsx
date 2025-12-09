import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import logo from '../Images/Logo.png';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    login({ name: 'Usuario Prueba', email: email });
    navigate('/');
  };

  return (
    <div className="min-h-screen flex font-sans bg-white">

      {/* Izquiewrda*/}
      <div className="hidden lg:flex w-1/2 bg-[#0D99FF] relative overflow-hidden items-center justify-center p-12">

        {/* Decoracion */}
        <div className="absolute inset-0 opacity-10">
          <svg className="h-full w-full" viewBox="0 0 100 100" preserveAspectRatio="none">
            <circle cx="0" cy="100" r="20" fill="white" />
            <circle cx="0" cy="100" r="40" fill="none" stroke="white" strokeWidth="0.5" />
            <circle cx="0" cy="100" r="60" fill="none" stroke="white" strokeWidth="0.5" />
            <circle cx="100" cy="0" r="30" fill="white" />
            <circle cx="100" cy="0" r="50" fill="none" stroke="white" strokeWidth="0.5" />
          </svg>
        </div>

        <div className="relative z-10 flex flex-col items-center text-center">

          {/* Logo */}
          <div className="relative mb-8 p-4">
            <div className="absolute inset-0 bg-white/20 blur-xl rounded-full"></div>
            <img
              src={logo}
              alt="Sanus Corpus Logo"
              className="relative w-64 object-contain drop-shadow-md"
            />
          </div>

          <h2 className="text-3xl font-bold text-white tracking-wide">
            Gestión de Salud Inteligente
          </h2>
          <p className="text-blue-100 mt-4 text-lg max-w-sm font-light">
            Conecta con tu bienestar de forma rápida, segura y eficiente.
          </p>
        </div>
      </div>

      {/* FORMULARIO */}
      <div className="w-full lg:w-1/2 flex items-center justify-center p-8">
        <div className="w-full max-w-md space-y-8">

          <div className="text-center">
            <img src={logo} alt="Sanus Corpus" className="h-20 mx-auto mb-6 lg:hidden" />
            <h3 className="text-4xl font-bold text-gray-900 tracking-tight">¡Hola!</h3>
            <p className="text-gray-500 mt-2">Bienvenido a tu espacio personal.</p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-5">
            <div className="group">
              <label className="block text-xs font-bold text-gray-500 uppercase tracking-wider mb-1 ml-1">Correo</label>
              <input
                type="email"
                placeholder="nombre@ejemplo.com"
                className="w-full p-4 rounded-lg bg-gray-100 border-transparent focus:bg-white focus:border-[#0D99FF] focus:ring-0 transition-all font-medium text-gray-800 placeholder-gray-400 border-2"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>

            <div className="group">
              <label className="block text-xs font-bold text-gray-500 uppercase tracking-wider mb-1 ml-1">Contraseña</label>
              <input
                type="password"
                placeholder="••••••••"
                className="w-full p-4 rounded-lg bg-gray-100 border-transparent focus:bg-white focus:border-[#0D99FF] focus:ring-0 transition-all font-medium text-gray-800 placeholder-gray-400 border-2"
                required
              />
              <div className="text-right mt-2">
                <a href="#" className="text-xs font-bold text-gray-400 hover:text-[#0D99FF] transition-colors">RECUPERAR CONTRASEÑA</a>
              </div>
            </div>

            <button type="submit" className="w-full bg-[#0D99FF] text-white py-4 rounded-lg text-lg font-bold shadow-lg hover:bg-[#007ACC] hover:shadow-[#0D99FF]/30 transition-all transform active:scale-95">
              ENTRAR
            </button>
          </form>

          <div className="relative mt-8">
            <div className="absolute inset-0 flex items-center">
              <div className="w-full border-t border-gray-200"></div>
            </div>
            <div className="relative flex justify-center text-sm">
              <span className="px-2 bg-white text-gray-500">O continúa con</span>
            </div>
          </div>

          <p className="text-center text-gray-600">
            ¿No tienes cuenta? <Link to="/registro" className="text-[#0D99FF] font-bold hover:underline">Regístrate gratis</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;