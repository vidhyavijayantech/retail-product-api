import { useEffect, useState } from 'react';
import { ProductDetail, Colour, ProductType } from '../types/productTypes';
import { useNavigate } from 'react-router-dom';

interface PaginatedResponse {
  content: ProductDetail[];
  totalPages: number;
}

export default function ProductList() {
  const [products, setProducts] = useState<ProductDetail[]>([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const pageSize = 10;
  const navigate = useNavigate();

  useEffect(() => {
    fetchProducts(page);
  }, [page]);

  const fetchProducts = async (page: number) => {
    try {
      const response = await fetch(
        `/api/products?page=${page}&size=${pageSize}`
      );
      const data: PaginatedResponse = await response.json();

      const productList = await Promise.all(
        data.content.map(async (p) => {
          const detailRes = await fetch(`/api/products/${p.id}`);
          const detailData: { productType: ProductType; colours: Colour[] } =
            await detailRes.json();

          return {
            id: p.id,
            name: p.name,
            productType: detailData.productType,
            colours: detailData.colours,
          };
        })
      );

      setProducts(productList);
      setTotalPages(data.totalPages);
    } catch (err) {
      console.error('Error fetching products:', err);
    }
  };

  const handleRowClick = (id: number) => {
    navigate(`/product/${id}`); // Navigate to the product detail page
  };

  const handlePrevPage = () => {
    if (page > 0) setPage(page - 1);
  };

  const handleNextPage = () => {
    if (page < totalPages - 1) setPage(page + 1);
  };

  return (
    <div className='product-detail'>
      <h2>Welcome to the store!</h2>
      <table>
        <thead>
          <tr>
            <th>Products</th>
          </tr>
        </thead>
        <tbody>
          {products.length > 0 ? (
            products.map((product) => (
              <tr key={product.id} onClick={() => handleRowClick(product.id)} className='product-item'>
                <td>{product.name}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={4}>No products found.</td>
            </tr>
          )}
        </tbody>
      </table>
      <div className='pagination-controls' style={{ marginTop: '1rem' }}>
        <button
          className='pagination-button'
          onClick={handlePrevPage}
          disabled={page === 0}
        >
          Previous
        </button>
        <span style={{ margin: '0 1rem' }}>
          Page {page + 1} of {totalPages}
        </span>
        <button
          className='pagination-button'
          onClick={handleNextPage}
          disabled={page >= totalPages - 1}
        >
          Next
        </button>
      </div>
    </div>
  );
}
