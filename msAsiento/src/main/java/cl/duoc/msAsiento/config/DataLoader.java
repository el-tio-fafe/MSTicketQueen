package cl.duoc.msAsiento.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msAsiento.model.Asiento;
import cl.duoc.msAsiento.repository.AsientoRepository;
import cl.duoc.msAsiento.repository.ReservaTemporalRepository;

@Configuration
public class DataLoader {

    @Bean                        //LLAMAMOS A TODOS LOS REPOSITORIOS QUE TENEMOS CON SUS OBJETOS (dentro del())
    CommandLineRunner initDataBase(AsientoRepository asientoRepository, ReservaTemporalRepository reservaTemporalRepository){
        return args -> {

            //SI(EL OBJETO DEL repositorio PPAL.count() ES >0)
            if( asientoRepository.count() > 0){
                System.out.println("No se cargaron datos porque ya existian datos en la base de datos");
            }else{

                //PRIMERO PONER NOMBRES DE NUEVOS ATRIBUTOS PARA LLENAR LAS TABLAS ASI:
                //NOMBRE DE CADA CLASE1 DEL MODEL clase1 = new CLASE1 (atributos);   //ESTADO ASIENTO: DISPONIBLE, RESERVADO O VENDIDO
                Asiento asien1 = new Asiento(null, "A100", "VENDIDO");
                Asiento asien2 = new Asiento(null, "B200", "DISPONIBLE");
                Asiento asien3 = new Asiento(null, "C300", "DISPONIBLE");
                Asiento asien4 = new Asiento(null, "D400", "VENDIDO");
                Asiento asien5 = new Asiento(null, "E500", "DISPONIBLE");


                //clase1Repo.save(clase1)
                asientoRepository.save(asien1);
                asientoRepository.save(asien2);
                asientoRepository.save(asien3);
                asientoRepository.save(asien4);
                asientoRepository.save(asien5);


                System.out.println("Datos cargados con exito a la base de datos");

            }

        };
    }

}
