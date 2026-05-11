package cl.duoc.msDireccion.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msDireccion.model.CiudadProvincia;
import cl.duoc.msDireccion.model.Comuna;
import cl.duoc.msDireccion.model.Region;
import cl.duoc.msDireccion.repository.CiudadProvinciaRepository;
import cl.duoc.msDireccion.repository.ComunaRepository;
import cl.duoc.msDireccion.repository.RegionRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(RegionRepository regionRepository, CiudadProvinciaRepository ciudadProvinciaRepository, ComunaRepository comunaRepository){
        return args -> {
            
            if(regionRepository.count() > 0){
                System.out.println("No se cargaron datos porque ya existian datos");

            }else{           
                
                Region reg1 = new Region(null, "Metropolitana", null, null);
                Region reg2 = new Region(null, "BioBio", null, null);
                Region reg3 = new Region(null, "Atacama", null, null);

                regionRepository.save(reg1);
                regionRepository.save(reg2);
                regionRepository.save(reg3);

                
                CiudadProvincia ciuPro1 = new CiudadProvincia(null, "Santiago", reg1, null);
                CiudadProvincia ciuPro2 = new CiudadProvincia(null, "Chacabuco", reg1, null);
                CiudadProvincia ciuPro3 = new CiudadProvincia(null, "Concepción", reg2, null);
                CiudadProvincia ciuPro4 = new CiudadProvincia(null, "Copiapó", reg3, null);

                ciudadProvinciaRepository.save(ciuPro1);
                ciudadProvinciaRepository.save(ciuPro2);
                ciudadProvinciaRepository.save(ciuPro3);
                ciudadProvinciaRepository.save(ciuPro4);

                
                Comuna com1 = new Comuna(null, "Quilicura", reg1, ciuPro1);
                Comuna com2 = new Comuna(null, "Chiguayante", reg2, ciuPro3);
                Comuna com3 = new Comuna(null, "Lampa", reg1, ciuPro2);
                

                comunaRepository.save(com1);
                comunaRepository.save(com2);
                comunaRepository.save(com3);


            
             
            

            System.out.println("Datos cargados con exito a la base de datos");



            

            }
        };


    }

}
