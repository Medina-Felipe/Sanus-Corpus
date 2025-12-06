import React from "react";
import { categories } from "../../data/categories";

const CategoriesBar = ({ selectedCategory, onCategorySelect }) => {
  return (
    <div className="bg-white shadow-sm border-b border-gray-200">
      <div className="max-w-7xl mx-auto px-4">
        <div className="flex space-x-6 overflow-x-auto py-4 scrollbar-hide">
          {categories.map(category => (
            <button
              key={category.id}
              onClick={() => onCategorySelect(category)}
              className={`flex flex-col items-center space-y-2 min-w-[90px] transition-all duration-300 ${
                selectedCategory?.id === category.id 
                  ? 'text-[#0D99FF] transform scale-105' 
                  : 'text-gray-600 hover:text-[#0D99FF]'
              }`}
            >
              <div className={`text-2xl p-3 rounded-full transition-all ${
                selectedCategory?.id === category.id 
                  ? 'bg-[#0D99FF] text-white shadow-md' 
                  : 'bg-gray-100 hover:bg-blue-50'
              }`}>
                {category.icon}
              </div>
              <span className="text-xs font-medium text-center leading-tight whitespace-nowrap">
                {category.name}
              </span>
            </button>
          ))}
        </div>
      </div>
    </div>
  );
};

export default CategoriesBar;