import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { ProductDetail } from '../types/productTypes';

export default function ProductDetailPage() {
  const { id } = useParams();
  const [product, setProduct] = useState<ProductDetail | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (id) {
      fetchProductDetails(parseInt(id, 10));
    }
  }, [id]);

  const fetchProductDetails = async (productId: number) => {
    try {
      const response = await fetch(`/api/products/${productId}`);
      if (!response.ok) throw new Error('Failed to fetch product');
      const data: ProductDetail = await response.json();
      setProduct(data);
    } catch (error) {
      console.error('Error fetching product details:', error);
      setError('Error fetching product details. Please try again.');
    }
  };

  return ( !error &&
    <div className="product-detail">
      {product ? (
        <div>
          <h2>Product Details</h2>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Type</th>
                <th>Colours</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{product.id}</td>
                <td>{product.name}</td>
                <td>{product.productType.name}</td>
                <td>{product.colours.map((colour) => colour.name).join(', ')}</td>
              </tr>
            </tbody>
          </table>
        </div>
      ) : (
        <p>Loading product details...</p>
      )}
    </div>
  );
}
