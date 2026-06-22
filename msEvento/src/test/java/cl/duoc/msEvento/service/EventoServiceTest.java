package cl.duoc.msEvento.service;

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

import cl.duoc.msEvento.model.Evento;
import cl.duoc.msEvento.model.Recinto;
import cl.duoc.msEvento.model.TipoEvento;
import cl.duoc.msEvento.repository.EventoRepository;
import cl.duoc.msEvento.repository.RecintoRepository;
import cl.duoc.msEvento.repository.TipoEventoRepository;

@ExtendWith(MockitoExtension.class)
public class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private TipoEventoRepository tipoEventoRepository;

    @Mock
    private RecintoRepository recintoRepository;

    @InjectMocks
    private EventoService eventoService;

    private Evento eventoEjemplo;
    private TipoEvento tipoEventoEjemplo;
    private Recinto recintoEjemplo;

    @BeforeEach
    void setUp() {
        eventoEjemplo = new Evento();
        eventoEjemplo.setIdEvento(1);
        eventoEjemplo.setCodigoEvento("EVT-2026-001");
        eventoEjemplo.setEstadoEvento("PENDIENTE");
        eventoEjemplo.setIdProd(10);

        tipoEventoEjemplo = new TipoEvento();
        tipoEventoEjemplo.setIdTipoEvento(1);
        tipoEventoEjemplo.setDescripcion("Concierto");

        recintoEjemplo = new Recinto();
        recintoEjemplo.setIdRecinto(1);
        recintoEjemplo.setRutRecinto("77666555-4");
        recintoEjemplo.setNombreRecinto("Movistar Arena");
        recintoEjemplo.setCapacidadRecinto(15000);
        recintoEjemplo.setTelefonoRecinto("+56222223333");
        recintoEjemplo.setCorreoRecinto("contacto@movistararena.cl");
    }

//-----------------------------------------------------------------------------------------------------------------------------
//LISTAR EVENTOS

    @Test
    void listarEventos_exitoso() {
        //Arrange
        when(eventoRepository.findAll()).thenReturn(List.of(eventoEjemplo));

        //Act
        List<Evento> resultado = eventoService.listarEventos();

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("EVT-2026-001", resultado.get(0).getCodigoEvento());
    }

    @Test
    void listarEventos_vacio() {
        //Arrange
        when(eventoRepository.findAll()).thenReturn(List.of());

        //Act
        List<Evento> resultado = eventoService.listarEventos();

        //Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

//-----------------------------------------------------------------------------------------------------------------------------
//BUSCAR EVENTOS

    @Test
    void buscarEventoPorId_encontrado() {
        //Arrange
        when(eventoRepository.findById(1)).thenReturn(Optional.of(eventoEjemplo));

        //Act
        Evento resultado = eventoService.buscarEventoPorId(1);

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdEvento());
    }

    @Test
    void buscarEventoPorId_noEncontrado_lanzaException() {
        //Arrange
        when(eventoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.buscarEventoPorId(99);
        });

        assertEquals("Evento con id: 99 no encontrado", exception.getMessage());
    }


//-----------------------------------------------------------------------------------------------------------------------------
//LISTAR EVENTOS POR ESTADO   

    @Test
    void listarEventosPorEstado_exitoso() {
        //Arrange
        when(eventoRepository.findByEstadoEvento("PENDIENTE")).thenReturn(List.of(eventoEjemplo));

        //Act
        List<Evento> resultado = eventoService.listarEventosPorEstado("PENDIENTE");

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstadoEvento());
    }

    @Test
    void listarEventosPorEstado_vacio() {
        //Arrange
        when(eventoRepository.findByEstadoEvento("PENDIENTE")).thenReturn(List.of());

        //Act
        List<Evento> resultado = eventoService.listarEventosPorEstado("PENDIENTE");

        //Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    void listarEventosPorProductora_exitoso() {
        //Arrange
        when(eventoRepository.findByIdProd(10)).thenReturn(List.of(eventoEjemplo));

        //Act
        List<Evento> resultado = eventoService.listarEventosPorProductora(10);

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10, resultado.get(0).getIdProd());
    }

    @Test
    void listarEventosPorProductora_vacio() {
        //Arrange
        when(eventoRepository.findByIdProd(10)).thenReturn(List.of());

        //Act
        List<Evento> resultado = eventoService.listarEventosPorProductora(10);

        //Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }
//-----------------------------------------------------------------------------------------------------------------------------
//CREAR EVENTOS
    @Test
    void crearEvento_exitoso() {
        //Arrange
        when(eventoRepository.findByCodigoEvento("EVT-2026-001")).thenReturn(Optional.empty());
        when(eventoRepository.save(any(Evento.class))).thenReturn(eventoEjemplo);

        //Act
        Evento resultado = eventoService.crearEvento(eventoEjemplo);

        //Assert
        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstadoEvento());
    }

    @Test
    void crearEvento_codigoDuplicado_lanzaException() {
        //Arrange
        when(eventoRepository.findByCodigoEvento("EVT-2026-001")).thenReturn(Optional.of(eventoEjemplo));

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.crearEvento(eventoEjemplo);
        });

        assertEquals("Ya existe un evento con código: EVT-2026-001", exception.getMessage());
    }
    
