package com.mercadona.interview.controller;

import com.mercadona.interview.exception.ProductNotFoundException;
import com.mercadona.interview.model.Product;
import com.mercadona.interview.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{ean}")
    public ResponseEntity<?> getProductByEan(@PathVariable String ean) {
        try {
            Product product = productService.getProductByEan(ean);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado para el EAN: " + ean);
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        Product createdProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado con éxito: " + createdProduct.getEan());
    }

    @PutMapping("/{ean}")
    public ResponseEntity<?> updateProduct(@PathVariable String ean, @RequestBody Product productDetails) {
        Optional<Product> optionalProduct = Optional.ofNullable(productService.getProductByEan(ean));
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado.");
        }

        Product productToUpdate = optionalProduct.get();

        productToUpdate.setName(productDetails.getName());
        productToUpdate.setProvider(productDetails.getProvider());
        productToUpdate.setDestination(productDetails.getDestination());

        Product updatedProduct = productService.addProduct(productToUpdate);
        return ResponseEntity.ok("Producto actualizado con éxito: " + updatedProduct.getEan());
    }

    @DeleteMapping("/{ean}")
    public ResponseEntity<String> deleteProduct(@PathVariable String ean) {
        productService.deleteProduct(ean);
        return ResponseEntity.ok("Producto eliminado con éxito: " + ean);
    }
}
