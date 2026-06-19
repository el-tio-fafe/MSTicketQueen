package cl.duoc.msDireccion.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doThrow;  -> SE USA PARA OCUPAR EL doThrow
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete; //-> SE USA PARA OCUPAR DELETE


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

import cl.duoc.msDireccion.dto.CalleUpdateDTO;
import cl.duoc.msDireccion.dto.CiudadProvinciaUpdateDTO;
import cl.duoc.msDireccion.dto.ComunaUpdateDTO;
import cl.duoc.msDireccion.dto.RegionDTO;
import cl.duoc.msDireccion.dto.RegionUpdateDTO;
import cl.duoc.msDireccion.model.Calle;
import cl.duoc.msDireccion.model.CiudadProvincia;
import cl.duoc.msDireccion.model.Comuna;
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

    private CiudadProvincia ciudadProvinciaEjemplo;
    private CiudadProvinciaUpdateDTO ciudadProvinciaUpdate;

    private Comuna comunaEjemplo;
    private ComunaUpdateDTO comunaUpdateEjemplo;

    private Calle calleEjemplo;
    private CalleUpdateDTO calleUpdate;

    @BeforeEach
    void setUp(){
        regionEjemplo = new Region();
        regionEjemplo.setIdRegion(1);
        regionEjemplo.setNombreRegion("Metropolitana");
    
        regionEjemploDTO = new RegionDTO();
        regionEjemploDTO.setIdRegion(1);
        regionEjemploDTO.setNombreRegion("Metropolitana");


        ciudadProvinciaEjemplo = new CiudadProvincia();
        ciudadProvinciaEjemplo.setIdCiudadProvincia(1);
        ciudadProvinciaEjemplo.setNombreCiudadProvincia("Santiago");
        ciudadProvinciaEjemplo.setRegion(regionEjemplo);

        ciudadProvinciaUpdate = new CiudadProvinciaUpdateDTO();
        ciudadProvinciaUpdate.setNombreCiudadProvincia("Santiago Actualizado");


        comunaEjemplo = new Comuna();
        comunaEjemplo.setIdComuna(1);
        comunaEjemplo.setNombreComuna("Providencia");
        comunaEjemplo.setCiudadProvincia(ciudadProvinciaEjemplo);

        comunaUpdateEjemplo = new ComunaUpdateDTO();
        comunaUpdateEjemplo.setNombreComuna("Providencia Actualizada");


        calleEjemplo = new Calle();
        calleEjemplo.setIdCalle(1);
        calleEjemplo.setNombreCalle("Avenida Libertador Bernardo O'Higgins");
        calleEjemplo.setComuna(comunaEjemplo);

        calleUpdate = new CalleUpdateDTO();
        calleUpdate.setNombreCalle("Avenida Alameda Actualizada");
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



//****************************************************************************************************** */
//TEST CIUDAD/PROVINCIA

    @Test
    void listarTodasCiudadesProvincias_Exitoso_Retorna200() throws Exception {
    // ARRANGE: 
    List<CiudadProvincia> lista = new ArrayList<>();
    lista.add(ciudadProvinciaEjemplo);
    when(regionService.listarCiudadesOProvincias()).thenReturn(lista);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/ciudad-provincia/listar"))
        .andExpect(status().isOk());
    }

    @Test
    void listarTodasCiudadesProvincias_Vacio_Retorna400() throws Exception {
    // ARRANGE: El service devuelve una lista vacía
    when(regionService.listarCiudadesOProvincias()).thenReturn(new ArrayList<>());

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/ciudad-provincia/listar"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void listarCiudadesProvinciasPorIdRegion_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    List<CiudadProvincia> lista = new ArrayList<>();
    lista.add(ciudadProvinciaEjemplo);
    when(regionService.listarCiudadesProvinciasPorIdRegion(1)).thenReturn(lista);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/ciudad-provincia/listar/id/1"))
        .andExpect(status().isOk());
    }

    @Test
    void listarCiudadesProvinciasPorIdRegion_VacioOError_Retorna400() throws Exception {
    // ARRANGE: Simulamos que lanza una excepción (o lista vacía)
    when(regionService.listarCiudadesProvinciasPorIdRegion(99))
        .thenThrow(new RuntimeException("Error o región sin registros"));

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/ciudad-provincia/listar/id/99"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void listarCiudadesProvinciasPorNombreRegion_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    List<CiudadProvincia> lista = new ArrayList<>();
    lista.add(ciudadProvinciaEjemplo);
    when(regionService.listarCiudadesProvinciasPorNombreRegion("Metropolitana")).thenReturn(lista);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/ciudad-provincia/listar/nombre/Metropolitana"))
        .andExpect(status().isOk());
    }

    @Test
    void listarCiudadesProvinciasPorNombreRegion_Error_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.listarCiudadesProvinciasPorNombreRegion("Inexistente"))
        .thenThrow(new RuntimeException("Error"));

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/ciudad-provincia/listar/nombre/Inexistente"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void buscarCiudadProvinciaPorId_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    when(regionService.buscarCiudadProvinciaPorId(1)).thenReturn(ciudadProvinciaEjemplo);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/buscar/ciudad-provincia/id/1"))
        .andExpect(status().isOk());
    }

    @Test
    void buscarCiudadProvinciaPorId_NoEncontrado_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.buscarCiudadProvinciaPorId(99))
        .thenThrow(new RuntimeException("No encontrada"));

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/buscar/ciudad-provincia/id/99"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void buscarCiudadProvinciaPorNombre_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    when(regionService.buscarCiudadProvinciaPorNombre("Santiago")).thenReturn(ciudadProvinciaEjemplo);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/buscar/ciudad-provincia/nombre/Santiago"))
        .andExpect(status().isOk());
    }

    @Test
    void buscarCiudadProvinciaPorNombre_NoEncontrado_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.buscarCiudadProvinciaPorNombre("Muy Lejana"))
        .thenThrow(new RuntimeException("No encontrada"));

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/buscar/ciudad-provincia/nombre/Muy Lejana"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void guardarCiudadProvincia_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    when(regionService.guardarCiudadProvincia(any(CiudadProvincia.class))).thenReturn(ciudadProvinciaEjemplo);

    // ACT + ASSERT
    mock.perform(post("/api/v1/direccion/guardar/ciudad-provincia")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"idCiudadProvincia\":1,\"nombreCiudadProvincia\":\"Santiago\"}"))
        .andExpect(status().isOk());
    }

    @Test
    void guardarCiudadProvincia_Error_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.guardarCiudadProvincia(any(CiudadProvincia.class)))
        .thenThrow(new RuntimeException("Error al guardar"));

    // ACT + ASSERT
    mock.perform(post("/api/v1/direccion/guardar/ciudad-provincia")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"idCiudadProvincia\":1,\"nombreCiudadProvincia\":\"Santiago\"}"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void actualizarCiudadProvinciaPorId_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    when(regionService.actualizarCiudadProvinciaPorId(eq(1), any(CiudadProvinciaUpdateDTO.class)))
        .thenReturn(ciudadProvinciaEjemplo);

    // ACT + ASSERT
    mock.perform(patch("/api/v1/direccion/actualizar/ciudad-provincia/id/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nombreCiudadProvincia\":\"Santiago Actualizado\"}"))
        .andExpect(status().isOk());
    }

    @Test
    void actualizarCiudadProvinciaPorId_NoEncontrado_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.actualizarCiudadProvinciaPorId(eq(99), any(CiudadProvinciaUpdateDTO.class)))
        .thenThrow(new RuntimeException("No encontrado"));

    // ACT + ASSERT
    mock.perform(patch("/api/v1/direccion/actualizar/ciudad-provincia/id/99")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nombreCiudadProvincia\":\"Santiago Actualizado\"}"))
        .andExpect(status().isBadRequest());
    }



    
    // @Test //PARA OCUPAR ESTE METODO NECESITAMOS IMPORTAR LO QUE QUEDÓ COMENTADO ARRIBA
    // void eliminarCiudadProvinciaPorId_Exitoso_Retorna200() throws Exception {
    // // ARRANGE: Como eliminarCiudadPorId es void, Mockito por defecto no hace nada.
    // // No necesitamos configurar un 'when(...).thenReturn(...)' aquí.
    // // ACT + ASSERT: Solo verificamos que al llamarlo responda 200 OK
    // mock.perform(delete("/api/v1/direccion/eliminar/ciudad-provincia/id/1"))
    //     .andExpect(status().isOk());
    // }

    // @Test
    // void eliminarCiudadProvinciaPorId_NoEncontrado_Retorna400() throws Exception {
    // // ARRANGE: Al ser un método 'void', usamos doThrow para obligarlo a fallar
    // doThrow(new RuntimeException("Ciudad/Provincia no encontrada"))
    //     .when(regionService).eliminarCiudadPorId(99);
    // // ACT + ASSERT: Verificamos que capture el error y responda 400 Bad Request
    // mock.perform(delete("/api/v1/direccion/eliminar/ciudad-provincia/id/99"))
    //     .andExpect(status().isBadRequest());
    // }

