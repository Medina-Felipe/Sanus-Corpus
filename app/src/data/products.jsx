import sunscreen from '../images/photo/sunscreen.png';
export const mockProducts = [
  {
    id: 1,
    name: "Protector Solar FPS 50",
    description: "Protector solar facial para piel sensible con protección UVA/UVB",
    price: 'lukita',
    image: "/src/images/photo/sunscreen.png", // ✅ Ruta absoluta desde public
    category: "dermocosmetica",
    stock: 25,
    rating: 4.5
  },
  {
    id: 2,
    name: "Vitamina C 1000mg",
    description: "Suplemento de vitamina C de liberación prolongada",
    price: 'dos lukitas',
    image: "/src/images/photo/vitamin.png", // ✅ Usa misma imagen por ahora
    category: "vitaminas-suplementos",
    stock: 40,
    rating: 4.8
  },
  {
    id: 3,
    name: "Paracetamol 500mg",
    description: "Analgésico y antipirético de venta libre",
    price: 'cinco peso',
    image: "/src/images/photo/paracetamol.png", // ✅ Misma imagen temporal
    category: "medicamentos",
    stock: 100,
    rating: 4.3
  },
  {
    id: 4,
    name: "Shampoo Anticaída",
    description: "Shampoo fortalecedor con biotina y queratina",
    price: 18.75,
    image: "/src/images/photo/shampoo.png",
    category: "cuidado-capilar",
    stock: 30,
    rating: 4.6
  },
  {
    id: 5,
    name: "Crema Hidratante Facial",
    description: "Crema hidratante con ácido hialurónico para piel seca",
    price: 22.50,
    image: "/images/moisturizer.jpg",
    category: "cuidado-piel",
    stock: 35,
    rating: 4.7
  },
  {
    id: 6,
    name: "Anticonceptivos Orales",
    description: "Método anticonceptivo hormonal mensual",
    price: 12.99,
    image: "/images/birth-control.jpg",
    category: "anticonceptivos",
    stock: 20,
    rating: 4.4
  },
  {
    id: 7,
    name: "Pañales Talla 3",
    description: "Pañales desechables ultra absorbentes",
    price: 32.99,
    image: "/images/diapers.jpg",
    category: "infantil-mama",
    stock: 50,
    rating: 4.9
  },
  {
    id: 8,
    name: "Jabón Líquido Antibacterial",
    description: "Jabón líquido con triclosán para manos",
    price: 6.99,
    image: "/images/soap.jpg",
    category: "higiene-cuidado-personal",
    stock: 60,
    rating: 4.2
  },
  {
    id: 9,
    name: "Labial Hidratante con Color",
    description: "Labial con vitamina E y protección solar",
    price: 9.99,
    image: "/images/lipstick.jpg",
    category: "belleza",
    stock: 45,
    rating: 4.5
  },
  {
    id: 10,
    name: "Multivitamínico Completo",
    description: "Complejo multivitamínico con minerales esenciales",
    price: 28.50,
    image: "/images/multivitamin.jpg",
    category: "vitaminas-suplementos",
    stock: 25,
    rating: 4.7
  },
  {
    id: 11,
    name: "Crema para Acné",
    description: "Tratamiento tópico para acné leve a moderado",
    price: 16.75,
    image: "/images/acne-cream.jpg",
    category: "dermocosmetica",
    stock: 30,
    rating: 4.4
  },
  {
    id: 12,
    name: "Suplemento de Hierro",
    description: "Suplemento de hierro para anemia y energía",
    price: 13.25,
    image: "/images/iron-supplement.jpg",
    category: "vitaminas-suplementos",
    stock: 35,
    rating: 4.6
  }
];