import Header from "./components/Layout/Header"; 
import Footer from "./components/Layout/Footer"; 
import RoutesConfig from "./RoutesConfig"; 

const App = () => {
  return (
    <div className="flex flex-col min-h-screen">
      <Header /> 
      <main className="flex-grow">
        <RoutesConfig /> 
      </main>
      <Footer /> 
    </div>
  );
};

export default App;