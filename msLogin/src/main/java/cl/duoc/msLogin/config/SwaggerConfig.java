package cl.duoc.msLogin.config;

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
                .title("MicroServicio Gestion De Login")
                .version("1.2")
                .description("Documentacion De APi que permite la gestion de la direciones en el sistema")
            );

            }
            
    }

 
