package com.losluminosos.medsystem.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI medSystemOpenApi() {
        var openApi = new OpenAPI();
        openApi
                .info(new Info()
                        .title("MedSystem API")
                        .description("MedSystem REST API documentation.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("MedSystem Wiki Documentation")
                        .url("https://losluminosos-sw57.github.io/LandingPageMedSystem/"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Development Server"),
                        new Server().url("https://cartunn.up.railway.app").description("production/ Server")
                ));


        return openApi;
    }
}
