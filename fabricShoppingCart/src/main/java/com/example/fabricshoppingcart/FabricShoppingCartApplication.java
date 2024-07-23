package com.example.fabricshoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class FabricShoppingCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(FabricShoppingCartApplication.class, args);
    }

}
