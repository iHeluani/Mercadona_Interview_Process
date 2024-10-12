package com.mercadona.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MercadonaInterviewProcess {

    public static void main(String[] args) {
        SpringApplication.run(MercadonaInterviewProcess.class, args);
    }

}
