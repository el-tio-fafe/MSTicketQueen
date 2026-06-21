package cl.duoc.msGestionArtistica.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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


import cl.duoc.msGestionArtistica.model.Artista;
import cl.duoc.msGestionArtistica.service.ArtistaService;

@WebMvcTest(ArtistaController.class)
public class ArtistaControllerTest {


    @Autowired
    private MockMvc mock;//simula llamadas http

    @MockitoBean
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
    void getAllArtistas() throws Exception {
        //Arrange
        when(service.getAllArtistas()).thenReturn(List.of(ejemplo));

        //act
        mock.perform(get("/api/v1/artistas/ver_artistas"))
            .andExpect(status().isOk());
    }

    @Test
    void getAllArtistas_vacio() throws Exception {
        //Arrange
        when(service.getAllArtistas()).thenReturn(List.of());
        //Act + Assert
        mock.perform(get("/api/v1/artistas/ver_artistas"))
            .andExpect(status().isNoContent());        
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getArtistaById_encontrado() throws Exception {
        //Arrange
        when(service.getArtistaById(1)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/artistas/ver_artistas/find_id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void getArtistaById_no_encontrado() throws Exception {
        //Arrange
        when(service.getArtistaById(99)).thenThrow(new RuntimeException("Artista con id: 99 no encontrado."));
        
        //Act + Assert
        mock.perform(get("/api/v1/artistas/ver_artistas/find_id/99"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getArtistaByRut_encontrado() throws Exception {
        //Arrage
        when(service.getArtistaByRut("12345678-9")).thenReturn(ejemplo);
        //Act + Assert
        mock.perform(get("/api/v1/artistas/ver_artistas/find_rut/12345678-9"))
            .andExpect(status().isOk());
    }



    @Test
    void getArtistaByRut_no_encontrado() throws Exception {
        //Arrage
        when(service.getArtistaByRut("98765432-1")).thenThrow(new RuntimeException("Artista con rut: 98765432-1 no encontrado."));
        //Act + Assert
        mock.perform(get("/api/v1/artistas/ver_artistas/find_rut/98765432-1"))
            .andExpect(status().isNotFound());    
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getArtistaByCorreo_Encontrado() throws Exception {
        //Arrage
        when(service.getArtistaByCorreo("losbunkers@gmail.com")).thenReturn(ejemplo);
        //Act + Assert
        mock.perform(get("/api/v1/artistas/ver_artistas/find_correo/losbunkers@gmail.com"))
            .andExpect(status().isOk());
    }


    @Test
    void getArtistaByCorreo_No_Encontrado() throws Exception {
        //Arrage
        when(service.getArtistaByCorreo("noencontrado@gmail.com")).thenThrow(new RuntimeException("Artista con correo: noencontrado@gmail.com no encontrado."));
        //Act + Assert
        mock.perform(get("/api/v1/artistas/ver_artistas/find_correo/noencontrado@gmail.com"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------


@Test
    void saveArtista_creado() throws Exception {
        //Arrange
        when(service.saveArtista(ejemplo)).thenReturn(ejemplo);
        //Act + Assert
        mock.perform(post("/api/v1/artistas/crear_artista")
            .contentType("application/json")
            .content("{\"idArt\":1,\"rutArt\":\"12345678-9\",\"nombreArt\":\"Los Bunkers\",\"correoArt\":\"losbunkers@gmail.com\",\"telefonoArt\":\"+56912345678\"}"))
            .andExpect(status().isCreated());
    }

    @Test
    void saveArtista_con_mismo_rut() throws Exception {
        //Arrange
        when(service.saveArtista(ejemplo)).thenThrow(new RuntimeException("Ya existe un artista con el rut: 1"));
        //Act + Assert
        mock.perform(post("/api/v1/artistas/crear_artista")
            .contentType("application/json")
            .content("{\"idArt\":1,\"rutArt\":\"12345678-9\",\"nombreArt\":\"Los Bunkers\",\"correoArt\":\"losbunkers@gmail.com\",\"telefonoArt\":\"+56912345678\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void saveArtista_con_mismo_correo() throws Exception {
        //Arrange
        when(service.saveArtista(ejemplo)).thenThrow(new RuntimeException("Ya existe un artista con el correo: losbunkers@gmail.com"));
        //Act + Assert
        mock.perform(post("/api/v1/artistas/crear_artista")
            .contentType("application/json")
            .content("{\"idArt\":1,\"rutArt\":\"12345678-9\",\"nombreArt\":\"Los Bunkers\",\"correoArt\":\"losbunkers@gmail.com\",\"telefonoArt\":\"+56912345678\"}"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void updateArtista_existente() throws Exception {
        //Arrange
        when(service.updateArtista(1, ejemplo)).thenReturn(ejemplo);
        //Act + Assert
        mock.perform(put("/api/v1/artistas/actualizar_artista/id/1")
            .contentType("application/json")
            .content("{\"idArt\":1,\"rutArt\":\"12345678-9\",\"nombreArt\":\"Los Bunkers\",\"correoArt\":\"losbunkers@gmail.com\",\"telefonoArt\":\"+56912345678\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void updateArtista_id_no_encontrado() throws Exception {
        //Arrange
        when(service.updateArtista(99, ejemplo)).thenThrow(new RuntimeException("Artista con id: 99 no encontrado. No se puede actualizar."));
        //Act + Assert
        mock.perform(put("/api/v1/artistas/actualizar_artista/id/99")
            .contentType("application/json")
            .content("{\"idArt\":1,\"rutArt\":\"12345678-9\",\"nombreArt\":\"Los Bunkers\",\"correoArt\":\"losbunkers@gmail.com\",\"telefonoArt\":\"+56912345678\"}"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void deleteArtista_existente() throws Exception {
        //Arrange

        //act + Assert
        mock.perform(delete("/api/v1/artistas/eliminar_artista/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void deleteArtista_no_existente() throws Exception {
        //Arrange
        doThrow(new RuntimeException("Artista con id: 99 no encontrado. No se puede eliminar."))
            .when(service).deleteArtista(99);
        //act + Assert
        mock.perform(delete("/api/v1/artistas/eliminar_artista/id/99"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------
}