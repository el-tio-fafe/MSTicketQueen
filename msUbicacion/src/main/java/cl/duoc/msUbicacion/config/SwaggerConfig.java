package cl.duoc.msUbicacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){

        return new OpenAPI()
            .info(new Info()
                .title("Microservicio de control de ubicaciones")
                .version("1.1")
                .description("Documentacion de el microservicio de control de ubicaciones")
    );
    }
}
