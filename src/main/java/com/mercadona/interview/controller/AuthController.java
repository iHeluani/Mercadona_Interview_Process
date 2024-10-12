package com.mercadona.interview.controller;

import com.mercadona.interview.model.User;
import com.mercadona.interview.service.UserLoginDetailsService;
import com.mercadona.interview.utils.JWTUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserLoginDetailsService userDetailsService;

  @Autowired
  private JWTUtils jwtUtils;

  @Operation(summary = "Autenticar un usuario", description = "Inicia sesión y genera un token JWT utilizando las credenciales proporcionadas.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
          @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
  })
  @PostMapping("/login")
  public ResponseEntity<String> authenticateUser(@RequestBody User loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    String token = jwtUtils.generateToken(authentication.getName());
    return ResponseEntity.ok(token);
  }

  @Operation(summary = "Registrar un nuevo usuario", description = "Registra un nuevo usuario en el sistema.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
          @ApiResponse(responseCode = "409", description = "El usuario ya existe")
  })
  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody User userDTO) {
    try {
      userDetailsService.loadUserByUsername(userDTO.getUsername());
      return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe.");
    } catch (UsernameNotFoundException e) {
      User user = new User();
      user.setUsername(userDTO.getUsername());
      user.setPassword(userDTO.getPassword());
      userDetailsService.save(user);
      return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con éxito.");
    }
  }
}
