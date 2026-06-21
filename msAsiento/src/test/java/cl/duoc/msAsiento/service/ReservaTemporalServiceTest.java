package cl.duoc.msAsiento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msAsiento.model.Asiento;
import cl.duoc.msAsiento.model.ReservaTemporal;
import cl.duoc.msAsiento.repository.AsientoRepository;
import cl.duoc.msAsiento.repository.ReservaTemporalRepository;

@ExtendWith(MockitoExtension.class)
public class ReservaTemporalServiceTest {

    @Mock
    private ReservaTemporalRepository reservaTemporalRepository;

    @Mock
    private AsientoRepository asientoRepository;

    @InjectMocks
    private ReservaTemporalService reservaTemporalService;

    private ReservaTemporal reservaTemporalEjemplo;
    private Asiento asientoEjemplo;


    @BeforeEach
    void setUp(){

        asientoEjemplo = new Asiento();
        asientoEjemplo.setIdAsiento(1);
        asientoEjemplo.setNumeroAsiento("A1");
        asientoEjemplo.setEstadoAsiento("DISPONIBLE");

        reservaTemporalEjemplo = new ReservaTemporal();
        reservaTemporalEjemplo.setIdReserva(10);
        reservaTemporalEjemplo.setFechaHoraReserva(LocalDateTime.now());
        reservaTemporalEjemplo.setFechaExpiracion(LocalDateTime.now().plusMinutes(10));
        reservaTemporalEjemplo.setEstado("RESERVADO");
        reservaTemporalEjemplo.setAsiento(asientoEjemplo);

    }

//LISTAR RESERVAS TEMPORALES

    @Test
    void listarReservasTemporales_retornaListaReservas() {
        when(reservaTemporalRepository.findAll()).thenReturn(List.of(reservaTemporalEjemplo));

        List<ReservaTemporal> resultado = reservaTemporalService.listarReservasTemporales();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(reservaTemporalRepository, times(1)).findAll();
    }

    @Test
    void listarReservasTemporales_RetornaListaVacia() {
        when(reservaTemporalRepository.findAll()).thenReturn(new ArrayList<>());

        List<ReservaTemporal> resultado = reservaTemporalService.listarReservasTemporales();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(reservaTemporalRepository, times(1)).findAll();
    }


//CREAR RESERVA TEMPORAL

    @Test
    void crearReservaTemporal_exitoso() {
        when(asientoRepository.findById(1)).thenReturn(Optional.of(asientoEjemplo));
        when(asientoRepository.save(any(Asiento.class))).thenReturn(asientoEjemplo);
        when(reservaTemporalRepository.save(any(ReservaTemporal.class))).thenReturn(reservaTemporalEjemplo);

        ReservaTemporal resultado = reservaTemporalService.crearReservaTemporal(1);

        assertNotNull(resultado);
        assertEquals("RESERVADO", asientoEjemplo.getEstadoAsiento());
        verify(asientoRepository, times(1)).save(asientoEjemplo);
        verify(reservaTemporalRepository, times(1)).save(any(ReservaTemporal.class));
    }

    @Test
    void crearReservaTemporal_AsientoNoEncontrado() {
        when(asientoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaTemporalService.crearReservaTemporal(99);
        });

        assertEquals("Asiento no encontrado", exception.getMessage());
        verify(reservaTemporalRepository, never()).save(any(ReservaTemporal.class));
    }

    @Test
    void crearReservaTemporal_AsientoVendido() {
        asientoEjemplo.setEstadoAsiento("VENDIDO");
        when(asientoRepository.findById(1)).thenReturn(Optional.of(asientoEjemplo));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaTemporalService.crearReservaTemporal(1);
        });

        assertEquals("El asiento 1 ya está vendido", exception.getMessage());
    }

    @Test
    void crearReservaTemporal_AsientoReservado() {
        asientoEjemplo.setEstadoAsiento("RESERVADO");
        when(asientoRepository.findById(1)).thenReturn(Optional.of(asientoEjemplo));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaTemporalService.crearReservaTemporal(1);
        });

        assertEquals("El asiento 1 ya tiene una reserva activa", exception.getMessage());
    }

    @Test
    void crearReservaTemporal_AsientoNoDisponible() {
        asientoEjemplo.setEstadoAsiento("MANTENCION");
        when(asientoRepository.findById(1)).thenReturn(Optional.of(asientoEjemplo));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaTemporalService.crearReservaTemporal(1);
        });

        assertEquals("El asiento no está disponible", exception.getMessage());
    }

