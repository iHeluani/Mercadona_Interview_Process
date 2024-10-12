package com.mercadona.Interview.Config;

import com.mercadona.Interview.Model.Product;
import com.mercadona.Interview.Repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

  @Bean
  CommandLineRunner initDatabase(ProductRepository productRepository) {
    return args -> {
      productRepository.save(new Product(null, "8437008459059", "Producto A", "Hacendado", "Tienda Mercadona España"));
      productRepository.save(new Product(null, "8480000160072", "Producto B", "Otro Proveedor", "Tienda Mercadona España"));
      productRepository.save(new Product(null, "8437008460006", "Producto C", "Hacendado", "Tienda Mercadona Portugal"));
      productRepository.save(new Product(null, "8480000190086", "Producto D", "Otro Proveedor", "Tienda Mercadona Portugal"));
      productRepository.save(new Product(null, "8437008459078", "Producto E", "Hacendado", "Almacenes"));
      productRepository.save(new Product(null, "8480000160088", "Producto F", "Otro Proveedor", "Almacenes"));
      productRepository.save(new Product(null, "8437008459099", "Producto G", "Hacendado", "Oficinas Mercadona"));
      productRepository.save(new Product(null, "8480000160099", "Producto H", "Otro Proveedor", "Oficinas Mercadona"));
      productRepository.save(new Product(null, "8437008459000", "Producto I", "Hacendado", "Colmenas"));
      productRepository.save(new Product(null, "8480000160000", "Producto J", "Otro Proveedor", "Colmenas"));
      productRepository.save(new Product(null, "8437008461115", "Producto K", "Hacendado", "Tienda Mercadona España"));
      productRepository.save(new Product(null, "8480000190225", "Producto L", "Otro Proveedor", "Tienda Mercadona España"));
      productRepository.save(new Product(null, "8437008460231", "Producto M", "Hacendado", "Tienda Mercadona Portugal"));
      productRepository.save(new Product(null, "8480000190336", "Producto N", "Otro Proveedor", "Tienda Mercadona Portugal"));
      productRepository.save(new Product(null, "8437008460378", "Producto O", "Hacendado", "Almacenes"));
      productRepository.save(new Product(null, "8480000190478", "Producto P", "Otro Proveedor", "Almacenes"));
      productRepository.save(new Product(null, "8437008460519", "Producto Q", "Hacendado", "Oficinas Mercadona"));
      productRepository.save(new Product(null, "8480000190599", "Producto R", "Otro Proveedor", "Oficinas Mercadona"));
      productRepository.save(new Product(null, "8437008460630", "Producto S", "Hacendado", "Colmenas"));
      productRepository.save(new Product(null, "8480000190710", "Producto T", "Otro Proveedor", "Colmenas"));
    };
  }
}
