package com.mercadona.interview.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProductDTO {

    @NotBlank(message = "EAN cannot be blank")
    @Pattern(regexp = "\\d{13}", message = "EAN must be a 13-digit number")
    private String ean;

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @NotBlank(message = "Provider cannot be blank")
    private String provider;
}
