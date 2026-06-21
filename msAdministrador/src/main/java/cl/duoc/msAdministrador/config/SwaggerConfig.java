package cl.duoc.msAdministrador.config;

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
                .title("Microservicio Gestión de Administración")
                .version("1.1")
                .description("Documentación de API que permite la gestión de los Administradores en el sistema")
        );
    }

}
