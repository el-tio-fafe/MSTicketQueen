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

import cl.duoc.msGestionArtistica.model.Manager;
import cl.duoc.msGestionArtistica.model.Productoras;
import cl.duoc.msGestionArtistica.repository.ManagerRepository;
import cl.duoc.msGestionArtistica.repository.ProductorasRepository;

@ExtendWith(MockitoExtension.class)
public class ProductorasServiceTest {

    @Mock
    private ProductorasRepository productorasRepository;

    @Mock
    private ManagerRepository managerRepository;

    @InjectMocks
    private ProductorasService service;

    private Productoras ejemplo;
    private Manager managerEjemplo;

    @BeforeEach
    void setUp() {
        ejemplo = new Productoras();
        ejemplo.setIdProd(1);
        ejemplo.setRutProd("55555555-5");
        ejemplo.setNombreProd("Bizarro Producciones");
        ejemplo.setCorreoProd("bizarro@gmail.com");
        ejemplo.setTelefonoProd("+56955555555");

        managerEjemplo = new Manager();
        managerEjemplo.setIdMngr(1);
        managerEjemplo.setRutMngr("11111111-1");
        managerEjemplo.setNombreMngr("María");
        managerEjemplo.setApPaternoMngr("González");
        managerEjemplo.setApMaternoMngr("Pérez");
        managerEjemplo.setCorreoMngr("maria.gonzalez@gmail.com");
        managerEjemplo.setTelefonoMngr("+56987654321");
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getAllProductoras() {
        //Arrange
        when(productorasRepository.findAll()).thenReturn(List.of(ejemplo));

        //Act
        List<Productoras> resultado = service.getAllProductoras();

        //Assert
        assertEquals(1, resultado.size());
        assertEquals(ejemplo, resultado.get(0));
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getProductoraById_encontrada() {
        //Arrange
        when(productorasRepository.findById(1)).thenReturn(Optional.of(ejemplo));

        //Act
        Productoras resultado = service.getProductoraById(1);

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void getProductoraById_no_encontrada() {
        //Arrange
        when(productorasRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getProductoraById(99));
        assertEquals("Productora con id: 99 no encontrada.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getProductoraByNombre_encontrada() {
        //Arrange
        when(productorasRepository.findByNombreProd("Bizarro Producciones")).thenReturn(Optional.of(ejemplo));

        //Act
        Productoras resultado = service.getProductoraByNombre("Bizarro Producciones");

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void getProductoraByNombre_no_encontrada() {
        //Arrange
        when(productorasRepository.findByNombreProd("No Existe S.A.")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getProductoraByNombre("No Existe S.A."));
        assertEquals("Productora con nombre: No Existe S.A. no encontrada.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getProductoraByRut_encontrada() {
        //Arrange
        when(productorasRepository.findByRutProd("55555555-5")).thenReturn(Optional.of(ejemplo));

        //Act
        Productoras resultado = service.getProductoraByRut("55555555-5");

        //Assert
        assertEquals(ejemplo, resultado);
    }

    @Test
    void getProductoraByRut_no_encontrada() {
        //Arrange
        when(productorasRepository.findByRutProd("99999999-9")).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getProductoraByRut("99999999-9"));
        assertEquals("Productora con rut: 99999999-9 no encontrada.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void saveProductora_exitosa() {
        //Arrange
        when(productorasRepository.findByNombreProd("Bizarro Producciones")).thenReturn(Optional.empty());
        when(productorasRepository.findByRutProd("55555555-5")).thenReturn(Optional.empty());
        when(productorasRepository.save(ejemplo)).thenReturn(ejemplo);

        //Act
        Productoras resultado = service.saveProductora(ejemplo);

        //Assert
        assertEquals(ejemplo, resultado);
        verify(productorasRepository, times(1)).save(ejemplo);
    }

    @Test
    void saveProductora_con_mismo_nombre() {
        //Arrange
        when(productorasRepository.findByNombreProd("Bizarro Producciones")).thenReturn(Optional.of(ejemplo));

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.saveProductora(ejemplo));
        assertEquals("Ya existe una productora con el nombre: Bizarro Producciones", ex.getMessage());
        verify(productorasRepository, never()).save(ejemplo);
    }

    @Test
    void saveProductora_con_mismo_rut() {
        //Arrange
        when(productorasRepository.findByNombreProd("Bizarro Producciones")).thenReturn(Optional.empty());
        when(productorasRepository.findByRutProd("55555555-5")).thenReturn(Optional.of(ejemplo));

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.saveProductora(ejemplo));
        assertEquals("Ya existe una productora con el rut: 55555555-5", ex.getMessage());
        verify(productorasRepository, never()).save(ejemplo);
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void updateProductora_existente() {
        //Arrange
        Productoras existente = new Productoras();
        existente.setIdProd(1);
        existente.setRutProd("00000000-0");
        existente.setNombreProd("Nombre Viejo");
        existente.setCorreoProd("viejo@gmail.com");
        existente.setTelefonoProd("+56900000000");

        when(productorasRepository.findById(1)).thenReturn(Optional.of(existente));
        when(productorasRepository.save(existente)).thenReturn(existente);

        //Act
        Productoras resultado = service.updateProductora(1, ejemplo);

        //Assert
        assertEquals(ejemplo.getRutProd(), resultado.getRutProd());
        assertEquals(ejemplo.getNombreProd(), resultado.getNombreProd());
        assertEquals(ejemplo.getCorreoProd(), resultado.getCorreoProd());
        assertEquals(ejemplo.getTelefonoProd(), resultado.getTelefonoProd());
        verify(productorasRepository, times(1)).save(existente);
    }

    @Test
    void updateProductora_no_encontrada() {
        //Arrange
        when(productorasRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.updateProductora(99, ejemplo));
        assertEquals("Productora con id: 99 no encontrada. No se puede actualizar.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void deleteProductora_existente() {
        //Arrange
        when(productorasRepository.existsById(1)).thenReturn(true);

        //Act
        service.deleteProductora(1);

        //Assert
        verify(productorasRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteProductora_no_encontrada() {
        //Arrange
        when(productorasRepository.existsById(99)).thenReturn(false);

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.deleteProductora(99));
        assertEquals("Productora con id: 99 no encontrada. No se puede eliminar.", ex.getMessage());
        verify(productorasRepository, never()).deleteById(99);
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void asignarManager_exitoso() {
        //Arrange
        when(productorasRepository.findById(1)).thenReturn(Optional.of(ejemplo));
        when(managerRepository.findById(1)).thenReturn(Optional.of(managerEjemplo));
        when(productorasRepository.save(ejemplo)).thenReturn(ejemplo);

        //Act
        Productoras resultado = service.asignarManager(1, 1);

        //Assert
        assertTrue(resultado.getManagers().contains(managerEjemplo));
        verify(productorasRepository, times(1)).save(ejemplo);
    }

    @Test
    void asignarManager_productora_no_encontrada() {
        //Arrange
        when(productorasRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.asignarManager(99, 1));
        assertEquals("Productora con id: 99 no encontrada. No se puede asignar manager.", ex.getMessage());
    }

    @Test
    void asignarManager_manager_no_encontrado() {
        //Arrange
        when(productorasRepository.findById(1)).thenReturn(Optional.of(ejemplo));
        when(managerRepository.findById(99)).thenReturn(Optional.empty());

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.asignarManager(1, 99));
        assertEquals("Manager con id: 99 no encontrado. No se puede asignar a la productora.", ex.getMessage());
    }

    @Test
    void asignarManager_ya_asignado() {
        //Arrange
        ejemplo.getManagers().add(managerEjemplo);
        when(productorasRepository.findById(1)).thenReturn(Optional.of(ejemplo));
        when(managerRepository.findById(1)).thenReturn(Optional.of(managerEjemplo));

        //Act + Assert
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.asignarManager(1, 1));
        assertEquals("El manager con id: 1 ya está asignado a la productora con id: 1.", ex.getMessage());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}