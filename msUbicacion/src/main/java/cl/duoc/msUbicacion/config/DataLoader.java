package cl.duoc.msUbicacion.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msUbicacion.model.Ubicacion;
import cl.duoc.msUbicacion.repository.UbicacionRepository;

@Configuration 
public class DataLoader {
    @Bean
    CommandLineRunner initDataBase(UbicacionRepository ubicacionRepository) {
        return args -> {
            if (ubicacionRepository.count() > 0) {
                System.out.println("No se cargaron datos porque ya existian datos en la base de datos");
            } else {
                Ubicacion ubi1 = new Ubicacion(null, "Cancha Central", 25000.0, 500, 500, true);
                Ubicacion ubi2 = new Ubicacion(null, "Sector Norte", 15000.0, 200, 200, true);
                Ubicacion ubi3 = new Ubicacion(null, "Zona Standing", 10000.0, 1000, 1000, false);
                Ubicacion ubi4 = new Ubicacion(null, "Palco VIP", 80000.0, 50, 50, true);
                Ubicacion ubi5 = new Ubicacion(null, "Galeria Sur", 8000.0, 300, 300, false);

                ubicacionRepository.save(ubi1);
                ubicacionRepository.save(ubi2); 
                ubicacionRepository.save(ubi3);
                ubicacionRepository.save(ubi4);
                ubicacionRepository.save(ubi5);

                System.out.println("Datos cargados con exito a la base de datos");
            }
        };
    }
}
