package com.retail.product.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ProductCreateRequest {
    @NotBlank(message = "Name is required")
    public String name;

    @NotNull(message = "Product type is required")
    public Long productType;

    @NotNull(message = "Colour is required")
    public List<Long> colours;
}