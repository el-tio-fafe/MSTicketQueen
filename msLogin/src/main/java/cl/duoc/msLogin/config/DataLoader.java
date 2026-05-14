package cl.duoc.msLogin.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.msLogin.model.TipoUsuario;
import cl.duoc.msLogin.model.Usuario;
import cl.duoc.msLogin.repository.TipoUsuarioRepository;
import cl.duoc.msLogin.repository.UsuarioRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(TipoUsuarioRepository tipoUsuariorepo, UsuarioRepository usuarioRepo){
    return arg -> {
         if(usuarioRepo.count() > 0){

            System.out.println("No se cargaron datos porque ya existian datos en la base de datos");

         }else{
            
            TipoUsuario tipoUsuario1 = new TipoUsuario(null, "Administrador", null);
            TipoUsuario tipoUsuario2 = new TipoUsuario(null, "Cliente", null);
            TipoUsuario tipoUsuario3 = new TipoUsuario(null, "Productora", null);

            tipoUsuariorepo.save(tipoUsuario1); 
            tipoUsuariorepo.save(tipoUsuario2);
            tipoUsuariorepo.save(tipoUsuario3);

            Usuario usuario1 = new Usuario(null, "Juan Silva", "juan@gmail.com", "123456", tipoUsuario3);
            Usuario usuario2 = new Usuario(null, "Janette Diaz", "jane@gmail.com", "123456", tipoUsuario2);
            Usuario usuario3 = new Usuario(null, "Gerardo Torres", "GeraTo@gmail.com", "123456", tipoUsuario1);

            usuarioRepo.save(usuario1);
            usuarioRepo.save(usuario2);
            usuarioRepo.save(usuario3);

            System.out.println("Datos cargados con éxito a la base de datos");

        }    
    };


    }

}


