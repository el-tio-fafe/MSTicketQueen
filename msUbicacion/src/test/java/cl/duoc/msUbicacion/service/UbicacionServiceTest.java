package cl.duoc.msUbicacion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
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

import cl.duoc.msUbicacion.dto.UbicacionDTO;
import cl.duoc.msUbicacion.model.Ubicacion;
import cl.duoc.msUbicacion.repository.UbicacionRepository;

@ExtendWith(MockitoExtension.class)
public class UbicacionServiceTest {

    @Mock
    private UbicacionRepository repository;

    @InjectMocks
    private UbicacionService service;

    private Ubicacion ejemplo;

    @BeforeEach
    void setUp() {
        ejemplo = new Ubicacion();
        ejemplo.setIdUbi(1);
        ejemplo.setNombreUbi("Cancha Central");
        ejemplo.setPrecioUbi(50.0);
        ejemplo.setCapacidadUbi(100);
        ejemplo.setStockDisponibleUbi(50);
        ejemplo.setTieneAsiento(true);
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getAllUbicaciones() {
        //Arrange
        when(repository.findAll()).thenReturn(List.of(ejemplo));

        //Act
        List<Ubicacion> resultado = service.getAllUbicaciones();

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(ejemplo, resultado.get(0));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getUbicacionById_encontrada() {
        //Arrange
        when(repository.findById(1)).thenReturn(Optional.of(ejemplo));

        //Act
        Ubicacion resultado = service.getUbicacionById(1);

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void getUbicacionById_no_encontrada() {
        //Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getUbicacionById(99));
        assertEquals("ubicación con id: 99 no encontrada", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getUbicacionByNombre_encontrada() {
        //Arrange
        when(repository.findByNombreUbi("Cancha Central")).thenReturn(Optional.of(ejemplo));

        //Act
        Ubicacion resultado = service.getUbicacionByNombre("Cancha Central");

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void getUbicacionByNombre_no_encontrada() {
        //Arrange
        when(repository.findByNombreUbi("No Existe")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getUbicacionByNombre("No Existe"));
        assertEquals("ubicación con nombre: No Existe no encontrada", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void tieneAsiento_true() {
        //Arrange
        when(repository.findById(1)).thenReturn(Optional.of(ejemplo));

        //Act
        Boolean resultado = service.tieneAsiento(1);

        //Assert
        assertTrue(resultado);
    }

    @Test
    void tieneAsiento_false() {
        //Arrange
        ejemplo.setTieneAsiento(false);
        when(repository.findById(1)).thenReturn(Optional.of(ejemplo));

        //Act
        Boolean resultado = service.tieneAsiento(1);

        //Assert
        assertFalse(resultado);
    }

    @Test
    void tieneAsiento_no_encontrada() {
        //Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.tieneAsiento(99));
        assertEquals("ubicación con id: 99 no encontrada", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getUbicacionesDisponibles_conContenido() {
        //Arrange
        when(repository.findByStockDisponibleUbiGreaterThan(0)).thenReturn(List.of(ejemplo));

        //Act
        List<Ubicacion> resultado = service.getUbicacionesDisponibles();

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(ejemplo, resultado.get(0));
    }

    @Test
    void getUbicacionesDisponibles_vacio() {
        //Arrange
        when(repository.findByStockDisponibleUbiGreaterThan(0)).thenReturn(List.of());

        //Act
        List<Ubicacion> resultado = service.getUbicacionesDisponibles();

        //Assert
        assertTrue(resultado.isEmpty());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void saveUbicacion_exitosa() {
        //Arrange
        when(repository.findByNombreUbi("Cancha Central")).thenReturn(Optional.empty());
        when(repository.save(ejemplo)).thenReturn(ejemplo);

        //Act
        Ubicacion resultado = service.saveUbicacion(ejemplo);

        //Assert
        assertEquals(ejemplo, resultado);
        verify(repository, times(1)).save(ejemplo);
    }

    @Test
    void saveUbicacion_ya_existe() {
        //Arrange
        when(repository.findByNombreUbi("Cancha Central")).thenReturn(Optional.of(ejemplo));

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.saveUbicacion(ejemplo));
        assertEquals("Ya existe una ubicación con ese nombre", ex.getMessage());
        verify(repository, never()).save(ejemplo);
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void updateUbicacion_existente() {
        //Arrange
        Ubicacion existente = new Ubicacion();
        existente.setIdUbi(1);
        existente.setNombreUbi("Nombre Viejo");
        existente.setPrecioUbi(10.0);
        existente.setCapacidadUbi(10);
        existente.setStockDisponibleUbi(5);
        existente.setTieneAsiento(false);

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        //Act
        Ubicacion resultado = service.updateUbicacion(1, ejemplo);

        //Assert
        assertEquals(ejemplo.getNombreUbi(), resultado.getNombreUbi());
        assertEquals(ejemplo.getPrecioUbi(), resultado.getPrecioUbi());
        assertEquals(ejemplo.getCapacidadUbi(), resultado.getCapacidadUbi());
        assertEquals(ejemplo.getStockDisponibleUbi(), resultado.getStockDisponibleUbi());
        assertEquals(ejemplo.getTieneAsiento(), resultado.getTieneAsiento());
        verify(repository, times(1)).save(existente);
    }

    @Test
    void updateUbicacion_no_encontrada() {
        //Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.updateUbicacion(99, ejemplo));
        assertEquals("Ubicación con id: 99 no encontrada", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void deleteUbicacion_existente() {
        //Arrange
        when(repository.existsById(1)).thenReturn(true);

        //Act
        service.deleteUbicacion(1);

        //Assert
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void deleteUbicacion_no_encontrada() {
        //Arrange
        when(repository.existsById(99)).thenReturn(false);

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.deleteUbicacion(99));
        assertEquals("Ubicación con id: 99 no encontrada. No se puede eliminar.", ex.getMessage());
        verify(repository, never()).deleteById(99);
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    // COMUNICACION CON OTROS MS
    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getUbicacionDTOById_encontrada() {
        //Arrange
        when(repository.findById(1)).thenReturn(Optional.of(ejemplo));

        //Act
        UbicacionDTO resultado = service.getUbicacionDTOById(1);

        //Assert
        assertEquals(ejemplo.getIdUbi(), resultado.getIdUbi());
        assertEquals(ejemplo.getNombreUbi(), resultado.getNombreUbi());
        assertEquals(ejemplo.getPrecioUbi(), resultado.getPrecioUbi());
        assertEquals(ejemplo.getStockDisponibleUbi(), resultado.getStockDisponibleUbi());
        assertEquals(ejemplo.getTieneAsiento(), resultado.getTieneAsiento());
    }

    @Test
    void getUbicacionDTOById_no_encontrada() {
        //Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getUbicacionDTOById(99));
        assertEquals("ubicación con id: 99 no encontrada", ex.getMessage());
    }

    @Test
    void getUbicacionDTOPorNombre_encontrada() {
        //Arrange
        when(repository.findByNombreUbi("Cancha Central")).thenReturn(Optional.of(ejemplo));

        //Act
        UbicacionDTO resultado = service.getUbicacionDTOPorNombre("Cancha Central");

        //Assert
        assertEquals(ejemplo.getIdUbi(), resultado.getIdUbi());
        assertEquals(ejemplo.getNombreUbi(), resultado.getNombreUbi());
        assertEquals(ejemplo.getPrecioUbi(), resultado.getPrecioUbi());
        assertEquals(ejemplo.getStockDisponibleUbi(), resultado.getStockDisponibleUbi());
        assertEquals(ejemplo.getTieneAsiento(), resultado.getTieneAsiento());
    }

    @Test
    void getUbicacionDTOPorNombre_no_encontrada() {
        //Arrange
        when(repository.findByNombreUbi("No Existe")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getUbicacionDTOPorNombre("No Existe"));
        assertEquals("Ubicacion con nombre: No Existe no encontrada", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void reducirStock_exitoso() {
        //Arrange
        when(repository.findById(1)).thenReturn(Optional.of(ejemplo));
        when(repository.save(ejemplo)).thenReturn(ejemplo);

        //Act
        Ubicacion resultado = service.reducirStock(1);

        //Assert
        assertEquals(49, resultado.getStockDisponibleUbi());
        verify(repository, times(1)).save(ejemplo);
    }

    @Test
    void reducirStock_sin_stock() {
        //Arrange
        ejemplo.setStockDisponibleUbi(0);
        when(repository.findById(1)).thenReturn(Optional.of(ejemplo));

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.reducirStock(1));
        assertEquals("No hay stock disponible para la ubicación: Cancha Central", ex.getMessage());
        verify(repository, never()).save(ejemplo);
    }

    @Test
    void reducirStock_no_encontrada() {
        //Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.reducirStock(99));
        assertEquals("ubicación con id: 99 no encontrada", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}