import React, { useState } from "react";
import { categories } from "../../data/categories";

const CategoriesBar = ({ selectedCategory, onCategorySelect }) => {
  const [isExpanded, setIsExpanded] = useState(false);

  return (
    <div className="bg-white border-b border-gray-100 shadow-[0_2px_8px_rgba(0,0,0,0.02)] relative md:sticky md:top-[64px] z-30 transition-all">
      <div className="max-w-7xl mx-auto px-2 py-3">
        
        {/* CONTENEDOR DE ÍCONOS */}
        <div className="flex flex-wrap items-start justify-center gap-x-4 gap-y-4 md:gap-x-8">
          
          {categories.map((category, index) => {
            const isActive = selectedCategory?.id === category.id;
            

            const mobileVisibilityClass = !isExpanded && index >= 5 ? "hidden md:flex" : "flex";

            return (
              <button
                key={category.id}
                onClick={() => onCategorySelect(category)}
                className={`${mobileVisibilityClass} flex-col items-center gap-2 group transition-all duration-300 w-16 md:w-auto`}
              >
                <div className={`w-10 h-10 md:w-14 md:h-14 rounded-full flex items-center justify-center text-xl md:text-3xl transition-all duration-300 border ${
                  isActive 
                    ? 'bg-[#0D99FF] border-[#0D99FF] text-white shadow-md transform -translate-y-1' 
                    : 'bg-gray-50 border-gray-100 text-gray-400 group-hover:border-[#0D99FF] group-hover:text-[#0D99FF] group-hover:bg-white'
                }`}>
                  {category.icon}
                </div>
                
                <span className={`text-[9px] md:text-xs font-bold tracking-wide uppercase text-center leading-tight max-w-[70px] md:max-w-[100px] ${
                  isActive 
                    ? 'text-[#0D99FF]' 
                    : 'text-gray-400 group-hover:text-[#0D99FF]'
                }`}>
                  {category.name}
                </span>
              </button>
            );
          })}
        </div>

        {/* BOTÓN FLECHITA */}
        {categories.length > 5 && (
            <button 
                onClick={() => setIsExpanded(!isExpanded)}
                className="w-full flex justify-center mt-2 md:hidden text-gray-300 hover:text-[#0D99FF] transition-colors"
            >
                {isExpanded ? (
                    // Flecha Arriba (Contraer)
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4 animate-bounce" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 15l7-7 7 7" />
                    </svg>
                ) : (
                    // Flecha Abajo (Ver más)
                    <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4 animate-bounce" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
                    </svg>
                )}
            </button>
        )}

      </div>
    </div>
  );
};

export default CategoriesBar;