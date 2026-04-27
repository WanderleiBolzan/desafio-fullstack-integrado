package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example")
// 1. Diz ao Spring Data onde procurar as Interfaces Repository
@EnableJpaRepositories(basePackages = "com.example.ejb.repository")
// 2. Diz ao Hibernate onde procurar as classes com @Entity
@EntityScan(basePackages = "com.example.ejb.model")
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}