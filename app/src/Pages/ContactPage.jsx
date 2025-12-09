import React from 'react';

const ContactPage = () => {
    return (
        <div className="min-h-screen bg-[#F9FAFB] py-12 px-4">
            <div className="max-w-6xl mx-auto">

                <div className="text-center mb-12">
                    <h1 className="text-4xl font-extrabold text-gray-900 mb-4">¿Cómo podemos ayudarte? 💬</h1>
                    <p className="text-gray-500 text-lg max-w-2xl mx-auto">
                        Estamos aquí para resolver tus dudas sobre productos, envíos o lo que necesites.
                    </p>
                </div>

                <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">

                    {/* COLUMNA 1: Información de Contacto */}
                    <div className="space-y-6">

                        {/* Tarjeta Teléfono */}
                        <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 flex items-start gap-4 hover:shadow-md transition-shadow">
                            <div className="bg-blue-50 p-3 rounded-full text-2xl">📞</div>
                            <div>
                                <h3 className="font-bold text-gray-900 text-lg">Llámanos</h3>
                                <p className="text-gray-500 mb-1">Lun a Vie de 9:00 a 18:00</p>
                                <a href="tel:+56912345678" className="text-[#0D99FF] font-bold text-lg hover:underline">+56 9 1234 5678</a>
                            </div>
                        </div>

                        {/* Tarjeta Email */}
                        <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 flex items-start gap-4 hover:shadow-md transition-shadow">
                            <div className="bg-blue-50 p-3 rounded-full text-2xl">✉️</div>
                            <div>
                                <h3 className="font-bold text-gray-900 text-lg">Escríbenos</h3>
                                <p className="text-gray-500 mb-1">Respondemos en menos de 24 hrs.</p>
                                <a href="mailto:soporte@sanuscorpus.cl" className="text-[#0D99FF] font-bold text-lg hover:underline">soporte@sanuscorpus.cl</a>
                            </div>
                        </div>

                        {/* Tarjeta Ubicación */}
                        <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 flex items-start gap-4 hover:shadow-md transition-shadow">
                            <div className="bg-blue-50 p-3 rounded-full text-2xl">📍</div>
                            <div>
                                <h3 className="font-bold text-gray-900 text-lg">Visítanos</h3>
                                <p className="text-gray-500">Av. Alemania 0123, Temuco.<br />Región de la Araucanía.</p>
                            </div>
                        </div>

                    </div>

                    {/* COLUMNA 2: Formulario */}
                    <div className="bg-white p-8 rounded-3xl shadow-lg shadow-blue-100/50 border border-blue-50">
                        <h3 className="text-2xl font-bold text-gray-900 mb-6">Envíanos un mensaje</h3>
                        <form className="space-y-4">
                            <div className="grid grid-cols-2 gap-4">
                                <input
                                    type="text"
                                    maxLength={50}
                                    placeholder="Nombre"
                                    className="w-full p-4 bg-gray-50 rounded-xl border border-gray-100 focus:bg-white focus:border-[#0D99FF] focus:ring-2 focus:ring-blue-100 outline-none transition-all"
                                />
                                <input
                                    type="text"
                                    maxLength={50}
                                    placeholder="Apellido"
                                    className="w-full p-4 bg-gray-50 rounded-xl border border-gray-100 focus:bg-white focus:border-[#0D99FF] focus:ring-2 focus:ring-blue-100 outline-none transition-all"
                                />
                            </div>
                            <input
                                type="email"
                                maxLength={100}
                                placeholder="Tu correo electrónico"
                                className="w-full p-4 bg-gray-50 rounded-xl border border-gray-100 focus:bg-white focus:border-[#0D99FF] focus:ring-2 focus:ring-blue-100 outline-none transition-all"
                            />
                            <textarea
                                rows="4"
                                maxLength={500}
                                placeholder="¿En qué podemos ayudarte?"
                                className="w-full p-4 bg-gray-50 rounded-xl border border-gray-100 focus:bg-white focus:border-[#0D99FF] focus:ring-2 focus:ring-blue-100 outline-none transition-all"
                            ></textarea>

                            <button type="submit" className="w-full bg-[#0D99FF] text-white py-4 rounded-xl font-bold hover:bg-[#007ACC] transition-colors shadow-lg shadow-blue-200">
                                Enviar Mensaje
                            </button>
                        </form>
                    </div>

                </div>
            </div>
        </div>
    );
};

export default ContactPage;