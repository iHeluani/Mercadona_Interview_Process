package com.mercadona.interview.service;

import com.mercadona.interview.dto.ProductDTO;
import com.mercadona.interview.exception.ProductNotFoundException;
import com.mercadona.interview.model.Product;
import com.mercadona.interview.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Cacheable(value = "products", key = "#ean")
    public Product getProductByEan(String ean) {
        return productRepository.findByEan(ean)
                .orElseThrow(() -> new ProductNotFoundException("Producto con EAN " + ean + " no encontrado."));
    }

    @Cacheable(value = "allProducts")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @CachePut(value = "products", key = "#product.ean")
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @CacheEvict(value = "products", key = "#product.ean")
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto con id " + id + " no encontrado."));
        product.setEan(productDetails.getEan());
        product.setName(productDetails.getName());
        return productRepository.save(product);
    }

    @CacheEvict(value = "products", key = "#ean")
    public void deleteProduct(String ean) {
        Product product = productRepository.findByEan(ean)
                .orElseThrow(() -> new ProductNotFoundException("Producto con EAN " + ean + " no encontrado."));
        productRepository.delete(product);
    }

    public List<ProductDTO> getAllProductsAsDTO() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductByEanAsDTO(String ean) throws ProductNotFoundException {
        Product product = productRepository.findByEan(ean)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado para el EAN: " + ean));
        return convertToDTO(product);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setEan(product.getEan());
        productDTO.setName(product.getName());
        productDTO.setProvider(product.getProvider());
        return productDTO;
    }
}
