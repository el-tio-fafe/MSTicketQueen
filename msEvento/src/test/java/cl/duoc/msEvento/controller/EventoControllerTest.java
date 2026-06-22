package cl.duoc.msEvento.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.msEvento.model.Evento;
import cl.duoc.msEvento.model.Recinto;
import cl.duoc.msEvento.model.TipoEvento;
import cl.duoc.msEvento.service.EventoService;

@WebMvcTest(EventoController.class)
public class EventoControllerTest {

    @Autowired
    private MockMvc mock;

    @MockitoBean
    private EventoService eventoService;

    private Evento eventoEjemplo;
    private TipoEvento tipoEventoEjemplo;
    private Recinto recintoEjemplo;

    @BeforeEach
    void setUp() {
        eventoEjemplo = new Evento();
        eventoEjemplo.setIdEvento(1);
        eventoEjemplo.setCodigoEvento("EVT-2026-001");
        eventoEjemplo.setEstadoEvento("PENDIENTE");
        eventoEjemplo.setIdProd(10);

        tipoEventoEjemplo = new TipoEvento();
        tipoEventoEjemplo.setIdTipoEvento(1);
        tipoEventoEjemplo.setDescripcion("Concierto");

        recintoEjemplo = new Recinto();
        recintoEjemplo.setIdRecinto(1);
        recintoEjemplo.setRutRecinto("77666555-4");
        recintoEjemplo.setNombreRecinto("Movistar Arena");
        recintoEjemplo.setCapacidadRecinto(15000);
    }

//-----------------------------------------------------------------------------------------------------------------------------
//LISTAR EVENTOS

    @Test
    void listarEventos_exitoso() throws Exception {
        //Arrange
        when(eventoService.listarEventos()).thenReturn(List.of(eventoEjemplo));

        //Act + Assert
        mock.perform(get("/api/v1/eventos/listartodos"))
            .andExpect(status().isOk());
    }

