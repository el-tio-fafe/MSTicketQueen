package cl.duoc.msAdministrador.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msAdministrador.dto.AdministradorDTO;
import cl.duoc.msAdministrador.dto.AdministradorEmailDTO;
import cl.duoc.msAdministrador.model.Administrador;
import cl.duoc.msAdministrador.model.Auditoria;
import cl.duoc.msAdministrador.repository.AdministradorRepository;
import cl.duoc.msAdministrador.repository.AuditoriaRepository;

@ExtendWith(MockitoExtension.class)
public class AdministradorServiceTest {

    @Mock 
    private AdministradorRepository administradorRepository;

    @Mock 
    private AuditoriaRepository auditoriaRepository;

    @InjectMocks 
    private AdministradorService administradorService;

    private Administrador administradorEjemplo;
    private Auditoria auditoriaEjemplo;
    private AdministradorEmailDTO emailDTOEjemplo;

    @BeforeEach
    void setUp(){
        administradorEjemplo = new Administrador();
        administradorEjemplo.setIdAdm(1);
        administradorEjemplo.setRutAdm("16517585-2");
        administradorEjemplo.setNombreAdm("Maria");
        administradorEjemplo.setApPatAdm("Cruz");
        administradorEjemplo.setApMatAdm("Recabarren");
        administradorEjemplo.setCorreoAdm("maria.r@gmail.com");
        administradorEjemplo.setTelefonoAdm("+56949854785");
        administradorEjemplo.setAuditoria(new ArrayList<>());

        auditoriaEjemplo = new Auditoria();
        auditoriaEjemplo.setIdAuditoria(1);
        auditoriaEjemplo.setNombreResponsable("Matias Gomez");
        auditoriaEjemplo.setFecha(new Date(System.currentTimeMillis()));
        auditoriaEjemplo.setTipoAccion("CREAR");
        auditoriaEjemplo.setDescripcion("Se crea evento '50 años de Trayectoria' Los Bunkers");
        auditoriaEjemplo.setAdministrador(administradorEjemplo);

        emailDTOEjemplo = new AdministradorEmailDTO();
        emailDTOEjemplo.setCorreoAdm("nuevo.correo@admin.com");
    }

    //****************************************************************************************************** */
    //LISTAR ADMINISTRADORES

    @Test
    void listarAdministradores_retornaListaAdministradores() {
        when(administradorRepository.findAll()).thenReturn(List.of(administradorEjemplo));

        List<Administrador> resultado = administradorService.listarAdministradores();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(administradorRepository, times(1)).findAll();
    }

    @Test
    void listarAdministradores_RetornaListaVacia() {
        when(administradorRepository.findAll()).thenReturn(new ArrayList<>());

        List<Administrador> resultado = administradorService.listarAdministradores();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(administradorRepository, times(1)).findAll();
    }

    //LISTAR AUDITORIAS

    @Test
    void listarAuditorias_retornaListaAuditorias() {
        when(auditoriaRepository.findAll()).thenReturn(List.of(auditoriaEjemplo));

        List<Auditoria> resultado = administradorService.listarAuditorias();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(auditoriaRepository, times(1)).findAll();
    }

    @Test
    void listarAuditorias_RetornaListaVacia() {
        when(auditoriaRepository.findAll()).thenReturn(new ArrayList<>());

        List<Auditoria> resultado = administradorService.listarAuditorias();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(auditoriaRepository, times(1)).findAll();
    }

    //LISTAR AUDITORIAS POR ADM

    @Test
    void listarAuditoriasPorAdm_encontrado_retornaLista() {
        when(administradorRepository.findById(1)).thenReturn(Optional.of(administradorEjemplo));
        when(auditoriaRepository.findByAdministrador(administradorEjemplo)).thenReturn(Optional.of(List.of(auditoriaEjemplo)));

        List<Auditoria> resultado = administradorService.listarAuditoriasPorAdm(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void listarAuditoriasPorAdm_AdministradorNoEncontrado() {
        Integer idInexistente = 1;
        when(administradorRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.listarAuditoriasPorAdm(idInexistente);
        });

        assertEquals("Administrador con id: " + idInexistente + " no encontrado.", exception.getMessage());
        verify(administradorRepository, times(1)).findById(idInexistente);
    }

    @Test
    void listarAuditoriasPorAdm_SinAuditorias() {
        Integer idAdm = 1;
        when(administradorRepository.findById(idAdm)).thenReturn(Optional.of(administradorEjemplo));
        when(auditoriaRepository.findByAdministrador(administradorEjemplo)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.listarAuditoriasPorAdm(idAdm);
        });

        assertEquals("No hay auditorías para el administrador con id: " + idAdm, exception.getMessage());
        verify(auditoriaRepository, times(1)).findByAdministrador(administradorEjemplo);
    }

