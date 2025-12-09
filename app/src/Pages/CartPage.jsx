import React from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useCart } from '../hooks/useCart';
import { useAuth } from '../context/AuthContext'; 


export default function CartPage() {
  const { cartItems, totals, updateQuantity, removeItem } = useCart();
  const { user } = useAuth(); 
  const navigate = useNavigate();

  const formatPrice = (amount) => Number(amount).toLocaleString('es-CL');

  //Si no hay usuario, mostrar pantalla de "Iniciar Sesión"
  if (!user) {
    return (
      <div className="min-h-screen bg-[#F9FAFB] flex items-center justify-center px-4">
        <div className="bg-white p-10 rounded-3xl shadow-lg text-center max-w-md w-full border border-gray-100">
          <div className="w-20 h-20 bg-blue-50 rounded-full flex items-center justify-center mx-auto mb-6 text-4xl">
            🛒
          </div>
          <h2 className="text-2xl font-bold text-gray-900 mb-3">Tu carrito te espera</h2>
          <p className="text-gray-500 mb-8">
            Inicia sesión para ver tus productos, guardar tu compra y acceder a descuentos exclusivos.
          </p>
          <div className="space-y-3">
            <button
              onClick={() => navigate('/login')}
              className="w-full py-3.5 bg-[#0D99FF] text-white font-bold rounded-xl hover:bg-[#007ACC] transition-all shadow-md hover:shadow-blue-200"
            >
              Iniciar Sesión
            </button>
            <button
              onClick={() => navigate('/registro')}
              className="w-full py-3.5 bg-white text-[#0D99FF] font-bold rounded-xl border-2 border-[#0D99FF] hover:bg-blue-50 transition-all"
            >
              Crear Cuenta
            </button>
          </div>
        </div>
      </div>
    );
  }

  // ESTADO VACÍO
  if (totals.itemCount === 0) {
    return (
      <div className="min-h-screen bg-[#F9FAFB] flex items-center justify-center px-4">
        <div className="text-center">
          <div className="w-24 h-24 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-6 text-5xl grayscale opacity-50">
            🛒
          </div>
          <h1 className="text-2xl font-bold text-gray-800 mb-2">Tu carrito está vacío</h1>
          <p className="text-gray-500 mb-8">Parece que aún no has añadido productos.</p>
          <button
            onClick={() => navigate('/')}
            className="px-8 py-3 bg-[#0D99FF] text-white font-bold rounded-full hover:bg-[#007ACC] transition-all shadow-lg shadow-blue-200"
          >
            Ir a Comprar
          </button>
        </div>
      </div>
    );
  }

  // CARRITO CON PRODUCTOS
  return (
    <div className="min-h-screen bg-[#F9FAFB] py-10 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto">
        <h1 className="text-3xl font-bold text-gray-900 mb-8 flex items-center gap-3">
          🛒 Tu Carrito <span className="text-lg font-normal text-gray-500">({totals.itemCount} productos)</span>
        </h1>

        <div className="lg:grid lg:grid-cols-12 lg:gap-12">

          {/* LISTA DE ITEMS*/}
          <div className="lg:col-span-8 space-y-4">
            {cartItems.map((item) => (
              <div key={item.id} className="bg-white p-4 sm:p-6 rounded-2xl shadow-sm border border-gray-100 flex flex-col sm:flex-row items-center gap-6 transition-transform hover:scale-[1.01]">
                {/* Imagen */}
                <div className="h-24 w-24 bg-gray-50 rounded-xl flex items-center justify-center p-2">
                  <img src={item.image} alt={item.name} className="max-h-full object-contain mix-blend-multiply" />
                </div>

                {/* Info */}
                <div className="flex-1 text-center sm:text-left">
                  <h3 className="text-lg font-bold text-gray-800">{item.name}</h3>
                  <p className="text-gray-500 text-sm">{item.category}</p>
                  <p className="text-[#0D99FF] font-bold mt-1">${formatPrice(item.price)}</p>
                </div>

                {/* controles */}
                <div className="flex items-center gap-4">
                  <div className="flex items-center bg-gray-50 rounded-lg p-1">
                    <button
                      onClick={() => updateQuantity(item.id, item.quantity - 1)}
                      className="w-8 h-8 flex items-center justify-center text-gray-600 hover:bg-white rounded-md shadow-sm transition-all"
                    >-</button>
                    <span className="w-10 text-center font-bold text-gray-800">{item.quantity}</span>
                    <button
                      onClick={() => updateQuantity(item.id, item.quantity + 1)}
                      className="w-8 h-8 flex items-center justify-center text-gray-600 hover:bg-white rounded-md shadow-sm transition-all"
                    >+</button>
                  </div>
                  <button
                    onClick={() => removeItem(item.id)}
                    className="text-gray-300 hover:text-red-500 transition-colors p-2"
                  >
                    🗑️
                  </button>
                </div>
              </div>
            ))}
          </div>

          {/* resumen */}
          <div className="lg:col-span-4 mt-8 lg:mt-0">
            <div className="bg-white p-6 rounded-3xl shadow-lg shadow-blue-100/50 border border-blue-50 sticky top-24">
              <h2 className="text-xl font-bold text-gray-900 mb-6">Resumen de Compra</h2>

              <div className="space-y-4 text-sm">
                <div className="flex justify-between text-gray-600">
                  <span>Subtotal</span>
                  <span>${formatPrice(totals.subtotal)}</span>
                </div>
                <div className="flex justify-between text-gray-600">
                  <span>Envío</span>
                  <span className="text-green-600 font-bold">{Number(totals.shipping) === 0 ? "GRATIS" : `$${formatPrice(totals.shipping)}`}</span>
                </div>
                <div className="flex justify-between text-gray-600 border-b border-gray-100 pb-4">
                  <span>IVA (19%)</span>
                  <span>${formatPrice(totals.tax)}</span>
                </div>

                <div className="flex justify-between items-center pt-2">
                  <span className="text-lg font-bold text-gray-900">Total</span>
                  <span className="text-3xl font-black text-[#0D99FF]">${formatPrice(totals.total)}</span>
                </div>
              </div>

              <button className="w-full mt-8 bg-[#0D99FF] text-white py-4 rounded-xl font-bold text-lg hover:bg-[#007ACC] transition-all shadow-md hover:shadow-blue-300 flex items-center justify-center gap-2">
                <span>Pagar Ahora</span>
                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor">
                  <path fillRule="evenodd" d="M12.293 5.293a1 1 0 011.414 0l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-2.293-2.293a1 1 0 010-1.414z" clipRule="evenodd" />
                </svg>
              </button>
            </div>
          </div>

        </div>
      </div>
    </div>
  );
}