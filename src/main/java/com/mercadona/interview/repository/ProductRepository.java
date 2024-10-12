package com.mercadona.interview.repository;

import com.mercadona.interview.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findByEan(String ean);
}