//CANCELAR RESERVA POR NUM ASIENTO

    @Test
    void cancelarReservaPorNumAsiento_exitoso() {
        asientoEjemplo.setEstadoAsiento("RESERVADO");
        when(asientoRepository.findByNumeroAsiento("A1")).thenReturn(Optional.of(asientoEjemplo));
        when(reservaTemporalRepository.findByAsientoNumeroAsientoAndEstado("A1", "RESERVADO"))
            .thenReturn(Optional.of(reservaTemporalEjemplo));

        String resultado = reservaTemporalService.cancelarReservaPorNumAsiento("A1");

        assertEquals("Reserva cancelada, asiento A1 liberado", resultado);
        assertEquals("DISPONIBLE", asientoEjemplo.getEstadoAsiento());
        assertEquals("CANCELADO", reservaTemporalEjemplo.getEstado());
        verify(asientoRepository, times(1)).save(asientoEjemplo);
        verify(reservaTemporalRepository, times(1)).save(reservaTemporalEjemplo);
    }

    @Test
    void cancelarReservaPorNumAsiento_AsientoNoEncontrado() {
        when(asientoRepository.findByNumeroAsiento("B2")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaTemporalService.cancelarReservaPorNumAsiento("B2");
        });

        assertEquals("Asiento no encontrado", exception.getMessage());
    }

    @Test
    void cancelarReservaPorNumAsiento_AsientoDisponible() {
        asientoEjemplo.setEstadoAsiento("DISPONIBLE");
        when(asientoRepository.findByNumeroAsiento("A1")).thenReturn(Optional.of(asientoEjemplo));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaTemporalService.cancelarReservaPorNumAsiento("A1");
        });

        assertEquals("El asiento A1 no tiene una reserva activa", exception.getMessage());
    }

    @Test
    void cancelarReservaPorNumAsiento_AsientoVendido() {
        asientoEjemplo.setEstadoAsiento("VENDIDO");
        when(asientoRepository.findByNumeroAsiento("A1")).thenReturn(Optional.of(asientoEjemplo));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaTemporalService.cancelarReservaPorNumAsiento("A1");
        });

        assertEquals("El asiento A1 ya está vendido, no se puede cancelar", exception.getMessage());
    }

    @Test
    void cancelarReservaPorNumAsiento_ReservaNoEncontrada() {
        asientoEjemplo.setEstadoAsiento("RESERVADO");
        when(asientoRepository.findByNumeroAsiento("A1")).thenReturn(Optional.of(asientoEjemplo));
        when(reservaTemporalRepository.findByAsientoNumeroAsientoAndEstado("A1", "RESERVADO"))
            .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaTemporalService.cancelarReservaPorNumAsiento("A1");
        });

        assertEquals("No hay reserva activa para el asiento: A1", exception.getMessage());
    }

    
//VERIFICAR EXPIRACION POR ID ASIENTO

    @Test
    void verificarExpiracionPorIdAsiento_ReservaVigente() {
        asientoEjemplo.setEstadoAsiento("RESERVADO");
        reservaTemporalEjemplo.setFechaExpiracion(LocalDateTime.now().plusMinutes(5));
        
        when(asientoRepository.findById(1)).thenReturn(Optional.of(asientoEjemplo));
        when(reservaTemporalRepository.findByAsientoIdAsientoAndEstado(1, "RESERVADO"))
            .thenReturn(Optional.of(reservaTemporalEjemplo));

        String resultado = reservaTemporalService.verificarExpiracionPorIdAsiento(1);

        assertEquals("Reserva vigente", resultado);
    }

    @Test
    void verificarExpiracionPorIdAsiento_ReservaExpirada() {
        asientoEjemplo.setEstadoAsiento("RESERVADO");
        reservaTemporalEjemplo.setFechaExpiracion(LocalDateTime.now().minusMinutes(5));
        
        when(asientoRepository.findById(1)).thenReturn(Optional.of(asientoEjemplo));
        when(reservaTemporalRepository.findByAsientoIdAsientoAndEstado(1, "RESERVADO"))
            .thenReturn(Optional.of(reservaTemporalEjemplo));

        String resultado = reservaTemporalService.verificarExpiracionPorIdAsiento(1);

        assertEquals("Reserva expirada, asiento liberado", resultado);
        assertEquals("DISPONIBLE", asientoEjemplo.getEstadoAsiento());
        assertEquals("CANCELADO", reservaTemporalEjemplo.getEstado());
        verify(asientoRepository, times(1)).save(asientoEjemplo);
        verify(reservaTemporalRepository, times(1)).save(reservaTemporalEjemplo);
    }

    @Test
    void verificarExpiracionPorIdAsiento_AsientoVendido() {
        asientoEjemplo.setEstadoAsiento("VENDIDO");
        when(asientoRepository.findById(1)).thenReturn(Optional.of(asientoEjemplo));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaTemporalService.verificarExpiracionPorIdAsiento(1);
        });

        assertEquals("El asiento id: 1 ya está vendido", exception.getMessage());
    }

    @Test
    void verificarExpiracionPorIdAsiento_AsientoDisponible() {
        asientoEjemplo.setEstadoAsiento("DISPONIBLE");
        when(asientoRepository.findById(1)).thenReturn(Optional.of(asientoEjemplo));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaTemporalService.verificarExpiracionPorIdAsiento(1);
        });

        assertEquals("El asiento id: 1 no tiene una reserva activa", exception.getMessage());
    }


