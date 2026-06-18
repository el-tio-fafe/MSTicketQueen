package cl.duoc.msDireccion.controller;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;



import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.duoc.msDireccion.dto.RegionDTO;
import cl.duoc.msDireccion.dto.RegionUpdateDTO;
import cl.duoc.msDireccion.model.Region;
import cl.duoc.msDireccion.service.RegionService;

@WebMvcTest(RegionController.class)  //levanta solo la capa web, no la bd
public class RegionControllerTest {

    @Autowired
    private MockMvc mock; //mock que simula las peticiones HTTP

    @MockitoBean
    private RegionService regionService; //service falso

    // Utilitario para convertir objetos Java a JSON de texto
    private final ObjectMapper objectMapper = new ObjectMapper();

    private RegionDTO regionEjemploDTO;
    private Region regionEjemplo;

    @BeforeEach
    void setUp(){
        regionEjemplo = new Region();
        regionEjemplo.setIdRegion(1);
        regionEjemplo.setNombreRegion("Metropolitana");
    
        regionEjemploDTO = new RegionDTO();
        regionEjemploDTO.setIdRegion(1);
        regionEjemploDTO.setNombreRegion("Metropolitana");
    }


//****************************************************************************************************** */
//TEST REGION

    @Test
    void listarRegiones_Exitoso_Retorna200YLista() throws Exception {
    // ARRANGE: Preparamos la lista original que devolverá el service
    List<Region> listaRegiones = new ArrayList<>();
    listaRegiones.add(regionEjemplo);

    when(regionService.listarRegiones()).thenReturn(listaRegiones);

    // Creamos de forma local la lista de DTOs tal cual como la genera el Controller
    List<RegionDTO> listaEsperadaDTO = new ArrayList<>();
    listaEsperadaDTO.add(new RegionDTO(
        regionEjemplo.getIdRegion(),
        regionEjemplo.getNombreRegion()
    ));

    // Convertimos nuestra lista de DTOs esperada a un texto  JSON
    String jsonEsperado = objectMapper.writeValueAsString(listaEsperadaDTO);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/regiones"))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonEsperado)); 
    }


