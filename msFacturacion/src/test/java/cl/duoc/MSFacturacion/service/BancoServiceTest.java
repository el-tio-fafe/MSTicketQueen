package cl.duoc.MSFacturacion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msFacturacion.dto.ComprobanteDTO;
import cl.duoc.msFacturacion.model.Banco;
import cl.duoc.msFacturacion.model.Comprobante;
import cl.duoc.msFacturacion.model.FormaPago;
import cl.duoc.msFacturacion.repository.BancoRepository;
import cl.duoc.msFacturacion.repository.ComprobanteRepository;
import cl.duoc.msFacturacion.repository.FormaPagoRepository;
import cl.duoc.msFacturacion.service.BancoService;

@ExtendWith(MockitoExtension.class)
public class BancoServiceTest {

    @Mock
    private BancoRepository bancoRepository;

    @Mock
    private ComprobanteRepository comprobanteRepository;

    @Mock
    private FormaPagoRepository formaPagoRepository;

    @InjectMocks
    private BancoService service;

    private Banco banco;
    private FormaPago formaPago;
    private Comprobante comprobante;

    @BeforeEach
    void setUp() {
        banco = new Banco();
        banco.setIdBanco(1);
        banco.setNombreBanco("Banco de Chile");

        formaPago = new FormaPago();
        formaPago.setIdFormaPago(1);
        formaPago.setMedioDePago("Transferencia");

        comprobante = new Comprobante();
        comprobante.setIdComprobante(1);
        comprobante.setNumeroComprobante("CMP-001");
        comprobante.setFechaEmision(LocalDateTime.of(2026, 6, 1, 10, 0));
        comprobante.setMontoTotal(50000);
        comprobante.setMetodoPago("Transferencia");
        comprobante.setEstadoPago(true);
        comprobante.setFormaPago(formaPago);
        comprobante.setBanco(banco);
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    // BANCO
    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarBancos() {
        //Arrange
        when(bancoRepository.findAll()).thenReturn(List.of(banco));

        //Act
        List<Banco> resultado = service.listarBancos();

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(banco, resultado.get(0));
    }

    @Test
    void buscarBancoPorId_encontrado() {
        //Arrange
        when(bancoRepository.findById(1)).thenReturn(Optional.of(banco));

        //Act
        Banco resultado = service.buscarBancoPorId(1);

        //Assert
        assertEquals(banco, resultado);
    }

    @Test
    void buscarBancoPorId_no_encontrado() {
        //Arrange
        when(bancoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarBancoPorId(99));
        assertEquals("Banco con id: 99 no encontrado.", ex.getMessage());
    }

    @Test
    void buscarBancoPorNombre_encontrado() {
        //Arrange
        when(bancoRepository.findByNombreBanco("Banco de Chile")).thenReturn(Optional.of(banco));

        //Act
        Banco resultado = service.buscarBancoPorNombre("Banco de Chile");

        //Assert
        assertEquals(banco, resultado);
    }

    @Test
    void buscarBancoPorNombre_no_encontrado() {
        //Arrange
        when(bancoRepository.findByNombreBanco("Banco Falso")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarBancoPorNombre("Banco Falso"));
        assertEquals("Banco con nombre: Banco Falso no encontrado.", ex.getMessage());
    }

    @Test
    void guardarBanco_exitoso() {
        //Arrange
        when(bancoRepository.findByNombreBanco("Banco de Chile")).thenReturn(Optional.empty());
        when(bancoRepository.save(banco)).thenReturn(banco);

        //Act
        Banco resultado = service.guardarBanco(banco);

        //Assert
        assertEquals(banco, resultado);
        verify(bancoRepository, times(1)).save(banco);
    }

    @Test
    void guardarBanco_ya_existe() {
        //Arrange
        when(bancoRepository.findByNombreBanco("Banco de Chile")).thenReturn(Optional.of(banco));

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.guardarBanco(banco));
        assertEquals("Ya existe un banco con el nombre: Banco de Chile.", ex.getMessage());
        verify(bancoRepository, never()).save(banco);
    }

