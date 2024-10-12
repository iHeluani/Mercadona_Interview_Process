package com.mercadona.interview.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = "ean"))
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "EAN cannot be blank")
  @Pattern(regexp = "\\d{13}", message = "EAN must be a 13-digit number")
  private String ean;

  @NotBlank(message = "Product name cannot be blank")
  private String name;

  @NotBlank(message = "Provider cannot be blank")
  private String provider;

  @NotBlank(message = "Destination cannot be blank")
  private String destination;

  @PostLoad
  public void determineDestination() {
    char lastDigit = ean.charAt(12);
    switch (lastDigit) {
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
        destination = "Mercadona España";
        break;
      case '6':
        destination = "Mercadona Portugal";
        break;
      case '8':
        destination = "Almacenes";
        break;
      case '9':
        destination = "Oficinas Mercadona";
        break;
      case '0':
        destination = "Colmenas";
        break;
      default:
        destination = "Desconocido";
        break;
    }
  }
}