//VERIFICAR EXPIRACION POR NUM ASIENTO

    @Test
    void verificarExpiracionPorNumAsiento_ReservaVigente() {
        asientoEjemplo.setEstadoAsiento("RESERVADO");
        reservaTemporalEjemplo.setFechaExpiracion(LocalDateTime.now().plusMinutes(5));

        when(asientoRepository.findByNumeroAsiento("A1")).thenReturn(Optional.of(asientoEjemplo));
        when(reservaTemporalRepository.findByAsientoNumeroAsientoAndEstado("A1", "RESERVADO"))
            .thenReturn(Optional.of(reservaTemporalEjemplo));

        String resultado = reservaTemporalService.verificarExpiracionPorNumAsiento("A1");

        assertEquals("Reserva aún vigente", resultado);
    }

    @Test
    void verificarExpiracionPorNumAsiento_ReservaExpirada() {
        asientoEjemplo.setEstadoAsiento("RESERVADO");
        reservaTemporalEjemplo.setFechaExpiracion(LocalDateTime.now().minusMinutes(5));

        when(asientoRepository.findByNumeroAsiento("A1")).thenReturn(Optional.of(asientoEjemplo));
        when(reservaTemporalRepository.findByAsientoNumeroAsientoAndEstado("A1", "RESERVADO"))
            .thenReturn(Optional.of(reservaTemporalEjemplo));

        String resultado = reservaTemporalService.verificarExpiracionPorNumAsiento("A1");

        assertEquals("Reserva expirada, asiento liberado", resultado);
        assertEquals("DISPONIBLE", asientoEjemplo.getEstadoAsiento());
        assertEquals("CANCELADO", reservaTemporalEjemplo.getEstado());
    }

    @Test
    void verificarExpiracionPorNumAsiento_AsientoVendido() {
        asientoEjemplo.setEstadoAsiento("VENDIDO");
        when(asientoRepository.findByNumeroAsiento("A1")).thenReturn(Optional.of(asientoEjemplo));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaTemporalService.verificarExpiracionPorNumAsiento("A1");
        });

        assertEquals("El asiento A1 ya está vendido", exception.getMessage());
    }

    
//CONFIRMAR COMPRA POR NUM ASIENTO

    @Test
    void confirmarCompraPorNumAsiento_exitoso() {
        asientoEjemplo.setEstadoAsiento("RESERVADO");
        reservaTemporalEjemplo.setFechaExpiracion(LocalDateTime.now().plusMinutes(5));

        when(asientoRepository.findByNumeroAsiento("A1")).thenReturn(Optional.of(asientoEjemplo));
        when(reservaTemporalRepository.findByAsientoIdAsientoAndEstado(1, "RESERVADO"))
            .thenReturn(Optional.of(reservaTemporalEjemplo));

        String resultado = reservaTemporalService.confirmarCompraPorNumAsiento("A1");

        assertEquals("Compra confirmada, asiento vendido", resultado);
        assertEquals("VENDIDO", asientoEjemplo.getEstadoAsiento());
        assertEquals("PAGADO", reservaTemporalEjemplo.getEstado());
        verify(asientoRepository, times(1)).save(asientoEjemplo);
        verify(reservaTemporalRepository, times(1)).save(reservaTemporalEjemplo);
    }

    @Test
    void confirmarCompraPorNumAsiento_ReservaExpirada() {
        asientoEjemplo.setEstadoAsiento("RESERVADO");
        reservaTemporalEjemplo.setFechaExpiracion(LocalDateTime.now().minusMinutes(5));

        when(asientoRepository.findByNumeroAsiento("A1")).thenReturn(Optional.of(asientoEjemplo));
        when(reservaTemporalRepository.findByAsientoIdAsientoAndEstado(1, "RESERVADO"))
            .thenReturn(Optional.of(reservaTemporalEjemplo));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reservaTemporalService.confirmarCompraPorNumAsiento("A1");
        });

        assertEquals("La reserva expiró, el asiento fue liberado", exception.getMessage());
        assertEquals("DISPONIBLE", asientoEjemplo.getEstadoAsiento());
        assertEquals("CANCELADO", reservaTemporalEjemplo.getEstado());
    }




}





