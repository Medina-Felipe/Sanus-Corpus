import { Routes, Route } from 'react-router-dom';
import HomePage from "./Pages/HomePage";
import ProductPage from "./Pages/ProductPage";
import SearchPage from "./Pages/SearchPage";
import CartPage from "./Pages/CartPage";
import LoginPage from "./Pages/LoginPage";      
import RegisterPage from "./Pages/RegisterPage"; 
import FAQPage from './Pages/FAQPage';
import WishlistPage from './Pages/WishlistPage';
import OrdersPage from './Pages/OrdersPage';
import ProfilePage from "./Pages/ProfilePage";
import ContactPage from "./Pages/ContactPage";
import TermsPage from "./Pages/TermsPage";

const RoutesConfig = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/productos/:id" element={<ProductPage />} />
      <Route path="/search" element={<SearchPage />} />
      <Route path="/search/:query" element={<SearchPage />} />
      <Route path="/carrito" element={<CartPage />} />
      <Route path="/login" element={<LoginPage />} />       
      <Route path="/registro" element={<RegisterPage />} /> 
      <Route path="/faq" element={<FAQPage />} />
      <Route path="/favoritos" element={<WishlistPage />} />
      <Route path="/pedidos" element={<OrdersPage />} />
      <Route path="/perfil" element={<ProfilePage />} />
      <Route path="/contacto" element={<ContactPage />} />
      <Route path="/terminos" element={<TermsPage />} />

    </Routes>
  );
};

export default RoutesConfig;