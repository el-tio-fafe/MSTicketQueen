package cl.duoc.msDireccion.controller;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.msDireccion.model.Region;
import cl.duoc.msDireccion.service.RegionService;

@WebMvcTest(RegionController.class)  //levanta solo la capa web, no la bd
public class RegionControllerTest {

    @Autowired
    private MockMvc mock; //mock que simula las éticiones HTTP

    @MockBean
    private RegionService regionService; //service falso

    private Region regionEjemplo;

    @BeforeEach
    void setUp(){
        regionEjemplo = new Region();
        regionEjemplo.setIdRegion(1);
        regionEjemplo.setNombreRegion("Metropolitana");


    }







    @Test
    void buscarPorId_retorna200(){
        //ARRANGE: el service debe retornar una region 
        when(regionService.buscarRegionPorId(1)).thenReturn(regionEjemplo);

        //ACT + ASSERT
        mock.perform(get("/api/v1/regiones/1")).andExpect(status().isOk());  //el mock simula las llamadas http, puede hacer delete, post, get, etc


    }


    @Test 
    void buscarPorId_retorna404(){
        //ARRANGE: buscamos unz region con id 99 y tira un error
        when(regionService.buscarRegionPorId(99)).thenThrow(new RuntimeException("Region no encontrada"));

        //ACT + ASSERT
        mock.perform(get("/api/v1/regiones/99")).andExpect(status().isNotFound());  // o sea un codigo HTTPS 404



    }

}
