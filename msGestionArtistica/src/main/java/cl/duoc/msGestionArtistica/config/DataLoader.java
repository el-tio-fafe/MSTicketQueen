package cl.duoc.msGestionArtistica.config;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msGestionArtistica.model.Artista;
import cl.duoc.msGestionArtistica.model.Manager;
import cl.duoc.msGestionArtistica.model.Productoras;
import cl.duoc.msGestionArtistica.repository.ArtistaRepository;
import cl.duoc.msGestionArtistica.repository.ManagerRepository;
import cl.duoc.msGestionArtistica.repository.ProductorasRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDatabase(ArtistaRepository artistaRepository, ProductorasRepository productorasRepository,
            ManagerRepository managerRepository) {
        return args -> {
            if (artistaRepository.count() > 0 || productorasRepository.count() > 0 || managerRepository.count() > 0) {

                System.out.println("No se cargaron datos porque ya existian datos en la base de datos");

            } else {
                Artista art1 = new Artista(null, "12345678-9", "Los Bunkers", "losbunkers@gmail.com", "+56912345678");
                Artista art2 = new Artista(null, "98765432-1", "Shakira", "shakira@gmail.com", "+56987654321");
                Artista art3 = new Artista(null, "11111111-1", "Ricardo Arjona", "arjona@gmail.com", "+56911111111");
                Artista art4 = new Artista(null, "22222222-2", "Mon Laferte", "monlaferte@gmail.com", "+56922222222");

                artistaRepository.save(art1);
                artistaRepository.save(art2);
                artistaRepository.save(art3);
                artistaRepository.save(art4);

                Manager mngr1 = new Manager(null, "33333333-3", "Carlos", "Mendez", "Rojas", "carlos.mendez@gmail.com",
                        "+56933333333", new ArrayList<>());
                Manager mngr2 = new Manager(null, "44444444-4", "Ana", "Lopez", "Vega", "ana.lopez@gmail.com",
                        "+56944444444", new ArrayList<>());

                managerRepository.save(mngr1);
                managerRepository.save(mngr2);

                Productoras prod1 = new Productoras(null, "55555555-5", "Bizarro Producciones", "bizarro@gmail.com",
                        "+56955555555", new ArrayList<>());
                Productoras prod2 = new Productoras(null, "66666666-6", "DG Medios", "dgmedios@gmail.com",
                        "+56966666666", new ArrayList<>());

                productorasRepository.save(prod1);
                productorasRepository.save(prod2);

                System.out.println("Datos cargados con exito a la base de datos");

            }

        };
    }

}
