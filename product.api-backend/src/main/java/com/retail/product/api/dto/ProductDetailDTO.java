package com.retail.product.api.dto;

import java.util.List;

public class ProductDetailDTO {
    public Long id;
    public String name;
    public ProductTypeDTO productType;
    public List<ColourDTO> colours;

    public static class ProductTypeDTO {
        public Long id;
        public String name;
    }

    public static class ColourDTO {
        public Long id;
        public String name;
    }
}