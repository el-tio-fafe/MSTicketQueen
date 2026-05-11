package cl.duoc.msGestionArtistica.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msGestionArtistica.model.Artista;
import cl.duoc.msGestionArtistica.repository.ArtistaRepository;
import cl.duoc.msGestionArtistica.repository.ManagerRepository;
import cl.duoc.msGestionArtistica.repository.ProductorasRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDatabase(ArtistaRepository artistaRepository, ProductorasRepository productorasRepository, ManagerRepository managerRepository) {
        return args -> {
            Artista art1 = new Artista(null, "Juan Perez", "12345678-9", "juan.perez@ejemplo.com", "123456789");

        };
    }

}
