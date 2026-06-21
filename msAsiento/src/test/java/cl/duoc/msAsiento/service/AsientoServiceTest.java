package cl.duoc.msAsiento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import cl.duoc.msAsiento.repository.AsientoRepository;

@ExtendWith(MockitoExtension.class)
public class AsientoServiceTest {

    @Mock
    private AsientoRepository asientoRepository;

    @InjectMocks
    private AsientoService asientoService;


    private Asiento asientoEjemplo;
    private Asiento asientoModificadoEjemplo;


    @BeforeEach
    void setUp(){

        asientoEjemplo = new Asiento();
        asientoEjemplo.setIdAsiento(1);
        asientoEjemplo.setNumeroAsiento("A1");
        asientoEjemplo.setEstadoAsiento("DISPONIBLE");


        asientoModificadoEjemplo = new Asiento();
        asientoModificadoEjemplo.setIdAsiento(1);
        asientoModificadoEjemplo.setNumeroAsiento("a1"); 
        asientoModificadoEjemplo.setEstadoAsiento("ocupado");


    }


//****************************************************************************************************** */
//LISTAR ASIENTOS

    @Test
    void listarAsientos_retornaListaAsientos() {
        when(asientoRepository.findAll()).thenReturn(List.of(asientoEjemplo));

        List<Asiento> resultado = asientoService.listarAsientos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(asientoRepository, times(1)).findAll();
    }

    @Test
    void listarAsientos_RetornaListaVacia() {
        when(asientoRepository.findAll()).thenReturn(new ArrayList<>());

        List<Asiento> resultado = asientoService.listarAsientos();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(asientoRepository, times(1)).findAll();
    }
    

//LISTAR POR ESTADO

    @Test
    void listarPorEstado_retornaListaAsientos() {
        when(asientoRepository.findByEstadoAsiento("DISPONIBLE")).thenReturn(List.of(asientoEjemplo));

        List<Asiento> resultado = asientoService.listarPorEstado("DISPONIBLE");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(asientoRepository, times(1)).findByEstadoAsiento("DISPONIBLE");
    }

    @Test
    void listarPorEstado_RetornaListaVacia() {
        when(asientoRepository.findByEstadoAsiento("OCUPADO")).thenReturn(new ArrayList<>());

        List<Asiento> resultado = asientoService.listarPorEstado("OCUPADO");

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(asientoRepository, times(1)).findByEstadoAsiento("OCUPADO");
    }


//BUSCAR POR ID

    @Test
    void buscarPorId_encontrado() {
        when(asientoRepository.findByIdAsiento(1)).thenReturn(Optional.of(asientoEjemplo));

        Asiento resultado = asientoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdAsiento());
    }

    @Test
    void buscarPorId_NoEncontrado() {
        Integer idInexistente = 99;
        when(asientoRepository.findByIdAsiento(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            asientoService.buscarPorId(idInexistente);
        });

        assertEquals("Asiento id: " + idInexistente + " no encontrado", exception.getMessage());
        verify(asientoRepository, times(1)).findByIdAsiento(idInexistente);
    }


//BUSCAR POR NUM ASIENTO

    @Test
    void buscarPorNumAsiento_encontrado() {
        when(asientoRepository.findByNumeroAsiento("A1")).thenReturn(Optional.of(asientoEjemplo));

        Asiento resultado = asientoService.buscarPorNumAsiento("A1");

        assertNotNull(resultado);
        assertEquals("A1", resultado.getNumeroAsiento());
    }

    @Test
    void buscarPorNumAsiento_NoEncontrado() {
        //String numInexistente = "B2";
        when(asientoRepository.findByNumeroAsiento("ZX9999")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            asientoService.buscarPorNumAsiento("ZX9999");
        });

        assertEquals("Asiento numero: ZX9999 no encontrado", exception.getMessage());
        verify(asientoRepository, times(1)).findByNumeroAsiento("ZX9999");
    }


//GUARDAR

    @Test
    void guardar_exitoso() {
        when(asientoRepository.findByNumeroAsiento("A1")).thenReturn(Optional.empty());
        when(asientoRepository.save(any(Asiento.class))).thenReturn(asientoEjemplo);

        Asiento resultado = asientoService.guardar(asientoModificadoEjemplo);

        assertNotNull(resultado);
        assertEquals("A1", asientoModificadoEjemplo.getNumeroAsiento());
        assertEquals("OCUPADO", asientoModificadoEjemplo.getEstadoAsiento());
        verify(asientoRepository, times(1)).save(asientoModificadoEjemplo);
    }

    @Test
    void guardar_NumeroAsientoDuplicado() {
        when(asientoRepository.findByNumeroAsiento("A1")).thenReturn(Optional.of(asientoEjemplo));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            asientoService.guardar(asientoModificadoEjemplo);
        });

        assertEquals("El número de asiento A1 ya existe", exception.getMessage());
        verify(asientoRepository, never()).save(any(Asiento.class));
    }


//ELIMINAR POR ID

    @Test
    void eliminarPorId_exitoso() {
        when(asientoRepository.existsById(1)).thenReturn(true);

        asientoService.eliminarPorId(1);

        verify(asientoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarPorId_NoExiste() {
        Integer idInexistente = 99;
        when(asientoRepository.existsById(idInexistente)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            asientoService.eliminarPorId(idInexistente);
        });

        assertEquals("Asiento id: " + idInexistente + " no existe", exception.getMessage());
        verify(asientoRepository, never()).deleteById(any(Integer.class));
    }

    //ELIMINAR POR NUM ASIENTO

    @Test
    void eliminarPorNumAsiento_exitoso() {
        when(asientoRepository.findByNumeroAsiento("A1")).thenReturn(Optional.of(asientoEjemplo));

        asientoService.eliminarPorNumAsiento("A1");

        verify(asientoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarPorNumAsiento_NoExiste() {
        String numInexistente = "B2";
        when(asientoRepository.findByNumeroAsiento(numInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            asientoService.eliminarPorNumAsiento(numInexistente);
        });

        assertEquals("Asiento numero: " + numInexistente + " no existe", exception.getMessage());
        verify(asientoRepository, never()).deleteById(any(Integer.class));
    }


//ACTUALIZAR

    @Test
    void actualizar_exitoso() {
        when(asientoRepository.findById(1)).thenReturn(Optional.of(asientoEjemplo));
        when(asientoRepository.save(any(Asiento.class))).thenReturn(asientoEjemplo);

        Asiento resultado = asientoService.actualizar(1, asientoModificadoEjemplo);

        assertNotNull(resultado);
        assertEquals("A1", asientoEjemplo.getNumeroAsiento());
        assertEquals("OCUPADO", asientoEjemplo.getEstadoAsiento());
        verify(asientoRepository, times(1)).save(asientoEjemplo);
    }

    @Test
    void actualizar_NoEncontrado() {
        //Integer idInexistente = 99;
        when(asientoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            asientoService.actualizar(99, asientoModificadoEjemplo);
        });

        assertEquals("Asiento id: 99 no encontrado", exception.getMessage());
        verify(asientoRepository, never()).save(any(Asiento.class));
    }








}







