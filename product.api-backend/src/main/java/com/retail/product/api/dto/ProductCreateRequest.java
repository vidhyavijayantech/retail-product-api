package com.retail.product.api.dto;

import java.util.List;

public class ProductCreateRequest {
    public String name;
    public Long productType;
    public List<Long> colours;
}