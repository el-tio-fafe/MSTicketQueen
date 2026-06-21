package cl.duoc.msGestionArtistica.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

import cl.duoc.msGestionArtistica.model.Productoras;
import cl.duoc.msGestionArtistica.service.ProductorasService;

@WebMvcTest(ProductorasController.class)
public class ProductorasControllerTest {

    @Autowired
    private MockMvc mock;//simula llamadas http

    @MockitoBean
    private ProductorasService service;

    private Productoras ejemplo;

    @BeforeEach
    void setUp() {
        ejemplo = new Productoras();
        ejemplo.setIdProd(1);
        ejemplo.setRutProd("55555555-5");
        ejemplo.setNombreProd("Bizarro Producciones");
        ejemplo.setCorreoProd("bizarro@gmail.com");
        ejemplo.setTelefonoProd("+56955555555");
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getAllProductoras() throws Exception {
        //Arrange
        when(service.getAllProductoras()).thenReturn(List.of(ejemplo));

        //Act + Assert
        mock.perform(get("/api/v1/productoras/ver_productoras"))
            .andExpect(status().isOk());
    }

    @Test
    void getAllProductoras_vacio() throws Exception {
        //Arrange
        when(service.getAllProductoras()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/productoras/ver_productoras"))
            .andExpect(status().isNoContent());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getProductoraById_encontrado() throws Exception {
        //Arrange
        when(service.getProductoraById(1)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/productoras/ver_productoras/find_id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void getProductoraById_no_encontrado() throws Exception {
        //Arrange
        when(service.getProductoraById(99)).thenThrow(new RuntimeException("Productora con id: 99 no encontrada."));

        //Act + Assert
        mock.perform(get("/api/v1/productoras/ver_productoras/find_id/99"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getProductoraByNombre_encontrado() throws Exception {
        //Arrange
        when(service.getProductoraByNombre("Bizarro Producciones")).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/productoras/ver_productoras/find_nombre/Bizarro Producciones"))
            .andExpect(status().isOk());
    }

    @Test
    void getProductoraByNombre_no_encontrado() throws Exception {
        //Arrange
        when(service.getProductoraByNombre("No Existe S.A.")).thenThrow(new RuntimeException("Productora con nombre: No Existe S.A. no encontrada."));

        //Act + Assert
        mock.perform(get("/api/v1/productoras/ver_productoras/find_nombre/No Existe S.A."))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void getProductoraByRut_encontrado() throws Exception {
        //Arrange
        when(service.getProductoraByRut("55555555-5")).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(get("/api/v1/productoras/ver_productoras/find_rut/55555555-5"))
            .andExpect(status().isOk());
    }

    @Test
    void getProductoraByRut_no_encontrado() throws Exception {
        //Arrange
        when(service.getProductoraByRut("99999999-9")).thenThrow(new RuntimeException("Productora con rut: 99999999-9 no encontrada."));

        //Act + Assert
        mock.perform(get("/api/v1/productoras/ver_productoras/find_rut/99999999-9"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void saveProductora_creada() throws Exception {
        //Arrange
        when(service.saveProductora(ejemplo)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(post("/api/v1/productoras/crear_productora")
            .contentType("application/json")
            .content("{\"idProd\":1,\"rutProd\":\"55555555-5\",\"nombreProd\":\"Bizarro Producciones\",\"correoProd\":\"bizarro@gmail.com\",\"telefonoProd\":\"+56955555555\"}"))
            .andExpect(status().isCreated());
    }

    @Test
    void saveProductora_con_mismo_nombre() throws Exception {
        //Arrange
        when(service.saveProductora(any(Productoras.class)))
            .thenThrow(new RuntimeException("Ya existe una productora con el nombre: Bizarro Producciones"));

        //Act + Assert
        mock.perform(post("/api/v1/productoras/crear_productora")
            .contentType("application/json")
            .content("{\"idProd\":1,\"rutProd\":\"55555555-5\",\"nombreProd\":\"Bizarro Producciones\",\"correoProd\":\"bizarro@gmail.com\",\"telefonoProd\":\"+56955555555\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void saveProductora_con_mismo_rut() throws Exception {
        //Arrange
        when(service.saveProductora(any(Productoras.class)))
            .thenThrow(new RuntimeException("Ya existe una productora con el rut: 55555555-5"));

        //Act + Assert
        mock.perform(post("/api/v1/productoras/crear_productora")
            .contentType("application/json")
            .content("{\"idProd\":1,\"rutProd\":\"55555555-5\",\"nombreProd\":\"Bizarro Producciones\",\"correoProd\":\"bizarro@gmail.com\",\"telefonoProd\":\"+56955555555\"}"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void updateProductora_existente() throws Exception {
        //Arrange
        when(service.updateProductora(1, ejemplo)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(put("/api/v1/productoras/actualizar_productora/1")
            .contentType("application/json")
            .content("{\"idProd\":1,\"rutProd\":\"55555555-5\",\"nombreProd\":\"Bizarro Producciones\",\"correoProd\":\"bizarro@gmail.com\",\"telefonoProd\":\"+56955555555\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void updateProductora_no_encontrada() throws Exception {
        //Arrange
        when(service.updateProductora(eq(99), any(Productoras.class)))
            .thenThrow(new RuntimeException("Productora con id: 99 no encontrada. No se puede actualizar."));

        //Act + Assert
        mock.perform(put("/api/v1/productoras/actualizar_productora/99")
            .contentType("application/json")
            .content("{\"idProd\":1,\"rutProd\":\"55555555-5\",\"nombreProd\":\"Bizarro Producciones\",\"correoProd\":\"bizarro@gmail.com\",\"telefonoProd\":\"+56955555555\"}"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void deleteProductora_existente() throws Exception {
        //Arrange
        //metodo void, no hace falta "when"

        //Act + Assert
        mock.perform(delete("/api/v1/productoras/eliminar_productora/1"))
            .andExpect(status().isOk());
    }

    @Test
    void deleteProductora_no_encontrada() throws Exception {
        //Arrange
        doThrow(new RuntimeException("Productora con id: 99 no encontrada. No se puede eliminar."))
            .when(service).deleteProductora(99);

        //Act + Assert
        mock.perform(delete("/api/v1/productoras/eliminar_productora/99"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void asignarManager_exitoso() throws Exception {
        //Arrange
        when(service.asignarManager(1, 1)).thenReturn(ejemplo);

        //Act + Assert
        mock.perform(patch("/api/v1/productoras/asignar_manager/1/1"))
            .andExpect(status().isOk());
    }

    @Test
    void asignarManager_productora_no_encontrada() throws Exception {
        //Arrange
        when(service.asignarManager(99, 1))
            .thenThrow(new RuntimeException("Productora con id: 99 no encontrada. No se puede asignar manager."));

        //Act + Assert
        mock.perform(patch("/api/v1/productoras/asignar_manager/99/1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void asignarManager_manager_no_encontrado() throws Exception {
        //Arrange
        when(service.asignarManager(1, 99))
            .thenThrow(new RuntimeException("Manager con id: 99 no encontrado. No se puede asignar a la productora."));

        //Act + Assert
        mock.perform(patch("/api/v1/productoras/asignar_manager/1/99"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void asignarManager_ya_asignado() throws Exception {
        //Arrange
        when(service.asignarManager(1, 1))
            .thenThrow(new RuntimeException("El manager con id: 1 ya está asignado a la productora con id: 1."));

        //Act + Assert
        mock.perform(patch("/api/v1/productoras/asignar_manager/1/1"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}