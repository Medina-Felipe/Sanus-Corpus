import React, { useEffect, useState } from 'react';
import { adminService } from '../../services/adminService';


const AdminUsers = () => {
    const [users, setUsers] = useState([]);
    const [isLoading, setIsLoading] = useState(true); 
    const [error, setError] = useState(null);     

    const loadUsers = async () => {
        setIsLoading(true);
        setError(null);
        try {
            const data = await adminService.getUsers();
            setUsers(data);
        } catch (err) {
            setError('Error al cargar los usuarios. Revisa el backend.');
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => { loadUsers(); }, []);

    // -CAMBIAR ROL ---
    const handleRoleChange = async (userId, newRole) => {
        if (!window.confirm(`¿Confirmar cambio de rol a ${newRole}?`)) return;
        try {
            await adminService.updateUserRole(userId, newRole);
            loadUsers(); 
        } catch (err) {
            alert("Error al actualizar el rol.");
        }
    };
    
    if (isLoading) return <div className="p-8 text-center text-gray-500">Cargando usuarios...</div>;
    if (error) return <div className="p-8 text-center text-red-600 border border-red-200 bg-red-50 rounded-xl">{error}</div>;

    return (
        <div>
            <h2 className="text-2xl font-bold text-gray-800 mb-6">Gestión de Clientes ({users.length} encontrados)</h2>
            

            <div className="bg-white rounded-2xl shadow-sm border border-gray-200 overflow-x-auto">
                <table className="w-full text-left">
                    <thead className="bg-gray-50 text-gray-500 uppercase text-xs">
                        <tr>
                            <th className="p-4">ID</th>
                            <th className="p-4">Nombre</th>
                            <th className="p-4">Email</th>
                            <th className="p-4">Rol</th>
                            <th className="p-4 text-right">Acciones</th> 
                        </tr>
                    </thead>
                    <tbody className="divide-y divide-gray-100 text-sm">
                        {users.map(user => (
                            <tr key={user.id} className="hover:bg-gray-50">
                                {/* ... Datos de usuario ... */}
                                <td className="p-4 text-gray-400">#{user.id}</td>
                                <td className="p-4 font-bold">{user.name}</td>
                                <td className="p-4">{user.email}</td>
                                <td className="p-4">
                                    <span className={`px-2 py-1 rounded text-xs font-bold ${user.role === 'admin' ? 'bg-purple-100 text-purple-700' : 'bg-gray-100 text-gray-600'}`}>
                                        {user.role}
                                    </span>
                                </td>
                                
                                {/* --- BOTONES DE ACCIÓN --- */}
                                <td className="p-4 text-right space-x-2">
                                    {user.role === 'admin' ? (
                                        <button onClick={() => handleRoleChange(user.id, 'user')} className="text-gray-500 hover:text-blue-700 text-xs">Degradar a User</button>
                                    ) : (
                                        <button onClick={() => handleRoleChange(user.id, 'admin')} className="text-purple-600 hover:text-purple-800 text-xs font-bold">Promover a Admin</button>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default AdminUsers;