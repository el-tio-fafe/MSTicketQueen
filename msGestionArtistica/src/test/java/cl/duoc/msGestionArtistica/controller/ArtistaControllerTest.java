package cl.duoc.msGestionArtistica.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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


    //public Artista getArtistaById(Integer id) {// Método para obtener un artista por su ID
    //    return artistaRepository.findById(id)
    //            .orElseThrow(() -> new RuntimeException("Artista con id: " + id + " no encontrado."));//mensaje de error
    //}

    @Test
    void getArtistaByRut_encontrado() throws Exeption {
        //Arrage

        //Act + Assert
    }



    @Test
    void getArtistaByRut_no_encontrado() throws Exeption {
        //Arrage

        //Act + Assert    
    }

}
