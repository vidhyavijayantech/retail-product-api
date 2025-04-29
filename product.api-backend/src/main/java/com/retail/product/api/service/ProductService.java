package com.retail.product.api.service;

import com.retail.product.api.dto.ProductCreateRequest;
import com.retail.product.api.dto.ProductDetailDTO;
import com.retail.product.api.dto.ProductSimpleDTO;
import com.retail.product.api.exception.ResourceNotFoundException;
import com.retail.product.api.model.Colour;
import com.retail.product.api.model.Product;
import com.retail.product.api.model.ProductType;
import com.retail.product.api.repository.ColourRepository;
import com.retail.product.api.repository.ProductRepository;
import com.retail.product.api.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ColourRepository colourRepository;

    public Product createProduct(ProductCreateRequest request) {
        log.info("Creating product with name: {}", request.name);
        Product product = new Product();
        product.setName(request.name);

        ProductType type = productTypeRepository.findById(request.productType)
                .orElseThrow(() -> new ResourceNotFoundException("Product type not found"));
        product.setProductType(type);

        List<Colour> colours = colourRepository.findAllById(request.colours);
        product.setColours(colours);

        log.debug("Saving product: {}", product);
        return productRepository.save(product);
    }

    public Page<ProductSimpleDTO> getAllProducts(Pageable pageable) {
        log.info("Fetching products page {} size {}", pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by(Sort.Direction.DESC, "createdAt")))
                .map(p -> {
                    ProductSimpleDTO dto = new ProductSimpleDTO();
                    dto.id = p.getId();
                    dto.name = p.getName();
                    return dto;
                });
    }

    public ProductDetailDTO getProductById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        ProductDetailDTO dto = new ProductDetailDTO();
        dto.id = p.getId();
        dto.name = p.getName();

        ProductDetailDTO.ProductTypeDTO typeDTO = new ProductDetailDTO.ProductTypeDTO();
        typeDTO.id = p.getProductType().getId();
        typeDTO.name = p.getProductType().getName();
        dto.productType = typeDTO;

        dto.colours = p.getColours().stream().map(c -> {
            ProductDetailDTO.ColourDTO cDTO = new ProductDetailDTO.ColourDTO();
            cDTO.id = c.getId();
            cDTO.name = c.getName();
            return cDTO;
        }).collect(Collectors.toList());

        return dto;
    }
}
