package cl.duoc.msUbicacion.controller;

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

import cl.duoc.msUbicacion.dto.UbicacionDTO;
import cl.duoc.msUbicacion.model.Ubicacion;
import cl.duoc.msUbicacion.service.UbicacionService;

@WebMvcTest(UbicacionController.class)
public class UbicacionControllerTest {

    @Autowired
    private MockMvc mock;//simula llamadas http

    @MockitoBean
    private UbicacionService service;

    private Ubicacion ejemplo;

    @BeforeEach
    void setUp() {
        ejemplo = new Ubicacion();
        ejemplo.setIdUbi(1);
        ejemplo.setNombreUbi("Cancha Central");
        ejemplo.setPrecioUbi(50.0);
        ejemplo.setCapacidadUbi(100);
        ejemplo.setStockDisponibleUbi(50);
        ejemplo.setTieneAsiento(true);
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getUbicaciones_conContenido() throws Exception {
        //Arrange
        when(service.getAllUbicaciones()).thenReturn(List.of(ejemplo));

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/ver_ubicaciones"))
            .andExpect(status().isOk());
    }

    @Test
    void getUbicaciones_vacio() throws Exception {
        //Arrange
        when(service.getAllUbicaciones()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/ver_ubicaciones"))
            .andExpect(status().isNoContent());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getUbicacionById_encontrada() throws Exception {
        //Arrange
        when(service.getUbicacionById(1)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/ver_ubicaciones/find_id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void getUbicacionById_no_encontrada() throws Exception {
        //Arrange
        when(service.getUbicacionById(99)).thenThrow(new RuntimeException("ubicación con id: 99 no encontrada"));

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/ver_ubicaciones/find_id/99"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getUbicacionByNombre_encontrada() throws Exception {
        //Arrange
        when(service.getUbicacionByNombre("Cancha Central")).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/ver_ubicaciones/find_nombre/Cancha Central"))
            .andExpect(status().isOk());
    }

    @Test
    void getUbicacionByNombre_no_encontrada() throws Exception {
        //Arrange
        when(service.getUbicacionByNombre("No Existe")).thenThrow(new RuntimeException("ubicación con nombre: No Existe no encontrada"));

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/ver_ubicaciones/find_nombre/No Existe"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void tieneAsiento_true() throws Exception {
        //Arrange
        when(service.tieneAsiento(1)).thenReturn(true);

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/ver_ubicaciones/1/asiento"))
            .andExpect(status().isOk());
    }

    @Test
    void tieneAsiento_false() throws Exception {
        //Arrange
        when(service.tieneAsiento(1)).thenReturn(false);

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/ver_ubicaciones/1/asiento"))
            .andExpect(status().isOk());
    }

    @Test
    void tieneAsiento_no_encontrada() throws Exception {
        //Arrange
        when(service.tieneAsiento(99)).thenThrow(new RuntimeException("ubicación con id: 99 no encontrada"));

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/ver_ubicaciones/99/asiento"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getUbicacionesDisponibles_conContenido() throws Exception {
        //Arrange
        when(service.getUbicacionesDisponibles()).thenReturn(List.of(ejemplo));

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/ver_ubicaciones/disponibles"))
            .andExpect(status().isOk());
    }

    @Test
    void getUbicacionesDisponibles_vacio() throws Exception {
        //Arrange
        when(service.getUbicacionesDisponibles()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/ver_ubicaciones/disponibles"))
            .andExpect(status().isNoContent());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void saveUbicacion_exitosa() throws Exception {
        //Arrange
        when(service.saveUbicacion(ejemplo)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(post("/api/v1/ubicaciones/crear_ubicacion")
            .contentType("application/json")
            .content("{\"idUbi\":1,\"nombreUbi\":\"Cancha Central\",\"precioUbi\":50.0,\"capacidadUbi\":100,\"stockDisponibleUbi\":50,\"tieneAsiento\":true}"))
            .andExpect(status().isCreated());
    }

    @Test
    void saveUbicacion_ya_existe() throws Exception {
        //Arrange
        when(service.saveUbicacion(ejemplo)).thenThrow(new RuntimeException("Ya existe una ubicación con ese nombre"));

        //Act + Assert
        mock.perform(post("/api/v1/ubicaciones/crear_ubicacion")
            .contentType("application/json")
            .content("{\"idUbi\":1,\"nombreUbi\":\"Cancha Central\",\"precioUbi\":50.0,\"capacidadUbi\":100,\"stockDisponibleUbi\":50,\"tieneAsiento\":true}"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void updateUbicacion_existente() throws Exception {
        //Arrange
        when(service.updateUbicacion(1, ejemplo)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(put("/api/v1/ubicaciones/actualizar_ubicacion/1")
            .contentType("application/json")
            .content("{\"idUbi\":1,\"nombreUbi\":\"Cancha Central\",\"precioUbi\":50.0,\"capacidadUbi\":100,\"stockDisponibleUbi\":50,\"tieneAsiento\":true}"))
            .andExpect(status().isOk());
    }

    @Test
    void updateUbicacion_no_encontrada() throws Exception {
        //Arrange
        when(service.updateUbicacion(99, ejemplo)).thenThrow(new RuntimeException("Ubicación con id: 99 no encontrada"));

        //Act + Assert
        mock.perform(put("/api/v1/ubicaciones/actualizar_ubicacion/99")
            .contentType("application/json")
            .content("{\"idUbi\":1,\"nombreUbi\":\"Cancha Central\",\"precioUbi\":50.0,\"capacidadUbi\":100,\"stockDisponibleUbi\":50,\"tieneAsiento\":true}"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void deleteUbicacion_existente() throws Exception {
        //Arrange
        //metodo void, no hace falta "when"

        //Act + Assert
        mock.perform(delete("/api/v1/ubicaciones/eliminar_ubicacion/1"))
            .andExpect(status().isOk());
    }

    @Test
    void deleteUbicacion_no_encontrada() throws Exception {
        //Arrange
        doThrow(new RuntimeException("Ubicación con id: 99 no encontrada. No se puede eliminar.")).when(service).deleteUbicacion(99);

        //Act + Assert
        mock.perform(delete("/api/v1/ubicaciones/eliminar_ubicacion/99"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    // COMUNICACION CON OTROS MS
    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarDTO_encontrada() throws Exception {
        //Arrange
        UbicacionDTO dto = new UbicacionDTO(1, "Cancha Central", 50.0, 50, true);
        when(service.getUbicacionDTOById(1)).thenReturn(dto);

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/dto/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarDTO_no_encontrada() throws Exception {
        //Arrange
        when(service.getUbicacionDTOById(99)).thenThrow(new RuntimeException("ubicación con id: 99 no encontrada"));

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/dto/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getUbicacionPorNombre_encontrada() throws Exception {
        //Arrange
        UbicacionDTO dto = new UbicacionDTO(1, "Cancha Central", 50.0, 50, true);
        when(service.getUbicacionDTOPorNombre("Cancha Central")).thenReturn(dto);

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/nombre/Cancha Central"))
            .andExpect(status().isOk());
    }

    @Test
    void getUbicacionPorNombre_no_encontrada() throws Exception {
        //Arrange
        when(service.getUbicacionDTOPorNombre("No Existe")).thenThrow(new RuntimeException("Ubicacion con nombre: No Existe no encontrada"));

        //Act + Assert
        mock.perform(get("/api/v1/ubicaciones/nombre/No Existe"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void reducirStock_exitoso() throws Exception {
        //Arrange
        when(service.reducirStock(1)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(patch("/api/v1/ubicaciones/reducir-stock/1"))
            .andExpect(status().isOk());
    }

    @Test
    void reducirStock_sin_stock() throws Exception {
        //Arrange
        when(service.reducirStock(1)).thenThrow(new RuntimeException("No hay stock disponible para la ubicación: Cancha Central"));

        //Act + Assert
        mock.perform(patch("/api/v1/ubicaciones/reducir-stock/1"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}