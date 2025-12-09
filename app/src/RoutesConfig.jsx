import { Routes, Route } from 'react-router-dom';

// Layouts
import StoreLayout from './components/Layout/StoreLayout';
import AdminLayout from './components/Admin/AdminLayout';

// Páginas Tienda (Según tus archivos reales)
import HomePage from './Pages/HomePage';
import ProductPage from './Pages/ProductPage';
import CartPage from './Pages/CartPage'; 
import WishlistPage from './Pages/WishlistPage';
import LoginPage from './Pages/LoginPage';
import ProfilePage from './Pages/ProfilePage'; // ✅ Usaremos esta para Perfil y Configuración
import RegisterPage from './Pages/RegisterPage'; 
import SearchPage from './Pages/SearchPage';     

// Páginas de Información / Usuario
import OrdersPage from './Pages/OrdersPage';
import ContactPage from './Pages/ContactPage';   
import FAQPage from './Pages/FAQPage';           
import TermsPage from './Pages/TermsPage';       

// Páginas Admin
import AdminDashboard from './Pages/Admin/AdminDashboard';
import AdminProducts from './Pages/Admin/AdminProducts';
import AdminUsers from './Pages/Admin/AdminUsers';

const RoutesConfig = () => {
  return (
    <Routes>
      
      {/* --- TIENDA PÚBLICA --- */}
      <Route element={<StoreLayout />}>
        {/* Principales */}
        <Route path="/" element={<HomePage />} />
        <Route path="/productos/:id" element={<ProductPage />} />
        <Route path="/carrito" element={<CartPage />} />
        <Route path="/search/:query" element={<SearchPage />} />
        
        {/* Usuario */}
        <Route path="/login" element={<LoginPage />} />
        <Route path="/registro" element={<RegisterPage />} />
        
        <Route path="/perfil" element={<ProfilePage />} />
        <Route path="/configuracion" element={<ProfilePage />} />
        
        <Route path="/favoritos" element={<WishlistPage />} />
        <Route path="/mis-pedidos" element={<OrdersPage />} />

        {/* Información y Soporte */}
        <Route path="/soporte" element={<ContactPage />} />
        <Route path="/preguntas-frecuentes" element={<FAQPage />} />
        <Route path="/terminos" element={<TermsPage />} />
      </Route>


      {/* --- ADMIN --- */}
      <Route path="/admin" element={<AdminLayout />}>
         <Route index element={<AdminDashboard />} />
         <Route path="productos" element={<AdminProducts />} />
         <Route path="clientes" element={<AdminUsers />} />
      </Route>

      {/* 404 */}
      <Route path="*" element={<div className="p-10 text-center"><h1>404 Página no encontrada</h1></div>} />

    </Routes>
  );
};

export default RoutesConfig;