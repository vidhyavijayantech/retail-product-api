package com.retail.product.api.controller;

import com.retail.product.api.model.Colour;
import com.retail.product.api.repository.ColourRepository;
import com.retail.product.api.service.ColourService;
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

    private final ColourService colourService;

    @GetMapping
    public List<Map<String, Object>> getAllColours() {
        return colourService.getAllColours();
    }
}
