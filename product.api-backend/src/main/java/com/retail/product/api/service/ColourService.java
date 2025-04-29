package com.retail.product.api.service;

import com.retail.product.api.model.Colour;
import com.retail.product.api.repository.ColourRepository;
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
public class ColourService {
    private final ColourRepository colourRepository;

    public List<Map<String, Object>> getAllColours() {
        log.info("Fetching all colours");
        return colourRepository.findAll().stream().map(colour -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", colour.getId());
            map.put("name", colour.getName());
            return map;
        }).collect(Collectors.toList());
    }
}
