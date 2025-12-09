import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import logo from '../Images/Logo.png';

const RegisterPage = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    register({ name, email });
    navigate('/');
  };

  return (
    <div className="min-h-screen flex font-sans bg-white">

      {/* Izquierda */}
      <div className="hidden lg:flex w-1/2 bg-[#0D99FF] relative overflow-hidden items-center justify-center p-12">
        <div className="absolute inset-0 opacity-10">
          <svg className="h-full w-full" viewBox="0 0 100 100" preserveAspectRatio="none">
            <rect x="-10" y="-10" width="40" height="40" rx="5" transform="rotate(45 10 10)" fill="white" />
            <rect x="80" y="80" width="50" height="50" rx="10" transform="rotate(45 90 90)" fill="none" stroke="white" strokeWidth="0.5" />
            <circle cx="50" cy="50" r="30" fill="none" stroke="white" strokeWidth="0.2" strokeDasharray="2 2" />
          </svg>
        </div>

        <div className="relative z-10 flex flex-col items-center text-center">
          <div className="relative mb-8 p-4">
            <div className="absolute inset-0 bg-white/20 blur-xl rounded-full"></div>
            <img src={logo} alt="Sanus Corpus Logo" className="relative w-64 object-contain drop-shadow-md" />
          </div>

          <h2 className="text-3xl font-bold text-white tracking-wide">
            Empieza Tu Cambio
          </h2>
          <p className="text-blue-100 mt-4 text-lg max-w-sm font-light">
            Únete a la comunidad líder en salud integral.
          </p>
        </div>
      </div>

      {/* Derecha  */}
      <div className="w-full lg:w-1/2 flex items-center justify-center p-8">
        <div className="w-full max-w-md space-y-8">

          <div className="text-center">
            <img src={logo} alt="Sanus Corpus" className="h-20 mx-auto mb-6 lg:hidden" />
            <h3 className="text-4xl font-bold text-gray-900 tracking-tight">Crear Cuenta</h3>
            <p className="text-gray-500 mt-2">Rellena el formulario en segundos.</p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-4">

            <div className="group">
              <label className="block text-xs font-bold text-gray-500 uppercase tracking-wider mb-1 ml-1">Nombre</label>
              <input type="text" placeholder="Tu Nombre" className="w-full p-4 rounded-lg bg-gray-100 border-transparent focus:bg-white focus:border-[#0D99FF] focus:ring-0 transition-all font-medium text-gray-800 placeholder-gray-400 border-2" value={name} onChange={e => setName(e.target.value)} required />
            </div>

            <div className="group">
              <label className="block text-xs font-bold text-gray-500 uppercase tracking-wider mb-1 ml-1">Correo</label>
              <input type="email" placeholder="nombre@ejemplo.com" className="w-full p-4 rounded-lg bg-gray-100 border-transparent focus:bg-white focus:border-[#0D99FF] focus:ring-0 transition-all font-medium text-gray-800 placeholder-gray-400 border-2" value={email} onChange={e => setEmail(e.target.value)} required />
            </div>

            <div className="group">
              <label className="block text-xs font-bold text-gray-500 uppercase tracking-wider mb-1 ml-1">Contraseña</label>
              <input type="password" placeholder="Mínimo 8 caracteres" className="w-full p-4 rounded-lg bg-gray-100 border-transparent focus:bg-white focus:border-[#0D99FF] focus:ring-0 transition-all font-medium text-gray-800 placeholder-gray-400 border-2" required />
            </div>

            <button type="submit" className="w-full bg-[#0D99FF] text-white py-4 rounded-lg text-lg font-bold shadow-lg hover:bg-[#007ACC] hover:shadow-[#0D99FF]/30 transition-all transform active:scale-95 mt-4">
              REGISTRARSE
            </button>
          </form>

          <p className="text-center text-gray-600 mt-6">
            ¿Ya tienes cuenta? <Link to="/login" className="text-[#0D99FF] font-bold hover:underline">Entrar ahora</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;