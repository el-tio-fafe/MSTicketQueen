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
            } else {
                // 1. REGIONES 
                Region reg1 = new Region(); reg1.setNombreRegion("Metropolitana");
                Region reg2 = new Region(); reg2.setNombreRegion("Del Biobio");
                Region reg3 = new Region(); reg3.setNombreRegion("La Araucania");
                Region reg4 = new Region(); reg4.setNombreRegion("Coquimbo");
                Region reg5 = new Region(); reg5.setNombreRegion("Valparaiso");
                
                regionRepository.save(reg1);
                regionRepository.save(reg2);
                regionRepository.save(reg3);
                regionRepository.save(reg4);
                regionRepository.save(reg5);

                // 2. CIUDADES / PROVINCIAS
                CiudadProvincia ciuPro1 = new CiudadProvincia(); ciuPro1.setNombreCiudadProvincia("Santiago"); ciuPro1.setRegion(reg1);
                CiudadProvincia ciuPro2 = new CiudadProvincia(); ciuPro2.setNombreCiudadProvincia("Chacabuco"); ciuPro2.setRegion(reg1);
                CiudadProvincia ciuPro3 = new CiudadProvincia(); ciuPro3.setNombreCiudadProvincia("Concepción"); ciuPro3.setRegion(reg2);
                CiudadProvincia ciuPro4 = new CiudadProvincia(); ciuPro4.setNombreCiudadProvincia("San Antonio"); ciuPro4.setRegion(reg5);
                CiudadProvincia ciuPro5 = new CiudadProvincia(); ciuPro5.setNombreCiudadProvincia("Cautin"); ciuPro5.setRegion(reg3);
                CiudadProvincia ciuPro6 = new CiudadProvincia(); ciuPro6.setNombreCiudadProvincia("Elqui"); ciuPro6.setRegion(reg4);
                
                ciudadProvinciaRepository.save(ciuPro1);
                ciudadProvinciaRepository.save(ciuPro2);
                ciudadProvinciaRepository.save(ciuPro3);
                ciudadProvinciaRepository.save(ciuPro4);
                ciudadProvinciaRepository.save(ciuPro5);
                ciudadProvinciaRepository.save(ciuPro6);

                // 3. COMUNAS
                Comuna com1 = new Comuna(); com1.setNombreComuna("Quilicura"); com1.setRegion(reg1); com1.setCiudadProvincia(ciuPro1);
                Comuna com2 = new Comuna(); com2.setNombreComuna("Huechuraba"); com2.setRegion(reg1); com2.setCiudadProvincia(ciuPro1);
                Comuna com3 = new Comuna(); com3.setNombreComuna("Lampa"); com3.setRegion(reg1); com3.setCiudadProvincia(ciuPro2);
                Comuna com4 = new Comuna(); com4.setNombreComuna("El Quisco"); com4.setRegion(reg5); com4.setCiudadProvincia(ciuPro4);
                Comuna com5 = new Comuna(); com5.setNombreComuna("Temuco"); com5.setRegion(reg3); com5.setCiudadProvincia(ciuPro5);
                
                comunaRepository.save(com1);
                comunaRepository.save(com2);
                comunaRepository.save(com3);
                comunaRepository.save(com4);
                comunaRepository.save(com5);

                // 4. CALLES 
                calleRepository.save(new Calle(1, "Av. Manuel Antonio Matta", "1230", null, com1));
                calleRepository.save(new Calle(2, "Américo Vespucio", "1500", "Depto 402", com2));
                calleRepository.save(new Calle(3, "Las Torres", "455", null, com1));
                calleRepository.save(new Calle(4, "Arturo Prat", "890", null, com3));
                calleRepository.save(new Calle(5, "Av. Isidoro Dubournais", "210", "Block B-12", com4));
                calleRepository.save(new Calle(6, "Av. Alemania", "01240", null, com5));
                calleRepository.save(new Calle(7, "Caupolicán", "530", "Depto 15", com5));
                calleRepository.save(new Calle(8, "Pedro Fontova", "7890", null, com2));

                System.out.println("Datos cargados con exito a la base de datos");
            }
        };
    }
}
