import { Routes, Route } from 'react-router-dom';
import HomePage from "./Pages/HomePage";
import ProductPage from "./Pages/ProductPage"; 
import SearchPage from "./Pages/SearchPage"; 
import CartPage from "./Pages/CartPage";


const RoutesConfig = () => {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/productos/:slug" element={<ProductPage />} />
      <Route path="/search" element={<SearchPage />} />
      <Route path="/search/:query" element={<SearchPage />} />
      <Route path="/carrito" element={<CartPage />} />
    </Routes>
  );
};

export default RoutesConfig;