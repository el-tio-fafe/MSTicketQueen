package cl.duoc.msComprador.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msComprador.model.Comprador;
import cl.duoc.msComprador.repository.CompradorRepository;

@Configuration
public class DataLoader {

    @Bean                        //LLAMAMOS A TODOS LOS REPOSITORIOS QUE TENEMOS CON SUS OBJETOS (dentro del())
    CommandLineRunner initDataBase(CompradorRepository compradorRepository){
        return args -> {

            //SI(EL OBJETO DEL repositorio PPAL.count() ES >0)
            if(compradorRepository.count()   > 0){
                System.out.println("No se cargaron datos porque ya existian datos en la base de datos");
            }else{

                //PRIMERO PONER NOMBRES DE NUEVOS ATRIBUTOS PARA LLENAR LAS TABLAS ASI:
                //NOMBRE DE CADA CLASE1 DEL MODEL clase1 = new CLASE1 (atributos);
                Comprador comp1 = new Comprador(null, "23388916-4", "Matias", "Gomez", "Cruces", "mati@gmail.com", "+569 84578596", "cuaderno");
                Comprador comp2 = new Comprador(null, "25008098-0", "Abraham", "Cortes", "Perez", "aby@gmail.com", "+569 52584565", "moto");
                Comprador comp3 = new Comprador(null, "9989533-0", "Rosa", "Huaiquil", "Briones", "rosa@gmail.com", "+569 93944430", "bebida70");
                Comprador comp4 = new Comprador(null, "17122488-2", "Alejandro", "Cruces", "Alcantara", "ale@gmail.com", "+569 22233565", "alejo");

                //clase1Repo.save(clase1)
                compradorRepository.save(comp1);
                compradorRepository.save(comp2);
                compradorRepository.save(comp3);
                compradorRepository.save(comp4);




                System.out.println("Datos cargados con exito a la base de datos");

            }

        };
    }

}
