package com.TicketQueen.mslogin.config;

import com.TicketQueen.mslogin.model.TipoUsuario;
import com.TicketQueen.mslogin.model.Usuario;
import com.TicketQueen.mslogin.repository.TipoUsuarioRepository;
import com.TicketQueen.mslogin.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
public class datalodear {


@Bean
CommandLineRunner initDataBase(TipoUsuarioRepository tipoUsuariorepo, UsuarioRepository usuarioRepo){
   return arg -> {
         if(usuarioRepo.count() > 0){
            System.out.println("No se cargaron datos porque ya existian datos en la base de datos");
         }else{
            //CARGAR DATOS DE TIPO USUARIO
            TipoUsuario tipoUsuario1 = new TipoUsuario(null, "Admin", null);
            TipoUsuario tipoUsuario2 = new TipoUsuario(null, "Cliente", null);
            TipoUsuario tipoUsuario3 = new TipoUsuario(null, "Empleado", null);

            Usuario usuario1 = new Usuario(null, "John Doe", "john.doe@gmail.com", "123456", tipoUsuario3);
            Usuario usuario2 = new Usuario(null, "Jane Smith", "jane.smith@gmail.com", "123456", tipoUsuario2);
            Usuario usuario3 = new Usuario(null, "Bob Johnson", "bob.johnson@gmail.com", "123456", tipoUsuario1);

            usuarioRepo.save(usuario1);
            usuarioRepo.save(usuario2);
            usuarioRepo.save(usuario3);

         }    
        };

}
}
