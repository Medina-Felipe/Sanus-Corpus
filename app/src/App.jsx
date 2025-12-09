import React from 'react';
import { useLocation } from 'react-router-dom'; 
import Header from "./components/Layout/Header"; 
import Footer from "./components/Layout/Footer"; 
import RoutesConfig from "./RoutesConfig"; 

const App = () => {
  const location = useLocation();

  const hideLayoutPaths = ['/login', '/registro'];

  const shouldHideLayout = hideLayoutPaths.includes(location.pathname);

  return (
    <div className="flex flex-col min-h-screen">
      
      {!shouldHideLayout && <Header />} 
      
      <main className="flex-grow">
        <RoutesConfig /> 
      </main>

      {!shouldHideLayout && <Footer />} 
      
    </div>
  );
};

export default App;