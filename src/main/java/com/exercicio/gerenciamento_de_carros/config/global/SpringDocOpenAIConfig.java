package com.exercicio.gerenciamento_de_carros.config.global;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Classe de configuração do Swagger
@Configuration
public class SpringDocOpenAIConfig {

    //Titulo, descrição e versão da API
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components( new Components().addSecuritySchemes("security", securityScheme()))
                .info(
                new Info()
                        .title("REST API - Gerenciamento de Carros")
                        .description("API para gestão de carros")
                        .version("1.0")
        );
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme().description("Insira um bearer token valido para prosseguir")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("security");
    }
}
