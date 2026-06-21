package cl.duoc.msComprador.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.msComprador.model.Comprador;
import cl.duoc.msComprador.service.CompradorService;

@WebMvcTest(CompradorController.class)
public class CompradorControllerTest {

    @Autowired
    private MockMvc mock; // Simula llamadas HTTP

    @MockitoBean
    private CompradorService compradorService;

    private Comprador ejemplo;

    @BeforeEach
    void setUp() {
        ejemplo = new Comprador();
        ejemplo.setIdCliente(1);
        ejemplo.setRutCliente("12345678-9");
        ejemplo.setNombreCliente("Juan");
        ejemplo.setApPaternoCliente("Pérez");
        ejemplo.setApMaternoCliente("González");
        ejemplo.setCorreoCliente("juan.perez@gmail.com");
        ejemplo.setTelefonoCliente("+56911112222");
        ejemplo.setPassCliente("password123");
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarClientes_exitoso() throws Exception {
        //Arrange
        when(compradorService.listarCompradores()).thenReturn(List.of(ejemplo));

        //Act + Assert
        mock.perform(get("/api/v1/compradores"))
            .andExpect(status().isOk());
    }

    @Test
    void listarClientes_vacio() throws Exception {
        //Arrange
        when(compradorService.listarCompradores()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/compradores"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("No hay compradores/Clientes registrados"));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void guardarCliente_exitoso() throws Exception {
        //Arrange
        when(compradorService.guardarComprador(any(Comprador.class))).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(post("/api/v1/compradores")
            .contentType("application/json")
            .content("{\"idCliente\":1,\"rutCliente\":\"12345678-9\",\"nombreCliente\":\"Juan\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void guardarCliente_error() throws Exception {
        //Arrange
        when(compradorService.guardarComprador(any(Comprador.class)))
            .thenThrow(new RuntimeException("Ya existe un comprador(cliente) con el rut: 12345678-9"));

        //Act + Assert
        mock.perform(post("/api/v1/compradores")
            .contentType("application/json")
            .content("{\"idCliente\":1,\"rutCliente\":\"12345678-9\",\"nombreCliente\":\"Juan\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Ya existe un comprador(cliente) con el rut: 12345678-9"));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarClientePorId_encontrado() throws Exception {
        //Arrange
        when(compradorService.buscarCompradorPorId(1)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/compradores/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarClientePorId_noEncontrado() throws Exception {
        //Arrange
        when(compradorService.buscarCompradorPorId(99))
            .thenThrow(new RuntimeException("Comprador/Cliente) con id: 99 no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/compradores/id/99"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Comprador/Cliente) con id: 99 no encontrado."));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarClientePorRut_encontrado() throws Exception {
        //Arrange
        when(compradorService.buscarCompradorPorRut("12345678-9")).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/compradores/rut/12345678-9"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarClientePorRut_noEncontrado() throws Exception {
        //Arrange
        when(compradorService.buscarCompradorPorRut("98765432-1"))
            .thenThrow(new RuntimeException("Comprador/Cliente con rut: 98765432-1 no encontrado"));

        //Act + Assert
        mock.perform(get("/api/v1/compradores/rut/98765432-1"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Comprador/Cliente con rut: 98765432-1 no encontrado"));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void actualizarClientePorId_existente() throws Exception {
        //Arrange
        when(compradorService.actualizarCompradorPorId(eq(1), any(Comprador.class))).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(put("/api/v1/compradores/actualizar/id/1")
            .contentType("application/json")
            .content("{\"rutCliente\":\"12345678-9\",\"nombreCliente\":\"Juan Carlos\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void actualizarClientePorId_noEncontrado() throws Exception {
        //Arrange
        when(compradorService.actualizarCompradorPorId(eq(99), any(Comprador.class)))
            .thenThrow(new RuntimeException("Comprador/Cliente con id: 99 no encontrado"));

        //Act + Assert
        mock.perform(put("/api/v1/compradores/actualizar/id/99")
            .contentType("application/json")
            .content("{\"rutCliente\":\"12345678-9\",\"nombreCliente\":\"Juan Carlos\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Comprador/Cliente con id: 99 no encontrado"));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void actualizarClientePorRut_existente() throws Exception {
        //Arrange
        when(compradorService.actualizarCompradorPorRut(eq("12345678-9"), any(Comprador.class))).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(put("/api/v1/compradores/actualizar/rut/12345678-9")
            .contentType("application/json")
            .content("{\"rutCliente\":\"12345678-9\",\"nombreCliente\":\"Juan Carlos\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void actualizarClientePorRut_noEncontrado() throws Exception {
        //Arrange
        when(compradorService.actualizarCompradorPorRut(eq("98765432-1"), any(Comprador.class)))
            .thenThrow(new RuntimeException("Comprador/Cliente con rut: 98765432-1 no encontrado"));

        //Act + Assert
        mock.perform(put("/api/v1/compradores/actualizar/rut/98765432-1")
            .contentType("application/json")
            .content("{\"rutCliente\":\"12345678-9\",\"nombreCliente\":\"Juan Carlos\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Comprador/Cliente con rut: 98765432-1 no encontrado"));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void actualizarCorreoClientePorRut_existente() throws Exception {
        //Arrange
        when(compradorService.actualizarCorreoPorRut("12345678-9", "nuevocorreo@gmail.com")).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(patch("/api/v1/compradores/actualizar/correo/12345678-9")
            .contentType("application/json")
            .content("{\"correoCliente\":\"nuevocorreo@gmail.com\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void actualizarCorreoClientePorRut_noEncontrado() throws Exception {
        //Arrange
        when(compradorService.actualizarCorreoPorRut("98765432-1", "nuevocorreo@gmail.com"))
            .thenThrow(new RuntimeException("Comprador/Cliente con rut: 98765432-1 no encontrado"));

        //Act + Assert
        mock.perform(patch("/api/v1/compradores/actualizar/correo/98765432-1")
            .contentType("application/json")
            .content("{\"correoCliente\":\"nuevocorreo@gmail.com\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Comprador/Cliente con rut: 98765432-1 no encontrado"));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void actualizarTelefonoPorRut_existente() throws Exception {
        //Arrange
        when(compradorService.actualizarTelefonoPorRut("12345678-9", "+56988888888")).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(patch("/api/v1/compradores/actualizar/telefono/12345678-9")
            .contentType("application/json")
            .content("{\"telefonoCliente\":\"+56988888888\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void actualizarTelefonoPorRut_noEncontrado() throws Exception {
        //Arrange
        when(compradorService.actualizarTelefonoPorRut("98765432-1", "+56988888888"))
            .thenThrow(new RuntimeException("Comprador/Cliente con rut: 98765432-1 no encontrado"));

        //Act + Assert
        mock.perform(patch("/api/v1/compradores/actualizar/telefono/98765432-1")
            .contentType("application/json")
            .content("{\"telefonoCliente\":\"+56988888888\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Comprador/Cliente con rut: 98765432-1 no encontrado"));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void eliminarClientePorId_existente() throws Exception {
        //Arrange (Al retornar void, no requerimos un 'when')

        //Act + Assert
        mock.perform(delete("/api/v1/compradores/eliminar/id/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Comprador/Cliente con id: 1 eliminado con exito"));
    }

    @Test
    void eliminarClientePorId_noExistente() throws Exception {
        //Arrange
        doThrow(new RuntimeException("Comprador/Cliente con id: 99 no encontrado."))
            .when(compradorService).eliminarCompradorPorId(99);

        //Act + Assert
        mock.perform(delete("/api/v1/compradores/eliminar/id/99"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Comprador/Cliente con id: 99 no encontrado."));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void eliminarClientePorRut_existente() throws Exception {
        //Arrange (Al retornar void, no requerimos un 'when')

        //Act + Assert
        mock.perform(delete("/api/v1/compradores/eliminar/rut/12345678-9"))
            .andExpect(status().isOk())
            .andExpect(content().string("Comprador/Cliente con rut: 12345678-9 eliminado con exito"));
    }

    @Test
    void eliminarClientePorRut_noExistente() throws Exception {
        //Arrange
        doThrow(new RuntimeException("Comprador/Cliente con rut: 98765432-1 no encontrado"))
            .when(compradorService).eliminarCompradorPorRut("98765432-1");

        //Act + Assert
        mock.perform(delete("/api/v1/compradores/eliminar/rut/98765432-1"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Comprador/Cliente con rut: 98765432-1 no encontrado"));
    }
    //-----------------------------------------------------------------------------------------------------------------------------
}