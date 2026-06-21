package cl.duoc.msGestionArtistica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import cl.duoc.msGestionArtistica.model.Artista;
import cl.duoc.msGestionArtistica.model.Manager;
import cl.duoc.msGestionArtistica.repository.ArtistaRepository;
import cl.duoc.msGestionArtistica.repository.ManagerRepository;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private ArtistaRepository artistaRepository;

    @InjectMocks
    private ManagerService service;

    private Manager ejemplo;
    private Artista artistaEjemplo;

    @BeforeEach
    void setUp() {
        ejemplo = new Manager();
        ejemplo.setIdMngr(1);
        ejemplo.setRutMngr("11111111-1");
        ejemplo.setNombreMngr("María");
        ejemplo.setApPaternoMngr("González");
        ejemplo.setApMaternoMngr("Pérez");
        ejemplo.setCorreoMngr("maria.gonzalez@gmail.com");
        ejemplo.setTelefonoMngr("+56987654321");

        artistaEjemplo = new Artista();
        artistaEjemplo.setIdArt(1);
        artistaEjemplo.setRutArt("12345678-9");
        artistaEjemplo.setNombreArt("Los Bunkers");
        artistaEjemplo.setCorreoArt("losbunkers@gmail.com");
        artistaEjemplo.setTelefonoArt("+56912345678");
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getAllManagers() {
        //Arrange
        when(managerRepository.findAll()).thenReturn(List.of(ejemplo));

        //Act
        List<Manager> resultado = service.getAllManagers();

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(ejemplo, resultado.get(0));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getManagerById_encontrado() {
        //Arrange
        when(managerRepository.findById(1)).thenReturn(Optional.of(ejemplo));

        //Act
        Manager resultado = service.getManagerById(1);

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void getManagerById_no_encontrado() {
        //Arrange
        when(managerRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getManagerById(99));
        assertEquals("Manager con id: 99 no encontrado.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getManagerByRut_encontrado() {
        //Arrange
        when(managerRepository.findByRutMngr("11111111-1")).thenReturn(Optional.of(ejemplo));

        //Act
        Manager resultado = service.getManagerByRut("11111111-1");

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void getManagerByRut_no_encontrado() {
        //Arrange
        when(managerRepository.findByRutMngr("99999999-9")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getManagerByRut("99999999-9"));
        assertEquals("Manager con rut: 99999999-9 no encontrado.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getManagerByCorreo_encontrado() {
        //Arrange
        when(managerRepository.findByCorreoMngr("maria.gonzalez@gmail.com")).thenReturn(Optional.of(ejemplo));

        //Act
        Manager resultado = service.getManagerByCorreo("maria.gonzalez@gmail.com");

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void getManagerByCorreo_no_encontrado() {
        //Arrange
        when(managerRepository.findByCorreoMngr("noencontrado@gmail.com")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getManagerByCorreo("noencontrado@gmail.com"));
        assertEquals("Manager con correo: noencontrado@gmail.com no encontrado.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void saveManager_exitoso() {
        //Arrange
        when(managerRepository.findByRutMngr("11111111-1")).thenReturn(Optional.empty());
        when(managerRepository.findByCorreoMngr("maria.gonzalez@gmail.com")).thenReturn(Optional.empty());
        when(managerRepository.save(ejemplo)).thenReturn(ejemplo);

        //Act
        Manager resultado = service.saveManager(ejemplo);

        //Assert
        assertEquals(ejemplo, resultado);
        verify(managerRepository, times(1)).save(ejemplo);
    }

    @Test
    void saveManager_con_mismo_rut() {
        //Arrange
        when(managerRepository.findByRutMngr("11111111-1")).thenReturn(Optional.of(ejemplo));

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.saveManager(ejemplo));
        assertEquals("Ya existe un manager con el rut: 11111111-1", ex.getMessage());
        verify(managerRepository, never()).save(ejemplo);
    }

    @Test
    void saveManager_con_mismo_correo() {
        //Arrange
        when(managerRepository.findByRutMngr("11111111-1")).thenReturn(Optional.empty());
        when(managerRepository.findByCorreoMngr("maria.gonzalez@gmail.com")).thenReturn(Optional.of(ejemplo));

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.saveManager(ejemplo));
        assertEquals("Ya existe un manager con el correo: maria.gonzalez@gmail.com", ex.getMessage());
        verify(managerRepository, never()).save(ejemplo);
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void updateManager_existente() {
        //Arrange
        Manager existente = new Manager();
        existente.setIdMngr(1);
        existente.setRutMngr("00000000-0");
        existente.setNombreMngr("Nombre Viejo");
        existente.setApPaternoMngr("Apellido Viejo");
        existente.setApMaternoMngr("Apellido Viejo");
        existente.setCorreoMngr("viejo@gmail.com");
        existente.setTelefonoMngr("+56900000000");

        when(managerRepository.findById(1)).thenReturn(Optional.of(existente));
        when(managerRepository.save(existente)).thenReturn(existente);

        //Act
        Manager resultado = service.updateManager(1, ejemplo);

        //Assert
        assertEquals(ejemplo.getRutMngr(), resultado.getRutMngr());
        assertEquals(ejemplo.getNombreMngr(), resultado.getNombreMngr());
        assertEquals(ejemplo.getApPaternoMngr(), resultado.getApPaternoMngr());
        assertEquals(ejemplo.getApMaternoMngr(), resultado.getApMaternoMngr());
        assertEquals(ejemplo.getCorreoMngr(), resultado.getCorreoMngr());
        assertEquals(ejemplo.getTelefonoMngr(), resultado.getTelefonoMngr());
        verify(managerRepository, times(1)).save(existente);
    }

    @Test
    void updateManager_no_encontrado() {
        //Arrange
        when(managerRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.updateManager(99, ejemplo));
        assertEquals("Manager con id: 99 no encontrado. No se puede actualizar.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void deleteManager_existente() {
        //Arrange
        when(managerRepository.existsById(1)).thenReturn(true);

        //Act
        service.deleteManager(1);

        //Assert
        verify(managerRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteManager_no_encontrado() {
        //Arrange
        when(managerRepository.existsById(99)).thenReturn(false);

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.deleteManager(99));
        assertEquals("Manager con id: 99 no encontrado. No se puede eliminar.", ex.getMessage());
        verify(managerRepository, never()).deleteById(99);
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void asignarArtista_exitoso() {
        //Arrange
        when(managerRepository.findById(1)).thenReturn(Optional.of(ejemplo));
        when(artistaRepository.findById(1)).thenReturn(Optional.of(artistaEjemplo));
        when(managerRepository.save(ejemplo)).thenReturn(ejemplo);

        //Act
        Manager resultado = service.asignarArtista(1, 1);

        //Assert
        assertTrue(resultado.getArtistas().contains(artistaEjemplo));
        verify(managerRepository, times(1)).save(ejemplo);
    }

    @Test
    void asignarArtista_manager_no_encontrado() {
        //Arrange
        when(managerRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.asignarArtista(99, 1));
        assertEquals("Manager con id: 99 no encontrado. No se puede asignar artista.", ex.getMessage());
    }

    @Test
    void asignarArtista_artista_no_encontrado() {
        //Arrange
        when(managerRepository.findById(1)).thenReturn(Optional.of(ejemplo));
        when(artistaRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.asignarArtista(1, 99));
        assertEquals("Artista con id: 99 no encontrado. No se puede asignar al manager.", ex.getMessage());
    }

    @Test
    void asignarArtista_ya_asignado() {
        //Arrange
        ejemplo.getArtistas().add(artistaEjemplo);
        when(managerRepository.findById(1)).thenReturn(Optional.of(ejemplo));
        when(artistaRepository.findById(1)).thenReturn(Optional.of(artistaEjemplo));

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.asignarArtista(1, 1));
        assertEquals("El artista con id: 1 ya está asignado al manager con id: 1.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}