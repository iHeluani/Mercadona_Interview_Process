package com.mercadona.Interview.Controller;

import com.mercadona.Interview.Exception.ProductNotFoundException;
import com.mercadona.Interview.Model.Product;
import com.mercadona.Interview.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/{ean}")
  public ResponseEntity<Product> getProductByEan(@PathVariable String ean) {
    Product product = productService.getProductByEan(ean);
    return ResponseEntity.ok(product);
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
    Product updatedProduct = productService.updateProduct(id, productDetails);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/{ean}")
  public ResponseEntity<Void> deleteProduct(@PathVariable String ean) {
    productService.deleteProduct(ean);
    return ResponseEntity.noContent().build();
  }
}
