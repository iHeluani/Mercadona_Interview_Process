package com.mercadona.interview.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class PasswordEncoder {

    public static void main(String[] args) {
        // Crear una instancia de BCryptPasswordEncoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Crear un escáner para leer la entrada desde la consola
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce la contraseña a codificar: ");
        String rawPassword = scanner.nextLine(); // Leer la contraseña en texto plano

        // Codificar la contraseña
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Mostrar la contraseña codificada
        System.out.println("Contraseña codificada: " + encodedPassword);

        // Cerrar el escáner
        scanner.close();
    }
}

