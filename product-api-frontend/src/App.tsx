import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import ProductForm from './components/ProductForm';
import ProductList from './components/ProductList';
import ProductDetailPage from './components/ProductDetailPage';
import './styles/App.css';

function App() {
  return (
    <div className="app">
      <Router>
        <nav>
          <Link to="/" className="nav-link">Create Product</Link> |{' '}
          <Link to="/products" className="nav-link">View Products</Link>
        </nav>

        <Routes>
          <Route path="/" element={<ProductForm />} />
          <Route path="/products" element={<ProductList />} />
          <Route path="/product/:id" element={<ProductDetailPage />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
