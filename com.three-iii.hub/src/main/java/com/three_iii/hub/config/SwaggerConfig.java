package com.three_iii.hub.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.ArrayList;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "Hub API 명세서",
        description = "Hub API 명세서",
        version = "v1"))
@Configuration
public class SwaggerConfig {

    @Value("${server.port}")
    String serverPort;

    @Bean
    public GroupedOpenApi publicAPI() {
        return GroupedOpenApi.builder()
            .group("com.three-iii.hub")
            .pathsToMatch("/**")
            .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        List<Server> serverList = new ArrayList<>();
        Server server = new Server()
            .url("http://localhost" + ":" + serverPort)
            .description("Server");
        Server gatewayServer = new Server()
            .url("http://localhost" + ":" + 19091)
            .description("Gateway Server");
        serverList.add(gatewayServer);
        serverList.add(server);
        return new OpenAPI()
            .servers(serverList)
            .components(new Components()
                .addSecuritySchemes("JWT-Token", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER)
                    .name("Authorization")))
            .addSecurityItem(new SecurityRequirement().addList("JWT-Token"));
    }
}
