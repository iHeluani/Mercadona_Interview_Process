package com.mercadona.Interview.Controller;

import com.mercadona.Interview.Model.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
    // Implementa la lógica de autenticación aquí
    return ResponseEntity.ok("Login successful");
  }
}

