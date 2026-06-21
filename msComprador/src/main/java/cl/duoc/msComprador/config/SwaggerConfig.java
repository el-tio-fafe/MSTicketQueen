package cl.duoc.msComprador.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi(){

        return new OpenAPI()
            .info(new Info()
                .title("Microservicio Gestion de Compradores")
                .version("1.2")
                .description("Documentación de API que permite la gestión de los compradores en el sistema" )
            );

    }

}
