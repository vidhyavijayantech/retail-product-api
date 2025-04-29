package com.retail.product.api.controller;

import com.retail.product.api.dto.ProductCreateRequest;
import com.retail.product.api.dto.ProductDetailDTO;
import com.retail.product.api.dto.ProductSimpleDTO;
import com.retail.product.api.model.Colour;
import com.retail.product.api.model.Product;
import com.retail.product.api.model.ProductType;
import com.retail.product.api.repository.ColourRepository;
import com.retail.product.api.repository.ProductRepository;
import com.retail.product.api.repository.ProductTypeRepository;
import com.retail.product.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ColourRepository colourRepository;

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductCreateRequest req) {
        productService.createProduct(req);
        return ResponseEntity.ok("Product created");
    }

    @GetMapping
    public Page<ProductSimpleDTO> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }


    @GetMapping("/{id}")
    public ProductDetailDTO getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }
}
