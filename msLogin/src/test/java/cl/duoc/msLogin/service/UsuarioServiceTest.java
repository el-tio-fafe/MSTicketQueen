package cl.duoc.msLogin.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.lang.management.RuntimeMXBean;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msLogin.model.Usuario;
import cl.duoc.msLogin.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario UsuarioEjemplo;

    @BeforeEach
    void setUp(){
      UsuarioEjemplo= new Usuario();
      UsuarioEjemplo.setId(1);
      UsuarioEjemplo.setNombreUsuario("juan");
      UsuarioEjemplo.setCorreo("juan@Gmail.com");
      UsuarioEjemplo.setPassword("juan1234");
    }
        @Test
        void BuscarporId_encontrado(){
            // 
            Optional<Usuario> Doctorvacio = Optional.of(UsuarioEjemplo);
            when(usuarioRepository.findById(99)).thenReturn(Doctorvacio);

      

        }

 
}


