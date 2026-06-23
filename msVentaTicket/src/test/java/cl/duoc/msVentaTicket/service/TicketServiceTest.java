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

import cl.duoc.msVentaTicket.model.Ticket;
import cl.duoc.msVentaTicket.repository.TicketRepository;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository repository;

    @InjectMocks
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
    void listarTickets() {
        //Arrange
        when(repository.findAll()).thenReturn(List.of(ejemplo));

        //Act
        List<Ticket> resultado = service.listarTickets();

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(ejemplo, resultado.get(0));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarTicketPorId_encontrado() {
        //Arrange
        when(repository.findById(1)).thenReturn(Optional.of(ejemplo));

        //Act
        Ticket resultado = service.buscarTicketPorId(1);

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void buscarTicketPorId_no_encontrado() {
        //Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarTicketPorId(99));
        assertEquals("Ticket con id: 99 no encontrado", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarTicketPorEvento_conContenido() {
        //Arrange
        when(repository.findByIdEvento(1)).thenReturn(List.of(ejemplo));

        //Act
        List<Ticket> resultado = service.listarTicketPorEvento(1);

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(ejemplo, resultado.get(0));
    }

    @Test
    void listarTicketPorEvento_vacio() {
        //Arrange
        when(repository.findByIdEvento(99)).thenReturn(List.of());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.listarTicketPorEvento(99));
        assertEquals("No hay tickets para el evento con id: 99", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarTicketsPorUbicacion_conContenido() {
        //Arrange
        when(repository.findByNombreUbicacion("Platea")).thenReturn(List.of(ejemplo));

        //Act
        List<Ticket> resultado = service.listarTicketsPorUbicacion("Platea");

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(ejemplo, resultado.get(0));
    }

    @Test
    void listarTicketsPorUbicacion_vacio() {
        //Arrange
        when(repository.findByNombreUbicacion("Tribuna")).thenReturn(List.of());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.listarTicketsPorUbicacion("Tribuna"));
        assertEquals("No hay tickets para la ubicacion: Tribuna", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarTicketPorNumeroAsiento_encontrado() {
        //Arrange
        when(repository.findByNumeroAsiento("A1")).thenReturn(Optional.of(ejemplo));

        //Act
        Ticket resultado = service.buscarTicketPorNumeroAsiento("A1");

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void buscarTicketPorNumeroAsiento_no_encontrado() {
        //Arrange
        when(repository.findByNumeroAsiento("Z99")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarTicketPorNumeroAsiento("Z99"));
        assertEquals("No existe un ticket para el asiento numero: Z99", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void crearTicket() {
        //Arrange
        when(repository.save(ejemplo)).thenReturn(ejemplo);

        //Act
        Ticket resultado = service.crearTicket(ejemplo);

        //Assert
        assertEquals(ejemplo, resultado);
        verify(repository, times(1)).save(ejemplo);
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}