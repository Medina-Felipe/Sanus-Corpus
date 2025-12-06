import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../hooks/useCart';

export default function CartPage() {
  const { cartItems, totals, updateQuantity, removeItem } = useCart();
  const navigate = useNavigate();

  const formatPrice = (amount) => {
    return Number(amount).toLocaleString('es-CL');
  };

  // Caso: Carrito Vacío
  if (totals.itemCount === 0) {
    return (
      <div className="max-w-4xl mx-auto p-10 text-center my-12 bg-white rounded-xl shadow-lg">
        <h1 className="text-3xl font-bold text-gray-800 mb-4">Tu Carrito Está Vacío</h1>
        <p className="text-gray-600 mb-6">Parece que aún no has añadido productos. ¡Comienza a explorar!</p>
        <button
          onClick={() => navigate('/')}
          className="px-6 py-3 bg-[#0D99FF] text-white font-semibold rounded-full hover:bg-[#0A7ACC] transition-colors shadow-md"
        >
          Ir a Comprar
        </button>
      </div>
    );
  }

  // Caso: Carrito con Productos
  return (
    <div className="max-w-7xl mx-auto py-10 px-4 sm:px-6 lg:px-8">
      <h1 className="text-4xl font-extrabold text-gray-900 mb-8">Carrito de Compras</h1>

      <div className="flex flex-col lg:flex-row gap-10">

        {/* Columna de Productos*/}
        <div className="lg:w-2/3 space-y-4">
          {cartItems.map(item => (
            <div
              key={item.id}
              className="flex items-center bg-white p-4 rounded-lg shadow-md border border-gray-100"
            >
              <img
                src={item.image || "/path/to/default-image.jpg"}
                alt={item.name}
                className="w-20 h-20 object-cover rounded-md mr-4"
              />

              <div className="flex-1">
                <h3 className="text-lg font-semibold text-gray-800">{item.name}</h3>
                {/* Precio Unitario */}
                <p className="text-[#0D99FF] font-bold">${formatPrice(item.price)}</p>
                {/* Total por ítem (Precio * Cantidad) */}
                <p className="text-sm text-gray-500">
                  Total: ${formatPrice(item.price * item.quantity)}
                </p>
              </div>

              {/* Controles de Cantidad */}
              <div className="flex items-center space-x-3 mx-4">
                <label htmlFor={`qty-${item.id}`} className="sr-only">Cantidad</label>
                <input
                  id={`qty-${item.id}`}
                  type="number"
                  min="1"
                  value={item.quantity || ''}
                  onChange={(e) => {
                    const value = parseInt(e.target.value, 10);
                    if (!isNaN(value) && value > 0) {
                      updateQuantity(item.id, value);
                    } else if (e.target.value === '') {

                    }
                  }}
                  className="w-16 p-2 border rounded-lg text-center focus:border-[#0D99FF] focus:ring-1 focus:ring-[#0D99FF]"
                />
              </div>

              {/* Botón Eliminar */}
              <button
                onClick={() => removeItem(item.id)}
                className="text-red-500 hover:text-red-700 transition-colors p-2 rounded-full hover:bg-red-50"
              >
                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" /></svg>
              </button>
            </div>
          ))}
        </div>

        {/* Columna de Resumen*/}
        <div className="lg:w-1/3 bg-white p-6 rounded-lg shadow-lg h-fit border-2 border-[#0D99FF]/30">
          <h2 className="text-2xl font-bold mb-6 border-b pb-2">Resumen de la Orden</h2>

          <div className="space-y-3 text-gray-700">
            <div className="flex justify-between">
              <span>Subtotal ({totals.itemCount} unidades):</span>
              {/* Subtotal formateado */}
              <span>${formatPrice(totals.subtotal)}</span>
            </div>
            <div className="flex justify-between">
              <span>Envío:</span>
              <span className={Number(totals.shipping) === 0 ? "text-green-600 font-semibold" : ""}>
                {Number(totals.shipping) === 0 ? "GRATIS" : `$${formatPrice(totals.shipping)}`}
              </span>
            </div>
            <div className="flex justify-between border-b pb-3">
              <span>IVA (19%):</span>
              {/* Impuesto formateado */}
              <span>${formatPrice(totals.tax)}</span>
            </div>

            <div className="flex justify-between font-extrabold text-2xl text-gray-900 pt-2">
              <span>Total:</span>
              {/* Total final formateado */}
              <span>${formatPrice(totals.total)}</span>
            </div>
          </div>

          <button
            onClick={() => navigate('/checkout')}
            className="w-full mt-8 px-6 py-3 bg-green-500 text-white font-semibold rounded-full hover:bg-green-600 transition-colors shadow-lg transform hover:scale-[1.01]"
          >
            Proceder al Pago
          </button>
        </div>

      </div>
    </div>
  );
}