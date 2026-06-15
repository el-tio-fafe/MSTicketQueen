package cl.duoc.msDireccion.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msDireccion.model.CiudadProvincia;
import cl.duoc.msDireccion.model.Comuna;
import cl.duoc.msDireccion.model.Region;
import cl.duoc.msDireccion.model.Calle; 
import cl.duoc.msDireccion.repository.CiudadProvinciaRepository;
import cl.duoc.msDireccion.repository.ComunaRepository;
import cl.duoc.msDireccion.repository.RegionRepository;
import cl.duoc.msDireccion.repository.CalleRepository; 

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(
        RegionRepository regionRepository, 
        CiudadProvinciaRepository ciudadProvinciaRepository, 
        ComunaRepository comunaRepository,
        CalleRepository calleRepository 
    ){
        return args -> {
            if(regionRepository.count() > 0){
                System.out.println("No se cargaron datos porque ya existian datos en la base de datos");
            }else{
                
                Region reg1 = new Region(null, "Metropolitana", null, null);
                Region reg2 = new Region(null, "Del Biobio", null, null);
                Region reg3 = new Region(null, "La Araucania", null, null);
                Region reg4 = new Region(null, "Coquimbo", null, null);
                Region reg5 = new Region(null, "Valparaiso", null, null);
                regionRepository.save(reg1);
                regionRepository.save(reg2);
                regionRepository.save(reg3);
                regionRepository.save(reg4);
                regionRepository.save(reg5);

                
                CiudadProvincia ciuPro1 = new CiudadProvincia(null, "Santiago", reg1, null);
                CiudadProvincia ciuPro2 = new CiudadProvincia(null, "Chacabuco", reg1, null);
                CiudadProvincia ciuPro3 = new CiudadProvincia(null, "Concepción", reg2, null);
                CiudadProvincia ciuPro4 = new CiudadProvincia(null, "San Antonio", reg5, null);
                CiudadProvincia ciuPro5 = new CiudadProvincia(null, "Cautin", reg3, null);
                CiudadProvincia ciuPro6 = new CiudadProvincia(null, "Elqui", reg4, null);
                ciudadProvinciaRepository.save(ciuPro1);
                ciudadProvinciaRepository.save(ciuPro2);
                ciudadProvinciaRepository.save(ciuPro3);
                ciudadProvinciaRepository.save(ciuPro4);
                ciudadProvinciaRepository.save(ciuPro5);
                ciudadProvinciaRepository.save(ciuPro6);

                
                Comuna com1 = new Comuna(null, "Quilicura", reg1, ciuPro1);
                Comuna com2 = new Comuna(null, "Huechuraba", reg1, ciuPro1);
                Comuna com3 = new Comuna(null, "Lampa", reg1, ciuPro2);
                Comuna com4 = new Comuna(null, "El Quisco", reg5, ciuPro4);
                Comuna com5 = new Comuna(null, "Temuco", reg3, ciuPro5);
                comunaRepository.save(com1);
                comunaRepository.save(com2);
                comunaRepository.save(com3);
                comunaRepository.save(com4);
                comunaRepository.save(com5);


                calleRepository.save(new Calle(null, "Av. Manuel Antonio Matta", "1230", null, com1));
                calleRepository.save(new Calle(null, "Américo Vespucio", "1500", "Depto 402", com2));
                calleRepository.save(new Calle(null, "Las Torres", "455", null, com1));
                calleRepository.save(new Calle(null, "Arturo Prat", "890", null, com3));
                calleRepository.save(new Calle(null, "Av. Isidoro Dubournais", "210", "Block B-12", com4));
                calleRepository.save(new Calle(null, "Av. Alemania", "01240", null, com5));
                calleRepository.save(new Calle(null, "Caupolicán", "530", "Depto 15", com5));
                calleRepository.save(new Calle(null, "Pedro Fontova", "7890", null, com2));

                System.out.println("Datos cargados con exito a la base de datos");
            }
        };
    }
}
