package cl.duoc.msGestionArtistica.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msGestionArtistica.model.Artista;
import cl.duoc.msGestionArtistica.service.ArtistaService;
import io.swagger.v3.oas.annotations.Operation;

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
    void getAllArtistas() throws Exception {
        //Arrange
        when(service.getAllArtistas()).thenReturn(List.of(ejemplo));

        //act
        mock.perform(get("/api/v1/artistas/ver_artistas"))
            .andExpect(status().isOk());
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


    //@GetMapping("/ver_artistas/find_correo/{correo}")
    //@Operation(
    //    summary = "Obtener un artista por correo", 
    //    description = "Endpoint para obtener un artista específico por su correo.")
    //public ResponseEntity<?> getArtistaByCorreo(@PathVariable String correo) {// endpoint para buscar un artista por correo
//
    //    try {
    //        Artista artista = artistaService.getArtistaByCorreo(correo);// intenta obtener el artista por correo,
    //        //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
    //        return ResponseEntity.ok(artista);
//
    //    } catch (RuntimeException e) {
    //        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    //    }
    //}

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
}
