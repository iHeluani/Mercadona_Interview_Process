package com.mercadona.interview.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class PasswordEncoder {

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce la contraseña a codificar: ");
        String rawPassword = scanner.nextLine(); // Leer la contraseña en texto plano
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("Contraseña codificada: " + encodedPassword);
        scanner.close();
    }
}

