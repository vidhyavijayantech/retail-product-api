package com.retail.product.api.controller;

import com.retail.product.api.repository.ColourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/colours")
@RequiredArgsConstructor
public class ColourController {

    private final ColourRepository colourRepository;

    @GetMapping
    public List<Map<String, Object>> getAllColours() {
        return colourRepository.findAll().stream().map(colour -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", colour.getId());
            map.put("name", colour.getName());
            return map;
        }).collect(Collectors.toList());
    }
}
