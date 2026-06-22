package cl.duoc.msLogin.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.msLogin.model.TipoUsuario;
import cl.duoc.msLogin.model.Usuario;
import cl.duoc.msLogin.service.UsuarioService;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mock;//simula llamadas http

    @MockitoBean
    private UsuarioService service;

    private Usuario ejemplo;
    private TipoUsuario tipoUsuario;

    @BeforeEach
    void setUp() {
        tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(1);
        tipoUsuario.setNombreTipoUsuario("Comprador");

        ejemplo = new Usuario();
        ejemplo.setId(1);
        ejemplo.setNombreUsuario("Juan Silva");
        ejemplo.setCorreo("juan@gmail.com");
        ejemplo.setPassword("123456");
        ejemplo.setTipoUsuario(tipoUsuario);
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarUsuarios_conContenido() throws Exception {
        //Arrange
        when(service.listarUsuarios()).thenReturn(List.of(ejemplo));

        //Act + Assert
        mock.perform(get("/api/v1/usuarios"))
            .andExpect(status().isOk());
    }

    @Test
    void listarUsuarios_vacio() throws Exception {
        //Arrange
        when(service.listarUsuarios()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/usuarios"))
            .andExpect(status().isNoContent());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void obtenerUsuarioPorId_encontrado() throws Exception {
        //Arrange
        when(service.buscarUsuarioPorId(1)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/usuarios/1"))
            .andExpect(status().isOk());
    }

    @Test
    void obtenerUsuarioPorId_no_encontrado() throws Exception {
        //Arrange
        when(service.buscarUsuarioPorId(99)).thenThrow(new RuntimeException("Usuario con id: 99 no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/usuarios/99"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void obtenerUsuarioPorCorreo_encontrado() throws Exception {
        //Arrange
        when(service.buscarUsuarioPorCorreo("juan@gmail.com")).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/usuarios/correo/juan@gmail.com"))
            .andExpect(status().isOk());
    }

    @Test
    void obtenerUsuarioPorCorreo_no_encontrado() throws Exception {
        //Arrange
        when(service.buscarUsuarioPorCorreo("noexiste@gmail.com")).thenThrow(new RuntimeException("Usuario con email: noexiste@gmail.com no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/usuarios/correo/noexiste@gmail.com"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void crearUsuario_exitoso() throws Exception {
        //Arrange
        when(service.crearUsuario(ejemplo)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(post("/api/v1/usuarios")
            .contentType("application/json")
            .content("{\"id\":1,\"nombreUsuario\":\"Juan Silva\",\"correo\":\"juan@gmail.com\",\"password\":\"123456\",\"tipoUsuario\":{\"id\":1,\"nombreTipoUsuario\":\"Comprador\"}}"))
            .andExpect(status().isCreated());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void actualizarUsuario_existente() throws Exception {
        //Arrange
        when(service.actualizarUsuario(1, ejemplo)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(put("/api/v1/usuarios/1")
            .contentType("application/json")
            .content("{\"id\":1,\"nombreUsuario\":\"Juan Silva\",\"correo\":\"juan@gmail.com\",\"password\":\"123456\",\"tipoUsuario\":{\"id\":1,\"nombreTipoUsuario\":\"Comprador\"}}"))
            .andExpect(status().isOk());
    }

    @Test
    void actualizarUsuario_no_encontrado() throws Exception {
        //Arrange
        when(service.actualizarUsuario(99, ejemplo)).thenThrow(new RuntimeException("Usuario con id: 99 no encontrado."));

        //Act + Assert
        mock.perform(put("/api/v1/usuarios/99")
            .contentType("application/json")
            .content("{\"id\":1,\"nombreUsuario\":\"Juan Silva\",\"correo\":\"juan@gmail.com\",\"password\":\"123456\",\"tipoUsuario\":{\"id\":1,\"nombreTipoUsuario\":\"Comprador\"}}"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void eliminarUsuario_existente() throws Exception {
        //Arrange
        //metodo void, no hace falta "when"

        //Act + Assert
        mock.perform(delete("/api/v1/usuarios/1"))
            .andExpect(status().isOk());
    }

    @Test
    void eliminarUsuario_no_encontrado() throws Exception {
        //Arrange
        doThrow(new RuntimeException("Usuario con id: 99 no encontrado.")).when(service).eliminarUsuario(99);

        //Act + Assert
        mock.perform(delete("/api/v1/usuarios/99"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void login_credencialesValidas() throws Exception {
        //Arrange
        when(service.verificarCredenciales("juan@gmail.com", "123456")).thenReturn(true);

        //Act + Assert
        mock.perform(post("/api/v1/usuarios/login")
            .contentType("application/json")
            .content("{\"correo\":\"juan@gmail.com\",\"password\":\"123456\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void login_credencialesInvalidas() throws Exception {
        //Arrange
        when(service.verificarCredenciales("juan@gmail.com", "incorrecta")).thenReturn(false);

        //Act + Assert
        mock.perform(post("/api/v1/usuarios/login")
            .contentType("application/json")
            .content("{\"correo\":\"juan@gmail.com\",\"password\":\"incorrecta\"}"))
            .andExpect(status().isUnauthorized());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}