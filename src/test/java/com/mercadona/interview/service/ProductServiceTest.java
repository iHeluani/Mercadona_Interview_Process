package com.mercadona.interview.service;

import com.mercadona.interview.exception.ProductNotFoundException;
import com.mercadona.interview.model.Product;
import com.mercadona.interview.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setEan("1234567890123");
        product.setName("Product Test");
        // ...set other fields
    }

    @Test
    void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.addProduct(product);

        assertNotNull(createdProduct);
        assertEquals("Product Test", createdProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testGetProductByEan_Success() {
        when(productRepository.findByEan("1234567890123")).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductByEan("1234567890123");

        assertNotNull(foundProduct);
        assertEquals("Product Test", foundProduct.getName());
        verify(productRepository, times(1)).findByEan("1234567890123");
    }

    @Test
    void testGetProductByEan_NotFound() {
        when(productRepository.findByEan("1234567890123")).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductByEan("1234567890123");
        });

        assertEquals("Producto con EAN 1234567890123 no encontrado.", exception.getMessage());
        verify(productRepository, times(1)).findByEan("1234567890123");
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Product Test", products.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testUpdateProduct_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = new Product();
        updatedProduct.setEan("1234567890123");
        updatedProduct.setName("Updated Product");

        Product result = productService.updateProduct(1L, updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(1L, product);
        });

        assertEquals("Producto con id 1 no encontrado.", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteProduct_Success() {
        when(productRepository.findByEan("1234567890123")).thenReturn(Optional.of(product));

        assertDoesNotThrow(() -> productService.deleteProduct("1234567890123"));
        verify(productRepository, times(1)).findByEan("1234567890123");
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.findByEan("1234567890123")).thenReturn(Optional.empty());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProduct("1234567890123");
        });

        assertEquals("Producto con EAN 1234567890123 no encontrado.", exception.getMessage());
        verify(productRepository, times(1)).findByEan("1234567890123");
    }
}
