import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import ProductForm from './ProductForm';
import { vi } from 'vitest';

beforeEach(() => {
  vi.stubGlobal('fetch', vi.fn());
});

afterEach(() => {
  vi.resetAllMocks();
});

describe('ProductForm', () => {
  const mockTypes = [{ id: 1, name: 'Shoes' }];
  const mockColours = [
    { id: 1, name: 'Red' },
    { id: 2, name: 'Green' },
  ];

  it('renders form and loads types/colours', async () => {
    (fetch as vi.Mock).mockResolvedValueOnce({
      ok: true,
      json: async () => mockTypes,
    }).mockResolvedValueOnce({
      ok: true,
      json: async () => mockColours,
    });

    render(<ProductForm />);

    await waitFor(() => {
      expect(screen.getByText('Shoes')).toBeInTheDocument();
      expect(screen.getByLabelText('Red')).toBeInTheDocument();
      expect(screen.getByLabelText('Green')).toBeInTheDocument();
    });
  });

  it('submits form successfully and resets fields', async () => {
    (fetch as vi.Mock).mockResolvedValueOnce({
      ok: true,
      json: async () => mockTypes,
    }).mockResolvedValueOnce({
      ok: true,
      json: async () => mockColours,
    });

    (fetch as vi.Mock).mockResolvedValueOnce({ ok: true });

    render(<ProductForm />);

    await waitFor(() => screen.getByText('Shoes'));

    fireEvent.change(screen.getByLabelText(/Product Name/i), {
      target: { value: 'Cool Sneakers' },
    });

    fireEvent.change(screen.getByLabelText(/Product Type/i), {
      target: { value: '1' },
    });

    fireEvent.click(screen.getByLabelText('Red'));
    fireEvent.click(screen.getByText(/Create Product/i));

    await waitFor(() =>
      expect(screen.getByText(/Product added sucessfully/i)).toBeInTheDocument()
    );
  });

  it('shows error on failed POST', async () => {
    (fetch as vi.Mock).mockResolvedValueOnce({
      ok: true,
      json: async () => mockTypes,
    }).mockResolvedValueOnce({
      ok: true,
      json: async () => mockColours,
    });

    (fetch as vi.Mock).mockResolvedValueOnce({ ok: false });

    render(<ProductForm />);

    await waitFor(() => screen.getByText('Shoes'));

    fireEvent.change(screen.getByLabelText(/Product Name/i), {
      target: { value: 'Fail Product' },
    });

    fireEvent.change(screen.getByLabelText(/Product Type/i), {
      target: { value: '1' },
    });

    fireEvent.click(screen.getByLabelText('Red'));
    fireEvent.click(screen.getByText(/Create Product/i));

    await waitFor(() =>
      expect(screen.getByText(/Sorry! Cannot add new product/i)).toBeInTheDocument()
    );
  });
});
