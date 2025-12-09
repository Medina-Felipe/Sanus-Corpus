import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import iconPlaceholder from '../Images/IconoPersona.png';

const ProfilePage = () => {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        name: user?.name || '',
        email: user?.email || '',
        phone: '+56 9 1234 5678',
        address: 'Av. Alemania 0123, Temuco'
    });

    if (!user) {
        navigate('/login');
        return null;
    }

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    return (
        <div className="min-h-screen bg-[#F9FAFB] py-10 px-4">
            <div className="max-w-4xl mx-auto">

                <h1 className="text-3xl font-bold text-gray-900 mb-8">⚙️ Configuración de Cuenta</h1>

                <div className="grid grid-cols-1 md:grid-cols-3 gap-8">

                    {/* Tarjeta de Perfil */}
                    <div className="md:col-span-1">
                        <div className="bg-white p-6 rounded-3xl shadow-sm border border-gray-100 text-center sticky top-24">

                            {/* Avatar */}
                            <div className="w-24 h-24 mx-auto bg-blue-50 rounded-full flex items-center justify-center border-4 border-white shadow-md mb-4 overflow-hidden">

                                <img
                                    src={iconPlaceholder}
                                    alt="Perfil"
                                    className="w-12 h-12 object-contain opacity-50"
                                />

                            </div>

                            <h2 className="text-xl font-bold text-gray-800">{user.name}</h2>
                            <p className="text-sm text-gray-500 mb-6">{user.email}</p>

                            <button
                                onClick={logout}
                                className="w-full py-2 px-4 border border-red-200 text-red-600 rounded-xl hover:bg-red-50 font-semibold transition-colors text-sm"
                            >
                                Cerrar Sesión
                            </button>
                        </div>
                    </div>

                    {/* Formularios */}
                    <div className="md:col-span-2 space-y-6">

                        {/* Datos Personales */}
                        <div className="bg-white p-8 rounded-3xl shadow-sm border border-gray-100">
                            <h3 className="text-xl font-bold text-gray-900 mb-6 flex items-center gap-2">
                                <span className="text-[#0D99FF]">📝</span> Datos Personales
                            </h3>

                            <div className="space-y-4">
                                <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                                    <div>
                                        <label className="block text-xs font-bold text-gray-400 uppercase mb-1">Nombre Completo</label>
                                        <input type="text" name="name" value={formData.name} onChange={handleChange} className="w-full p-3 bg-gray-50 rounded-xl border border-gray-200 focus:border-[#0D99FF] focus:bg-white focus:ring-2 focus:ring-blue-100 outline-none transition-all" />
                                    </div>
                                    <div>
                                        <label className="block text-xs font-bold text-gray-400 uppercase mb-1">Teléfono</label>
                                        <input type="text" name="phone" value={formData.phone} onChange={handleChange} className="w-full p-3 bg-gray-50 rounded-xl border border-gray-200 focus:border-[#0D99FF] focus:bg-white focus:ring-2 focus:ring-blue-100 outline-none transition-all" />
                                    </div>
                                </div>
                                <div>
                                    <label className="block text-xs font-bold text-gray-400 uppercase mb-1">Correo Electrónico</label>
                                    <input type="email" name="email" value={formData.email} disabled className="w-full p-3 bg-gray-100 rounded-xl border border-gray-200 text-gray-500 cursor-not-allowed" />
                                </div>
                                <div>
                                    <label className="block text-xs font-bold text-gray-400 uppercase mb-1">Dirección de Envío</label>
                                    <input type="text" name="address" value={formData.address} onChange={handleChange} className="w-full p-3 bg-gray-50 rounded-xl border border-gray-200 focus:border-[#0D99FF] focus:bg-white focus:ring-2 focus:ring-blue-100 outline-none transition-all" />
                                </div>
                            </div>
                        </div>

                        {/* Seguridad */}
                        <div className="bg-white p-8 rounded-3xl shadow-sm border border-gray-100">
                            <h3 className="text-xl font-bold text-gray-900 mb-6 flex items-center gap-2">
                                <span className="text-[#0D99FF]">🔒</span> Seguridad
                            </h3>
                            <div className="space-y-4">
                                <div>
                                    <label className="block text-xs font-bold text-gray-400 uppercase mb-1">Nueva Contraseña</label>
                                    <input type="password" placeholder="••••••••" className="w-full p-3 bg-gray-50 rounded-xl border border-gray-200 focus:border-[#0D99FF] focus:bg-white focus:ring-2 focus:ring-blue-100 outline-none transition-all" />
                                </div>
                                <div>
                                    <label className="block text-xs font-bold text-gray-400 uppercase mb-1">Confirmar Contraseña</label>
                                    <input type="password" placeholder="••••••••" className="w-full p-3 bg-gray-50 rounded-xl border border-gray-200 focus:border-[#0D99FF] focus:bg-white focus:ring-2 focus:ring-blue-100 outline-none transition-all" />
                                </div>
                            </div>
                        </div>

                        {/* Botón Guardar */}
                        <div className="flex justify-end">
                            <button className="bg-[#0D99FF] text-white px-8 py-3 rounded-xl font-bold hover:bg-[#007ACC] shadow-lg shadow-blue-200 transition-all transform hover:-translate-y-1">
                                Guardar Cambios
                            </button>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    );
};

export default ProfilePage;