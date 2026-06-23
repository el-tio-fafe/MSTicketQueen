package cl.duoc.msVentaTicket.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.msVentaTicket.model.Detalle;
import cl.duoc.msVentaTicket.model.Ticket;
import cl.duoc.msVentaTicket.service.DetalleService;

@WebMvcTest(DetalleController.class)
public class DetalleControllerTest {

    @Autowired
    private MockMvc mock;//simula llamadas http

    @MockitoBean
    private DetalleService service;

    private Detalle ejemplo;
    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setIdTicket(1);
        ticket.setCodigoQR("qr-1");
        ticket.setIdEvento(1);
        ticket.setNumeroAsiento("A1");
        ticket.setNombreUbicacion(null);

        ejemplo = new Detalle();
        ejemplo.setIdDetalle(1);
        ejemplo.setCantidad(1);
        ejemplo.setPrecioUnitario(10000);
        ejemplo.setDescuento(0);
        ejemplo.setSubTotal(10000);
        ejemplo.setTicket(ticket);
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarDetalles_conContenido() throws Exception {
        //Arrange
        when(service.listarDetalles()).thenReturn(List.of(ejemplo));

        //Act + Assert
        mock.perform(get("/api/v1/detalles"))
            .andExpect(status().isOk());
    }

    @Test
    void listarDetalles_vacio() throws Exception {
        //Arrange
        when(service.listarDetalles()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/detalles"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarDetallePorId_encontrado() throws Exception {
        //Arrange
        when(service.buscarDetallePorId(1)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/detalles/buscar/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarDetallePorId_no_encontrado() throws Exception {
        //Arrange
        when(service.buscarDetallePorId(99)).thenThrow(new RuntimeException("Detalle con id: 99 no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/detalles/buscar/id/99"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarDetallesPorEvento_conContenido() throws Exception {
        //Arrange
        when(service.listarDetallePorEvento(1)).thenReturn(List.of(ejemplo));

        //Act + Assert
        mock.perform(get("/api/v1/detalles/listar/detalles-por-evento/1"))
            .andExpect(status().isOk());
    }

    @Test
    void listarDetallesPorEvento_sin_detalles() throws Exception {
        //Arrange
        when(service.listarDetallePorEvento(99)).thenThrow(new RuntimeException("No hay detalles para el evento id: 99"));

        //Act + Assert
        mock.perform(get("/api/v1/detalles/listar/detalles-por-evento/99"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void crearDetalle_exitoso() throws Exception {
        //Arrange
        when(service.crearDetalle(ejemplo)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(post("/api/v1/detalles")
            .contentType("application/json")
            .content("{\"idDetalle\":1,\"cantidad\":1,\"precioUnitario\":10000,\"descuento\":0,\"subTotal\":10000,\"ticket\":{\"idTicket\":1,\"codigoQR\":\"qr-1\",\"idEvento\":1,\"numeroAsiento\":\"A1\",\"nombreUbicacion\":null}}"))
            .andExpect(status().isOk());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}