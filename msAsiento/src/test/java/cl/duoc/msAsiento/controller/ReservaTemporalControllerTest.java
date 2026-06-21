package cl.duoc.msAsiento.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.msAsiento.model.Asiento;
import cl.duoc.msAsiento.model.ReservaTemporal;
import cl.duoc.msAsiento.service.ReservaTemporalService;

@WebMvcTest(ReservaTemporalController.class)
public class ReservaTemporalControllerTest {

    @Autowired
    private MockMvc mock; // Simula las peticiones HTTP

    @MockitoBean
    private ReservaTemporalService reservaTemporalService; // Service falso utilizando la nueva anotación

    private ReservaTemporal ejemplo;

    @BeforeEach
    void setUp() {
        Asiento asiento = new Asiento();
        asiento.setIdAsiento(1);
        asiento.setNumeroAsiento("A1");
        asiento.setEstadoAsiento("RESERVADO");

        ejemplo = new ReservaTemporal();
        ejemplo.setIdReserva(10);
        ejemplo.setAsiento(asiento);
        ejemplo.setFechaHoraReserva(LocalDateTime.now());
        ejemplo.setFechaExpiracion(LocalDateTime.now().plusMinutes(10));
        ejemplo.setEstado("RESERVADO");
    }


    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarReservasTemporales_exitoso() throws Exception {
        //Arrange
        when(reservaTemporalService.listarReservasTemporales()).thenReturn(List.of(ejemplo));

        //Act + Assert
        mock.perform(get("/api/v1/reservas"))
            .andExpect(status().isOk());
    }

    @Test
    void listarReservasTemporales_vacio() throws Exception {
        //Arrange
        when(reservaTemporalService.listarReservasTemporales()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/reservas"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("No hay reservas registradas"));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void crearReserva_exitoso() throws Exception {
        //Arrange
        when(reservaTemporalService.crearReservaTemporal(1)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(post("/api/v1/reservas/1"))
            .andExpect(status().isOk());
    }

    @Test
    void crearReserva_error() throws Exception {
        //Arrange
        when(reservaTemporalService.crearReservaTemporal(99)).thenThrow(new RuntimeException("Asiento no disponible o ya reservado"));

        //Act + Assert
        mock.perform(post("/api/v1/reservas/99"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Asiento no disponible o ya reservado"));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void cancelarReserva_exitoso() throws Exception {
        //Arrange
        when(reservaTemporalService.cancelarReservaPorNumAsiento("A1")).thenReturn("Reserva cancelada exitosamente");

        //Act + Assert
        mock.perform(put("/api/v1/reservas/cancelar/A1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Reserva cancelada exitosamente"));
    }

    @Test
    void cancelarReserva_error() throws Exception {
        //Arrange
        when(reservaTemporalService.cancelarReservaPorNumAsiento("B2")).thenThrow(new RuntimeException("No existe reserva activa para el asiento B2"));

        //Act + Assert
        mock.perform(put("/api/v1/reservas/cancelar/B2"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("No existe reserva activa para el asiento B2"));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void verificarPorIdAsiento_exitoso() throws Exception {
        //Arrange
        when(reservaTemporalService.verificarExpiracionPorIdAsiento(1)).thenReturn("Reserva vigente");

        //Act + Assert
        mock.perform(get("/api/v1/reservas/verificar/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Reserva vigente"));
    }

    @Test
    void verificarPorIdAsiento_error() throws Exception {
        //Arrange
        when(reservaTemporalService.verificarExpiracionPorIdAsiento(99)).thenThrow(new RuntimeException("Id de asiento inválido"));

        //Act + Assert
        mock.perform(get("/api/v1/reservas/verificar/99"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Id de asiento inválido"));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void verificarPorNumAsiento_exitoso() throws Exception {
        //Arrange
        when(reservaTemporalService.verificarExpiracionPorNumAsiento("A1")).thenReturn("Reserva dentro del tiempo");

        //Act + Assert
        mock.perform(get("/api/v1/reservas/verificar/numero/A1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Reserva dentro del tiempo"));
    }

    @Test
    void verificarPorNumAsiento_error() throws Exception {
        //Arrange
        when(reservaTemporalService.verificarExpiracionPorNumAsiento("B2")).thenThrow(new RuntimeException("El asiento no posee reserva temporal"));

        //Act + Assert
        mock.perform(get("/api/v1/reservas/verificar/numero/B2"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("El asiento no posee reserva temporal"));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void confirmarPorNumAsiento_exitoso() throws Exception {
        //Arrange
        when(reservaTemporalService.confirmarCompraPorNumAsiento("A1")).thenReturn("Compra confirmada con éxito");

        //Act + Assert
        mock.perform(put("/api/v1/reservas/confirmar/numero/A1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Compra confirmada con éxito"));
    }

    @Test
    void confirmarPorNumAsiento_error() throws Exception {
        //Arrange
        when(reservaTemporalService.confirmarCompraPorNumAsiento("B2")).thenThrow(new RuntimeException("La reserva temporal ha expirado"));

        //Act + Assert
        mock.perform(put("/api/v1/reservas/confirmar/numero/B2"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("La reserva temporal ha expirado"));
    }




}
