// src/pages/__tests__/ProductDetailPage.test.tsx
import { render, screen, waitFor } from '@testing-library/react';
import ProductDetailPage from './ProductDetailPage';
import { vi } from 'vitest';
import { MemoryRouter, Route, Routes } from 'react-router-dom';

describe('ProductDetailPage', () => {
  beforeEach(() => {
    vi.stubGlobal('fetch', vi.fn());
  });

  afterEach(() => {
    vi.resetAllMocks();
  });

  it('renders product details when API call succeeds', async () => {
    const mockProduct = {
      id: 1,
      name: 'LANDSKRONA',
      productType: {
        id: 2,
        name: 'Sofa',
      },
      colours: [
        { id: 1, name: 'Red' },
        { id: 2, name: 'Blue' },
      ],
    };

    (fetch as vi.Mock).mockResolvedValueOnce({
      ok: true,
      json: async () => mockProduct,
    });

    render(
      <MemoryRouter initialEntries={['/products/1']}>
        <Routes>
          <Route path='/products/:id' element={<ProductDetailPage />} />
        </Routes>
      </MemoryRouter>
    );

    expect(screen.getByText(/Loading product details/i)).toBeInTheDocument();

    await waitFor(() => {
      expect(screen.getByText('Product Details')).toBeInTheDocument();
      expect(screen.getByText('LANDSKRONA')).toBeInTheDocument();
      expect(screen.getByText('Sofa')).toBeInTheDocument();
      expect(screen.getByText('Red, Blue')).toBeInTheDocument();
    });
  });
});
