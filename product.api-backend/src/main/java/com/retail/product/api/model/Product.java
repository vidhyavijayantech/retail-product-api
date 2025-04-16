package com.retail.product.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private ProductType productType;

    @ManyToMany
    private List<Colour> colours = new ArrayList<>();

    private Date createdAt = new Date();
}
