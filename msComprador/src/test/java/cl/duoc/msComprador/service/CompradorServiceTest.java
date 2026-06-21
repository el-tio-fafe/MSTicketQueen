package cl.duoc.msComprador.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msComprador.model.Comprador;
import cl.duoc.msComprador.repository.CompradorRepository;

@ExtendWith(MockitoExtension.class)
public class CompradorServiceTest {

    @Mock
    private CompradorRepository compradorRepository;

    @InjectMocks
    private CompradorService compradorService;

    private Comprador ejemplo;
    private Comprador actualizado;

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

        actualizado = new Comprador();
        actualizado.setRutCliente("12345678-9");
        actualizado.setNombreCliente("Juan Carlos");
        actualizado.setApPaternoCliente("Pérez");
        actualizado.setApMaternoCliente("González");
        actualizado.setCorreoCliente("nuevo.correo@gmail.com");
        actualizado.setTelefonoCliente("+56933334444");
        actualizado.setPassCliente("nuevaPass456");
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarCompradores_exitoso() {
        //Arrange
        when(compradorRepository.findAll()).thenReturn(List.of(ejemplo));

        //Act
        List<Comprador> resultado = compradorService.listarCompradores();

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("12345678-9", resultado.get(0).getRutCliente());
    }

    @Test
    void listarCompradores_vacio() {
        //Arrange
        when(compradorRepository.findAll()).thenReturn(List.of());

        //Act
        List<Comprador> resultado = compradorService.listarCompradores();

        //Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void guardarComprador_exitoso() {
        //Arrange
        when(compradorRepository.existsByRutCliente("12345678-9")).thenReturn(false);
        when(compradorRepository.save(any(Comprador.class))).thenReturn(ejemplo);

        //Act
        Comprador resultado = compradorService.guardarComprador(ejemplo);

        //Assert
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombreCliente());
    }

    @Test
    void guardarComprador_rutDuplicado_lanzaException() {
        //Arrange
        when(compradorRepository.existsByRutCliente("12345678-9")).thenReturn(true);

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compradorService.guardarComprador(ejemplo);
        });

        assertEquals("Ya existe un comprador(cliente) con el rut: 12345678-9", exception.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarCompradorPorId_encontrado() {
        //Arrange
        when(compradorRepository.findById(1)).thenReturn(Optional.of(ejemplo));

        //Act
        Comprador resultado = compradorService.buscarCompradorPorId(1);

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdCliente());
    }

    @Test
    void buscarCompradorPorId_noEncontrado_lanzaException() {
        //Arrange
        when(compradorRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compradorService.buscarCompradorPorId(99);
        });

        assertEquals("Comprador/Cliente) con id: 99 no encontrado.", exception.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarCompradorPorRut_encontrado() {
        //Arrange
        when(compradorRepository.findByRutCliente("12345678-9")).thenReturn(Optional.of(ejemplo));

        //Act
        Comprador resultado = compradorService.buscarCompradorPorRut("12345678-9");

        //Assert
        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRutCliente());
    }

    @Test
    void buscarCompradorPorRut_noEncontrado_lanzaException() {
        //Arrange
        when(compradorRepository.findByRutCliente("98765432-1")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compradorService.buscarCompradorPorRut("98765432-1");
        });

        assertEquals("Comprador/Cliente con rut: 98765432-1 no encontrado", exception.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void actualizarCompradorPorId_exitoso() {
        //Arrange
        when(compradorRepository.findById(1)).thenReturn(Optional.of(ejemplo));
        when(compradorRepository.save(any(Comprador.class))).thenReturn(ejemplo);

        //Act
        Comprador resultado = compradorService.actualizarCompradorPorId(1, actualizado);

        //Assert
        assertNotNull(resultado);
        assertEquals("Juan Carlos", resultado.getNombreCliente());
        assertEquals("nuevo.correo@gmail.com", resultado.getCorreoCliente());
    }

    @Test
    void actualizarCompradorPorId_noEncontrado_lanzaException() {
        //Arrange
        when(compradorRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compradorService.actualizarCompradorPorId(99, actualizado);
        });

        assertEquals("Comprador/Cliente con id: 99 no encontrado", exception.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void actualizarCompradorPorRut_exitoso() {
        //Arrange
        when(compradorRepository.findByRutCliente("12345678-9")).thenReturn(Optional.of(ejemplo));
        when(compradorRepository.save(any(Comprador.class))).thenReturn(ejemplo);

        //Act
        Comprador resultado = compradorService.actualizarCompradorPorRut("12345678-9", actualizado);

        //Assert
        assertNotNull(resultado);
        assertEquals("Juan Carlos", resultado.getNombreCliente());
    }

    @Test
    void actualizarCompradorPorRut_noEncontrado_lanzaException() {
        //Arrange
        when(compradorRepository.findByRutCliente("98765432-1")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compradorService.actualizarCompradorPorRut("98765432-1", actualizado);
        });

        assertEquals("Comprador/Cliente con rut: 98765432-1 no encontrado", exception.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void actualizarCorreoPorRut_exitoso() {
        //Arrange
        when(compradorRepository.findByRutCliente("12345678-9")).thenReturn(Optional.of(ejemplo));
        when(compradorRepository.save(any(Comprador.class))).thenReturn(ejemplo);

        //Act
        Comprador resultado = compradorService.actualizarCorreoPorRut("12345678-9", "cambio.correo@gmail.com");

        //Assert
        assertNotNull(resultado);
        assertEquals("cambio.correo@gmail.com", resultado.getCorreoCliente());
    }


    @Test
    void actualizarCorreoPorRut_noEncontrado_lanzaException() {
        //Arrange
        when(compradorRepository.findByRutCliente("98765432-1")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compradorService.actualizarCorreoPorRut("98765432-1", "nuevo@correo.com");
        });

        assertEquals("Comprador/Cliente con rut: 98765432-1 no encontrado", exception.getMessage());
    }

    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void actualizarTelefonoPorRut_exitoso() {
        //Arrange
        when(compradorRepository.findByRutCliente("12345678-9")).thenReturn(Optional.of(ejemplo));
        when(compradorRepository.save(any(Comprador.class))).thenReturn(ejemplo);

        //Act
        Comprador resultado = compradorService.actualizarTelefonoPorRut("12345678-9", "+56999999999");

        //Assert
        assertNotNull(resultado);
        assertEquals("+56999999999", resultado.getTelefonoCliente());
    }

    @Test
    void actualizarTelefonoPorRut_noEncontrado_lanzaException() {
        //Arrange
        when(compradorRepository.findByRutCliente("98765432-1")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compradorService.actualizarTelefonoPorRut("98765432-1", "+56911111111");
        });

        assertEquals("Comprador/Cliente con rut: 98765432-1 no encontrado", exception.getMessage());
    }


    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void eliminarCompradorPorId_exitoso() {
        //Arrange
        when(compradorRepository.existsById(1)).thenReturn(true);

        //Act
        compradorService.eliminarCompradorPorId(1);

        //Assert
        verify(compradorRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarCompradorPorId_noEncontrado_lanzaException() {
        //Arrange
        when(compradorRepository.existsById(99)).thenReturn(false);

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compradorService.eliminarCompradorPorId(99);
        });

        assertEquals("Comprador/Cliente con id: 99 no encontrado.", exception.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void eliminarCompradorPorRut_exitoso() {
        //Arrange
        when(compradorRepository.findByRutCliente("12345678-9")).thenReturn(Optional.of(ejemplo));

        //Act
        compradorService.eliminarCompradorPorRut("12345678-9");

        //Assert
        verify(compradorRepository, times(1)).deleteById(1);
    }

    
    @Test
    void eliminarCompradorPorRut_noEncontrado_lanzaException() {
        //Arrange
        when(compradorRepository.findByRutCliente("98765432-1")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            compradorService.eliminarCompradorPorRut("98765432-1");
        });

        assertEquals("Comprador/Cliente con rut: 98765432-1 no encontrado", exception.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------
}