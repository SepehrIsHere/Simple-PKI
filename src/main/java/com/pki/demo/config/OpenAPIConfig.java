package com.pki.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI openAPI() {
        Contact contact = new Contact();
        contact.setName("Sepehr");
        contact.setUrl("https://github.com/SepehrIsHere");
        contact.email("sepehrjedaridv@gmail.com");

        License license = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Simple PKI project")
                .contact(contact)
                .license(license)
                .description("Simple API for some PKI operations")
                .version("1.0.0");
        return new OpenAPI().info(info);
    }
}
