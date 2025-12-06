import { describe, test, expect, vi } from 'vitest'; 
import { render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import App from '../App'; 


// MOCKS
vi.mock('../Pages/HomePage', () => ({ default: () => <div>Home Page Content: Productos Destacados</div> }));
vi.mock('../Pages/ProductPage', () => ({ default: () => <div>Product Page Content: Detalle de Producto</div> }));
vi.mock('../Pages/SearchPage', () => ({ default: () => <div>Search Page Content: Resultados para: ' '</div> }));
vi.mock('../Pages/CartPage', () => ({ default: () => <div>Cart Page Content: Resumen de la Orden</div> }));


describe('Integración de Rutas de la Aplicacion', () => {

  const setupTest = (path) => {
    return render(
      <MemoryRouter initialEntries={[path]}>
        <App />
      </MemoryRouter>
    );
  };

  // TI-01
  test('TI-01: Renderiza la HomePage en la ruta /', async () => {
    setupTest('/');
    await waitFor(() => {
        expect(screen.getByText(/Productos Destacados/i)).toBeInTheDocument();
    });
  });

  // TI-04
  test('TI-04: Renderiza la CartPage en la ruta /carrito', async () => {
    setupTest('/carrito');
    await waitFor(() => {
      expect(screen.getByText(/Resumen de la Orden/i)).toBeInTheDocument();
    });
  });

  // TI-02
  test('TI-02: Renderiza la ProductPage en la ruta /productos/:slug', async () => {
    setupTest('/productos/ejemplo-slug-1');
    await waitFor(() => {
      expect(screen.getByText(/Detalle de Producto/i)).toBeInTheDocument();
    });
  });

  // TI-03
  test('TI-03: Renderiza la SearchPage en la ruta /search/:query', async () => {
    setupTest('/search/vitaminas');
    await waitFor(() => {
      expect(screen.getByText(/Resultados para:/i)).toBeInTheDocument();
    });
  });
});