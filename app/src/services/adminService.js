import { mockProducts } from '../data/products';

// Simulamos datos de clientes (incluyendo el token como pediste para debug)
const mockUsers = [
  { id: 1, name: "Juan Pérez", email: "juan@gmail.com", role: "user", token: "eyJhbGciOiJIUz...", lastLogin: "2023-12-08" },
  { id: 2, name: "Admin Principal", email: "admin@sanus.cl", role: "admin", token: "admin-token-123", lastLogin: "2023-12-08" },
];

const mockCoupons = [
  { id: 1, code: "VERANO2024", discount: 20, active: true },
];

// Simulamos latencia de red
const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));

const createCategory = async (categoryData) => {
    // categoryData será { name: "Nueva Categoría" }
    try {
        // Debes enviar la data al endpoint de creación de categorías
        const response = await axios.post(`${API_URL}/categories`, categoryData);
        // El backend idealmente responde con el objeto de la categoría creada (incluyendo su ID)
        return response.data; 
    } catch (error) {
        // Lanza el error para que el componente (AdminProducts) lo capture y lo muestre
        throw new Error(error.response?.data?.message || 'Fallo al crear la categoría en el servidor.');
    }
};

export const adminService = {
  // --- 1. ANALÍTICAS ---
  getStats: async () => {
    await delay(500);
    return {
      totalSales: 1549900,
      totalOrders: 45,
      totalUsers: mockUsers.length,
      totalProducts: mockProducts.length
    };
  },

  // --- 2. PRODUCTOS (CRUD) ---
  createProduct: async (productData) => {
    await delay(800);
    console.log("🔥 Enviando a Backend (CREATE):", productData);
    return { ...productData, id: Math.floor(Math.random() * 1000) };
  },

  updateProduct: async (id, productData) => {
    await delay(800);
    console.log(`🔥 Enviando a Backend (UPDATE ID: ${id}):`, productData);
    return true;
  },

  deleteProduct: async (id) => {
    await delay(600);
    console.log(`🔥 Enviando a Backend (DELETE ID: ${id})`);
    return true;
  },

  // --- 3. CLIENTES & TOKENS ---
  getUsers: async () => {
    await delay(500);
    // AQUÍ el backend decidirá si enviar el token o no.
    return [...mockUsers]; 
  },

  // --- 4. CUPONES ---
  getCoupons: async () => {
    await delay(300);
    return [...mockCoupons];
  },

  createCoupon: async (couponData) => {
    await delay(500);
    console.log("🔥 Creando cupón:", couponData);
    return { ...couponData, id: Math.random() };
  }
};