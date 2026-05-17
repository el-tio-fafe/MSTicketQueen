package cl.duoc.msVentaTicket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsVentaTicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVentaTicketApplication.class, args);
	}

}