    @Test
    void actualizarBanco_existente() {
        //Arrange
        Banco existente = new Banco();
        existente.setIdBanco(1);
        existente.setNombreBanco("Banco Viejo");

        Banco actualizado = new Banco();
        actualizado.setNombreBanco("Banco de Chile");

        when(bancoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(bancoRepository.save(existente)).thenReturn(existente);

        //Act
        Banco resultado = service.actualizarBanco(1, actualizado);

        //Assert
        assertEquals("Banco de Chile", resultado.getNombreBanco());
        verify(bancoRepository, times(1)).save(existente);
    }

    @Test
    void actualizarBanco_no_encontrado() {
        //Arrange
        when(bancoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.actualizarBanco(99, banco));
        assertEquals("Banco con id: 99 no encontrado.", ex.getMessage());
    }

    @Test
    void eliminarBancoPorId_existente() {
        //Arrange
        when(bancoRepository.findById(1)).thenReturn(Optional.of(banco));

        //Act
        service.eliminarBancoPorId(1);

        //Assert
        verify(bancoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarBancoPorId_no_encontrado() {
        //Arrange
        when(bancoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.eliminarBancoPorId(99));
        assertEquals("Banco con id: 99 no encontrado.", ex.getMessage());
        verify(bancoRepository, never()).deleteById(99);
    }

    @Test
    void eliminarBancoPorNombre_existente() {
        //Arrange
        when(bancoRepository.findByNombreBanco("Banco de Chile")).thenReturn(Optional.of(banco));

        //Act
        service.eliminarBancoPorNombre("Banco de Chile");

        //Assert
        verify(bancoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarBancoPorNombre_no_encontrado() {
        //Arrange
        when(bancoRepository.findByNombreBanco("Banco Falso")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.eliminarBancoPorNombre("Banco Falso"));
        assertEquals("Banco con nombre: Banco Falso no encontrado.", ex.getMessage());
        verify(bancoRepository, never()).deleteById(anyInt());
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    // COMPROBANTE
    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarTodosComprobantes() {
        //Arrange
        when(comprobanteRepository.findAll()).thenReturn(List.of(comprobante));

        //Act
        List<Comprobante> resultado = service.listarTodosComprobantes();

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(comprobante, resultado.get(0));
    }

    @Test
    void listarComprobantesPorBanco_encontrado() {
        //Arrange
        when(bancoRepository.findById(1)).thenReturn(Optional.of(banco));
        when(comprobanteRepository.findByBanco(banco)).thenReturn(List.of(comprobante));

        //Act
        List<Comprobante> resultado = service.listarComprobantesPorBanco(1);

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(comprobante, resultado.get(0));
    }

    @Test
    void listarComprobantesPorBanco_banco_no_encontrado() {
        //Arrange
        when(bancoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.listarComprobantesPorBanco(99));
        assertEquals("Banco con id: 99 no encontrado.", ex.getMessage());
    }

    @Test
    void generarComprobante_exitoso() {
        //Arrange
        when(comprobanteRepository.findByNumeroComprobante("CMP-001")).thenReturn(Optional.empty());
        when(bancoRepository.findById(1)).thenReturn(Optional.of(banco));
        when(comprobanteRepository.save(comprobante)).thenReturn(comprobante);

        //Act
        Comprobante resultado = service.generarComprobante(comprobante);

        //Assert
        assertEquals(comprobante, resultado);
        verify(comprobanteRepository, times(1)).save(comprobante);
    }

    @Test
    void generarComprobante_numero_duplicado() {
        //Arrange
        when(comprobanteRepository.findByNumeroComprobante("CMP-001")).thenReturn(Optional.of(comprobante));

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.generarComprobante(comprobante));
        assertEquals("Ya existe un comprobante con número: CMP-001", ex.getMessage());
        verify(comprobanteRepository, never()).save(comprobante);
    }

    @Test
    void generarComprobante_banco_no_encontrado() {
        //Arrange
        when(comprobanteRepository.findByNumeroComprobante("CMP-001")).thenReturn(Optional.empty());
        when(bancoRepository.findById(1)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.generarComprobante(comprobante));
        assertEquals("Banco con id: 1 no encontrado.", ex.getMessage());
        verify(comprobanteRepository, never()).save(comprobante);
    }

    @Test
    void buscarComprobantePorNumero_encontrado() {
        //Arrange
        when(comprobanteRepository.findByNumeroComprobante("CMP-001")).thenReturn(Optional.of(comprobante));

        //Act
        Comprobante resultado = service.buscarComprobantePorNumero("CMP-001");

        //Assert
        assertEquals(comprobante, resultado);
    }

    @Test
    void buscarComprobantePorNumero_no_encontrado() {
        //Arrange
        when(comprobanteRepository.findByNumeroComprobante("CMP-999")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarComprobantePorNumero("CMP-999"));
        assertEquals("Comprobante con número: CMP-999 no encontrado.", ex.getMessage());
    }

    @Test
    void anularComprobantePorNumero_exitoso() {
        //Arrange
        when(comprobanteRepository.findByNumeroComprobante("CMP-001")).thenReturn(Optional.of(comprobante));
        when(comprobanteRepository.save(comprobante)).thenReturn(comprobante);

        //Act
        Comprobante resultado = service.anularComprobantePorNumero("CMP-001");

        //Assert
        assertEquals(false, resultado.isEstadoPago());
        verify(comprobanteRepository, times(1)).save(comprobante);
    }

    @Test
    void anularComprobantePorNumero_no_encontrado() {
        //Arrange
        when(comprobanteRepository.findByNumeroComprobante("CMP-999")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.anularComprobantePorNumero("CMP-999"));
        assertEquals("Comprobante con número: CMP-999 no encontrado.", ex.getMessage());
    }

    @Test
    void filtrarComprobantesPorFechaEmision_encontrado() {
        //Arrange
        LocalDateTime fecha = LocalDateTime.of(2026, 6, 1, 10, 0);
        when(bancoRepository.findById(1)).thenReturn(Optional.of(banco));
        when(comprobanteRepository.findByBancoAndFechaEmision(banco, fecha)).thenReturn(List.of(comprobante));

        //Act
        List<Comprobante> resultado = service.filtrarComprobantesPorFechaEmision(1, fecha);

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(comprobante, resultado.get(0));
    }

    @Test
    void filtrarComprobantesPorFechaEmision_banco_no_encontrado() {
        //Arrange
        LocalDateTime fecha = LocalDateTime.of(2026, 6, 1, 10, 0);
        when(bancoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.filtrarComprobantesPorFechaEmision(99, fecha));
        assertEquals("Banco con id: 99 no encontrado.", ex.getMessage());
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    // FORMA DE PAGO
    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarTodasFormasPago() {
        //Arrange
        when(formaPagoRepository.findAll()).thenReturn(List.of(formaPago));

        //Act
        List<FormaPago> resultado = service.listarTodasFormasPago();

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(formaPago, resultado.get(0));
    }

    @Test
    void listarFormasDePagoPorIdBanco_encontrado() {
        //Arrange
        when(bancoRepository.findById(1)).thenReturn(Optional.of(banco));
        when(comprobanteRepository.findByBanco(banco)).thenReturn(List.of(comprobante));

        //Act
        List<FormaPago> resultado = service.listarFormasDePagoPorIdBanco(1);

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(formaPago, resultado.get(0));
    }

    @Test
    void listarFormasDePagoPorIdBanco_banco_no_encontrado() {
        //Arrange
        when(bancoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.listarFormasDePagoPorIdBanco(99));
        assertEquals("Banco con id: 99 no encontrado.", ex.getMessage());
    }

    @Test
    void listarFormasDePagoPorNombreBanco_encontrado() {
        //Arrange
        when(bancoRepository.findByNombreBanco("Banco de Chile")).thenReturn(Optional.of(banco));
        when(comprobanteRepository.findByBanco(banco)).thenReturn(List.of(comprobante));

        //Act
        List<FormaPago> resultado = service.listarFormasDePagoPorNombreBanco("Banco de Chile");

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(formaPago, resultado.get(0));
    }

    @Test
    void listarFormasDePagoPorNombreBanco_banco_no_encontrado() {
        //Arrange
        when(bancoRepository.findByNombreBanco("Banco Falso")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.listarFormasDePagoPorNombreBanco("Banco Falso"));
        assertEquals("Banco con nombre: Banco Falso no encontrado.", ex.getMessage());
    }

    @Test
    void buscarFormaPagoPorSuId_encontrado() {
        //Arrange
        when(formaPagoRepository.findById(1)).thenReturn(Optional.of(formaPago));

        //Act
        FormaPago resultado = service.buscarFormaPagoPorSuId(1);

        //Assert
        assertEquals(formaPago, resultado);
    }

    @Test
    void buscarFormaPagoPorSuId_no_encontrado() {
        //Arrange
        when(formaPagoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarFormaPagoPorSuId(99));
        assertEquals("Forma de pago con id: 99 no encontrada", ex.getMessage());
    }

    @Test
    void buscarFormaPagoPorMedio_encontrado() {
        //Arrange
        when(formaPagoRepository.findByMedioDePago("Transferencia")).thenReturn(Optional.of(formaPago));

        //Act
        FormaPago resultado = service.buscarFormaPagoPorMedio("Transferencia");

        //Assert
        assertEquals(formaPago, resultado);
    }

    @Test
    void buscarFormaPagoPorMedio_no_encontrado() {
        //Arrange
        when(formaPagoRepository.findByMedioDePago("Bitcoin")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarFormaPagoPorMedio("Bitcoin"));
        assertEquals("Forma de pago: Bitcoin no encontrada.", ex.getMessage());
    }

    @Test
    void guardarFormaPago_exitoso() {
        //Arrange
        when(formaPagoRepository.findByMedioDePago("Transferencia")).thenReturn(Optional.empty());
        when(formaPagoRepository.save(formaPago)).thenReturn(formaPago);

        //Act
        FormaPago resultado = service.guardarFormaPago(formaPago);

        //Assert
        assertEquals(formaPago, resultado);
        verify(formaPagoRepository, times(1)).save(formaPago);
    }

    @Test
    void guardarFormaPago_ya_existe() {
        //Arrange
        when(formaPagoRepository.findByMedioDePago("Transferencia")).thenReturn(Optional.of(formaPago));

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.guardarFormaPago(formaPago));
        assertEquals("Ya existe la forma de pago: Transferencia", ex.getMessage());
        verify(formaPagoRepository, never()).save(formaPago);
    }

    @Test
    void actualizarFormaPago_existente() {
        //Arrange
        FormaPago existente = new FormaPago();
        existente.setIdFormaPago(1);
        existente.setMedioDePago("Efectivo");

        FormaPago actualizado = new FormaPago();
        actualizado.setMedioDePago("Transferencia");

        when(formaPagoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(formaPagoRepository.save(existente)).thenReturn(existente);

        //Act
        FormaPago resultado = service.actualizarFormaPago(1, actualizado);

        //Assert
        assertEquals("Transferencia", resultado.getMedioDePago());
        verify(formaPagoRepository, times(1)).save(existente);
    }

    @Test
    void actualizarFormaPago_no_encontrada() {
        //Arrange
        when(formaPagoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.actualizarFormaPago(99, formaPago));
        assertEquals("Forma de pago con id: 99 no encontrada", ex.getMessage());
    }

    @Test
    void eliminarFormaPago_existente() {
        //Arrange
        when(formaPagoRepository.findById(1)).thenReturn(Optional.of(formaPago));

        //Act
        service.eliminarFormaPago(1);

        //Assert
        verify(formaPagoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarFormaPago_no_encontrada() {
        //Arrange
        when(formaPagoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.eliminarFormaPago(99));
        assertEquals("Forma de pago con id: 99 no encontrada", ex.getMessage());
        verify(formaPagoRepository, never()).deleteById(99);
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    // COMUNICACION CON OTRO MS (DTO)
    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarComprobanteDTOPorId_encontrado() {
        //Arrange
        when(comprobanteRepository.findById(1)).thenReturn(Optional.of(comprobante));

        //Act
        ComprobanteDTO resultado = service.buscarComprobanteDTOPorId(1);

        //Assert
        assertEquals(comprobante.getIdComprobante(), resultado.getIdComprobante());
        assertEquals(comprobante.getNumeroComprobante(), resultado.getNumeroComprobante());
        assertEquals(comprobante.getMontoTotal(), resultado.getMontoTotal());
        assertEquals(comprobante.isEstadoPago(), resultado.isEstadoPago());
    }

    @Test
    void buscarComprobanteDTOPorId_no_encontrado() {
        //Arrange
        when(comprobanteRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarComprobanteDTOPorId(99));
        assertEquals("Comprobante con id: 99 no encontrado.", ex.getMessage());
    }

}