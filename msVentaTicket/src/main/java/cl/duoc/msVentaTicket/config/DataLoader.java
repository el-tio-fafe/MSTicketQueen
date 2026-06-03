package cl.duoc.msVentaTicket.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msVentaTicket.model.Boleta;
import cl.duoc.msVentaTicket.model.Detalle;
import cl.duoc.msVentaTicket.model.Ticket;
import cl.duoc.msVentaTicket.repository.BoletaRepository;
import cl.duoc.msVentaTicket.repository.DetalleRepository;
import cl.duoc.msVentaTicket.repository.TicketRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(BoletaRepository boletaRepository, DetalleRepository detalleRepository, TicketRepository ticketRepository) {
        
        return args -> {

            if (boletaRepository.count() > 0) {
               
                System.out.println("No se cargaron datos porque ya existían datos en la base de datos");
           
            } else {


                Ticket ticket1 = new Ticket(null, null, 1, "A1", null);
                Ticket ticket2 = new Ticket(null, null, 1, "A2", null);
                Ticket ticket3 = new Ticket(null, null, 2, null, "Cancha General");

                ticket1 = ticketRepository.save(ticket1);
                ticket2 = ticketRepository.save(ticket2);
                ticket3 = ticketRepository.save(ticket3);


                Detalle detalle1 = new Detalle(null, 1, 50000, 0, null, ticket1);
                Detalle detalle2 = new Detalle(null, 1, 50000, 5000, null, ticket2);
                Detalle detalle3 = new Detalle(null, 2, 30000, 0, null, ticket3);

                detalle1 = detalleRepository.save(detalle1);
                detalle2 = detalleRepository.save(detalle2);
                detalle3 = detalleRepository.save(detalle3);


                Boleta boleta1 = new Boleta(null, null, null, null, null, 1, null, List.of(detalle1, detalle2));
                boleta1 = boletaRepository.save(boleta1);
                boleta1.setNumeroBoleta(boleta1.getIdBoleta());
                boletaRepository.save(boleta1);

                Boleta boleta2 = new Boleta(null, null, null, null, null, 2, null, List.of(detalle3));
                boleta2 = boletaRepository.save(boleta2);
                boleta2.setNumeroBoleta(boleta2.getIdBoleta());
                boletaRepository.save(boleta2);


                System.out.println("Datos cargados con éxito a la base de datos");

        }

    };

    }
}
