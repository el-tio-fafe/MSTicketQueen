package cl.duoc.msComprador.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean                        //LLAMAMOS A TODOS LOS REPOSITORIOS QUE TENEMOS CON SUS OBJETOS (dentro del())
    CommandLineRunner initDataBase(  ){
        return args -> {

            //SI(EL OBJETO DEL repositorio PPAL.count() ES >0)
            if(    > 0){
                System.out.println("No se cargaron datos porque ya existian datos en la base de datos");
            }else{

                //PRIMERO PONER NOMBRES DE NUEVOS ATRIBUTOS PARA LLENAR LAS TABLAS ASI:
                //NOMBRE DE CADA CLASE1 DEL MODEL clase1 = new CLASE1 (atributos);



                //clase1Repo.save(clase1)





                System.out.println("Datos cargados con exito a la base de datos");

            }

        };
    }

}
