package cl.duoc.msGestionArtistica.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.msGestionArtistica.model.Manager;
import cl.duoc.msGestionArtistica.service.ManagerService;

@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {

    @Autowired
    private MockMvc mock;//simula llamadas http

    @MockitoBean
    private ManagerService service;

    private Manager ejemplo;

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
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getAllManagers() throws Exception {
        //Arrange
        when(service.getAllManagers()).thenReturn(List.of(ejemplo));

        //Act + Assert
        mock.perform(get("/api/v1/managers/ver_managers"))
            .andExpect(status().isOk());
    }

    @Test
    void getAllManagers_vacio() throws Exception {
        //Arrange
        when(service.getAllManagers()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/managers/ver_managers"))
            .andExpect(status().isNoContent());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getManagerById_encontrado() throws Exception {
        //Arrange
        when(service.getManagerById(1)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/managers/ver_managers/find_id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void getManagerById_no_encontrado() throws Exception {
        //Arrange
        when(service.getManagerById(99)).thenThrow(new RuntimeException("Manager con id: 99 no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/managers/ver_managers/find_id/99"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getManagerByRut_encontrado() throws Exception {
        //Arrange
        when(service.getManagerByRut("11111111-1")).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/managers/ver_managers/find_rut/11111111-1"))
            .andExpect(status().isOk());
    }

    @Test
    void getManagerByRut_no_encontrado() throws Exception {
        //Arrange
        when(service.getManagerByRut("99999999-9")).thenThrow(new RuntimeException("Manager con rut: 99999999-9 no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/managers/ver_managers/find_rut/99999999-9"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getManagerByCorreo_encontrado() throws Exception {
        //Arrange
        when(service.getManagerByCorreo("maria.gonzalez@gmail.com")).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/managers/ver_managers/find_correo/maria.gonzalez@gmail.com"))
            .andExpect(status().isOk());
    }

    @Test
    void getManagerByCorreo_no_encontrado() throws Exception {
        //Arrange
        when(service.getManagerByCorreo("noencontrado@gmail.com")).thenThrow(new RuntimeException("Manager con correo: noencontrado@gmail.com no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/managers/ver_managers/find_correo/noencontrado@gmail.com"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void saveManager_creado() throws Exception {
        //Arrange
        when(service.saveManager(any(Manager.class))).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(post("/api/v1/managers/crear_manager")
                .contentType("application/json")
                .content("{\"idMngr\":1,\"rutMngr\":\"11111111-1\",\"nombreMngr\":\"María\",\"apPaternoMngr\":\"González\",\"apMaternoMngr\":\"Pérez\",\"correoMngr\":\"maria.gonzalez@gmail.com\",\"telefonoMngr\":\"+56987654321\"}"))
            .andExpect(status().isCreated());
    }

    @Test
    void saveManager_con_mismo_rut() throws Exception {
        //Arrange
        when(service.saveManager(any(Manager.class)))
            .thenThrow(new RuntimeException("Ya existe un manager con el rut: 11111111-1"));

        //Act + Assert
        mock.perform(post("/api/v1/managers/crear_manager")
                .contentType("application/json")
                .content("{\"idMngr\":1,\"rutMngr\":\"11111111-1\",\"nombreMngr\":\"María\",\"apPaternoMngr\":\"González\",\"apMaternoMngr\":\"Pérez\",\"correoMngr\":\"maria.gonzalez@gmail.com\",\"telefonoMngr\":\"+56987654321\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void saveManager_con_mismo_correo() throws Exception {
        //Arrange
        when(service.saveManager(any(Manager.class)))
            .thenThrow(new RuntimeException("Ya existe un manager con el correo: maria.gonzalez@gmail.com"));

        //Act + Assert
        mock.perform(post("/api/v1/managers/crear_manager")
                .contentType("application/json")
                .content("{\"idMngr\":1,\"rutMngr\":\"11111111-1\",\"nombreMngr\":\"María\",\"apPaternoMngr\":\"González\",\"apMaternoMngr\":\"Pérez\",\"correoMngr\":\"maria.gonzalez@gmail.com\",\"telefonoMngr\":\"+56987654321\"}"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void updateManager_existente() throws Exception {
        //Arrange
        when(service.updateManager(eq(1), any(Manager.class))).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(put("/api/v1/managers/actualizar_manager/id/1")
                .contentType("application/json")
                .content("{\"idMngr\":1,\"rutMngr\":\"11111111-1\",\"nombreMngr\":\"María\",\"apPaternoMngr\":\"González\",\"apMaternoMngr\":\"Pérez\",\"correoMngr\":\"maria.gonzalez@gmail.com\",\"telefonoMngr\":\"+56987654321\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void updateManager_id_no_encontrado() throws Exception {
        //Arrange
        when(service.updateManager(eq(99), any(Manager.class)))
            .thenThrow(new RuntimeException("Manager con id: 99 no encontrado. No se puede actualizar."));

        //Act + Assert
        mock.perform(put("/api/v1/managers/actualizar_manager/id/99")
                .contentType("application/json")
                .content("{\"idMngr\":1,\"rutMngr\":\"11111111-1\",\"nombreMngr\":\"María\",\"apPaternoMngr\":\"González\",\"apMaternoMngr\":\"Pérez\",\"correoMngr\":\"maria.gonzalez@gmail.com\",\"telefonoMngr\":\"+56987654321\"}"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void deleteManager_existente() throws Exception {
        //Arrange
        //metodo void, no hace falta "when"

        //Act + Assert
        mock.perform(delete("/api/v1/managers/eliminar_manager/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void deleteManager_no_existente() throws Exception {
        //Arrange
        doThrow(new RuntimeException("Manager con id: 99 no encontrado. No se puede eliminar.")).when(service).deleteManager(99);

        //Act + Assert
        mock.perform(delete("/api/v1/managers/eliminar_manager/id/99"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void asignarArtista_exitoso() throws Exception {
        //Arrange
        when(service.asignarArtista(1, 1)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(patch("/api/v1/managers/asignar_artista/1/1"))
            .andExpect(status().isOk());
    }

    @Test
    void asignarArtista_manager_no_encontrado() throws Exception {
        //Arrange
        when(service.asignarArtista(99, 1))
            .thenThrow(new RuntimeException("Manager con id: 99 no encontrado. No se puede asignar artista."));

        //Act + Assert
        mock.perform(patch("/api/v1/managers/asignar_artista/99/1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void asignarArtista_artista_no_encontrado() throws Exception {
        //Arrange
        when(service.asignarArtista(1, 99))
            .thenThrow(new RuntimeException("Artista con id: 99 no encontrado. No se puede asignar al manager."));

        //Act + Assert
        mock.perform(patch("/api/v1/managers/asignar_artista/1/99"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void asignarArtista_ya_asignado() throws Exception {
        //Arrange
        when(service.asignarArtista(1, 1))
            .thenThrow(new RuntimeException("El artista con id: 1 ya está asignado al manager con id: 1."));

        //Act + Assert
        mock.perform(patch("/api/v1/managers/asignar_artista/1/1"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}