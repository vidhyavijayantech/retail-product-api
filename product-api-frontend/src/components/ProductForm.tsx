import { useEffect, useState } from 'react';
import { ProductType, Colour } from '../types/productTypes';

export default function ProductForm() {
  const [name, setName] = useState('');
  const [productTypes, setProductTypes] = useState<ProductType[]>([]);
  const [colours, setColours] = useState<Colour[]>([]);
  const [selectedType, setSelectedType] = useState('');
  const [selectedColours, setSelectedColours] = useState<number[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);

  useEffect(() => {
    fetch('/api/product-types')
      .then((res) => res.json())
      .then((data: ProductType[]) => setProductTypes(data));

    fetch('/api/colours')
      .then((res) => res.json())
      .then((data: Colour[]) => setColours(data));
  }, []);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setSuccess(null);
    setError(null);
    try {
      const res = await fetch('/api/products', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          name,
          productType: selectedType,
          colours: selectedColours,
        }),
      });

      if (!res.ok) throw new Error('Failed to create product');

      setSuccess('Product added sucessfully!');

      // Wait for the message to be shown, then reset the form and hide it
      setTimeout(() => {
        resetForm();
        //setIsFormVisible(false); // Hide the form after success message is shown
      }, 1000);
    } catch (error) {
      console.error(error);
      alert('Something went wrong. Please try again.');
      setError('Sorry! Cannot add new product at the moment');
    }
  };

  const resetForm = () => {
    setName('');
    setSelectedColours([]);
    setSelectedType('');
    setSuccess(null);
  };

  return (
    <div className='add-product'>
      <form onSubmit={handleSubmit}>
        <h2>Add New Product</h2>

        {/* First Row: Name + Type */}
        <div className='form-row'>
          <label htmlFor='productName'>Product Name</label>
          <input
            id='productName'
            type='text'
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder='Enter product name'
            required
          />
        </div>

        <div className='form-row'>
          <label htmlFor='productType'>Product Type</label>
          <select
            id='productType'
            value={selectedType}
            onChange={(e) => setSelectedType(e.target.value)}
            required
          >
            <option value=''>Select Type</option>
            {productTypes.map((pt) => (
              <option key={pt.id} value={pt.id}>
                {pt.name}
              </option>
            ))}
          </select>
        </div>

        {/* Second Row: Colors */}
        <div className='form-row'>
          <label>Select Colours</label>
          <div className='checkbox-group'>
            {colours.map((colour) => (
              <label key={colour.id} className='checkbox-item'>
                <input
                  type='checkbox'
                  value={colour.id}
                  checked={selectedColours.includes(colour.id)}
                  onChange={(e) => {
                    const value = parseInt(e.target.value, 10);
                    if (e.target.checked) {
                      setSelectedColours([...selectedColours, value]);
                    } else {
                      setSelectedColours(
                        selectedColours.filter((id) => id !== value)
                      );
                    }
                  }}
                />
                {colour.name}
              </label>
            ))}
          </div>
        </div>

        {error && <p className='error'>{error}</p>}
        {success && <p className='success'>{success}</p>}
        <button type='submit' className='submit-button'>
          Create Product
        </button>
      </form>
    </div>
  );
}
