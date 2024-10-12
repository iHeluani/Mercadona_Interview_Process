package com.mercadona.Interview.Repository;

import com.mercadona.Interview.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findByEan(String ean);
}
