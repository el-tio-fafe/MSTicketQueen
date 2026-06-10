package cl.duoc.msLogin.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.msLogin.model.Usuario;
import cl.duoc.msLogin.service.UsuarioService;

@WebMvcTest(UsuarioController.class )
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mock;

    @MockitoBean
    private UsuarioService service;

    private Usuario UsuarioEjemplo;

    @BeforeEach
    void setUp(){
      UsuarioEjemplo= new Usuario();
      UsuarioEjemplo.setId(1);
      UsuarioEjemplo.setNombreUsuario("juan");
      UsuarioEjemplo.setCorreo("juan@Gmail.com");
      UsuarioEjemplo.setPassword("juan1234");
    }


    


}