    //BUSCAR AUDITORIAS POR ID

    @Test
    void buscarAuditoriaPorId_encontrado() {
        when(auditoriaRepository.findById(1)).thenReturn(Optional.of(auditoriaEjemplo));

        Auditoria resultado = administradorService.buscarAuditoriaPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdAuditoria());
    }

    @Test
    void buscarAuditoriaPorId_NoEncontrada() {
    
        when(auditoriaRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.buscarAuditoriaPorId(99);
        });

        assertEquals("Auditoría con id: 99 no encontrada.", exception.getMessage());
        verify(auditoriaRepository, times(1)).findById(99);
    }

    //BUSCAR AUDITORIAS POR RUT ADM

    @Test
    void buscarAuditoriaPorRutAdm_encontrado() {
        when(auditoriaRepository.findByAdministrador_RutAdm("16517585-2")).thenReturn(List.of(auditoriaEjemplo));

        List<Auditoria> resultado = administradorService.buscarAuditoriaPorRutAdm("16517585-2");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarAuditoriaPorRutAdm_NoEncontradoRetornaListaVacia() {
        when(auditoriaRepository.findByAdministrador_RutAdm("12345678-9")).thenReturn(new ArrayList<>());

        List<Auditoria> resultado = administradorService.buscarAuditoriaPorRutAdm("12345678-9");

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        verify(auditoriaRepository, times(1)).findByAdministrador_RutAdm("12345678-9");
    }

    //GUARDAR ADM

    @Test
    void guardarAdministrador_exitoso() {
        String rutObj = administradorEjemplo.getRutAdm();
        when(administradorRepository.findByRutAdm(any(String.class))).thenReturn(Optional.empty());
        when(administradorRepository.save(any(Administrador.class))).thenReturn(administradorEjemplo);
        when(auditoriaRepository.save(any(Auditoria.class))).thenReturn(auditoriaEjemplo);

        Administrador resultado = administradorService.guardarAdministrador(administradorEjemplo);

        assertNotNull(resultado);
        assertEquals(rutObj, resultado.getRutAdm());
        verify(administradorRepository, times(1)).save(administradorEjemplo);
        verify(auditoriaRepository, times(1)).save(any(Auditoria.class));
    }

    @Test
    void guardarAdministrador_RutDuplicado() {
        //String rutDuplicado = administradorEjemplo.getRutAdm();
        when(administradorRepository.findByRutAdm("16517585-2")).thenReturn(Optional.of(administradorEjemplo));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.guardarAdministrador(administradorEjemplo);
        });

        assertEquals("Ya existe un administrador con el rut: 16517585-2", exception.getMessage());
        verify(administradorRepository, times(1)).findByRutAdm("16517585-2");
    }

    //BUSCAR POR ID ADM

    @Test
    void buscarPorIdAdm_encontrado() {
        when(administradorRepository.findById(1)).thenReturn(Optional.of(administradorEjemplo));

        Administrador resultado = administradorService.buscarPorIdAdm(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdAdm());
    }

    @Test
    void buscarPorIdAdm_NoEncontrado() {
        Integer idInexistente = 99;
        when(administradorRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.buscarPorIdAdm(idInexistente);
        });

        assertEquals("Administrador con id: " + idInexistente + " no encontrado.", exception.getMessage());
        verify(administradorRepository, times(1)).findById(idInexistente);
    }

    // BUSCAR POR RUT ADM

    @Test
    void buscarPorRutAdm_encontrado() {
        when(administradorRepository.findByRutAdm("16517585-2")).thenReturn(Optional.of(administradorEjemplo));

        Administrador resultado = administradorService.buscarPorRutAdm("16517585-2");

        assertNotNull(resultado);
        assertEquals("16517585-2", resultado.getRutAdm());
    }

    @Test
    void buscarPorRutAdm_NoEncontrado() {
        
        when(administradorRepository.findByRutAdm("12345678-9")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.buscarPorRutAdm("12345678-9");
        });

        assertEquals("Administrador con rut: 12345678-9 no encontrado.", exception.getMessage());
        verify(administradorRepository, times(1)).findByRutAdm("12345678-9");
    }

    //ELIMINAR POR ID

    @Test
    void eliminarPorId_exitoso() {
        when(administradorRepository.findById(1)).thenReturn(Optional.of(administradorEjemplo));

        administradorService.eliminarPorId(1);

        verify(administradorRepository, times(1)).deleteById(1);
        verify(auditoriaRepository, times(1)).save(any(Auditoria.class));
    }

    @Test
    void eliminarPorId_NoEncontrado() {
        
        when(administradorRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.eliminarPorId(99);
        });

        assertEquals("Administrador con id 99 no encontrado.", exception.getMessage());
        verify(administradorRepository, times(1)).findById(99);
    }

    //ELIMINAR POR RUT

    @Test
    void eliminarPorRut_exitoso() {
        when(administradorRepository.findByRutAdm("16517585-2")).thenReturn(Optional.of(administradorEjemplo));

        administradorService.eliminarPorRut("16517585-2");

        verify(administradorRepository, times(1)).deleteById(1);
        verify(auditoriaRepository, times(1)).save(any(Auditoria.class));
    }

    @Test
    void eliminarPorRut_NoEncontrado() {
        String rutInexistente = "12345678-9";
        when(administradorRepository.findByRutAdm(rutInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.eliminarPorRut(rutInexistente);
        });

        assertEquals("Administrador con rut: " + rutInexistente + " no encontrado", exception.getMessage());
        verify(administradorRepository, times(1)).findByRutAdm(rutInexistente);
    }

    //ACTUALIZAR ADM POR ID

    @Test
    void actualizarAdmPorId_exitoso() {
        when(administradorRepository.findById(1)).thenReturn(Optional.of(administradorEjemplo));
        when(administradorRepository.save(any(Administrador.class))).thenReturn(administradorEjemplo);

        Administrador resultado = administradorService.actualizarAdmPorId(1, administradorEjemplo);

        assertNotNull(resultado);
        verify(administradorRepository, times(1)).save(any(Administrador.class));
        verify(auditoriaRepository, times(1)).save(any(Auditoria.class));
    }

    @Test
    void actualizarAdmPorId_NoEncontrado() {
        Integer idInexistente = 1;
        when(administradorRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.actualizarAdmPorId(idInexistente, administradorEjemplo);
        });

        assertEquals("Administrador con id " + idInexistente + " no encontrado.", exception.getMessage());
        verify(administradorRepository, times(1)).findById(idInexistente);
    }

    //ACTUALIZAR ADM POR RUT

    @Test
    void actualizarAdmPorRut_exitoso() {
        when(administradorRepository.findByRutAdm("16517585-2")).thenReturn(Optional.of(administradorEjemplo));
        when(administradorRepository.save(any(Administrador.class))).thenReturn(administradorEjemplo);

        Administrador resultado = administradorService.actualizarAdmPorRut("16517585-2", administradorEjemplo);

        assertNotNull(resultado);
        verify(administradorRepository, times(1)).save(any(Administrador.class));
        verify(auditoriaRepository, times(1)).save(any(Auditoria.class));
    }

    @Test
    void actualizarAdmPorRut_NoEncontrado() {
        
        when(administradorRepository.findByRutAdm("12345678-9")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.actualizarAdmPorRut("12345678-9", administradorEjemplo);
        });

        assertEquals("Administrador con rut 12345678-9 no encontrado.", exception.getMessage());
        verify(administradorRepository, times(1)).findByRutAdm("12345678-9");
    }

    //ACTUALIZAR EMAIL ADM

    @Test
    void actualizarEmailAdm_exitoso() {
        when(administradorRepository.findById(1)).thenReturn(Optional.of(administradorEjemplo));
        when(administradorRepository.save(any(Administrador.class))).thenReturn(administradorEjemplo);

        AdministradorDTO resultado = administradorService.actualizarEmailAdm(1, emailDTOEjemplo);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdAdm());
        verify(administradorRepository, times(1)).save(any(Administrador.class));
    }

    @Test
    void actualizarEmailAdm_NoEncontrado() {
        Integer idInexistente = 99;
        when(administradorRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.actualizarEmailAdm(idInexistente, emailDTOEjemplo);
        });

        assertEquals("Administrador con id: " + idInexistente + " no encontrado", exception.getMessage());
        verify(administradorRepository, times(1)).findById(idInexistente);
    }

    //GUARDAR AUDITORIA

    @Test
    void guardarAuditoria_exitoso() {
        when(administradorRepository.findById(1)).thenReturn(Optional.of(administradorEjemplo));
        when(auditoriaRepository.save(any(Auditoria.class))).thenReturn(auditoriaEjemplo);

        Auditoria resultado = administradorService.guardarAuditoria(auditoriaEjemplo);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdAuditoria());
        verify(auditoriaRepository, times(1)).save(auditoriaEjemplo);
    }

    @Test
    void guardarAuditoria_AdministradorNoExiste() {
        // Obtenemos el ID asignado en el objeto de auditoría (que es 1)
        //Integer idAsignado = auditoriaEjemplo.getAdministrador().getIdAdm();
        auditoriaEjemplo.getAdministrador().setIdAdm(99);
        when(administradorRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            administradorService.guardarAuditoria(auditoriaEjemplo);
        });

        assertEquals("Administrador con id: 99 no se encuentra o no existe", exception.getMessage());
        verify(administradorRepository, times(1)).findById(99);
    }


}