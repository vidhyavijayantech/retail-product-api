package com.retail.product.api.repository;

import com.retail.product.api.model.Colour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColourRepository extends JpaRepository<Colour, Long> {}