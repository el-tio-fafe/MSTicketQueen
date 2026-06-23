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

import cl.duoc.msVentaTicket.model.Ticket;
import cl.duoc.msVentaTicket.service.TicketService;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mock;//simula llamadas http

    @MockitoBean
    private TicketService service;

    private Ticket ejemplo;

    @BeforeEach
    void setUp() {
        ejemplo = new Ticket();
        ejemplo.setIdTicket(1);
        ejemplo.setCodigoQR("qr-uuid-123");
        ejemplo.setIdEvento(1);
        ejemplo.setNumeroAsiento("A1");
        ejemplo.setNombreUbicacion("Platea");
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarTickets_conContenido() throws Exception {
        //Arrange
        when(service.listarTickets()).thenReturn(List.of(ejemplo));

        //Act + Assert
        mock.perform(get("/api/v1/tickets"))
            .andExpect(status().isOk());
    }

    @Test
    void listarTickets_vacio() throws Exception {
        //Arrange
        when(service.listarTickets()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/tickets"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarTicketsPorIdTicket_encontrado() throws Exception {
        //Arrange
        when(service.buscarTicketPorId(1)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/tickets/buscar/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarTicketsPorIdTicket_no_encontrado() throws Exception {
        //Arrange
        when(service.buscarTicketPorId(99)).thenThrow(new RuntimeException("Ticket con id: 99 no encontrado"));

        //Act + Assert
        mock.perform(get("/api/v1/tickets/buscar/id/99"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarTicketPorIdEvento_conContenido() throws Exception {
        //Arrange
        when(service.listarTicketPorEvento(1)).thenReturn(List.of(ejemplo));

        //Act + Assert
        mock.perform(get("/api/v1/tickets/listar/evento/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void listarTicketPorIdEvento_sin_tickets() throws Exception {
        //Arrange
        when(service.listarTicketPorEvento(99)).thenThrow(new RuntimeException("No hay tickets para el evento con id: 99"));

        //Act + Assert
        mock.perform(get("/api/v1/tickets/listar/evento/id/99"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarTicketsPorUbicacion_conContenido() throws Exception {
        //Arrange
        when(service.listarTicketsPorUbicacion("Platea")).thenReturn(List.of(ejemplo));

        //Act + Assert
        mock.perform(get("/api/v1/tickets/listar/ubicacion/nombre/Platea"))
            .andExpect(status().isOk());
    }

    @Test
    void listarTicketsPorUbicacion_sin_tickets() throws Exception {
        //Arrange
        when(service.listarTicketsPorUbicacion("Tribuna")).thenThrow(new RuntimeException("No hay tickets para la ubicacion: Tribuna"));

        //Act + Assert
        mock.perform(get("/api/v1/tickets/listar/ubicacion/nombre/Tribuna"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarTicketPorNumAsiento_encontrado() throws Exception {
        //Arrange
        when(service.buscarTicketPorNumeroAsiento("A1")).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/tickets/buscar/asiento/A1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarTicketPorNumAsiento_no_encontrado() throws Exception {
        //Arrange
        when(service.buscarTicketPorNumeroAsiento("Z99")).thenThrow(new RuntimeException("No existe un ticket para el asiento numero: Z99"));

        //Act + Assert
        mock.perform(get("/api/v1/tickets/buscar/asiento/Z99"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void crearTicket_exitoso() throws Exception {
        //Arrange
        when(service.crearTicket(ejemplo)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(post("/api/v1/tickets")
            .contentType("application/json")
            .content("{\"idTicket\":1,\"codigoQR\":\"qr-uuid-123\",\"idEvento\":1,\"numeroAsiento\":\"A1\",\"nombreUbicacion\":\"Platea\"}"))
            .andExpect(status().isOk());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}