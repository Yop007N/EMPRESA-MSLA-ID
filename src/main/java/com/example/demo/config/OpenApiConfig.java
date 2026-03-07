package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Demo Microservice API")
                        .description("Plantilla base para microservicios con Spring Boot")
                        .version("0.0.1-SNAPSHOT")
                        .contact(new Contact()
                                .name("Demo Team")
                                .email("demo@example.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local")));
    }
}
