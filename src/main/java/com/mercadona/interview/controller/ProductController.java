package com.mercadona.interview.controller;

import com.mercadona.interview.exception.ProductNotFoundException;
import com.mercadona.interview.model.Product;
import com.mercadona.interview.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/{ean}")
  public ResponseEntity<Product> getProductByEan(@PathVariable String ean) {
    try {
      Product product = productService.getProductByEan(ean);
      return ResponseEntity.ok(product);
    } catch (ProductNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    List<Product> products = productService.getAllProducts();
    return ResponseEntity.ok(products);
  }

  @PostMapping
  public ResponseEntity<Product> addProduct(@RequestBody Product product) {
    Product createdProduct = productService.addProduct(product);
    return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
    try {
      Product updatedProduct = productService.updateProduct(id, productDetails);
      return ResponseEntity.ok(updatedProduct);
    } catch (ProductNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  @DeleteMapping("/{ean}")
  public ResponseEntity<Void> deleteProduct(@PathVariable String ean) {
    productService.deleteProduct(ean);
    return ResponseEntity.noContent().build();
  }
}