//-----------------------------------------------------------------------------------------------------------------------------
//APROBAR EVENTO POR SU ID

    @Test
    void aprobarEvento_exitoso() {
        //Arrange
        eventoEjemplo.setEstadoEvento("PENDIENTE");
        when(eventoRepository.findById(1)).thenReturn(Optional.of(eventoEjemplo));
        when(eventoRepository.save(any(Evento.class))).thenReturn(eventoEjemplo);

        //Act
        Evento resultado = eventoService.aprobarEvento(1, 5);

        //Assert
        assertNotNull(resultado);
        assertEquals("APROBADO", resultado.getEstadoEvento());
        assertEquals(5, resultado.getIdAdministrador());
    }

    @Test
    void aprobarEvento_noPendiente_lanzaException() {
        //Arrange
        eventoEjemplo.setEstadoEvento("APROBADO");
        when(eventoRepository.findById(1)).thenReturn(Optional.of(eventoEjemplo));

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.aprobarEvento(1, 5);
        });

        assertEquals("Solo se pueden aprobar eventos en estado PENDIENTE", exception.getMessage());
    }

    @Test
    void aprobarEvento_noEncontrado_lanzaException() {
        //Arrange
        when(eventoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.aprobarEvento(99, 5);
        });

        assertEquals("Evento con id: 99 no encontrado", exception.getMessage());
    }

//-----------------------------------------------------------------------------------------------------------------------------
//RECHAZAR EVENTO
    @Test
    void rechazarEvento_exitoso() {
        //Arrange
        eventoEjemplo.setEstadoEvento("PENDIENTE");
        when(eventoRepository.findById(1)).thenReturn(Optional.of(eventoEjemplo));
        when(eventoRepository.save(any(Evento.class))).thenReturn(eventoEjemplo);

        //Act
        Evento resultado = eventoService.rechazarEvento(1, 5);

        //Assert
        assertNotNull(resultado);
        assertEquals("RECHAZADO", resultado.getEstadoEvento());
        assertEquals(5, resultado.getIdAdministrador());
    }

    @Test
    void rechazarEvento_noPendiente_lanzaException() {
        //Arrange
        eventoEjemplo.setEstadoEvento("RECHAZADO");
        when(eventoRepository.findById(1)).thenReturn(Optional.of(eventoEjemplo));

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.rechazarEvento(1, 5);
        });

        // Nota: El servicio actual cuenta con un error de tipografía en el mensaje ("aprobar" en vez de "rechazar") en su código de producción. Se respeta la lógica de negocio provista.
        assertEquals("Solo se pueden aprobar eventos en estado PENDIENTE", exception.getMessage());
    }

    @Test
    void rechazarEvento_noEncontrado_lanzaException() {
        //Arrange
        when(eventoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.rechazarEvento(99, 5);
        });

        assertEquals("Evento con id: 99 no encontrado", exception.getMessage());
    }

