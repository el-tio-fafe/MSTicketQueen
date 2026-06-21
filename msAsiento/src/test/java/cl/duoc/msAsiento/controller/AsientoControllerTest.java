package cl.duoc.msAsiento.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import cl.duoc.msAsiento.dto.AsientoDTO;
import cl.duoc.msAsiento.model.Asiento;
import cl.duoc.msAsiento.service.AsientoService;


@WebMvcTest(AsientoController.class)
public class AsientoControllerTest {

    @Autowired
    private MockMvc mock; // Simula las peticiones HTTP

    @MockitoBean
    private AsientoService asientoService; // Service falso utilizando la nueva anotación

    // Utilitario local sin inyección de Spring
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Asiento asientoEjemplo;

    @BeforeEach
    void setUp() {
        asientoEjemplo = new Asiento();
        asientoEjemplo.setIdAsiento(1);
        asientoEjemplo.setNumeroAsiento("A1");
        asientoEjemplo.setEstadoAsiento("DISPONIBLE");
    }

//************************************************************************************ */
//LISTAR ASIENTOS

    @Test
    void listar_Exitoso_Retorna200YLista() throws Exception {
        List<Asiento> lista = List.of(asientoEjemplo);
        when(asientoService.listarAsientos()).thenReturn(lista);
        String jsonEsperado = objectMapper.writeValueAsString(lista);

        mock.perform(get("/api/v1/asientos"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEsperado));
    }

    @Test
    void listar_ListaVacia_Retorna204() throws Exception {
        when(asientoService.listarAsientos()).thenReturn(new ArrayList<>());

        mock.perform(get("/api/v1/asientos"))
                .andExpect(status().isNoContent());
    }


//LISTAR POR ESTADO

    @Test
    void listarPorEstado_Exitoso_Retorna200YLista() throws Exception {
        List<Asiento> lista = List.of(asientoEjemplo);
        when(asientoService.listarPorEstado("DISPONIBLE")).thenReturn(lista);
        String jsonEsperado = objectMapper.writeValueAsString(lista);

        mock.perform(get("/api/v1/asientos/estado/DISPONIBLE"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEsperado));
    }

    @Test
    void listarPorEstado_ListaVacia_Retorna400() throws Exception {
        when(asientoService.listarPorEstado("VENDIDO")).thenReturn(new ArrayList<>());

        mock.perform(get("/api/v1/asientos/estado/VENDIDO"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No hay asientos en estado: VENDIDO"));
    }


//BUSCAR ASIENTO POR ID

    @Test
    void buscarPorId_Exitoso_Retorna200() throws Exception {
        when(asientoService.buscarPorId(1)).thenReturn(asientoEjemplo);
        //String jsonEsperado = objectMapper.writeValueAsString(asientoEjemplo);

        mock.perform(get("/api/v1/asientos/1"))
                .andExpect(status().isOk());
                //.andExpect(content().json(jsonEsperado));
    }

    @Test
    void buscarPorId_ErrorOException_Retorna404() throws Exception {
        when(asientoService.buscarPorId(99)).thenThrow(new RuntimeException("No encontrado"));

        mock.perform(get("/api/v1/asientos/99"))
                .andExpect(status().isNotFound());
    }


//BUSCAR ASIENTO POR NUMERO

    @Test
    void buscarPorNumero_Exitoso_Retorna200() throws Exception {
        when(asientoService.buscarPorNumAsiento("A1")).thenReturn(asientoEjemplo);
        String jsonEsperado = objectMapper.writeValueAsString(asientoEjemplo);

        mock.perform(get("/api/v1/asientos/numero/A1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEsperado));
    }

    @Test
    void buscarPorNumero_ErrorOException_Retorna404() throws Exception {
        when(asientoService.buscarPorNumAsiento("B2")).thenThrow(new RuntimeException("No encontrado"));

        mock.perform(get("/api/v1/asientos/numero/B2"))
                .andExpect(status().isNotFound());
    }


//GUARDAR ASIENTO

    @Test
    void guardar_Exitoso_Retorna200() throws Exception {
        when(asientoService.guardar(any(Asiento.class))).thenReturn(asientoEjemplo);
        String jsonEnviar = objectMapper.writeValueAsString(asientoEjemplo);

        mock.perform(post("/api/v1/asientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEnviar));
    }

    @Test
    void guardar_ErrorServicio_Retorna400() throws Exception {
        when(asientoService.guardar(any(Asiento.class)))
                .thenThrow(new RuntimeException("El número de asiento A1 ya existe"));
        String jsonEnviar = objectMapper.writeValueAsString(asientoEjemplo);

        mock.perform(post("/api/v1/asientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El número de asiento A1 ya existe"));
    }


//OBTENER ASIENTO DTO POR ID

    @Test
    void obtenerAsientoDTO_Exitoso_Retorna200() throws Exception {
        when(asientoService.buscarPorId(1)).thenReturn(asientoEjemplo);
        
        AsientoDTO dtoEsperado = new AsientoDTO(1, "A1", "DISPONIBLE");
        String jsonEsperado = objectMapper.writeValueAsString(dtoEsperado);

        mock.perform(get("/api/v1/asientos/dto/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEsperado));
    }

    @Test
    void obtenerAsientoDTO_NoEncontrado_Retorna404() throws Exception {
    when(asientoService.buscarPorId(99)).thenThrow(new RuntimeException("Asiento id: 99 no encontrado"));

    mock.perform(get("/api/v1/asientos/dto/99"))
            .andExpect(status().isNotFound());
    }


//ACTUALIZAR ASIENTO

    @Test
    void actualizar_Exitoso_Retorna200() throws Exception {
        when(asientoService.actualizar(eq(1), any(Asiento.class))).thenReturn(asientoEjemplo);
        String jsonEnviar = objectMapper.writeValueAsString(asientoEjemplo);

        mock.perform(put("/api/v1/asientos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonEnviar));
                
        verify(asientoService, times(1)).actualizar(eq(1), any(Asiento.class));
    }

    @Test
    void actualizar_ErrorOException_Retorna404() throws Exception {
        when(asientoService.actualizar(eq(99), any(Asiento.class)))
                .thenThrow(new RuntimeException("Asiento no encontrado"));
        String jsonEnviar = objectMapper.writeValueAsString(asientoEjemplo);

        mock.perform(put("/api/v1/asientos/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonEnviar))
                .andExpect(status().isNotFound());

    }


}
