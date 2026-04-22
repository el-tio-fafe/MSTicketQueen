package cl.duoc.msAtenciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients// Habilita Feign para llamadas a otros microservicios
@SpringBootApplication
public class MsAtencionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsAtencionesApplication.class, args);
	}

}