//-----------------------------------------------------------------------------------------------------------------------------
//ELIMINAR EVENTO

    @Test
    void eliminarEvento_exitoso() {
        //Arrange
        when(eventoRepository.findById(1)).thenReturn(Optional.of(eventoEjemplo));

        //Act
        eventoService.eliminarEvento(1);

        //Assert
        verify(eventoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarEvento_noEncontrado_lanzaException() {
        //Arrange
        when(eventoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.eliminarEvento(99);
        });

        assertEquals("Evento con id: 99 no encontrado", exception.getMessage());
    }

//-----------------------------------------------------------------------------------------------------------------------------
//LISTAR TIPOS DE EVENTO

    @Test
    void listarTiposEvento_exitoso() {
        //Arrange
        when(tipoEventoRepository.findAll()).thenReturn(List.of(tipoEventoEjemplo));

        //Act
        List<TipoEvento> resultado = eventoService.listarTiposEvento();

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Concierto", resultado.get(0).getDescripcion());
    }

    @Test
    void listarTiposEvento_vacio() {
        //Arrange
        when(tipoEventoRepository.findAll()).thenReturn(List.of());

        //Act
        List<TipoEvento> resultado = eventoService.listarTiposEvento();

        //Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }


//BUSCAR TIPO DE EVENTO POR SU ID

    @Test
    void buscarTipoEventoPorId_encontrado() {
        //Arrange
        when(tipoEventoRepository.findById(1)).thenReturn(Optional.of(tipoEventoEjemplo));

        //Act
        TipoEvento resultado = eventoService.buscarTipoEventoPorId(1);

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdTipoEvento());
    }

    @Test
    void buscarTipoEventoPorId_noEncontrado_lanzaException() {
        //Arrange
        when(tipoEventoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.buscarTipoEventoPorId(99);
        });

        assertEquals("Tipo de Evento con id: 99 no encontrado o no existe", exception.getMessage());
    }

//-----------------------------------------------------------------------------------------------------------------------------
//GUARDAR TIPO DE EVENTO

    @Test
    void guardarTipoEvento_exitoso() {
        //Arrange
        when(tipoEventoRepository.findByDescripcion("Concierto")).thenReturn(Optional.empty());
        when(tipoEventoRepository.save(any(TipoEvento.class))).thenReturn(tipoEventoEjemplo);

        //Act
        TipoEvento resultado = eventoService.guardarTipoEvento(tipoEventoEjemplo);

        //Assert
        assertNotNull(resultado);
        assertEquals("Concierto", resultado.getDescripcion()); // O getDescripcion() según tus getters
    }

    @Test
    void guardarTipoEvento_descripcionDuplicada_lanzaException() {
        //Arrange
        when(tipoEventoRepository.findByDescripcion("Concierto")).thenReturn(Optional.of(tipoEventoEjemplo));

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.guardarTipoEvento(tipoEventoEjemplo);
        });

        assertEquals("Ya existe el tipo de evento: Concierto", exception.getMessage());
    }


//-----------------------------------------------------------------------------------------------------------------------------
//GUARDAR TIPO DE EVENTO

    @Test
    void actualizarTipoEvento_exitoso() {
        //Arrange
        TipoEvento modificado = new TipoEvento();
        modificado.setDescripcion("Festival");

        when(tipoEventoRepository.findById(1)).thenReturn(Optional.of(tipoEventoEjemplo));
        when(tipoEventoRepository.save(any(TipoEvento.class))).thenReturn(tipoEventoEjemplo);

        //Act
        TipoEvento resultado = eventoService.actualizarTipoEvento(1, modificado);

        //Assert
        assertNotNull(resultado);
        assertEquals("Festival", resultado.getDescripcion());
    }

    @Test
    void actualizarTipoEvento_noEncontrado_lanzaException() {
        //Arrange
        TipoEvento modificado = new TipoEvento();
        when(tipoEventoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.actualizarTipoEvento(99, modificado);
        });

        assertEquals("Tipo de Evento con id: 99No encontrado", exception.getMessage());
    }

//-----------------------------------------------------------------------------------------------------------------------------
//ELIMINAR TIPO DE EVENTO

    @Test
    void eliminarTipoEvento_exitoso() {
        //Arrange
        when(tipoEventoRepository.findById(1)).thenReturn(Optional.of(tipoEventoEjemplo));

        //Act
        eventoService.eliminarTipoEvento(1);

        //Assert
        verify(tipoEventoRepository, times(1)).deleteById(1);
    }
   
