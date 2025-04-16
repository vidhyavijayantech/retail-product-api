package com.retail.product.api.service;

import com.retail.product.api.dto.ProductCreateRequest;
import com.retail.product.api.dto.ProductDetailDTO;
import com.retail.product.api.dto.ProductSimpleDTO;
import com.retail.product.api.model.Colour;
import com.retail.product.api.model.Product;
import com.retail.product.api.model.ProductType;
import com.retail.product.api.repository.ColourRepository;
import com.retail.product.api.repository.ProductRepository;
import com.retail.product.api.repository.ProductTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductTypeRepository productTypeRepository;
    private ColourRepository colourRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productTypeRepository = mock(ProductTypeRepository.class);
        colourRepository = mock(ColourRepository.class);
        productService = new ProductService(productRepository, productTypeRepository, colourRepository);
    }

    @Test
    void testCreateProduct() {
        ProductCreateRequest request = new ProductCreateRequest();
        request.name = "Test Product";
        request.productType = 1L;
        request.colours = Arrays.asList(1L, 2L);

        ProductType productType = new ProductType();
        productType.setId(1L);
        productType.setName("Sofa");

        Colour red = new Colour();
        red.setId(1L);
        red.setName("Red");

        Colour blue = new Colour();
        blue.setId(2L);
        blue.setName("Blue");

        when(productTypeRepository.findById(1L)).thenReturn(Optional.of(productType));
        when(colourRepository.findAllById(request.colours)).thenReturn(Arrays.asList(red, blue));

        Product savedProduct = new Product();
        savedProduct.setId(100L);
        savedProduct.setName(request.name);
        savedProduct.setProductType(productType);
        savedProduct.setColours(Arrays.asList(red, blue));

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product created = productService.createProduct(request);

        assertNotNull(created);
        assertEquals("Test Product", created.getName());
        assertEquals("Sofa", created.getProductType().getName());
        assertEquals(2, created.getColours().size());
    }

    @Test
    void testGetAllProducts() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));

        when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);

        Page<ProductSimpleDTO> result = productService.getAllProducts(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Product 1", result.getContent().get(0).name);
    }

    @Test
    void testGetProductById() {
        ProductType type = new ProductType();
        type.setId(1L);
        type.setName("Table");

        Colour colour = new Colour();
        colour.setId(5L);
        colour.setName("Green");

        Product product = new Product();
        product.setId(99L);
        product.setName("Fancy Table");
        product.setProductType(type);
        product.setColours(Collections.singletonList(colour));

        when(productRepository.findById(99L)).thenReturn(Optional.of(product));

        ProductDetailDTO dto = productService.getProductById(99L);

        assertNotNull(dto);
        assertEquals(99L, dto.id);
        assertEquals("Fancy Table", dto.name);
        assertEquals("Table", dto.productType.name);
        assertEquals(1, dto.colours.size());
        assertEquals("Green", dto.colours.get(0).name);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(123L)).thenReturn(Optional.empty());
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            productService.getProductById(123L);
        });
        assertEquals("Product not found", thrown.getMessage());
    }

    @Test
    void testCreateProduct_ProductTypeNotFound() {
        ProductCreateRequest request = new ProductCreateRequest();
        request.name = "Invalid";
        request.productType = 404L;

        when(productTypeRepository.findById(404L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            productService.createProduct(request);
        });

        assertEquals("Product type not found", thrown.getMessage());
    }
}