//****************************************************************************************************** */
//TEST COMUNA

    @Test
    void listarComunas_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    List<Comuna> lista = new ArrayList<>();
    lista.add(comunaEjemplo);
    when(regionService.listarComunas()).thenReturn(lista);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/comunas"))
        .andExpect(status().isOk());
    }

    @Test
    void listarComunas_Vacio_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.listarComunas()).thenReturn(new ArrayList<>());

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/comunas"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void listarComunasPorIdCiudadProvincia_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    List<Comuna> lista = new ArrayList<>();
    lista.add(comunaEjemplo);
    when(regionService.listarComunasPorIdCiudadProvin(1)).thenReturn(lista);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/comuna/listar/id/1"))
        .andExpect(status().isOk());
    }

    @Test
    void listarComunasPorIdCiudadProvincia_VacioOError_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.listarComunasPorIdCiudadProvin(99))
        .thenThrow(new RuntimeException("Error o vació"));

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/comuna/listar/id/99"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void listarComunasPorNombreCiudadProvincia_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    List<Comuna> lista = new ArrayList<>();
    lista.add(comunaEjemplo);
    when(regionService.listarComunasPorNombreCiudadProvin("Santiago")).thenReturn(lista);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/comuna/listar/nombre/Santiago"))
        .andExpect(status().isOk());
    }

    @Test
    void listarComunasPorNombreCiudadProvincia_Error_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.listarComunasPorNombreCiudadProvin("Muy Lejana"))
        .thenThrow(new RuntimeException("Error"));

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/comuna/listar/nombre/Muy Lejana"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void buscarComunaPorId_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    when(regionService.buscarComunaPorId(1)).thenReturn(comunaEjemplo);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/buscar/comuna/id/1"))
        .andExpect(status().isOk());
    }

    @Test
    void buscarComunaPorId_NoEncontrado_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.buscarComunaPorId(99))
        .thenThrow(new RuntimeException("No encontrada"));

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/buscar/comuna/id/99"))
        .andExpect(status().isBadRequest());
    }




    @Test
    void buscarComunaPorNombre_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    when(regionService.buscarComunaPorNombre("Providencia")).thenReturn(comunaEjemplo);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/buscar/comuna/nombre/Providencia"))
        .andExpect(status().isOk());
    }

    @Test
    void buscarComunaPorNombre_NoEncontrado_Retorna400() throws Exception {
    // ARRANGE - Ojo: Llamamos al método correcto de comuna
    when(regionService.buscarComunaPorNombre("Inexistente"))
        .thenThrow(new RuntimeException("No encontrada"));

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/buscar/comuna/nombre/Inexistente"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void guardarComuna_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    when(regionService.guardarComuna(any(Comuna.class))).thenReturn(comunaEjemplo);

    // ACT + ASSERT
    mock.perform(post("/api/v1/direccion/guardar/comuna")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"idComuna\":1,\"nombreComuna\":\"Providencia\"}"))
        .andExpect(status().isOk());
    }

    @Test
    void guardarComuna_Error_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.guardarComuna(any(Comuna.class)))
        .thenThrow(new RuntimeException("Error"));

    // ACT + ASSERT
    mock.perform(post("/api/v1/direccion/guardar/comuna")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"idComuna\":1,\"nombreComuna\":\"Providencia\"}"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void actualizarComunaPorId_Exitoso_Retorna200() throws Exception {
    // ARRANGE - Retorna la entidad base comunaEjemplo
    when(regionService.actualizarComunaPorId(eq(1), any(ComunaUpdateDTO.class)))
        .thenReturn(comunaEjemplo);

    // ACT + ASSERT
    mock.perform(patch("/api/v1/direccion/actualizar/comuna/id/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nombreComuna\":\"Providencia Actualizada\"}"))
        .andExpect(status().isOk());
    }

    @Test
    void actualizarComunaPorId_NoEncontrado_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.actualizarComunaPorId(eq(99), any(ComunaUpdateDTO.class)))
        .thenThrow(new RuntimeException("No encontrado"));

    // ACT + ASSERT
    mock.perform(patch("/api/v1/direccion/actualizar/comuna/id/99")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nombreComuna\":\"Providencia Actualizada\"}"))
        .andExpect(status().isBadRequest());
    }


