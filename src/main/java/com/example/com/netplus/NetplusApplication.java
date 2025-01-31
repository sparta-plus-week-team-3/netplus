package com.example.com.netplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NetplusApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetplusApplication.class, args);
    }

}
