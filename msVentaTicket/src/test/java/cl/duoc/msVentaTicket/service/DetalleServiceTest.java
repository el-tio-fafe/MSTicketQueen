package cl.duoc.msVentaTicket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msVentaTicket.model.Detalle;
import cl.duoc.msVentaTicket.model.Ticket;
import cl.duoc.msVentaTicket.repository.DetalleRepository;

@ExtendWith(MockitoExtension.class)
public class DetalleServiceTest {

    @Mock
    private DetalleRepository repository;

    @InjectMocks
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
    void listarDetalles() {
        //Arrange
        when(repository.findAll()).thenReturn(List.of(ejemplo));

        //Act
        List<Detalle> resultado = service.listarDetalles();

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(ejemplo, resultado.get(0));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarDetallePorId_encontrado() {
        //Arrange
        when(repository.findById(1)).thenReturn(Optional.of(ejemplo));

        //Act
        Detalle resultado = service.buscarDetallePorId(1);

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void buscarDetallePorId_no_encontrado() {
        //Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarDetallePorId(99));
        assertEquals("Detalle con id: 99 no encontrado.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarDetallePorEvento_conContenido() {
        //Arrange
        when(repository.findByTicket_IdEvento(1)).thenReturn(List.of(ejemplo));

        //Act
        List<Detalle> resultado = service.listarDetallePorEvento(1);

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(ejemplo, resultado.get(0));
    }

    @Test
    void listarDetallePorEvento_vacio() {
        //Arrange
        when(repository.findByTicket_IdEvento(99)).thenReturn(List.of());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.listarDetallePorEvento(99));
        assertEquals("No hay detalles para el evento id: 99", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void crearDetalle() {
        //Arrange
        when(repository.save(ejemplo)).thenReturn(ejemplo);

        //Act
        Detalle resultado = service.crearDetalle(ejemplo);

        //Assert
        assertEquals(ejemplo, resultado);
        verify(repository, times(1)).save(ejemplo);
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}