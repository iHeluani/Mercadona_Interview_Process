package com.mercadona.interview.controller;

import com.mercadona.interview.model.User;
import com.mercadona.interview.model.UserLoginDetails;
import com.mercadona.interview.service.UserLoginDetailsService;
import com.mercadona.interview.utils.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserLoginDetailsService userDetailsService;

    @Mock
    private JWTUtils jwtUtils;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
    }

    @Test
    void testAuthenticateUser_Success() {
        User loginRequest = new User();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("testPassword");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        String mockedToken = "mockedToken";
        when(jwtUtils.generateToken(authentication.getName())).thenReturn(mockedToken);

        ResponseEntity<String> response = authController.authenticateUser(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedToken, response.getBody());
    }


    @Test
    void testRegisterUser_UserExists() {
        when(userDetailsService.loadUserByUsername(user.getUsername()))
                .thenThrow(new UsernameNotFoundException("Usuario no encontrado"));

        ResponseEntity<String> response = authController.registerUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuario registrado con éxito.", response.getBody());
        verify(userDetailsService, times(1)).save(user);
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        User existingUser = new User();
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword("somePassword");

        when(userDetailsService.loadUserByUsername(user.getUsername())).thenReturn(new UserLoginDetails(existingUser));

        ResponseEntity<String> response = authController.registerUser(user);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El usuario ya existe.", response.getBody());
        verify(userDetailsService, never()).save(user);
    }

}
