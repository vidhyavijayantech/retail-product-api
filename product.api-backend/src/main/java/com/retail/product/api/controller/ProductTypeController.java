package com.retail.product.api.controller;

import com.retail.product.api.repository.ProductTypeRepository;
import com.retail.product.api.service.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product-types")
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @GetMapping
    public List<Map<String, Object>> getAllProductTypes() {
        return productTypeService.getAllProductTypes();
    }
}
