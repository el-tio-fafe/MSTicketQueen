package cl.duoc.msAsiento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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


    @BeforeEach
    void setUp(){

        asientoEjemplo = new Asiento();
        asientoEjemplo.setIdAsiento(1);
        asientoEjemplo.setNumeroAsiento("A1");
        asientoEjemplo.setEstadoAsiento("DISPONIBLE");


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


    



}
