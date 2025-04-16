package com.retail.product.api.controller;

import com.retail.product.api.repository.ProductTypeRepository;
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

    private final ProductTypeRepository productTypeRepository;

    @GetMapping
    public List<Map<String, Object>> getAllProductTypes() {
        return productTypeRepository.findAll().stream().map(pt -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", pt.getId());
            map.put("name", pt.getName());
            return map;
        }).collect(Collectors.toList());
    }
}
