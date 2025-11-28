package com.exercicio.gerenciamento_de_carros.config.global;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//Classe de configuração do Swagger
@Configuration
public class SpringDocOpenAIConfig {

    //Titulo, descrição e versão da API
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("REST API - Gerenciamento de Carros")
                        .description("API para gestão de carros")
                        .version("1.0")
        );
    }
}
