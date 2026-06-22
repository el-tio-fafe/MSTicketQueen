package cl.duoc.msLogin.service;

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

import cl.duoc.msLogin.model.TipoUsuario;
import cl.duoc.msLogin.model.Usuario;
import cl.duoc.msLogin.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    private Usuario ejemplo;
    private TipoUsuario tipoUsuario;

    @BeforeEach
    void setUp() {
        tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(1);
        tipoUsuario.setNombreTipoUsuario("Comprador");

        ejemplo = new Usuario();
        ejemplo.setId(1);
        ejemplo.setNombreUsuario("Juan Silva");
        ejemplo.setCorreo("juan@gmail.com");
        ejemplo.setPassword("123456");
        ejemplo.setTipoUsuario(tipoUsuario);
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarUsuarios() {
        //Arrange
        when(repository.findAll()).thenReturn(List.of(ejemplo));

        //Act
        List<Usuario> resultado = service.listarUsuarios();

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(ejemplo, resultado.get(0));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarUsuarioPorId_encontrado() {
        //Arrange
        when(repository.findById(1)).thenReturn(Optional.of(ejemplo));

        //Act
        Usuario resultado = service.buscarUsuarioPorId(1);

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void buscarUsuarioPorId_no_encontrado() {
        //Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarUsuarioPorId(99));
        assertEquals("Usuario con id: 99 no encontrado.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarUsuarioPorCorreo_encontrado() {
        //Arrange
        when(repository.findByCorreo("juan@gmail.com")).thenReturn(Optional.of(ejemplo));

        //Act
        Usuario resultado = service.buscarUsuarioPorCorreo("juan@gmail.com");

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void buscarUsuarioPorCorreo_no_encontrado() {
        //Arrange
        when(repository.findByCorreo("noexiste@gmail.com")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.buscarUsuarioPorCorreo("noexiste@gmail.com"));
        assertEquals("Usuario con email: noexiste@gmail.com no encontrado.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void crearUsuario() {
        //Arrange
        when(repository.save(ejemplo)).thenReturn(ejemplo);

        //Act
        Usuario resultado = service.crearUsuario(ejemplo);

        //Assert
        assertEquals(ejemplo, resultado);
        verify(repository, times(1)).save(ejemplo);
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void actualizarUsuario_existente() {
        //Arrange
        Usuario existente = new Usuario();
        existente.setId(1);
        existente.setNombreUsuario("Nombre Viejo");
        existente.setCorreo("viejo@gmail.com");
        existente.setPassword("oldpass");

        when(repository.findById(1)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        //Act
        Usuario resultado = service.actualizarUsuario(1, ejemplo);

        //Assert
        assertEquals(ejemplo.getNombreUsuario(), resultado.getNombreUsuario());
        assertEquals(ejemplo.getCorreo(), resultado.getCorreo());
        assertEquals(ejemplo.getPassword(), resultado.getPassword());
        assertEquals(ejemplo.getTipoUsuario(), resultado.getTipoUsuario());
        verify(repository, times(1)).save(existente);
    }

    @Test
    void actualizarUsuario_no_encontrado() {
        //Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.actualizarUsuario(99, ejemplo));
        assertEquals("Usuario con id: 99 no encontrado.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void eliminarUsuario_existente() {
        //Arrange
        when(repository.findById(1)).thenReturn(Optional.of(ejemplo));

        //Act
        service.eliminarUsuario(1);

        //Assert
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void eliminarUsuario_no_encontrado() {
        //Arrange
        when(repository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.eliminarUsuario(99));
        assertEquals("Usuario con id: 99 no encontrado.", ex.getMessage());
        verify(repository, never()).deleteById(99);
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void verificarCredenciales_validas() {
        //Arrange
        when(repository.findByCorreo("juan@gmail.com")).thenReturn(Optional.of(ejemplo));

        //Act
        boolean resultado = service.verificarCredenciales("juan@gmail.com", "123456");

        //Assert
        assertTrue(resultado);
    }

    @Test
    void verificarCredenciales_password_incorrecta() {
        //Arrange
        when(repository.findByCorreo("juan@gmail.com")).thenReturn(Optional.of(ejemplo));

        //Act
        boolean resultado = service.verificarCredenciales("juan@gmail.com", "incorrecta");

        //Assert
        assertFalse(resultado);
    }

    @Test
    void verificarCredenciales_correo_no_existe() {
        //Arrange
        when(repository.findByCorreo("noexiste@gmail.com")).thenReturn(Optional.empty());

        //Act
        boolean resultado = service.verificarCredenciales("noexiste@gmail.com", "123456");

        //Assert
        assertFalse(resultado);
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}