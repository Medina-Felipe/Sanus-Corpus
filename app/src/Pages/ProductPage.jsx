import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { useCart } from '../hooks/useCart';
import { useWishlist } from '../context/WishlistContext';
import { useAuth } from '../context/AuthContext';
import LoginModal from '../components/Layout/LoginModal';

import { productService } from '../services/productServices';

const ProductPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const { addToCart } = useCart();
  const { toggleWishlist, isInWishlist } = useWishlist();
  const { user } = useAuth();

  const [quantity, setQuantity] = useState(1);
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    const fetchProduct = async () => {
      setLoading(true);
      try {
        const foundProduct = await productService.getById(id);

        if (foundProduct) {
          setProduct(foundProduct);
        } else {
          console.error("Producto no encontrado con ID:", id);
        }
      } catch (error) {
        console.error("Error cargando producto:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [id]);

  const handleWishlist = (e) => {
    if (e) e.preventDefault();
    if (!user) {
      setShowModal(true);
      return;
    }
    toggleWishlist(product);
  };

  const handleAddToCart = () => {
    if (!product) return;
    if (typeof addToCart === 'function') {
      addToCart(product, quantity);
    }
  };

  if (loading) return (
    <div className="min-h-screen bg-[#F9FAFB] flex items-center justify-center">
      <div className="animate-spin rounded-full h-12 w-12 border-4 border-gray-200 border-t-[#0D99FF]"></div>
    </div>
  );

  if (!product) return (
    <div className="min-h-screen bg-[#F9FAFB] flex flex-col items-center justify-center">
      <h2 className="text-2xl font-bold text-gray-800">Producto no encontrado 😕</h2>
      <button onClick={() => navigate('/')} className="mt-6 px-6 py-2 bg-[#0D99FF] text-white rounded-full font-bold">Volver al inicio</button>
    </div>
  );

  const isLiked = isInWishlist(product.id);
  const stock = product.stock || 0;
  const hasStock = stock > 0;
  const requiresPrescription = product.prescription === true;

  // Lógica para mostrar info adicional
  const hasUsageInfo = product.usageMode || product.instructions;
  const hasPrecautionsInfo = product.recommendedFor || product.precautions;
  const hasExtraInfo = hasUsageInfo || hasPrecautionsInfo;

  return (
    <>
      <LoginModal isOpen={showModal} onClose={() => setShowModal(false)} onConfirm={() => { setShowModal(false); navigate('/login'); }} />

      <div className="min-h-screen bg-[#F9FAFB] py-8 px-4 sm:px-6 lg:px-8">
        <div className="max-w-7xl mx-auto">

          <nav className="text-sm text-gray-500 mb-6 flex items-center gap-2">
            <Link to="/" className="hover:text-[#0D99FF]">Inicio</Link> /
            <span className="text-[#0D99FF] font-bold uppercase">{product.category}</span> /
            <span className="text-gray-400">{product.name}</span>
          </nav>

          {/* TARJETA PRINCIPAL */}
          <div className="bg-white rounded-3xl shadow-sm border border-gray-100 overflow-hidden mb-8">
            <div className="grid grid-cols-1 lg:grid-cols-2">

              <div className="bg-[#F3F4F6] p-10 flex items-center justify-center min-h-[400px]">
                <img src={product.image} alt={product.name} className="max-h-[80%] max-w-[90%] object-contain mix-blend-multiply hover:scale-105 transition-transform duration-500" />
              </div>

              <div className="p-8 lg:p-12 flex flex-col justify-center relative">
                <button onClick={handleWishlist} className="absolute top-8 right-8 text-2xl p-2 rounded-full hover:bg-gray-50 transition-all">
                  {isLiked ? '❤️' : '🤍'}
                </button>
                <div className="mb-4">
                  {requiresPrescription ? (
                    <span className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-bold bg-amber-50 text-amber-700 border border-amber-200">📄 Receta Médica Requerida</span>
                  ) : (
                    <span className="inline-flex items-center gap-1.5 px-3 py-1 rounded-full text-xs font-bold bg-green-50 text-green-700 border border-green-200">✅ Venta Libre</span>
                  )}
                </div>
                <h1 className="text-3xl font-extrabold text-gray-900 mb-4">{product.name}</h1>
                <div className="text-4xl font-black text-[#0D99FF] mb-6">${product.price.toLocaleString('es-CL')}</div>
                <p className="text-gray-600 mb-6 text-lg">{product.description}</p>
                <div className="mb-6">
                  {hasStock ? (
                    <div className="inline-flex items-center gap-2 text-green-700 text-sm font-bold">
                      <span className="w-2.5 h-2.5 bg-green-500 rounded-full animate-pulse"></span>
                      Stock Disponible: {stock} u.
                    </div>
                  ) : (
                    <div className="text-red-600 text-sm font-bold">🔴 Agotado</div>
                  )}
                </div>
                <div className="flex flex-col sm:flex-row gap-4 mt-auto border-t border-gray-100 pt-6">
                  <div className="flex items-center justify-between bg-gray-50 rounded-xl border border-gray-200 w-full sm:w-40 px-4 py-3">
                    <button onClick={() => quantity > 1 && setQuantity(quantity - 1)} className="text-xl font-bold text-gray-400 hover:text-[#0D99FF]">-</button>
                    <span className="font-bold text-gray-900">{quantity}</span>
                    <button onClick={() => quantity < stock && setQuantity(quantity + 1)} className={`text-xl font-bold ${quantity >= stock ? 'text-gray-300' : 'text-gray-400 hover:text-[#0D99FF]'}`}>+</button>
                  </div>
                  <button
                    onClick={handleAddToCart}
                    disabled={!hasStock}
                    className={`flex-1 py-3.5 rounded-xl font-bold text-white shadow-lg flex items-center justify-center gap-2 transition-all ${hasStock ? 'bg-[#0D99FF] hover:bg-[#007ACC] hover:-translate-y-1' : 'bg-gray-300 cursor-not-allowed'
                      }`}
                  >
                    {hasStock ? 'Añadir al Carrito' : 'Sin Stock'}
                  </button>
                </div>
              </div>
            </div>
          </div>

          {/* INFORMACIÓN ADICIONAL */}
          {hasExtraInfo && (
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-8">
              {hasUsageInfo && (
                <div className="bg-white p-8 rounded-3xl shadow-sm border border-gray-100">
                  <h3 className="text-lg font-bold text-gray-900 mb-4 flex items-center gap-2">
                    <span className="bg-blue-50 text-[#0D99FF] p-2 rounded-lg">🧴</span> Modo de Uso
                  </h3>
                  <div className="space-y-4">
                    {product.usageMode && (
                      <div>
                        <span className="block text-xs font-bold text-gray-400 uppercase mb-1">Vía de Administración</span>
                        <p className="text-gray-700 font-medium">{product.usageMode}</p>
                      </div>
                    )}
                    {product.instructions && (
                      <div>
                        <span className="block text-xs font-bold text-gray-400 uppercase mb-1">Instrucciones</span>
                        <p className="text-gray-600 leading-relaxed">{product.instructions}</p>
                      </div>
                    )}
                  </div>
                </div>
              )}

              {hasPrecautionsInfo && (
                <div className="bg-white p-8 rounded-3xl shadow-sm border border-gray-100">
                  <h3 className="text-lg font-bold text-gray-900 mb-4 flex items-center gap-2">
                    <span className="bg-red-50 text-red-500 p-2 rounded-lg">⚠️</span> Precauciones
                  </h3>
                  <div className="space-y-4">
                    {product.recommendedFor && (
                      <div>
                        <span className="block text-xs font-bold text-gray-400 uppercase mb-1">Recomendado para</span>
                        <p className="text-gray-700 font-medium">{product.recommendedFor}</p>
                      </div>
                    )}
                    <div>
                      <span className="block text-xs font-bold text-gray-400 uppercase mb-1">Advertencias</span>
                      <p className="text-gray-600 leading-relaxed">
                        {product.precautions || "En caso de duda o reacción adversa, consulte a su médico o farmacéutico."}
                      </p>
                    </div>
                  </div>
                </div>
              )}
            </div>
          )}

        </div>
      </div>
    </>
  );
};

export default ProductPage;