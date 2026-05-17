package cl.duoc.msDireccion.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msDireccion.model.Calle;
import cl.duoc.msDireccion.model.CiudadProvincia;
import cl.duoc.msDireccion.model.Comuna;
import cl.duoc.msDireccion.model.Region;
import cl.duoc.msDireccion.repository.CalleRepository;
import cl.duoc.msDireccion.repository.CiudadProvinciaRepository;
import cl.duoc.msDireccion.repository.ComunaRepository;
import cl.duoc.msDireccion.repository.RegionRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(RegionRepository regionRepository, CiudadProvinciaRepository ciudadProvinciaRepository, ComunaRepository comunaRepository, CalleRepository calleRepository){
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


                Calle calle1 = new Calle(null, "Av. Grecia", 2001, null, null, com1);
                Calle calle2 = new Calle(null, "Av. Providencia", 1234, null, null, com1);
                Calle calle3 = new Calle(null, "Plaza de Armas", 100, null, null, com3);

                calleRepository.save(calle1);
                calleRepository.save(calle2);
                calleRepository.save(calle3);


                System.out.println("Datos cargados con exito a la base de datos");


            }
        };


    }

}
