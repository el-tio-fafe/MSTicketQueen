package cl.duoc.msGestionArtistica.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msGestionArtistica.model.Artista;
import cl.duoc.msGestionArtistica.repository.ArtistaRepository;

@ExtendWith(MockitoExtension.class)
public class ArtistaServiceTest {

    @Mock //crea un objeto simulado de ArtistaRepository para ser utilizado en las pruebas
    private ArtistaRepository artistaRepository;

    @InjectMocks //crea una instancia de ArtistaService e inyecta el objeto simulado de ArtistaRepository en ella
    private ArtistaService artistaService;

    private Artista artistaEjemplo;

    @BeforeEach
    void setUp() {
        artistaEjemplo = new Artista();
        artistaEjemplo.setIdArt(1);
        artistaEjemplo.setRutArt("12345678-9");
        artistaEjemplo.setNombreArt("Los Bunkers");
        artistaEjemplo.setCorreoArt("losbunkers@gmail.com");
        artistaEjemplo.setTelefonoArt("+56912345678");
    }

    @Test
    public void getArtistaById_encontrado() {
        //Arrange(preparar el escenario de prueba, como configurar los objetos simulados y definir los datos de entrada)
        Optional<Artista> artistaOptional = Optional.of(artistaEjemplo);
        when(artistaRepository.findById(1)).thenReturn(artistaOptional);

        //Act(ejecutar el método que se va a probar, en este caso, el método getArtistaById del servicio ArtistaService)

        Artista resultado = artistaService.getArtistaById(1);

        //Assert(analizar los resultados obtenidos y compararlos con los resultados esperados para determinar si la prueba fue exitosa o no)

        assertEquals(1, resultado.getIdArt());
        assertEquals("12345678-9", resultado.getRutArt());
        assertEquals("Los Bunkers", resultado.getNombreArt());
        assertEquals("losbunkers@gmail.com", resultado.getCorreoArt());
        assertEquals("+56912345678", resultado.getTelefonoArt());

    }

    @Test
    public void getArtistaById_noEncontrado() {
        //Arrange
        Optional<Artista> artistaVacio = Optional.empty();
        when(artistaRepository.findById(99)).thenReturn(artistaVacio);

        //Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> artistaService.getArtistaById(99));
    
        assertEquals("Artista con id: 99 no encontrado.", exception.getMessage());
    
    }

    @Test
    public void saveArtista_exito() {
        //Arrange
        when(artistaRepository.save(artistaEjemplo)).thenReturn(artistaEjemplo);

        //Act
        Artista resultado = artistaService.saveArtista(artistaEjemplo);

        //Assert
        assertEquals(1, resultado.getIdArt());
        assertEquals("12345678-9", resultado.getRutArt());
        assertEquals("Los Bunkers", resultado.getNombreArt());
        assertEquals("losbunkers@gmail.com", resultado.getCorreoArt());
        assertEquals("+56912345678", resultado.getTelefonoArt());

    }

    @Test
    void eliminarArtista(){
        //Arrange
        when(artistaRepository.existsById(1)).thenReturn(true);

        //Act + Assert
        assertDoesNotThrow( () -> artistaService.deleteArtista(1));
        
        verify(artistaRepository, times(1)).deleteById(1);
        
    }

    @Test
    void eliminarArtista_no_encontrado(){
        //Arrange
        when(artistaRepository.existsById(99)).thenReturn(false);

        //Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> artistaService.deleteArtista(99));
    
        assertEquals("Artista con id: 99 no encontrado. No se puede eliminar.", exception.getMessage());
    }




} 