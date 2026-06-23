package cl.duoc.msVentaTicket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msVentaTicket.client.AsientoClient;
import cl.duoc.msVentaTicket.client.CompradorClient;
import cl.duoc.msVentaTicket.client.EventoClient;
import cl.duoc.msVentaTicket.client.FacturacionClient;
import cl.duoc.msVentaTicket.client.UbicacionClient;
import cl.duoc.msVentaTicket.dto.AsientoDTO;
import cl.duoc.msVentaTicket.dto.BoletaDTO;
import cl.duoc.msVentaTicket.dto.ComprobanteDTO;
import cl.duoc.msVentaTicket.dto.CompradorDTO;
import cl.duoc.msVentaTicket.dto.EventoDTO;
import cl.duoc.msVentaTicket.dto.UbicacionDTO;
import cl.duoc.msVentaTicket.model.Boleta;
import cl.duoc.msVentaTicket.model.Detalle;
import cl.duoc.msVentaTicket.model.Ticket;
import cl.duoc.msVentaTicket.repository.BoletaRepository;
import cl.duoc.msVentaTicket.repository.DetalleRepository;

@ExtendWith(MockitoExtension.class)
public class BoletaServiceTest {

    @Mock
    private BoletaRepository boletaRepository;

    @Mock
    private DetalleRepository detalleRepository;

    @Mock
    private EventoClient eventoClient;

    @Mock
    private CompradorClient compradorClient;

    @Mock
    private AsientoClient asientoClient;

    @Mock
    private FacturacionClient facturacionClient;

    @Mock
    private UbicacionClient ubicacionClient;

    @InjectMocks
    private BoletaService service;

    private Boleta boleta;
    private Detalle detalleConAsiento;
    private Detalle detalleConUbicacion;
    private Ticket ticketConAsiento;
    private Ticket ticketConUbicacion;
    private CompradorDTO compradorDTO;
    private EventoDTO eventoAprobado;

    @BeforeEach
    void setUp() {
        ticketConAsiento = new Ticket();
        ticketConAsiento.setIdTicket(1);
        ticketConAsiento.setCodigoQR("qr-1");
        ticketConAsiento.setIdEvento(1);
        ticketConAsiento.setNumeroAsiento("A1");
        ticketConAsiento.setNombreUbicacion(null);

        ticketConUbicacion = new Ticket();
        ticketConUbicacion.setIdTicket(2);
        ticketConUbicacion.setCodigoQR("qr-2");
        ticketConUbicacion.setIdEvento(1);
        ticketConUbicacion.setNumeroAsiento(null);
        ticketConUbicacion.setNombreUbicacion("Platea");

        detalleConAsiento = new Detalle();
        detalleConAsiento.setIdDetalle(1);
        detalleConAsiento.setCantidad(1);
        detalleConAsiento.setPrecioUnitario(10000);
        detalleConAsiento.setDescuento(0);
        detalleConAsiento.setSubTotal(10000);
        detalleConAsiento.setTicket(ticketConAsiento);

        detalleConUbicacion = new Detalle();
        detalleConUbicacion.setIdDetalle(2);
        detalleConUbicacion.setCantidad(2);
        detalleConUbicacion.setPrecioUnitario(5000);
        detalleConUbicacion.setDescuento(500);
        detalleConUbicacion.setSubTotal(9000);
        detalleConUbicacion.setTicket(ticketConUbicacion);

        boleta = new Boleta();
        boleta.setIdBoleta(1);
        boleta.setNumeroBoleta(null);
        boleta.setFechaEmision(LocalDate.of(2026, 6, 1));
        boleta.setHoraEmision(LocalTime.of(10, 0));
        boleta.setTotalBoleta(null);
        boleta.setIdComprador(1);
        boleta.setIdComprobante(null);
        boleta.setDetalles(List.of(detalleConAsiento));

        compradorDTO = new CompradorDTO(1, "11111111-1", "Juan", "Pérez", "juan@gmail.com");

        eventoAprobado = new EventoDTO(1, "EVT-001", "Concierto Test", "APROBADO");
    }

    // -----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarBoletas() {
        // Arrange
        when(boletaRepository.findAll()).thenReturn(List.of(boleta));

        // Act
        List<Boleta> resultado = service.listarBoletas();

