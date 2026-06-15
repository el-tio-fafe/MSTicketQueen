package cl.duoc.msDireccion.controller;



import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import cl.duoc.msDireccion.dto.RegionDTO;
import cl.duoc.msDireccion.model.Region;
import cl.duoc.msDireccion.service.RegionService;

@WebMvcTest(RegionController.class)  //levanta solo la capa web, no la bd
public class RegionControllerTest {

    @Autowired
    private MockMvc mock; //mock que simula las éticiones HTTP

    @MockitoBean
    private RegionService regionService; //service falso

    private RegionDTO regionEjemplo;

    @BeforeEach
    void setUp(){
        regionEjemplo = new RegionDTO();
        regionEjemplo.setIdRegion(1);
        regionEjemplo.setNombreRegion("Metropolitana");

        

    }







    @Test
    void buscarPorId_retorna200() throws Exception{
        //ARRANGE: el service debe retornar una region 
        when(regionService.buscarRegionPorId(1)).thenReturn(regionEjemplo);

        //ACT + ASSERT
        mock.perform(get("/api/v1/direccion/buscar/region-resumida/id/1"))
            .andExpect(status().isOk());  //el mock simula las llamadas http, puede hacer delete, post, get, etc


    }


    @Test 
    void buscarPorId_retorna404() throws Exception{
        //ARRANGE: buscamos unz region con id 99 y tira un error
        when(regionService.buscarRegionPorId(99)).thenThrow(new RuntimeException("Region con id 99 no encontrada"));

        //ACT + ASSERT
        mock.perform(get("/api/v1/direccion/buscar/region-resumida/id/99")).andExpect(status().isNotFound());  // o sea un codigo HTTPS 404



    }

}
