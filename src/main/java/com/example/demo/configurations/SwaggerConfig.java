package com.example.demo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact()
                .name("Илиян")
                .url("https://example.com")
                .email("contact@example.com");

        Info info = new Info()
                .title("Регистър на физически лица")
                .version("1.0")
                .description("API за регистрация на физически лица")
                .contact(contact);

        return new OpenAPI().info(info);
    }
}

