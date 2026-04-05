package com.interviewprep.orderflow_lite.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI orderFlowOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OrderFlow Lite API")
                        .description("Interview showcase project for API design, JPA, async processing, Redis caching, and Angular integration.")
                        .version("v1")
                        .contact(new Contact()
                                .name("Nuwan Samarasinghe")
                                .email("nuwansamarasinghe100@gmail.com"))
                        .license(new License()
                                .name("Demo License")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local server")
                ))
                .components(new Components()
                        .addParameters("CorrelationIdHeader",
                                new Parameter()
                                        .in("header")
                                        .name("X-Correlation-Id")
                                        .description("Optional correlation id for tracing requests")
                                        .required(false)
                                        .schema(new StringSchema())))
                .externalDocs(new ExternalDocumentation()
                        .description("Project README")
                        .url("https://example.com"));
}