    @Test //400 es el BadRequest, es decir que la solicitud no es válida
    void listarRegiones_ListaVacia_Retorna400YMensaje() throws Exception {
        when(regionService.listarRegiones()).thenReturn(new ArrayList<>());

        mock.perform(get("/api/v1/direccion/regiones"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("No hay regiones registradas"));
    }



    @Test
    void buscarRegionPorIdCompleto_Exitoso_Retorna200() throws Exception {
        // ARRANGE
        when(regionService.buscarRegionCompletaPorId(1)).thenReturn(regionEjemplo);
        
        // Convertimos la entidad esperada a JSON plano
        //String jsonEsperado = objectMapper.writeValueAsString(regionEjemplo);

        // ACT + ASSERT
        mock.perform(get("/api/v1/direccion/buscar/region-completa/id/1"))
            .andExpect(status().isOk());
            //.andExpect(content().json(jsonEsperado)); // Compara todo el JSON
    }

    @Test
    void buscarRegionPorIdCompleto_NoEncontrado_Retorna400() throws Exception {
        // ARRANGE
        when(regionService.buscarRegionCompletaPorId(99))
            .thenThrow(new RuntimeException("Region con id: 99 no encontrada"));

        // ACT + ASSERT
        mock.perform(get("/api/v1/direccion/buscar/region-completa/id/99"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Region con id: 99 no encontrada"));
    }




    @Test
    void buscarRegionPorId_retorna200() throws Exception{
        //ARRANGE: el service debe retornar una region 
        when(regionService.buscarRegionPorId(1)).thenReturn(regionEjemploDTO);

        //La sgte linea es para validar el texto del Json junto con la ultima que comentamos tambien
        //String jsonEsperado = objectMapper.writeValueAsString(regionEjemploDTO);
        
        //ACT + ASSERT
        mock.perform(get("/api/v1/direccion/buscar/region-resumida/id/1"))
            .andExpect(status().isOk());  //el mock simula las llamadas http, puede hacer delete, post, get, etc
          //.andExpect(content().json(jsonEsperado));  ->Esta linea valida el texto del JSON que tenga los mismos datos  
        }



    @Test 
    void buscarRegionPorId_retorna400() throws Exception{
        //ARRANGE: buscamos unz region con id 99 y tira un error
        when(regionService.buscarRegionPorId(99)).thenThrow(new RuntimeException("Region con id: 99 no encontrada"));

        //ACT + ASSERT
        mock.perform(get("/api/v1/direccion/buscar/region-resumida/id/99"))
            .andExpect(status().isBadRequest());  // o sea un codigo HTTPS 400
          //.andExpect(content().string("Region con id: 99 no encontrada")); -> esta linea 
          //revisa el texto exacto del mensaje de error en lugar de un JSON cuando ocurre un error porque ocupé 
          //catch(Exception e)
        }




    @Test
    void buscarRegionResumidaPorNombre_Exitoso_Retorna200() throws Exception {
        // ARRANGE
        when(regionService.buscarRegionPorNombre("Metropolitana")).thenReturn(regionEjemploDTO);
        
        //String jsonEsperado = objectMapper.writeValueAsString(regionEjemploDTO);

        // ACT + ASSERT
        mock.perform(get("/api/v1/direccion/buscar/region/nombre/Metropolitana"))
            .andExpect(status().isOk());
            //.andExpect(content().json(jsonEsperado));
    }

    @Test
    void buscarRegionResumidaPorNombre_NoEncontrado_Retorna400() throws Exception {
        // ARRANGE
        when(regionService.buscarRegionPorNombre("Nunca Jamas"))
            .thenThrow(new RuntimeException("Region: Nunca Jamas no encontrada"));

        // ACT + ASSERT
        mock.perform(get("/api/v1/direccion/buscar/region/nombre/Nunca Jamas"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Region: Nunca Jamas no encontrada"));
    }



    @Test
    void guardarRegion_Exitoso_Retorna200() throws Exception {
        // ARRANGE
        when(regionService.guardarRegion(any(Region.class))).thenReturn(regionEjemplo);
        
        //String jsonEsperado = objectMapper.writeValueAsString(regionEjemplo);

        // ACT + ASSERT
        mock.perform(post("/api/v1/direccion/guardar/region")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idRegion\":1,\"nombreRegion\":\"Metropolitana\"}")) // Si comprobaramos el JSON en el parentesis iría (jsonEsperado) que está declarado más arriba y que comentamos
            .andExpect(status().isOk());
            //.andExpect(content().json(jsonEsperado)); // Verificamos que responda el mismo JSON
    }

    @Test
    void guardarRegion_NombreDuplicado_Retorna400() throws Exception {
        // ARRANGE
        when(regionService.guardarRegion(any(Region.class)))
            .thenThrow(new RuntimeException("Ya existe la region: Metropolitana"));

        //String jsonEnviar = objectMapper.writeValueAsString(regionEjemplo);

        // ACT + ASSERT
        mock.perform(post("/api/v1/direccion/guardar/region")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idRegion\":1,\"nombreRegion\":\"Metropolitana\"}")) //-> aquí iría el jsonEnviar
            .andExpect(status().isBadRequest());
            //.andExpect(content().string("Ya existe la region: Metropolitana"));
    }




    @Test
    void actualizarRegionPorId_Exitoso_Retorna200() throws Exception {
        // ARRANGE
        RegionUpdateDTO updateResponseDTO = new RegionUpdateDTO("Metropolitana Actualizada");
        
        when(regionService.actualizarRegionPorId(eq(1), any(Region.class))).thenReturn(updateResponseDTO);

        String jsonEnviar = objectMapper.writeValueAsString(regionEjemplo);
        String jsonEsperado = objectMapper.writeValueAsString(updateResponseDTO);

        // ACT + ASSERT
        mock.perform(put("/api/v1/direccion/actualizar/region/id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
            .andExpect(status().isOk())
            .andExpect(content().json(jsonEsperado));
    }

    @Test
    void actualizarRegionPorId_NoEncontrado_Retorna400() throws Exception {
        // ARRANGE
        when(regionService.actualizarRegionPorId(eq(99), any(Region.class)))
            .thenThrow(new RuntimeException("Region con id: 99 no encontrada"));

        String jsonEnviar = objectMapper.writeValueAsString(regionEjemplo);

        // ACT + ASSERT
        mock.perform(put("/api/v1/direccion/actualizar/region/id/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Region con id: 99 no encontrada"));
    }


}
