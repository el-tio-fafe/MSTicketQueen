package cl.duoc.msAdministrador.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.msAdministrador.dto.AdministradorDTO;
import cl.duoc.msAdministrador.dto.AdministradorEmailDTO;
import cl.duoc.msAdministrador.model.Administrador;
import cl.duoc.msAdministrador.model.Auditoria;
import cl.duoc.msAdministrador.service.AdministradorService;

@WebMvcTest(AdministradorController.class)
public class AdministradorControllerTest {

    @Autowired
    private MockMvc mock; // Simula las peticiones HTTP

    @MockitoBean
    private AdministradorService administradorService; // Service falso utilizando la nueva anotación

    // Utilitario local sin inyección de Spring
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Administrador administradorEjemplo;
    private Auditoria auditoriaEjemplo;
    private AdministradorEmailDTO emailDTOEjemplo;

    @BeforeEach
    void setUp() {
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


//************************************************************************************ */
//LISTAR ADMINISTRADORES

    @Test
    void listarAdministradores_Exitoso_Retorna200YLista() throws Exception {
        List<Administrador> lista = new ArrayList<>();
        lista.add(administradorEjemplo);
        when(administradorService.listarAdministradores()).thenReturn(lista);

        // Generamos la lista DTO esperada tal como lo procesa el mapeador interno del controlador
        List<AdministradorDTO> listaEsperadaDTO = List.of(new AdministradorDTO(
            administradorEjemplo.getIdAdm(),
            administradorEjemplo.getNombreAdm(),
            administradorEjemplo.getApPatAdm(),
            administradorEjemplo.getRutAdm(),
            administradorEjemplo.getCorreoAdm()
        ));
        String jsonEsperado = objectMapper.writeValueAsString(listaEsperadaDTO);

        mock.perform(get("/api/v1/administradores"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEsperado));
    }

    @Test
    //CODIGO 204: "Sin Contenido" (No Content)
    void listarAdministradores_ListaVacia_Retorna204() throws Exception {
        when(administradorService.listarAdministradores()).thenReturn(new ArrayList<>());

        mock.perform(get("/api/v1/administradores"))
                .andExpect(status().isNoContent());
    }


//LISTAR AUDITORIAS

    @Test
    void listarAuditorias_Exitoso_Retorna200() throws Exception {
        List<Auditoria> lista = List.of(auditoriaEjemplo);
        when(administradorService.listarAuditorias()).thenReturn(lista);

        mock.perform(get("/api/v1/administradores/auditorias"))
                .andExpect(status().isOk());
    }

    @Test
    void listarAuditorias_ListaVacia_Retorna204() throws Exception {
        when(administradorService.listarAuditorias()).thenReturn(new ArrayList<>());

        mock.perform(get("/api/v1/administradores/auditorias"))
                .andExpect(status().isNoContent());
    }

    @Test
    void listarAuditorias_ErrorServicio_Retorna400() throws Exception {
        when(administradorService.listarAuditorias()).thenThrow(new RuntimeException("Error al listar auditorías"));

        mock.perform(get("/api/v1/administradores/auditorias"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error al listar auditorías"));
    }


//BUSCAR AUDITORIA POR ID

    @Test
    void buscarAuditoriaPorId_Exitoso_Retorna200() throws Exception {
        when(administradorService.buscarAuditoriaPorId(1)).thenReturn(auditoriaEjemplo);

        mock.perform(get("/api/v1/administradores/auditorias/buscar/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarAuditoriaPorId_NoEncontrada_Retorna400() throws Exception {
        when(administradorService.buscarAuditoriaPorId(100)).thenThrow(new RuntimeException("Auditoría con id: 100 no encontrada."));

        mock.perform(get("/api/v1/administradores/auditorias/buscar/100"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Auditoría con id: 100 no encontrada."));
    }


//BUSCAR AUDITORIAS POR RUT ADM

    @Test
    void buscarAuditoriasPorAdm_Exitoso_Retorna200() throws Exception {
        when(administradorService.buscarAuditoriaPorRutAdm("16517585-2")).thenReturn(List.of(auditoriaEjemplo));

        mock.perform(get("/api/v1/administradores/auditorias/buscar/rut/16517585-2"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarAuditoriasPorAdm_ListaVacia_Retorna204() throws Exception {
        when(administradorService.buscarAuditoriaPorRutAdm("12345678-9")).thenReturn(new ArrayList<>());

        mock.perform(get("/api/v1/administradores/auditorias/buscar/rut/12345678-9"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarAuditoriasPorAdm_ErrorServicio_Retorna400() throws Exception {
        //error 400 (Bad Request) tiene datos inválidos o su sintaxis es incorrecta.
        when(administradorService.buscarAuditoriaPorRutAdm("error")).thenThrow(new RuntimeException("Error crítico de consulta"));

        mock.perform(get("/api/v1/administradores/auditorias/buscar/rut/error"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error crítico de consulta"));
    }


//LISTAR AUDITORIAS POR ID ADM

    @Test
    void listarAuditoriaPorAdm_Exitoso_Retorna200() throws Exception {
        when(administradorService.listarAuditoriasPorAdm(1)).thenReturn(List.of(auditoriaEjemplo));

        mock.perform(get("/api/v1/administradores/auditorias/listar/1"))
                .andExpect(status().isOk());
    }

    @Test
    void listarAuditoriaPorAdm_ListaVacia_Retorna204() throws Exception {
        when(administradorService.listarAuditoriasPorAdm(1)).thenReturn(new ArrayList<>());

        mock.perform(get("/api/v1/administradores/auditorias/listar/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void listarAuditoriaPorAdm_ErrorServicio_Retorna400() throws Exception {
        when(administradorService.listarAuditoriasPorAdm(99)).thenThrow(new RuntimeException("Administrador no existe"));

        mock.perform(get("/api/v1/administradores/auditorias/listar/99"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Administrador no existe"));
    }


//BUSCAR ADMINISTRADOR POR ID

    @Test
    void buscarPorId_Exitoso_Retorna200() throws Exception {
        when(administradorService.buscarPorIdAdm(1)).thenReturn(administradorEjemplo);

        mock.perform(get("/api/v1/administradores/id/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorId_NoEncontrado_Retorna400() throws Exception {
        when(administradorService.buscarPorIdAdm(99)).thenThrow(new RuntimeException("Administrador con id: 99 no encontrado."));

        mock.perform(get("/api/v1/administradores/id/99"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Administrador con id: 99 no encontrado."));
    }


//BUSCAR ADMINISTRADOR POR RUT

    @Test
    void buscarPorRut_Exitoso_Retorna200() throws Exception {
        when(administradorService.buscarPorRutAdm("16517585-2")).thenReturn(administradorEjemplo);

        mock.perform(get("/api/v1/administradores/rut/16517585-2"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorRut_NoEncontrado_Retorna400() throws Exception {
        when(administradorService.buscarPorRutAdm("12345678-9")).thenThrow(new RuntimeException("Administrador con rut: 12345678-9 no encontrado."));

        mock.perform(get("/api/v1/administradores/rut/12345678-9"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Administrador con rut: 12345678-9 no encontrado."));
    }


//GUARDAR ADMINISTRADOR

    @Test
    void guardar_Exitoso_Retorna200() throws Exception {
        when(administradorService.guardarAdministrador(any(Administrador.class))).thenReturn(administradorEjemplo);
        String jsonEnviar = objectMapper.writeValueAsString(administradorEjemplo);

        mock.perform(post("/api/v1/administradores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isOk());
    }

    @Test
    void guardar_RutDuplicado_Retorna400() throws Exception {
        when(administradorService.guardarAdministrador(any(Administrador.class)))
                .thenThrow(new RuntimeException("Ya existe un administrador con el rut: 16517585-2"));
        String jsonEnviar = objectMapper.writeValueAsString(administradorEjemplo);

        mock.perform(post("/api/v1/administradores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Ya existe un administrador con el rut: 16517585-2"));
    }



//ELIMINAR POR ID

    @Test
    void eliminarPorId_Exitoso_Retorna200() throws Exception {
        mock.perform(delete("/api/v1/administradores/eliminar/id/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Administrador con id: 1 eliminado con exito."));

        verify(administradorService, times(1)).eliminarPorId(1);
    }

    @Test
    void eliminarPorId_NoEncontrado_Retorna400() throws Exception {
        doThrow(new RuntimeException("Administrador con id 99 no encontrado.")).when(administradorService).eliminarPorId(99);

        mock.perform(delete("/api/v1/administradores/eliminar/id/99"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Administrador con id 99 no encontrado."));
    }


//ELIMINAR POR RUT

    @Test
    void eliminarPorRut_Exitoso_Retorna200() throws Exception {
        mock.perform(delete("/api/v1/administradores/eliminar/rut/16517585-2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Administrador con rut: 16517585-2 eliminado con exito."));

        verify(administradorService, times(1)).eliminarPorRut("16517585-2");
    }

    @Test
    void eliminarPorRut_NoEncontrado_Retorna400() throws Exception {
        doThrow(new RuntimeException("Administrador con rut: 12345678-9 no encontrado")).when(administradorService).eliminarPorRut("12345678-9");

        mock.perform(delete("/api/v1/administradores/eliminar/rut/12345678-9"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Administrador con rut: 12345678-9 no encontrado"));
    }


//ACTUALIZAR ADM POR ID

    @Test
    void actualizarPorId_Exitoso_Retorna200() throws Exception {
        when(administradorService.actualizarAdmPorId(eq(1), any(Administrador.class))).thenReturn(administradorEjemplo);
        String jsonEnviar = objectMapper.writeValueAsString(administradorEjemplo);

        mock.perform(put("/api/v1/administradores/actualizar/id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isOk());
    }

    @Test
    void actualizarPorId_NoEncontrado_Retorna400() throws Exception {
        when(administradorService.actualizarAdmPorId(eq(99), any(Administrador.class)))
                .thenThrow(new RuntimeException("Administrador con id 99 no encontrado."));
        String jsonEnviar = objectMapper.writeValueAsString(administradorEjemplo);

        mock.perform(put("/api/v1/administradores/actualizar/id/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Administrador con id 99 no encontrado."));
    }


//ACTUALIZAR ADM POR RUT

    @Test
    void actualizarPorRut_Exitoso_Retorna200() throws Exception {
        when(administradorService.actualizarAdmPorRut(eq("16517585-2"), any(Administrador.class))).thenReturn(administradorEjemplo);
        String jsonEnviar = objectMapper.writeValueAsString(administradorEjemplo);

        mock.perform(put("/api/v1/administradores/actualizar/rut/16517585-2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isOk());
    }

    
    /*@Test   ESTE METODO REVISA TAMBIÉN EL JSON:
    void actualizarPorRut_Exitoso_Retorna200YValidaJson() throws Exception {
    when(administradorService.actualizarAdmPorRut(eq("16517585-2"), any(Administrador.class))).thenReturn(administradorEjemplo);
    String jsonEnviar = objectMapper.writeValueAsString(administradorEjemplo);

    mock.perform(put("/api/v1/administradores/actualizar/rut/16517585-2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonEnviar))
            .andExpect(status().isOk())
            .andExpect(content().json(jsonEnviar)); // <-- ESTA LÍNEA SÍ REVISA EL JSON DE RESPUESTA
    } */

    @Test
    void actualizarPorRut_NoEncontrado_Retorna400() throws Exception {
        when(administradorService.actualizarAdmPorRut(eq("12345678-9"), any(Administrador.class)))
                .thenThrow(new RuntimeException("Administrador con rut 12345678-9 no encontrado."));
        String jsonEnviar = objectMapper.writeValueAsString(administradorEjemplo);

        mock.perform(put("/api/v1/administradores/actualizar/rut/12345678-9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Administrador con rut 12345678-9 no encontrado."));
    }


//ACTUALIZAR EMAIL POR ID

    @Test
    void actualizarEmailPorId_Exitoso_Retorna200() throws Exception {
        AdministradorDTO dtoRespuesta = new AdministradorDTO(1, "Maria", "Cruz", "16517585-2", "nuevo.correo@admin.com");
        when(administradorService.actualizarEmailAdm(eq(1), any(AdministradorEmailDTO.class))).thenReturn(dtoRespuesta);
        String jsonEnviar = objectMapper.writeValueAsString(emailDTOEjemplo);
        String jsonEsperado = objectMapper.writeValueAsString(dtoRespuesta);

        mock.perform(patch("/api/v1/administradores/actualizar/email/id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEsperado));
    }

    @Test
    void actualizarEmailPorId_NoEncontrado_Retorna400() throws Exception {
        when(administradorService.actualizarEmailAdm(eq(99), any(AdministradorEmailDTO.class)))
                .thenThrow(new RuntimeException("Administrador con id: 99 no encontrado"));
        String jsonEnviar = objectMapper.writeValueAsString(emailDTOEjemplo);

        mock.perform(patch("/api/v1/administradores/actualizar/email/id/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Administrador con id: 99 no encontrado"));
    }


//GUARDAR AUDITORIA

    @Test
    void guardarAuditoria_Exitoso_Retorna200() throws Exception {
        when(administradorService.guardarAuditoria(any(Auditoria.class))).thenReturn(auditoriaEjemplo);
        String jsonEnviar = objectMapper.writeValueAsString(auditoriaEjemplo);

        mock.perform(post("/api/v1/administradores/auditorias/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isOk());
    }

    @Test
    void guardarAuditoria_AdministradorNoExiste_Retorna400() throws Exception {
        when(administradorService.guardarAuditoria(any(Auditoria.class)))
                .thenThrow(new RuntimeException("Administrador con id: 99 no se encuentra o no existe"));
        String jsonEnviar = objectMapper.writeValueAsString(auditoriaEjemplo);

        mock.perform(post("/api/v1/administradores/auditorias/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Administrador con id: 99 no se encuentra o no existe"));
    }



}
