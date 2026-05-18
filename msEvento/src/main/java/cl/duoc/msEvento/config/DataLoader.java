package cl.duoc.msEvento.config;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msEvento.model.Evento;
import cl.duoc.msEvento.model.Recinto;
import cl.duoc.msEvento.model.TipoEvento;
import cl.duoc.msEvento.repository.EventoRepository;
import cl.duoc.msEvento.repository.RecintoRepository;
import cl.duoc.msEvento.repository.TipoEventoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(
            EventoRepository eventoRepository,
            RecintoRepository recintoRepository,
            TipoEventoRepository tipoEventoRepository) {

        return args -> {
            if (eventoRepository.count() > 0) {
                System.out.println("No se cargaron datos porque ya existían datos en la base de datos");
            } else {

                // TIPO EVENTO
                TipoEvento tipo1 = new TipoEvento(null, "Concierto");
                TipoEvento tipo2 = new TipoEvento(null, "Festival");
                TipoEvento tipo3 = new TipoEvento(null, "Teatro");

                tipoEventoRepository.save(tipo1);
                tipoEventoRepository.save(tipo2);
                tipoEventoRepository.save(tipo3);

                // RECINTO
                Recinto recinto1 = new Recinto(null, "76123456-7", "Estadio Nacional", 60000, "+56223456789", "estadionacional@gmail.com", 1);
                Recinto recinto2 = new Recinto(null, "76234567-8", "Movistar Arena", 15000, "+56223456790", "movistararena@gmail.com", 2);
                Recinto recinto3 = new Recinto(null, "76345678-9", "Teatro Municipal", 1500, "+56223456791", "teatromunicipal@gmail.com", 3);

                recintoRepository.save(recinto1);
                recintoRepository.save(recinto2);
                recintoRepository.save(recinto3);

                // EVENTO
                Evento evento1 = new Evento(null, "EVT-001", "50 años de Trayectoria Los Bunkers",
                        LocalDate.of(2026, 8, 15), LocalTime.of(20, 0),
                        "APROBADO", 1, 1, recinto1, tipo1);

                Evento evento2 = new Evento(null, "EVT-002", "Tour Chile Shakira",
                        LocalDate.of(2026, 9, 20), LocalTime.of(21, 0),
                        "PENDIENTE", 2, 2, recinto2, tipo1);

                Evento evento3 = new Evento(null, "EVT-003", "Festival de Viña 2027",
                        LocalDate.of(2027, 2, 20), LocalTime.of(22, 0),
                        "PENDIENTE", 1, 1, recinto1, tipo2);

                eventoRepository.save(evento1);
                eventoRepository.save(evento2);
                eventoRepository.save(evento3);

                System.out.println("Datos cargados con éxito a la base de datos");
            }
        };
    }

}
