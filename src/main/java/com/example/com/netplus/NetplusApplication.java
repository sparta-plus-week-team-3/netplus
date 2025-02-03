package com.example.com.netplus;

import com.example.com.netplus.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class NetplusApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetplusApplication.class, args);
    }

}
