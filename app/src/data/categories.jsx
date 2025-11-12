// src/data/categories.js

// DATOS MOCK - Eliminar cuando tengas backend
export const categories = [
  { 
    id: 1, 
    name: "Dermocosmética", 
    slug: "dermocosmetica", 
    icon: "✨",
    description: "Productos dermatológicos y cosméticos de calidad"
  },
  { 
    id: 2, 
    name: "Medicamentos", 
    slug: "medicamentos", 
    icon: "💊",
    description: "Medicamentos y fármacos esenciales"
  },
  { 
    id: 3, 
    name: "Vitaminas y Suplementos", 
    slug: "vitaminas-suplementos", 
    icon: "🌿",
    description: "Vitaminas y suplementos alimenticios"
  },
  { 
    id: 4, 
    name: "Anticonceptivos", 
    slug: "anticonceptivos", 
    icon: "🔒",
    description: "Métodos anticonceptivos seguros"
  },
  { 
    id: 5, 
    name: "Infantil y Mamá", 
    slug: "infantil-mama", 
    icon: "👶",
    description: "Productos para bebés y mamás"
  },
  { 
    id: 6, 
    name: "Cuidado de la Piel", 
    slug: "cuidado-piel", 
    icon: "🧴",
    description: "Cremas y tratamientos faciales"
  },
  { 
    id: 7, 
    name: "Higiene y Cuidado Personal", 
    slug: "higiene-cuidado-personal", 
    icon: "🚿",
    description: "Productos de higiene personal"
  },
  { 
    id: 8, 
    name: "Cuidado Capilar", 
    slug: "cuidado-capilar", 
    icon: "💇",
    description: "Shampoos y tratamientos capilares"
  },
  { 
    id: 9, 
    name: "Belleza", 
    slug: "belleza", 
    icon: "💄",
    description: "Productos de belleza y maquillaje"
  }
];

// 🚀 PARA BACKEND: Reemplazar con esta función
/*
export const useCategories = () => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        // ✅ CONEXIÓN BACKEND: Descomentar cuando tengas API
        // const response = await fetch('/api/categories');
        // const data = await response.json();
        // setCategories(data);
        
        // ⏳ Por ahora usa datos mock
        setCategories(staticCategories);
      } catch (error) {
        console.error('Error fetching categories:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchCategories();
  }, []);

  return { categories, loading };
};
*/