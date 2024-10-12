package com.mercadona.interview.controller;

import com.mercadona.interview.dto.ProductDTO;
import com.mercadona.interview.exception.ProductNotFoundException;
import com.mercadona.interview.model.Product;
import com.mercadona.interview.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Obtener un producto por su EAN", description = "Devuelve los detalles del producto asociado al EAN proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{ean}")
    public ResponseEntity<?> getProductByEan(@PathVariable @NotBlank @Parameter(description = "EAN del producto") String ean) {
        try {
            // Obtener el producto como DTO
            ProductDTO productDTO = productService.getProductByEanAsDTO(ean);
            return ResponseEntity.ok(productDTO);
        } catch (ProductNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado para el EAN: " + ean);
        }
    }

    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles.")
    @ApiResponse(responseCode = "200", description = "Lista de productos")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        // Obtener la lista de productos del servicio en formato DTO
        List<ProductDTO> products = productService.getAllProductsAsDTO();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Agregar un nuevo producto", description = "Crea un nuevo producto utilizando la información proporcionada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product productDTO) {
        // Convertir ProductDTO a Product
        Product product = new Product();
        product.setEan(productDTO.getEan());
        product.setName(productDTO.getName());
        product.setProvider(productDTO.getProvider());

        Product createdProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado con éxito: " + createdProduct.getEan());
    }

    @Operation(summary = "Actualizar un producto existente", description = "Actualiza los detalles de un producto específico utilizando su EAN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{ean}")
    public ResponseEntity<?> updateProduct(@PathVariable @NotBlank String ean, @RequestBody Product productDTO) {
        Optional<Product> optionalProduct = Optional.ofNullable(productService.getProductByEan(ean));
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado.");
        }

        Product productToUpdate = optionalProduct.get();
        productToUpdate.setName(productDTO.getName());
        productToUpdate.setProvider(productDTO.getProvider());

        Product updatedProduct = productService.addProduct(productToUpdate);
        return ResponseEntity.ok("Producto actualizado con éxito: " + updatedProduct.getEan());
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto específico utilizando su EAN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{ean}")
    public ResponseEntity<String> deleteProduct(@PathVariable @NotBlank String ean) {
        productService.deleteProduct(ean);
        return ResponseEntity.ok("Producto eliminado con éxito: " + ean);
    }
}
