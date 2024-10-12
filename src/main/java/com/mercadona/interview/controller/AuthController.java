package com.mercadona.interview.controller;

import com.mercadona.interview.model.User;
import com.mercadona.interview.utils.JWTUtils;
import com.mercadona.interview.service.UserDetailsServiceImpl;
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
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private JWTUtils jwtUtils;

  @PostMapping("/login")
  public ResponseEntity<String> authenticateUser(@RequestBody User loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    String token = jwtUtils.generateToken(authentication.getName());
    return ResponseEntity.ok(token);
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody User user) {
    try {
      userDetailsService.loadUserByUsername(user.getUsername());
      return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe.");
    } catch (UsernameNotFoundException e) {
      userDetailsService.save(user); // Guarda el nuevo usuario
      return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con éxito.");
    }
  }
}
