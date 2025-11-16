package com.dalal.all_in_one.repositories;

import com.dalal.all_in_one.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