        // Assert
        assertEquals(1, resultado.size());
        assertEquals(boleta, resultado.get(0));
    }
    // -----------------------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------------------
    // mostrarDetalleBoleta (DTO)
    // -----------------------------------------------------------------------------------------------------------------------------
    @Test
    void mostrarDetalleBoleta_encontrada() {
        // Arrange
        when(boletaRepository.findById(1)).thenReturn(Optional.of(boleta));
        when(compradorClient.buscarCompradorPorId(1)).thenReturn(compradorDTO);
        when(eventoClient.buscarEventoPorId(1)).thenReturn(eventoAprobado);

        // Act
        BoletaDTO resultado = service.mostrarDetalleBoleta(1);

        // Assert
        assertEquals(boleta.getNumeroBoleta(), resultado.getNumeroBoleta());
        assertEquals(boleta.getFechaEmision(), resultado.getFecha());
        assertEquals(boleta.getTotalBoleta(), resultado.getTotalBoleta());
        assertEquals(compradorDTO, resultado.getComprador());
        assertEquals(1, resultado.getEventos().size());
        assertEquals(eventoAprobado, resultado.getEventos().get(0));
    }

    @Test
    void mostrarDetalleBoleta_no_encontrada() {
        // Arrange
        when(boletaRepository.findById(99)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.mostrarDetalleBoleta(99));
        assertEquals("Boleta id: 99no encontrada", ex.getMessage());
    }
    // -----------------------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarBoletaPorId_encontrada() {
        // Arrange
        when(boletaRepository.findById(1)).thenReturn(Optional.of(boleta));

        // Act
        Boleta resultado = service.buscarBoletaPorId(1);

        // Assert
        assertEquals(boleta, resultado);
    }

    @Test
    void buscarBoletaPorId_no_encontrada() {
        // Arrange
        when(boletaRepository.findById(99)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarBoletaPorId(99));
        assertEquals("Boleta con id: 99 no encontrado.", ex.getMessage());
    }
    // -----------------------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarBoletaPorNumero_encontrada() {
        // Arrange
        boleta.setNumeroBoleta(100);
        when(boletaRepository.findByNumeroBoleta(100)).thenReturn(Optional.of(boleta));

        // Act
        Boleta resultado = service.buscarBoletaPorNumero(100);

        // Assert
        assertEquals(boleta, resultado);
    }

    @Test
    void buscarBoletaPorNumero_no_encontrada() {
        // Arrange
        when(boletaRepository.findByNumeroBoleta(999)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarBoletaPorNumero(999));
        assertEquals("Número boleta: 999 no encontrado.", ex.getMessage());
    }
    // -----------------------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarBoletasPorComprador_conContenido() {
        // Arrange
        when(boletaRepository.findByIdComprador(1)).thenReturn(List.of(boleta));

        // Act
        List<Boleta> resultado = service.listarBoletasPorComprador(1);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals(boleta, resultado.get(0));
    }

    @Test
    void listarBoletasPorComprador_vacio() {
        // Arrange
        when(boletaRepository.findByIdComprador(99)).thenReturn(List.of());

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.listarBoletasPorComprador(99));
        assertEquals("El comprador id: 99 no tiene boletas asociadas", ex.getMessage());
    }
    // -----------------------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------------------
    // crearBoleta
    // -----------------------------------------------------------------------------------------------------------------------------
    @Test
    void crearBoleta_comprador_no_existe() {
        // Arrange
        when(compradorClient.buscarCompradorPorId(1)).thenThrow(new RuntimeException("404"));

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crearBoleta(boleta));
        assertEquals("No se puede crear la boleta porque el comprador id: 1 no existe", ex.getMessage());
    }

    @Test
    void crearBoleta_sin_detalles() {
        // Arrange
        boleta.setDetalles(List.of());
        when(compradorClient.buscarCompradorPorId(1)).thenReturn(compradorDTO);

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crearBoleta(boleta));
        assertEquals("La boleta debe tener al menos una compra", ex.getMessage());
    }

    @Test
    void crearBoleta_detalle_no_encontrado_en_bd() {
        // Arrange
        when(compradorClient.buscarCompradorPorId(1)).thenReturn(compradorDTO);
        when(detalleRepository.findById(1)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crearBoleta(boleta));
        assertEquals("Detalle con id: 1 no encontrado", ex.getMessage());
    }

    @Test
    void crearBoleta_evento_no_aprobado() {
        // Arrange
        EventoDTO eventoPendiente = new EventoDTO(1, "EVT-001", "Concierto Test", "PENDIENTE");
        when(compradorClient.buscarCompradorPorId(1)).thenReturn(compradorDTO);
        when(detalleRepository.findById(1)).thenReturn(Optional.of(detalleConAsiento));
        when(eventoClient.buscarEventoPorId(1)).thenReturn(eventoPendiente);

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crearBoleta(boleta));
        assertEquals("No se pueden vender tickets para el evento id: 1 porque no está APROBADO.", ex.getMessage());
    }

    @Test
    void crearBoleta_evento_no_existe() {
        // Arrange
        when(compradorClient.buscarCompradorPorId(1)).thenReturn(compradorDTO);
        when(detalleRepository.findById(1)).thenReturn(Optional.of(detalleConAsiento));
        when(eventoClient.buscarEventoPorId(1)).thenThrow(new RuntimeException("404"));

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crearBoleta(boleta));
        assertEquals("404", ex.getMessage());
    }

    @Test
    void crearBoleta_asiento_no_disponible() {
        // Arrange
        AsientoDTO asientoOcupado = new AsientoDTO(1, "A1", "OCUPADO");
        when(compradorClient.buscarCompradorPorId(1)).thenReturn(compradorDTO);
        when(detalleRepository.findById(1)).thenReturn(Optional.of(detalleConAsiento));
        when(eventoClient.buscarEventoPorId(1)).thenReturn(eventoAprobado);
        when(asientoClient.buscarAsientoPorNum("A1")).thenReturn(asientoOcupado);

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crearBoleta(boleta));
        assertEquals("El asiento: A1 no está disponible.", ex.getMessage());
    }

    @Test
    void crearBoleta_asiento_no_existe() {
        // Arrange
        when(compradorClient.buscarCompradorPorId(1)).thenReturn(compradorDTO);
        when(detalleRepository.findById(1)).thenReturn(Optional.of(detalleConAsiento));
        when(eventoClient.buscarEventoPorId(1)).thenReturn(eventoAprobado);
        when(asientoClient.buscarAsientoPorNum("A1")).thenThrow(new RuntimeException("404"));

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crearBoleta(boleta));
        assertEquals("404", ex.getMessage());
    }

    @Test
    void crearBoleta_exitosa_con_asiento() {
        // Arrange
        AsientoDTO asientoDisponible = new AsientoDTO(1, "A1", "DISPONIBLE");
        when(compradorClient.buscarCompradorPorId(1)).thenReturn(compradorDTO);
        when(detalleRepository.findById(1)).thenReturn(Optional.of(detalleConAsiento));
        when(eventoClient.buscarEventoPorId(1)).thenReturn(eventoAprobado);
        when(asientoClient.buscarAsientoPorNum("A1")).thenReturn(asientoDisponible);
        when(boletaRepository.save(boleta)).thenReturn(boleta);

        // Act
        Boleta resultado = service.crearBoleta(boleta);

        // Assert
        assertEquals(10000, resultado.getTotalBoleta());
        verify(asientoClient, times(1)).crearReservaTemporal(1);
        verify(boletaRepository, times(2)).save(boleta);
    }

    @Test
    void crearBoleta_ubicacion_sin_stock() {
        // Arrange
        boleta.setDetalles(List.of(detalleConUbicacion));
        UbicacionDTO ubicacionSinStock = new UbicacionDTO(1, "Platea", 5000.0, 0, false);

        when(compradorClient.buscarCompradorPorId(1)).thenReturn(compradorDTO);
        when(detalleRepository.findById(2)).thenReturn(Optional.of(detalleConUbicacion));
        when(eventoClient.buscarEventoPorId(1)).thenReturn(eventoAprobado);
        when(ubicacionClient.buscarUbicacionDTOPorNombre("Platea")).thenReturn(ubicacionSinStock);

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crearBoleta(boleta));
        assertEquals("No hay stock disponible para la ubicación: Platea", ex.getMessage());
    }

    @Test
    void crearBoleta_ubicacion_no_existe() {
        // Arrange
        boleta.setDetalles(List.of(detalleConUbicacion));

        when(compradorClient.buscarCompradorPorId(1)).thenReturn(compradorDTO);
        when(detalleRepository.findById(2)).thenReturn(Optional.of(detalleConUbicacion));
        when(eventoClient.buscarEventoPorId(1)).thenReturn(eventoAprobado);
        when(ubicacionClient.buscarUbicacionDTOPorNombre("Platea")).thenThrow(new RuntimeException("404"));

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.crearBoleta(boleta));
        assertEquals("404", ex.getMessage());
    }

    @Test
    void crearBoleta_exitosa_con_ubicacion() {
        // Arrange
        boleta.setDetalles(List.of(detalleConUbicacion));
        UbicacionDTO ubicacionConStock = new UbicacionDTO(1, "Platea", 5000.0, 10, false);

        when(compradorClient.buscarCompradorPorId(1)).thenReturn(compradorDTO);
        when(detalleRepository.findById(2)).thenReturn(Optional.of(detalleConUbicacion));
        when(eventoClient.buscarEventoPorId(1)).thenReturn(eventoAprobado);
        when(ubicacionClient.buscarUbicacionDTOPorNombre("Platea")).thenReturn(ubicacionConStock);
        when(boletaRepository.save(boleta)).thenReturn(boleta);

        // Act
        Boleta resultado = service.crearBoleta(boleta);

        // Assert
        assertEquals(9000, resultado.getTotalBoleta());
        verify(ubicacionClient, times(1)).reducirStock(1);
        verify(boletaRepository, times(2)).save(boleta);
    }
    // -----------------------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------------------
    // asociarComprobante
    // -----------------------------------------------------------------------------------------------------------------------------
    @Test
    void asociarComprobante_boleta_no_encontrada() {
        // Arrange
        when(boletaRepository.findById(99)).thenReturn(Optional.empty());

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.asociarComprobante(99, 1));
        assertEquals("Boleta con id: 99 no encontrado.", ex.getMessage());
    }

    @Test
    void asociarComprobante_ya_tiene_comprobante() {
        // Arrange
        boleta.setIdComprobante(50);
        when(boletaRepository.findById(1)).thenReturn(Optional.of(boleta));

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.asociarComprobante(1, 1));
        assertEquals("La boleta ya tiene un comprobante asociado", ex.getMessage());
    }

    @Test
    void asociarComprobante_comprobante_no_existe() {
        // Arrange
        when(boletaRepository.findById(1)).thenReturn(Optional.of(boleta));
        when(facturacionClient.buscarComprobanteDTOPorId(1)).thenThrow(new RuntimeException("404"));

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.asociarComprobante(1, 1));
        assertEquals("No se puede asociar porque el comprobante con id: 1 no existe.", ex.getMessage());
    }

    @Test
    void asociarComprobante_falla_confirmacion_asiento() {
        // Arrange
        ComprobanteDTO comprobanteDTO = new ComprobanteDTO(1, "CMP-001", 10000, true);
        when(boletaRepository.findById(1)).thenReturn(Optional.of(boleta));
        when(facturacionClient.buscarComprobanteDTOPorId(1)).thenReturn(comprobanteDTO);
        when(asientoClient.confirmarCompra("A1")).thenThrow(new RuntimeException("error feign"));

        // Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.asociarComprobante(1, 1));
        assertEquals("No se pudo confirmar la compra del asiento: A1", ex.getMessage());
    }

    @Test
    void asociarComprobante_exitoso() {
        // Arrange
        ComprobanteDTO comprobanteDTO = new ComprobanteDTO(1, "CMP-001", 10000, true);
        when(boletaRepository.findById(1)).thenReturn(Optional.of(boleta));
        when(facturacionClient.buscarComprobanteDTOPorId(1)).thenReturn(comprobanteDTO);
        when(asientoClient.confirmarCompra("A1")).thenReturn("OK");
        when(boletaRepository.save(boleta)).thenReturn(boleta);

        // Act
        Boleta resultado = service.asociarComprobante(1, 1);

        // Assert
        assertEquals(1, resultado.getIdComprobante());
        verify(boletaRepository, times(1)).save(boleta);
    }

    @Test
    void asociarComprobante_exitoso_sin_asiento_numerado() {
        // Arrange
        boleta.setDetalles(List.of(detalleConUbicacion));
        ComprobanteDTO comprobanteDTO = new ComprobanteDTO(1, "CMP-001", 9000, true);
        when(boletaRepository.findById(1)).thenReturn(Optional.of(boleta));
        when(facturacionClient.buscarComprobanteDTOPorId(1)).thenReturn(comprobanteDTO);
        when(boletaRepository.save(boleta)).thenReturn(boleta);

        // Act
        Boleta resultado = service.asociarComprobante(1, 1);

        // Assert
        assertEquals(1, resultado.getIdComprobante());
        verify(asientoClient, times(0)).confirmarCompra(anyString());
        verify(boletaRepository, times(1)).save(boleta);
    }
    // -----------------------------------------------------------------------------------------------------------------------------

}