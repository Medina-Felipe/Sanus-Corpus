import React from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';

const AdminLayout = () => {
    const location = useLocation();

    const AdminSidebar = () => {
        const links = [
            { path: '/admin', name: 'Dashboard', icon: '📊' },
            { path: '/admin/productos', name: 'Productos', icon: '💊' },
            { path: '/admin/clientes', name: 'Clientes', icon: '👥' },
        ];

        return (
            <div className="w-64 bg-gray-900 text-white min-h-screen p-6 shadow-xl sticky top-0">
                <h1 className="text-3xl font-extrabold text-[#0D99FF] mb-10">Admin Panel</h1>
                <nav className="space-y-3">
                    {links.map((link) => (
                        <Link
                            key={link.path}
                            to={link.path}
                            className={`flex items-center p-3 rounded-xl font-medium transition-colors ${
                                location.pathname === link.path 
                                    ? 'bg-[#0D99FF] text-white shadow-lg' 
                                    : 'text-gray-300 hover:bg-gray-700'
                            }`}
                        >
                            <span className="mr-3">{link.icon}</span>
                            {link.name}
                        </Link>
                    ))}
                </nav>
                <div className="mt-10 pt-4 border-t border-gray-700">
                    <Link to="/" className="text-sm text-gray-400 hover:text-white">
                        ← Volver a la Tienda
                    </Link>
                </div>
            </div>
        );
    };

    return (
        <div className="flex">
            <AdminSidebar />
            
            <main className="flex-1 p-8 bg-[#F9FAFB]">
                <div className="max-w-7xl mx-auto">
                    <Outlet />
                </div>
            </main>
        </div>
    );
};

export default AdminLayout;