import React, { useState } from 'react';

const FAQPage = () => {
  const faqs = [
    { q: "¿Cuánto tarda el despacho?", a: "En Santiago de 24 a 48 horas. En regiones de 3 a 5 días hábiles." },
    { q: "¿Tienen tienda física?", a: "Sí, estamos ubicados en Temuco, pero hacemos envíos a todo Chile." },
    { q: "¿Cómo puedo devolver un producto?", a: "Tienes 10 días para devoluciones presentando tu boleta y el producto sellado." },
    { q: "¿Venden medicamentos con receta?", a: "Sí, debes subir la foto de tu receta al momento de pagar." },
  ];

  return (
    <div className="min-h-screen bg-[#F9FAFB] py-10 px-4">
      <div className="max-w-3xl mx-auto">
        <h1 className="text-3xl font-bold text-center text-gray-900 mb-2">Preguntas Frecuentes</h1>
        <p className="text-center text-gray-500 mb-10">Resolvemos tus dudas sobre nuestros servicios.</p>
        
        <div className="space-y-4">
          {faqs.map((item, index) => (
            <AccordionItem key={index} question={item.q} answer={item.a} />
          ))}
        </div>
      </div>
    </div>
  );
};

const AccordionItem = ({ question, answer }) => {
  const [isOpen, setIsOpen] = useState(false);
  return (
    <div className="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden transition-all duration-300">
      <button 
        className="w-full flex justify-between items-center p-6 text-left font-bold text-gray-800 hover:bg-gray-50 transition-colors"
        onClick={() => setIsOpen(!isOpen)}
      >
        <span>{question}</span>
        <span className={`transform transition-transform duration-300 text-[#0D99FF] ${isOpen ? 'rotate-180' : ''}`}>
           ▼
        </span>
      </button>
      
      <div className={`transition-all duration-300 ease-in-out ${isOpen ? 'max-h-40 opacity-100' : 'max-h-0 opacity-0'}`}>
        <div className="p-6 pt-0 text-gray-600 leading-relaxed border-t border-gray-50 mt-2">
            {answer}
        </div>
      </div>
    </div>
  );
};

export default FAQPage;