package com.musinsa.fashionshopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FashionShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FashionShoppingApplication.class, args);
    }

}
