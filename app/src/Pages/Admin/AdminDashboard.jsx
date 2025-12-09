import React from 'react';

const AdminDashboard = () => {
    // Mock Data para el Dashboard
    const stats = [
        { title: 'Ventas Hoy', value: '$345.000', change: '+12%', color: 'text-green-500', icon: '💰' },
        { title: 'Pedidos Pendientes', value: '14', change: '-5%', color: 'text-yellow-500', icon: '📦' },
        { title: 'Nuevos Clientes', value: '45', change: '+20%', color: 'text-blue-500', icon: '👥' },
        { title: 'Stock Crítico', value: '3', change: '⚠️', color: 'text-red-500', icon: '🚨' },
    ];

    return (
        <div>
            <h2 className="text-3xl font-bold text-gray-900 mb-8">Dashboard de Administración</h2>

            {/* Tarjetas de Estadísticas */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-10">
                {stats.map((stat, index) => (
                    <div key={index} className="bg-white p-6 rounded-2xl shadow-md border border-gray-100 hover:shadow-lg transition-shadow">
                        <div className="flex justify-between items-center">
                            <p className="text-sm font-semibold text-gray-500">{stat.title}</p>
                            <span className="text-2xl">{stat.icon}</span>
                        </div>
                        <h3 className="text-3xl font-extrabold text-gray-900 mt-2">{stat.value}</h3>
                        <p className={`text-sm mt-1 ${stat.color}`}>{stat.change}</p>
                    </div>
                ))}
            </div>

            {/* Gráfico de Ventas (Placeholder) */}
            <div className="bg-white p-6 rounded-2xl shadow-md border border-gray-100 h-96 flex items-center justify-center">
                <p className="text-gray-500">Gráfico de Tendencias de Ventas (Placeholder)</p>
            </div>
        </div>
    );
};

export default AdminDashboard;