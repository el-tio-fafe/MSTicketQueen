package cl.duoc.msAdministrador.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msAdministrador.model.Administrador;
import cl.duoc.msAdministrador.model.Auditoria;
import cl.duoc.msAdministrador.repository.AdministradorRepository;
import cl.duoc.msAdministrador.repository.AuditoriaRepository;

@Configuration
public class DataLoader {

    @Bean                        //LLAMAMOS A TODOS LOS REPOSITORIOS QUE TENEMOS CON SUS OBJETOS (dentro del())
    CommandLineRunner initDataBase(AdministradorRepository administradorRepository, AuditoriaRepository auditoriaRepository ){
        return args -> {

            //SI(EL OBJETO DEL repositorio PPAL.count() ES >0)
            if(administradorRepository.count() > 0){

                System.out.println("No se cargaron datos porque ya existian datos en la base de datos");

            }else{

                //PRIMERO PONER NOMBRES DE NUEVOS ATRIBUTOS PARA LLENAR LAS TABLAS ASI:
                //NOMBRE DE CADA CLASE1 DEL MODEL clase1 = new CLASE1 (atributos);

                Administrador adm1 = new Administrador(null, "16517526-3", "Maria Jose", "Cruces", "Huaiquil", "cote.cruces@gmail.com", "+56949783198", null);
                Administrador adm2 = new Administrador(null, "16123152-5", "Ramon", "Gomez", "Cortes", "ramon@gmail.com", "+56933032274", null);



                //clase1Repo.save(cla1)

                administradorRepository.save(adm1);
                administradorRepository.save(adm2);


                Auditoria aud1 = new Auditoria(null, "Matias Gomez", java.sql.Date.valueOf("2026-03-28"), 
                                            "CREAR", "Se crea evento '50 años de Trayectoria' Los Bunkers", adm2);
                Auditoria aud2 = new Auditoria(null, "Pablo Cortes", java.sql.Date.valueOf("2026-04-18"), 
                                            "CREAR", "Se crea evento 'Tour-Chile' Shakira", adm2);
                Auditoria aud3 = new Auditoria(null, "Rosa Araneda", java.sql.Date.valueOf("2026-05-07"),
                                            "CREAR", "Se crea evento 'No te olvido' Ricardo Arjona", adm1);

                auditoriaRepository.save(aud1);
                auditoriaRepository.save(aud2);
                auditoriaRepository.save(aud3);


                System.out.println("Datos cargados con exito a la base de datos");

            }

        };
    }



}
