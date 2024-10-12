package com.mercadona.interview.controller;

import com.mercadona.interview.exception.ProductNotFoundException;
import com.mercadona.interview.model.Product;
import com.mercadona.interview.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setEan("1234567890123");
        product.setName("Product Test");
    }

    @Test
    void testGetProductByEan_Success() {
        when(productService.getProductByEan(product.getEan())).thenReturn(product);

        ResponseEntity<?> response = productController.getProductByEan(product.getEan());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).getProductByEan(product.getEan());
    }

    @Test
    void testGetProductByEan_NotFound() {
        when(productService.getProductByEan(anyString())).thenThrow(new ProductNotFoundException("Producto no encontrado"));

        ResponseEntity<?> response = productController.getProductByEan("invalid-ean");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Producto no encontrado para el EAN: invalid-ean", response.getBody());
        verify(productService, times(1)).getProductByEan("invalid-ean");
    }

    @Test
    void testGetAllProducts() {
        List<Product> productList = Arrays.asList(product);
        when(productService.getAllProducts()).thenReturn(productList);

        ResponseEntity<List<Product>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(product.getEan(), response.getBody().get(0).getEan());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testAddProduct() {
        when(productService.addProduct(any(Product.class))).thenReturn(product);

        ResponseEntity<?> response = productController.addProduct(product);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Producto creado con éxito: " + product.getEan(), response.getBody());
        verify(productService, times(1)).addProduct(product);
    }

    @Test
    void testUpdateProduct_Success() {
        when(productService.getProductByEan(product.getEan())).thenReturn(product);
        when(productService.addProduct(any(Product.class))).thenReturn(product);

        ResponseEntity<?> response = productController.updateProduct(product.getEan(), product);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Producto actualizado con éxito: " + product.getEan(), response.getBody());
        verify(productService, times(1)).getProductByEan(product.getEan());
        verify(productService, times(1)).addProduct(product);
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(productService.getProductByEan(anyString())).thenThrow(new ProductNotFoundException("Producto no encontrado"));

        ResponseEntity<?> response = productController.getProductByEan("invalid-ean");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Producto no encontrado para el EAN: invalid-ean", response.getBody());
        verify(productService, times(1)).getProductByEan("invalid-ean");
    }

    @Test
    void testDeleteProduct_Success() {
        ResponseEntity<String> response = productController.deleteProduct(product.getEan());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Producto eliminado con éxito: " + product.getEan(), response.getBody());
        verify(productService, times(1)).deleteProduct(product.getEan());
    }
}