    @Test
    void listarEventos_vacio() throws Exception {
        //Arrange
        when(eventoService.listarEventos()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/eventos/listartodos"))
            .andExpect(status().isNoContent());
    }
    
//-----------------------------------------------------------------------------------------------------------------------------
//BUSCAR EVENTOS

    @Test
    void buscarEventoPorId_encontrado() throws Exception {
        //Arrange
        when(eventoService.buscarEventoPorId(1)).thenReturn(eventoEjemplo);

        //Act + Assert
        mock.perform(get("/api/v1/eventos/buscar/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarEventoPorId_noEncontrado() throws Exception {
        //Arrange
        when(eventoService.buscarEventoPorId(99))
            .thenThrow(new RuntimeException("Evento con id: 99 no encontrado"));

        //Act + Assert
        mock.perform(get("/api/v1/eventos/buscar/id/99"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Evento con id: 99 no encontrado"));
    }
    
//-----------------------------------------------------------------------------------------------------------------------------
//LISTAR EVENTOS POR ESTADO

    @Test
    void listarEventosPorEstado_exitoso() throws Exception {
        //Arrange
        when(eventoService.listarEventosPorEstado("PENDIENTE")).thenReturn(List.of(eventoEjemplo));

        //Act + Assert
        mock.perform(get("/api/v1/eventos/listar/estado/PENDIENTE"))
            .andExpect(status().isOk());
    }

    @Test
    void listarEventosPorEstado_vacio() throws Exception {
        //Arrange
        when(eventoService.listarEventosPorEstado("PENDIENTE")).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/eventos/listar/estado/PENDIENTE"))
            .andExpect(status().isNoContent());
    }
    
    @Test
    void listarEventosPorProductora_exitoso() throws Exception {
        //Arrange
        when(eventoService.listarEventosPorProductora(10)).thenReturn(List.of(eventoEjemplo));

        //Act + Assert
        mock.perform(get("/api/v1/eventos/listar-eventos/por-productora/id/10"))
            .andExpect(status().isOk());
    }

    @Test
    void listarEventosPorProductora_vacio() throws Exception {
        //Arrange
        when(eventoService.listarEventosPorProductora(10)).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/eventos/listar-eventos/por-productora/id/10"))
            .andExpect(status().isNoContent());
    }

//-----------------------------------------------------------------------------------------------------------------------------
//CREAR EVENTO

    @Test
    void crearEvento_exitoso() throws Exception {
        //Arrange
        when(eventoService.crearEvento(any(Evento.class))).thenReturn(eventoEjemplo);

        //Act + Assert
        mock.perform(post("/api/v1/eventos/crear")
            .contentType("application/json")
            .content("{\"codigoEvento\":\"EVT-2026-001\",\"idProd\":10}"))
            .andExpect(status().isOk());
    }

    @Test
    void crearEvento_error() throws Exception {
        //Arrange
        when(eventoService.crearEvento(any(Evento.class)))
            .thenThrow(new RuntimeException("Ya existe un evento con código: EVT-2026-001"));

        //Act + Assert
        mock.perform(post("/api/v1/eventos/crear")
            .contentType("application/json")
            .content("{\"codigoEvento\":\"EVT-2026-001\",\"idProd\":10}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Ya existe un evento con código: EVT-2026-001"));
    }


//-----------------------------------------------------------------------------------------------------------------------------
//APROBAR EVENTO

    @Test
    void aprobarEventoPorId_existente() throws Exception {
        //Arrange
        when(eventoService.aprobarEvento(1, 5)).thenReturn(eventoEjemplo);

        //Act + Assert
        mock.perform(patch("/api/v1/eventos/aprobar/id/1")
            .param("idAdministrador", "5"))
            .andExpect(status().isOk());
    }

    @Test
    void aprobarEventoPorId_error() throws Exception {
        //Arrange
        when(eventoService.aprobarEvento(99, 5))
            .thenThrow(new RuntimeException("Evento con id: 99 no encontrado"));

        //Act + Assert
        mock.perform(patch("/api/v1/eventos/aprobar/id/99")
            .param("idAdministrador", "5"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Evento con id: 99 no encontrado"));
    }
    

//-----------------------------------------------------------------------------------------------------------------------------
//RECHAZAR EVENTO   

    @Test
    void rechazarEventoPorId_existente() throws Exception {
        //Arrange
        when(eventoService.rechazarEvento(1, 5)).thenReturn(eventoEjemplo);

        //Act + Assert
        mock.perform(patch("/api/v1/eventos/rechazar/id/1")
            .param("idAdministrador", "5"))
            .andExpect(status().isOk());
    }

    @Test
    void rechazarEventoPorId_error() throws Exception {
        //Arrange
        when(eventoService.rechazarEvento(1, 5))
            .thenThrow(new RuntimeException("Solo se pueden aprobar eventos en estado PENDIENTE"));

        //Act + Assert
        mock.perform(patch("/api/v1/eventos/rechazar/id/1")
            .param("idAdministrador", "5"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Solo se pueden aprobar eventos en estado PENDIENTE"));
    }
   
//-----------------------------------------------------------------------------------------------------------------------------
//ELIMINAR EVENTO

    @Test
    void eliminarEventoPorId_existente() throws Exception {
        //Arrange (Retorna void)

        //Act + Assert
        mock.perform(delete("/api/v1/eventos/delete/id/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Evento id: 1 eliminado con exito"));
    }

    @Test
    void eliminarEventoPorId_noExistente() throws Exception {
        //Arrange
        doThrow(new RuntimeException("Evento con id: 99 no encontrado"))
            .when(eventoService).eliminarEvento(99);

        //Act + Assert
        mock.perform(delete("/api/v1/eventos/delete/id/99"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Evento con id: 99 no encontrado"));
    }


//-----------------------------------------------------------------------------------------------------------------------------
//LISTAR TIPOS DE EVENTOS

    @Test
    void listarTiposEvento_exitoso() throws Exception {
        //Arrange
        when(eventoService.listarTiposEvento()).thenReturn(List.of(tipoEventoEjemplo));

        //Act + Assert
        mock.perform(get("/api/v1/eventos/tiposEvento"))
            .andExpect(status().isOk());
    }

    @Test
    void listarTiposEvento_vacio() throws Exception {
        //Arrange
        when(eventoService.listarTiposEvento()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/eventos/tiposEvento"))
            .andExpect(status().isNoContent());
    }
    

//-----------------------------------------------------------------------------------------------------------------------------
//BUSCAR TIPO DE EVENTO

    @Test
    void buscarTiposEvento_encontrado() throws Exception {
        //Arrange
        when(eventoService.buscarTipoEventoPorId(1)).thenReturn(tipoEventoEjemplo); // Nota: Tu controlador invoca a buscarEventoPorId internamente

        //Act + Assert
        mock.perform(get("/api/v1/eventos/tiposEvento/buscar/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarTiposEvento_noEncontrado() throws Exception {
        //Arrange
        when(eventoService.buscarTipoEventoPorId(99))
            .thenThrow(new RuntimeException("Tipo de Evento con id: 99 no encontrado"));

        //Act + Assert
        mock.perform(get("/api/v1/eventos/tiposEvento/buscar/id/99"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Tipo de Evento con id: 99 no encontrado"));
    }


//-----------------------------------------------------------------------------------------------------------------------------
//GUARDAR TIPO DE EVENTO

    @Test
    void guardarTiposEvento_exitoso() throws Exception {
        //Arrange
        when(eventoService.guardarTipoEvento(any(TipoEvento.class))).thenReturn(tipoEventoEjemplo);

        //Act + Assert
        mock.perform(post("/api/v1/eventos/tiposEvento")
            .contentType("application/json")
            .content("{\"descripcion\":\"Concierto\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void guardarTiposEvento_error() throws Exception {
        //Arrange
        when(eventoService.guardarTipoEvento(any(TipoEvento.class)))
            .thenThrow(new RuntimeException("Ya existe el tipo de evento: Concierto"));

        //Act + Assert
        mock.perform(post("/api/v1/eventos/tiposEvento")
            .contentType("application/json")
            .content("{\"descripcion\":\"Concierto\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Ya existe el tipo de evento: Concierto"));
    }
    

//-----------------------------------------------------------------------------------------------------------------------------
//ACTUALIZAR TIPO DE EVENTO

    @Test
    void actualizarTiposEvento_existente() throws Exception {
        //Arrange
        when(eventoService.actualizarTipoEvento(eq(1), any(TipoEvento.class))).thenReturn(tipoEventoEjemplo);

        //Act + Assert
        mock.perform(patch("/api/v1/eventos/tipoEvento/id/1")
            .contentType("application/json")
            .content("{\"descripcion\":\"Festival\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void actualizarTiposEvento_noEncontrado() throws Exception {
        //Arrange
        when(eventoService.actualizarTipoEvento(eq(99), any(TipoEvento.class)))
            .thenThrow(new RuntimeException("Tipo de Evento con id: 99No encontrado"));

        //Act + Assert
        mock.perform(patch("/api/v1/eventos/tipoEvento/id/99")
            .contentType("application/json")
            .content("{\"descripcion\":\"Festival\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Tipo de Evento con id: 99No encontrado"));
    }


//-----------------------------------------------------------------------------------------------------------------------------
//ELIMINAR TIPO DE EVENTO

    @Test
    void eliminarTiposEvento_existente() throws Exception {
        //Arrange (Nota: Tu controlador llama a eventoService.eliminarEvento() en vez de eliminarTipoEvento())

        //Act + Assert
        mock.perform(delete("/api/v1/eventos/tipoEvento/id/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Tipo de Evento con id: 1 eliminado con éxito"));
    }

    @Test
    void eliminarTiposEvento_noExistente() throws Exception {
        //Arrange
        doThrow(new RuntimeException("Tipo de Evento con id: 99No encontrado"))
            .when(eventoService).eliminarEvento(99);

        //Act + Assert
        mock.perform(delete("/api/v1/eventos/tipoEvento/id/99"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Tipo de Evento con id: 99No encontrado"));
    }
 

//-----------------------------------------------------------------------------------------------------------------------------
//LISTAR RECINTOS

    @Test
    void listarRecintos_exitoso() throws Exception {
        //Arrange
        when(eventoService.listarRecintos()).thenReturn(List.of(recintoEjemplo));

        //Act + Assert
        mock.perform(get("/api/v1/eventos/recinto/listar"))
            .andExpect(status().isOk());
    }

    @Test
    void listarRecintos_vacio() throws Exception {
        //Arrange
        when(eventoService.listarRecintos()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/eventos/recinto/listar"))
            .andExpect(status().isNoContent());
    }
 

//-----------------------------------------------------------------------------------------------------------------------------
//BUSCAR RECINTO

    @Test
    void buscarRecintoPorId_encontrado() throws Exception {
        //Arrange
        when(eventoService.buscarRecintoPorId(1)).thenReturn(recintoEjemplo);

        //Act + Assert
        mock.perform(get("/api/v1/eventos/recinto/buscar/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarRecintoPorId_noEncontrado() throws Exception {
        //Arrange
        when(eventoService.buscarRecintoPorId(99))
            .thenThrow(new RuntimeException("Recinto con id: 99 no encontrado"));

        //Act + Assert
        mock.perform(get("/api/v1/eventos/recinto/buscar/id/99"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Recinto con id: 99 no encontrado"));
    }
   
   
    @Test
    void buscarRecintoPorNombre_encontrado() throws Exception {
        //Arrange
        when(eventoService.buscarRecintoPorNombre("Movistar Arena")).thenReturn(recintoEjemplo);

        //Act + Assert
        mock.perform(get("/api/v1/eventos/recinto/buscar/nombre/Movistar Arena"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarRecintoPorNombre_noEncontrado() throws Exception {
        //Arrange
        when(eventoService.buscarRecintoPorNombre("Estadio Inexistente"))
            .thenThrow(new RuntimeException("Recinto con nombre: Estadio Inexistente no encontrado"));

        //Act + Assert
        mock.perform(get("/api/v1/eventos/recinto/buscar/nombre/Estadio Inexistente"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Recinto con nombre: Estadio Inexistente no encontrado"));
    }
  

//-----------------------------------------------------------------------------------------------------------------------------
//GUARDAR RECINTO

    @Test
    void guardarRecinto_exitoso() throws Exception {
        //Arrange
        when(eventoService.guardarRecinto(any(Recinto.class))).thenReturn(recintoEjemplo);

        //Act + Assert
        mock.perform(post("/api/v1/eventos/recinto/guardar")
            .contentType("application/json")
            .content("{\"rutRecinto\":\"77666555-4\",\"nombreRecinto\":\"Movistar Arena\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void guardarRecinto_errorDuplicado() throws Exception {
        //Arrange
        when(eventoService.guardarRecinto(any(Recinto.class)))
            .thenThrow(new RuntimeException("Ya existe un recinto con rut: 77666555-4"));

        //Act + Assert
        mock.perform(post("/api/v1/eventos/recinto/guardar")
            .contentType("application/json")
            .content("{\"rutRecinto\":\"77666555-4\",\"nombreRecinto\":\"Movistar Arena\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Ya existe un recinto con rut: 77666555-4"));
    }
    

//-----------------------------------------------------------------------------------------------------------------------------
//ACTUALIZAR RECINTO

    @Test
    void actualizarRecinto_existente() throws Exception {
        //Arrange
        when(eventoService.actualizarRecinto(eq(1), any(Recinto.class))).thenReturn(recintoEjemplo);

        //Act + Assert
        mock.perform(patch("/api/v1/eventos/recinto/actualizar/id/1")
            .contentType("application/json")
            .content("{\"nombreRecinto\":\"Movistar Arena Modificado\",\"capacidadRecinto\":16000}"))
            .andExpect(status().isOk());
    }

    @Test
    void actualizarRecinto_noEncontrado() throws Exception {
        //Arrange
        when(eventoService.actualizarRecinto(eq(99), any(Recinto.class)))
            .thenThrow(new RuntimeException("Recinto con id: 99 no encontrado"));

        //Act + Assert
        mock.perform(patch("/api/v1/eventos/recinto/actualizar/id/99")
            .contentType("application/json")
            .content("{\"nombreRecinto\":\"Movistar Arena Modificado\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Recinto con id: 99 no encontrado"));
    }


//-----------------------------------------------------------------------------------------------------------------------------
//ELIMINAR RECINTO

    @Test
    void eliminarRecinto_existente() throws Exception {
        //Arrange (Retorna void)

        //Act + Assert
        mock.perform(delete("/api/v1/eventos/recinto/eliminar/id/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("Recinto con id: 1 eliminado con éxito"));
    }

    @Test
    void eliminarRecinto_noExistente() throws Exception {
        //Arrange
        doThrow(new RuntimeException("Recinto con id: 99 no encontrado"))
            .when(eventoService).eliminarRecinto(99);

        //Act + Assert
        mock.perform(delete("/api/v1/eventos/recinto/eliminar/id/99"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Recinto con id: 99 no encontrado"));
    }
    
}
