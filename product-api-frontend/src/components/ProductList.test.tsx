import { render, screen, waitFor } from '@testing-library/react';
import { vi } from 'vitest';
import ProductList from './ProductList'; 
import { BrowserRouter } from 'react-router-dom';

beforeEach(() => {
  vi.stubGlobal('fetch', vi.fn());
});

afterEach(() => {
  vi.resetAllMocks(); 
});

describe('ProductList Component', () => {
  it('should render the list of products correctly', async () => {
    (fetch as vi.Mock)
      .mockResolvedValueOnce({
        json: () =>
          Promise.resolve({
            content: [
              { id: 1, name: 'LANDSKRONA' },
              { id: 2, name: 'MALMO' },
            ],
            totalPages: 3,
          }),
      })
      .mockResolvedValueOnce({
        json: () =>
          Promise.resolve({
            productType: { id: 1, name: 'Sofa' },
            colours: [{ id: 1, name: 'Red' }, { id: 2, name: 'Blue' }],
          })
      })
      .mockResolvedValueOnce({
        json: () =>
          Promise.resolve({
            productType: { id: 1, name: 'Sofa' },
            colours: [{ id: 1, name: 'Red' }, { id: 2, name: 'Blue' }],
          })
      });

    render(
      <BrowserRouter>
        <ProductList />
      </BrowserRouter>
    );

    await waitFor(() => expect(fetch).toHaveBeenCalledTimes(3));

    expect(screen.getByText('LANDSKRONA')).toBeInTheDocument();
    expect(screen.getByText('MALMO')).toBeInTheDocument();

    expect(screen.getByText('Page 1 of 3')).toBeInTheDocument();
  });

  it('should show "No products found" if no products are returned', async () => {
    (fetch as vi.Mock)
      .mockResolvedValueOnce({
        json: () =>
          Promise.resolve({
            content: [],
            totalPages: 1,
          }),
      });

    render(
      <BrowserRouter>
        <ProductList />
      </BrowserRouter>
    );

    await waitFor(() => expect(fetch).toHaveBeenCalledTimes(1));

    expect(screen.getByText('No products found.')).toBeInTheDocument();
  });
});