//-----------------------------------------------------------------------------------------------------------------------------
//LISTAR RECINTOS

    @Test
    void listarRecintos_exitoso() {
        //Arrange
        when(recintoRepository.findAll()).thenReturn(List.of(recintoEjemplo));

        //Act
        List<Recinto> resultado = eventoService.listarRecintos();

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void listarRecintos_vacio() {
        //Arrange
        when(recintoRepository.findAll()).thenReturn(List.of());

        //Act
        List<Recinto> resultado = eventoService.listarRecintos();

        //Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

//-----------------------------------------------------------------------------------------------------------------------------
//BUSCAR RECINTOS

    @Test
    void buscarRecintoPorId_encontrado() {
        //Arrange
        when(recintoRepository.findById(1)).thenReturn(Optional.of(recintoEjemplo));

        //Act
        Recinto resultado = eventoService.buscarRecintoPorId(1);

        //Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdRecinto());
    }

    @Test
    void buscarRecintoPorId_noEncontrado_lanzaException() {
        //Arrange
        when(recintoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.buscarRecintoPorId(99);
        });

        assertEquals("Recinto con id: 99 no encontrado", exception.getMessage());
    }


    @Test
    void buscarRecintoPorNombre_encontrado() {
        //Arrange
        when(recintoRepository.findByNombreRecinto("Movistar Arena")).thenReturn(Optional.of(recintoEjemplo));

        //Act
        Recinto resultado = eventoService.buscarRecintoPorNombre("Movistar Arena");

        //Assert
        assertNotNull(resultado);
        assertEquals("Movistar Arena", resultado.getNombreRecinto());
    }

    @Test
    void buscarRecintoPorNombre_noEncontrado_lanzaException() {
        //Arrange
        when(recintoRepository.findByNombreRecinto("Estadio No Existe")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.buscarRecintoPorNombre("Estadio No Existe");
        });

        assertEquals("Recinto con nombre: Estadio No Existe no encontrado", exception.getMessage());
    }


//-----------------------------------------------------------------------------------------------------------------------------
//GUARDAR RECINTO

    @Test
    void guardarRecinto_exitoso() {
        //Arrange
        when(recintoRepository.findByRutRecinto("77666555-4")).thenReturn(Optional.empty());
        when(recintoRepository.findByNombreRecinto("Movistar Arena")).thenReturn(Optional.empty());
        when(recintoRepository.save(any(Recinto.class))).thenReturn(recintoEjemplo);

        //Act
        Recinto resultado = eventoService.guardarRecinto(recintoEjemplo);

        //Assert
        assertNotNull(resultado);
        assertEquals("Movistar Arena", resultado.getNombreRecinto());
    }

    @Test
    void guardarRecinto_rutDuplicado_lanzaException() {
        //Arrange
        when(recintoRepository.findByRutRecinto("77666555-4")).thenReturn(Optional.of(recintoEjemplo));

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.guardarRecinto(recintoEjemplo);
        });

        assertEquals("Ya existe un recinto con rut: 77666555-4", exception.getMessage());
    }

//-----------------------------------------------------------------------------------------------------------------------------
//ACTUALIZAR RECINTO

    @Test
    void actualizarRecinto_exitoso() {
        //Arrange
        Recinto editado = new Recinto();
        editado.setNombreRecinto("Movistar Arena Modificado");
        editado.setCapacidadRecinto(16000);
        editado.setTelefonoRecinto("+56222224444");
        editado.setCorreoRecinto("nuevo@movistar.cl");

        when(recintoRepository.findById(1)).thenReturn(Optional.of(recintoEjemplo));
        when(recintoRepository.save(any(Recinto.class))).thenReturn(recintoEjemplo);

        //Act
        Recinto resultado = eventoService.actualizarRecinto(1, editado);

        //Assert
        assertNotNull(resultado);
        assertEquals("Movistar Arena Modificado", resultado.getNombreRecinto());
        assertEquals(16000, resultado.getCapacidadRecinto());
    }

    @Test
    void actualizarRecinto_noEncontrado_lanzaException() {
        //Arrange
        Recinto editado = new Recinto();
        when(recintoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.actualizarRecinto(99, editado);
        });

        assertEquals("Recinto con id: 99 no encontrado", exception.getMessage());
    }

//-----------------------------------------------------------------------------------------------------------------------------
//ELIMINAR RECINTO

    @Test
    void eliminarRecinto_exitoso() {
        //Arrange
        when(recintoRepository.findById(1)).thenReturn(Optional.of(recintoEjemplo));

        //Act
        eventoService.eliminarRecinto(1);

        //Assert
        verify(recintoRepository, times(1)).deleteById(1);
    }


    @Test
    void eliminarRecinto_noEncontrado_lanzaException() {
        //Arrange
        when(recintoRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            eventoService.eliminarRecinto(99);
        });

        assertEquals("Recinto con id: 99 no encontrado", exception.getMessage());
    }
   
}
