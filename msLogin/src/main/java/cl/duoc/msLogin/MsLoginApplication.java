package cl.duoc.msLogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsLoginApplication.class, args);
	}

}
