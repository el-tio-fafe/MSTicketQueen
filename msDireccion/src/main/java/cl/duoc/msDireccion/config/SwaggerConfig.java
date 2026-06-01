package cl.duoc.msDireccion.config;

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
                .title("Microservicio Gestion de Direcciones")
                .version("1.2")
                .description("Documentacion de API que permite la gestion de las direcciones en el sistema" )
            );

    }

}
