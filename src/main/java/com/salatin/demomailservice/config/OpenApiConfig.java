package com.salatin.demomailservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "OpenApi specification for Mailing Service",
                contact = @Contact(
                        name = "Serhii Salatin",
                        url = "https://github.com/sltnsrh"
                ),
                version = "1.0.0"
        ),
        servers = @Server(description = "local", url = "http://localhost:8080")
)
public class OpenApiConfig {
}