//******************************************************************************************************************/
//TEST CALLE

    @Test
    void listarCalles_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    List<Calle> lista = new ArrayList<>();
    lista.add(calleEjemplo);
    when(regionService.listarCalles()).thenReturn(lista);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/calles"))
        .andExpect(status().isOk());
    }

    @Test
    void listarCalles_Vacio_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.listarCalles()).thenReturn(new ArrayList<>());

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/calles"))
        .andExpect(status().isBadRequest());
    }    



    @Test
    void buscarCallePorId_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    when(regionService.buscarCallePorId(1)).thenReturn(calleEjemplo);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/buscar/calle/id/1"))
        .andExpect(status().isOk());
    }

    @Test
    void buscarCallePorId_NoEncontrado_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.buscarCallePorId(99))
        .thenThrow(new RuntimeException("Calle no encontrada"));

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/buscar/calle/id/99"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void listarCallesPorComuna_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    List<Calle> lista = new ArrayList<>();
    lista.add(calleEjemplo);
    when(regionService.listarCallesPorComuna(1)).thenReturn(lista);

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/calle/listar/comuna/1"))
        .andExpect(status().isOk());
    }

    @Test
    void listarCallesPorComuna_VacioOError_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.listarCallesPorComuna(99))
        .thenThrow(new RuntimeException("Comuna vacía o inexistente"));

    // ACT + ASSERT
    mock.perform(get("/api/v1/direccion/calle/listar/comuna/99"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void guardarCalle_Exitoso_Retorna200() throws Exception {
    // ARRANGE
    when(regionService.guardarCalle(any(Calle.class))).thenReturn(calleEjemplo);

    // ACT + ASSERT
    mock.perform(post("/api/v1/direccion/guardar/calle")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"idCalle\":1,\"nombreCalle\":\"Avenida Libertador Bernardo O'Higgins\"}"))
        .andExpect(status().isOk());
    }

    @Test
    void guardarCalle_Error_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.guardarCalle(any(Calle.class)))
        .thenThrow(new RuntimeException("Error al guardar calle"));

    // ACT + ASSERT
    mock.perform(post("/api/v1/direccion/guardar/calle")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"idCalle\":1,\"nombreCalle\":\"Avenida Libertador Bernardo O'Higgins\"}"))
        .andExpect(status().isBadRequest());
    }



    @Test
    void actualizarNombreCalle_Exitoso_Retorna200() throws Exception {
    // ARRANGE - Recuerda poner el método exacto del service: actualizarNombreCalle
    when(regionService.actualizarNombreCalle(eq(1), any(CalleUpdateDTO.class)))
        .thenReturn(calleEjemplo);

    // ACT + ASSERT
    mock.perform(patch("/api/v1/direccion/actualizar/calle/id/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nombreCalle\":\"Avenida Alameda Actualizada\"}"))
        .andExpect(status().isOk());
    }

    @Test
    void actualizarNombreCalle_NoEncontrado_Retorna400() throws Exception {
    // ARRANGE
    when(regionService.actualizarNombreCalle(eq(99), any(CalleUpdateDTO.class)))
        .thenThrow(new RuntimeException("Calle no encontrada para actualizar"));

    // ACT + ASSERT
    mock.perform(patch("/api/v1/direccion/actualizar/calle/id/99")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nombreCalle\":\"Avenida Alameda Actualizada\"}"))
        .andExpect(status().isBadRequest());
    }





}
