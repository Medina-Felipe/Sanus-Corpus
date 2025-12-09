import React from 'react';

const OrdersPage = () => {
  // Mock Data
  const orders = [
    { id: '#ORD-9921', date: '08 Dic 2023', total: 45990, status: 'Entregado', items: 3, image: 'https://via.placeholder.com/50' },
    { id: '#ORD-9850', date: '15 Nov 2023', total: 12500, status: 'En camino', items: 1, image: 'https://via.placeholder.com/50' },
  ];

  return (
    <div className="min-h-screen bg-[#F9FAFB] py-10 px-4">
      <div className="max-w-4xl mx-auto">
        <h1 className="text-3xl font-bold text-gray-900 mb-8">📦 Mis Pedidos</h1>
        
        <div className="space-y-4">
          {orders.map((order) => (
            <div key={order.id} className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 flex flex-col md:flex-row justify-between items-center gap-4 transition-all hover:shadow-md">
              
              <div className="flex items-center gap-4 w-full md:w-auto">
                <div className="h-12 w-12 bg-blue-50 rounded-full flex items-center justify-center text-xl">
                  📦
                </div>
                <div>
                  <h3 className="font-bold text-gray-900 text-lg">{order.id}</h3>
                  <p className="text-sm text-gray-500">{order.date} • {order.items} productos</p>
                </div>
              </div>
              
              <div className="flex items-center gap-6 w-full md:w-auto justify-between md:justify-end">
                <div className={`px-4 py-1.5 rounded-full text-xs font-bold uppercase tracking-wide ${
                  order.status === 'Entregado' ? 'bg-green-100 text-green-600' : 'bg-blue-100 text-blue-600'
                }`}>
                  {order.status}
                </div>
                <div className="text-right">
                   <p className="text-xs text-gray-400 font-bold">Total</p>
                   <p className="font-bold text-xl text-gray-900">${order.total.toLocaleString('es-CL')}</p>
                </div>
              </div>

            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default OrdersPage;