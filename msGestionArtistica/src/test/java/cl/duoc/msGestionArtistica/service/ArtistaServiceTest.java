package cl.duoc.msGestionArtistica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import cl.duoc.msGestionArtistica.model.Artista;
import cl.duoc.msGestionArtistica.repository.ArtistaRepository;

@ExtendWith(MockitoExtension.class)
public class ArtistaServiceTest {

    @Mock
    private ArtistaRepository repository;

    @InjectMocks
    private ArtistaService service;

    private Artista ejemplo;

    @BeforeEach
    void setUp() {
        ejemplo = new Artista();
        ejemplo.setIdArt(1);
        ejemplo.setRutArt("12345678-9");
        ejemplo.setNombreArt("Los Bunkers");
        ejemplo.setCorreoArt("losbunkers@gmail.com");
        ejemplo.setTelefonoArt("+56912345678");
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getAllArtistas() {
        //Arrange
        when(repository.findAll()).thenReturn(List.of(ejemplo));

        //Act
        List<Artista> resultado = service.getAllArtistas();

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(ejemplo, resultado.get(0));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getArtistaById_encontrado() {
        //Arrange
        when(repository.findById(1)).thenReturn(Optional.of(ejemplo));

        //Act
        Artista resultado = service.getArtistaById(1);

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void getArtistaById_no_encontrado() {
        //Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getArtistaById(99));
        assertEquals("Artista con id: 99 no encontrado.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getArtistaByRut_encontrado() {
        //Arrange
        when(repository.findByRutArt("12345678-9")).thenReturn(Optional.of(ejemplo));

        //Act
        Artista resultado = service.getArtistaByRut("12345678-9");

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void getArtistaByRut_no_encontrado() {
        //Arrange
        when(repository.findByRutArt("98765432-1")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getArtistaByRut("98765432-1"));
        assertEquals("Artista con rut: 98765432-1 no encontrado.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getArtistaByCorreo_encontrado() {
        //Arrange
        when(repository.findByCorreoArt("losbunkers@gmail.com")).thenReturn(Optional.of(ejemplo));

        //Act
        Artista resultado = service.getArtistaByCorreo("losbunkers@gmail.com");

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void getArtistaByCorreo_no_encontrado() {
        //Arrange
        when(repository.findByCorreoArt("noencontrado@gmail.com")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getArtistaByCorreo("noencontrado@gmail.com"));
        assertEquals("Artista con correo: noencontrado@gmail.com no encontrado.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void saveArtista_exitoso() {
        //Arrange
        when(repository.findByRutArt("12345678-9")).thenReturn(Optional.empty());
        when(repository.findByCorreoArt("losbunkers@gmail.com")).thenReturn(Optional.empty());
        when(repository.save(ejemplo)).thenReturn(ejemplo);

        //Act
        Artista resultado = service.saveArtista(ejemplo);

        //Assert
        assertEquals(ejemplo, resultado);
        verify(repository, times(1)).save(ejemplo);
    }

    @Test
    void saveArtista_con_mismo_rut() {
        //Arrange
        when(repository.findByRutArt("12345678-9")).thenReturn(Optional.of(ejemplo));

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.saveArtista(ejemplo));
        assertEquals("Ya existe un artista con el rut: 12345678-9", ex.getMessage());
        verify(repository, never()).save(ejemplo);
    }

    @Test
    void saveArtista_con_mismo_correo() {
        //Arrange
        when(repository.findByRutArt("12345678-9")).thenReturn(Optional.empty());
        when(repository.findByCorreoArt("losbunkers@gmail.com")).thenReturn(Optional.of(ejemplo));

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.saveArtista(ejemplo));
        assertEquals("Ya existe un artista con el correo: losbunkers@gmail.com", ex.getMessage());
        verify(repository, never()).save(ejemplo);
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void updateArtista_existente() {
        //Arrange
        Artista existente = new Artista();
        existente.setIdArt(1);
        existente.setRutArt("00000000-0");
        existente.setNombreArt("Nombre Viejo");
        existente.setCorreoArt("viejo@gmail.com");
        existente.setTelefonoArt("+56900000000");

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        //Act
        Artista resultado = service.updateArtista(1, ejemplo);

        //Assert
        assertEquals(ejemplo.getRutArt(), resultado.getRutArt());
        assertEquals(ejemplo.getNombreArt(), resultado.getNombreArt());
        assertEquals(ejemplo.getCorreoArt(), resultado.getCorreoArt());
        assertEquals(ejemplo.getTelefonoArt(), resultado.getTelefonoArt());
        verify(repository, times(1)).save(existente);
    }

    @Test
    void updateArtista_no_encontrado() {
        //Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.updateArtista(99, ejemplo));
        assertEquals("Artista con id: 99 no encontrado. No se puede actualizar.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void deleteArtista_existente() {
        //Arrange
        when(repository.existsById(1)).thenReturn(true);

        //Act
        service.deleteArtista(1);

        //Assert
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void deleteArtista_no_encontrado() {
        //Arrange
        when(repository.existsById(99)).thenReturn(false);

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.deleteArtista(99));
        assertEquals("Artista con id: 99 no encontrado. No se puede eliminar.", ex.getMessage());
        verify(repository, never()).deleteById(99);
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}