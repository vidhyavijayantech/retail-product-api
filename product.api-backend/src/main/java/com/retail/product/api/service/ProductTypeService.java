package com.retail.product.api.service;

import com.retail.product.api.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    public List<Map<String, Object>> getAllProductTypes() {
        log.info("Fetching all product types");
        return productTypeRepository.findAll().stream().map(pt -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", pt.getId());
            map.put("name", pt.getName());
            return map;
        }).collect(Collectors.toList());
    }
}
