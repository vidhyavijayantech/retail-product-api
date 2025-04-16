package com.retail.product.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.product.api.dto.*;
import com.retail.product.api.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(ProductControllerTest.Config.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    @TestConfiguration
    static class Config {
        @Bean
        public ProductService productService() {
            return mock(ProductService.class);
        }

        @Bean
        public com.retail.product.api.repository.ProductRepository productRepository() {
            return mock(com.retail.product.api.repository.ProductRepository.class);
        }

        @Bean
        public com.retail.product.api.repository.ProductTypeRepository productTypeRepository() {
            return mock(com.retail.product.api.repository.ProductTypeRepository.class);
        }

        @Bean
        public com.retail.product.api.repository.ColourRepository colourRepository() {
            return mock(com.retail.product.api.repository.ColourRepository.class);
        }
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest();
        request.name = "LANDSKRONA";
        request.productType = 1L;
        request.colours = Arrays.asList(1L, 2L);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Product created"));
    }

    @Test
    void testGetAllProducts() throws Exception {
        ProductSimpleDTO dto = new ProductSimpleDTO();
        dto.id = 1L;
        dto.name = "LANDSKRONA";

        Page<ProductSimpleDTO> page = new PageImpl<>(List.of(dto));

        when(productService.getAllProducts(any())).thenReturn(page);

        mockMvc.perform(get("/api/products?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id").value(1));
    }

    @Test
    void testGetProductById() throws Exception {
        ProductDetailDTO dto = new ProductDetailDTO();
        dto.id = 1L;
        dto.name = "LANDSKRONA";

        ProductDetailDTO.ProductTypeDTO type = new ProductDetailDTO.ProductTypeDTO();
        type.id = 1L;
        type.name = "Sofa";
        dto.productType = type;

        ProductDetailDTO.ColourDTO color = new ProductDetailDTO.ColourDTO();
        color.id = 2L;
        color.name = "Red";
        dto.colours = List.of(color);

        when(productService.getProductById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("LANDSKRONA"))
                .andExpect(jsonPath("$.productType.name").value("Sofa"))
                .andExpect(jsonPath("$.colours[0].name").value("Red"));
    }
}
