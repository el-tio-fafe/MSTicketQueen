package cl.duoc.MSFacturacion.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.msFacturacion.controller.BancoController;
import cl.duoc.msFacturacion.dto.ComprobanteDTO;
import cl.duoc.msFacturacion.model.Banco;
import cl.duoc.msFacturacion.model.Comprobante;
import cl.duoc.msFacturacion.model.FormaPago;
import cl.duoc.msFacturacion.service.BancoService;

@WebMvcTest(BancoController.class)
public class BancoControllerTest {

    @Autowired
    private MockMvc mock;//simula llamadas http

    @MockitoBean
    private BancoService service;

    private Banco banco;
    private FormaPago formaPago;
    private Comprobante comprobante;

    @BeforeEach
    void setUp() {
        banco = new Banco();
        banco.setIdBanco(1);
        banco.setNombreBanco("Banco de Chile");

        formaPago = new FormaPago();
        formaPago.setIdFormaPago(1);
        formaPago.setMedioDePago("Transferencia");

        comprobante = new Comprobante();
        comprobante.setIdComprobante(1);
        comprobante.setNumeroComprobante("CMP-001");
        comprobante.setFechaEmision(LocalDateTime.of(2026, 6, 1, 10, 0));
        comprobante.setMontoTotal(50000);
        comprobante.setMetodoPago("Transferencia");
        comprobante.setEstadoPago(true);
        comprobante.setFormaPago(formaPago);
        comprobante.setBanco(banco);
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    // BANCO
    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarBancos_conContenido() throws Exception {
        //Arrange
        when(service.listarBancos()).thenReturn(List.of(banco));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/listarBancos"))
            .andExpect(status().isOk());
    }

    @Test
    void listarBancos_vacio() throws Exception {
        //Arrange
        when(service.listarBancos()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/listarBancos"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerBancoPorId_encontrado() throws Exception {
        //Arrange
        when(service.buscarBancoPorId(1)).thenReturn(banco);

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/bancos/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void obtenerBancoPorId_no_encontrado() throws Exception {
        //Arrange
        when(service.buscarBancoPorId(99)).thenThrow(new RuntimeException("Banco con id: 99 no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/bancos/id/99"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerBancoPorNombre_encontrado() throws Exception {
        //Arrange
        when(service.buscarBancoPorNombre("Banco de Chile")).thenReturn(banco);

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/bancos/nombre/Banco de Chile"))
            .andExpect(status().isOk());
    }

    @Test
    void obtenerBancoPorNombre_no_encontrado() throws Exception {
        //Arrange
        when(service.buscarBancoPorNombre("Banco Falso")).thenThrow(new RuntimeException("Banco con nombre: Banco Falso no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/bancos/nombre/Banco Falso"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void crearBanco_exitoso() throws Exception {
        //Arrange
        when(service.guardarBanco(banco)).thenReturn(banco);

        //Act + Assert
        mock.perform(post("/api/v1/facturacion/crearBanco")
            .contentType("application/json")
            .content("{\"idBanco\":1,\"nombreBanco\":\"Banco de Chile\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void crearBanco_ya_existe() throws Exception {
        //Arrange
        when(service.guardarBanco(banco)).thenThrow(new RuntimeException("Ya existe un banco con el nombre: Banco de Chile."));

        //Act + Assert
        mock.perform(post("/api/v1/facturacion/crearBanco")
            .contentType("application/json")
            .content("{\"idBanco\":1,\"nombreBanco\":\"Banco de Chile\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void actualizarBanco_existente() throws Exception {
        //Arrange
        when(service.actualizarBanco(1, banco)).thenReturn(banco);

        //Act + Assert
        mock.perform(put("/api/v1/facturacion/bancos/id/1")
            .contentType("application/json")
            .content("{\"idBanco\":1,\"nombreBanco\":\"Banco de Chile\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void actualizarBanco_no_encontrado() throws Exception {
        //Arrange
        when(service.actualizarBanco(99, banco)).thenThrow(new RuntimeException("Banco con id: 99 no encontrado."));

        //Act + Assert
        mock.perform(put("/api/v1/facturacion/bancos/id/99")
            .contentType("application/json")
            .content("{\"idBanco\":1,\"nombreBanco\":\"Banco de Chile\"}"))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminarBancoPorId_existente() throws Exception {
        //Arrange
        //metodo void, no hace falta "when"

        //Act + Assert
        mock.perform(delete("/api/v1/facturacion/bancos/eliminar/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void eliminarBancoPorId_no_encontrado() throws Exception {
        //Arrange
        doThrow(new RuntimeException("Banco con id: 99 no encontrado.")).when(service).eliminarBancoPorId(99);

        //Act + Assert
        mock.perform(delete("/api/v1/facturacion/bancos/eliminar/id/99"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void eliminarBancoPorNombre_existente() throws Exception {
        //Arrange
        //metodo void, no hace falta "when"

        //Act + Assert
        mock.perform(delete("/api/v1/facturacion/bancos/eliminar/nombre/Banco de Chile"))
            .andExpect(status().isOk());
    }

    @Test
    void eliminarBancoPorNombre_no_encontrado() throws Exception {
        //Arrange
        doThrow(new RuntimeException("Banco con nombre: Banco Falso no encontrado.")).when(service).eliminarBancoPorNombre("Banco Falso");

        //Act + Assert
        mock.perform(delete("/api/v1/facturacion/bancos/eliminar/nombre/Banco Falso"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    // COMPROBANTE
    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarComprobantes_conContenido() throws Exception {
        //Arrange
        when(service.listarTodosComprobantes()).thenReturn(List.of(comprobante));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/comprobantes"))
            .andExpect(status().isOk());
    }

    @Test
    void listarComprobantes_vacio() throws Exception {
        //Arrange
        when(service.listarTodosComprobantes()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/comprobantes"))
            .andExpect(status().isNoContent());
    }

    @Test
    void listarComprobantesPorBanco_conContenido() throws Exception {
        //Arrange
        when(service.listarComprobantesPorBanco(1)).thenReturn(List.of(comprobante));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/comprobantes/banco/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void listarComprobantesPorBanco_vacio() throws Exception {
        //Arrange
        when(service.listarComprobantesPorBanco(1)).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/comprobantes/banco/id/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void listarComprobantesPorBanco_banco_no_encontrado() throws Exception {
        //Arrange
        when(service.listarComprobantesPorBanco(99)).thenThrow(new RuntimeException("Banco con id: 99 no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/comprobantes/banco/id/99"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void buscarComprobantePorNumero_encontrado() throws Exception {
        //Arrange
        when(service.buscarComprobantePorNumero("CMP-001")).thenReturn(comprobante);

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/comprobantes/numero/CMP-001"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarComprobantePorNumero_no_encontrado() throws Exception {
        //Arrange
        when(service.buscarComprobantePorNumero("CMP-999")).thenThrow(new RuntimeException("Comprobante con número: CMP-999 no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/comprobantes/numero/CMP-999"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void filtrarComprobantesPorFecha_conContenido() throws Exception {
        //Arrange
        LocalDateTime fecha = LocalDateTime.of(2026, 6, 1, 10, 0);
        when(service.filtrarComprobantesPorFechaEmision(1, fecha)).thenReturn(List.of(comprobante));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/comprobantes/banco/id/1/fecha")
            .param("fechaEmision", fecha.toString()))
            .andExpect(status().isOk());
    }

    @Test
    void filtrarComprobantesPorFecha_vacio() throws Exception {
        //Arrange
        LocalDateTime fecha = LocalDateTime.of(2026, 6, 1, 10, 0);
        when(service.filtrarComprobantesPorFechaEmision(1, fecha)).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/comprobantes/banco/id/1/fecha")
            .param("fechaEmision", fecha.toString()))
            .andExpect(status().isNoContent());
    }

    @Test
    void filtrarComprobantesPorFecha_banco_no_encontrado() throws Exception {
        //Arrange
        LocalDateTime fecha = LocalDateTime.of(2026, 6, 1, 10, 0);
        when(service.filtrarComprobantesPorFechaEmision(99, fecha)).thenThrow(new RuntimeException("Banco con id: 99 no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/comprobantes/banco/id/99/fecha")
            .param("fechaEmision", fecha.toString()))
            .andExpect(status().isBadRequest());
    }

    @Test
    void generarComprobante_exitoso() throws Exception {
        //Arrange
        when(service.generarComprobante(comprobante)).thenReturn(comprobante);

        //Act + Assert
        mock.perform(post("/api/v1/facturacion/comprobantes")
            .contentType("application/json")
            .content("{\"idComprobante\":1,\"numeroComprobante\":\"CMP-001\",\"fechaEmision\":\"2026-06-01T10:00:00\",\"montoTotal\":50000,\"metodoPago\":\"Transferencia\",\"estadoPago\":true,\"formaPago\":{\"idFormaPago\":1,\"medioDePago\":\"Transferencia\"},\"banco\":{\"idBanco\":1,\"nombreBanco\":\"Banco de Chile\"}}"))
            .andExpect(status().isOk());
    }

    @Test
    void generarComprobante_numero_duplicado() throws Exception {
        //Arrange
        when(service.generarComprobante(comprobante)).thenThrow(new RuntimeException("Ya existe un comprobante con número: CMP-001"));

        //Act + Assert
        mock.perform(post("/api/v1/facturacion/comprobantes")
            .contentType("application/json")
            .content("{\"idComprobante\":1,\"numeroComprobante\":\"CMP-001\",\"fechaEmision\":\"2026-06-01T10:00:00\",\"montoTotal\":50000,\"metodoPago\":\"Transferencia\",\"estadoPago\":true,\"formaPago\":{\"idFormaPago\":1,\"medioDePago\":\"Transferencia\"},\"banco\":{\"idBanco\":1,\"nombreBanco\":\"Banco de Chile\"}}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void anularComprobante_existente() throws Exception {
        //Arrange
        when(service.anularComprobantePorNumero("CMP-001")).thenReturn(comprobante);

        //Act + Assert
        mock.perform(patch("/api/v1/facturacion/comprobantes/anular/CMP-001"))
            .andExpect(status().isOk());
    }

    @Test
    void anularComprobante_no_encontrado() throws Exception {
        //Arrange
        when(service.anularComprobantePorNumero("CMP-999")).thenThrow(new RuntimeException("Comprobante con número: CMP-999 no encontrado."));

        //Act + Assert
        mock.perform(patch("/api/v1/facturacion/comprobantes/anular/CMP-999"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    // FORMA DE PAGO
    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarTodasFormasDePago_conContenido() throws Exception {
        //Arrange
        when(service.listarTodasFormasPago()).thenReturn(List.of(formaPago));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/formas-pago"))
            .andExpect(status().isOk());
    }

    @Test
    void listarTodasFormasDePago_vacio() throws Exception {
        //Arrange
        when(service.listarTodasFormasPago()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/formas-pago"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void listarFormasDePagoPorIdBanco_conContenido() throws Exception {
        //Arrange
        when(service.listarFormasDePagoPorIdBanco(1)).thenReturn(List.of(formaPago));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/formas-pago/banco/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void listarFormasDePagoPorIdBanco_vacio() throws Exception {
        //Arrange
        when(service.listarFormasDePagoPorIdBanco(1)).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/formas-pago/banco/id/1"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void listarFormasDePagoPorNombreBanco_conContenido() throws Exception {
        //Arrange
        when(service.listarFormasDePagoPorNombreBanco("Banco de Chile")).thenReturn(List.of(formaPago));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/formas-pago/banco/nombre/Banco de Chile"))
            .andExpect(status().isOk());
    }

    @Test
    void listarFormasDePagoPorNombreBanco_vacio() throws Exception {
        //Arrange
        when(service.listarFormasDePagoPorNombreBanco("Banco de Chile")).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/formas-pago/banco/nombre/Banco de Chile"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void buscarFormaPagoPorSuId_encontrado() throws Exception {
        //Arrange
        when(service.buscarFormaPagoPorSuId(1)).thenReturn(formaPago);

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/formas-pago/buscar/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarFormaPagoPorSuId_no_encontrado() throws Exception {
        //Arrange
        when(service.buscarFormaPagoPorSuId(99)).thenThrow(new RuntimeException("Forma de pago con id: 99 no encontrada"));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/formas-pago/buscar/id/99"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void buscarFormaPagoPorMedio_encontrado() throws Exception {
        //Arrange
        when(service.buscarFormaPagoPorMedio("Transferencia")).thenReturn(formaPago);

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/formas-pago/medio/Transferencia"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarFormaPagoPorMedio_no_encontrado() throws Exception {
        //Arrange
        when(service.buscarFormaPagoPorMedio("Bitcoin")).thenThrow(new RuntimeException("Forma de pago: Bitcoin no encontrada."));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/formas-pago/medio/Bitcoin"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void guardarFormaPago_exitoso() throws Exception {
        //Arrange
        when(service.guardarFormaPago(formaPago)).thenReturn(formaPago);

        //Act + Assert
        mock.perform(post("/api/v1/facturacion/formas-pago/guardar")
            .contentType("application/json")
            .content("{\"idFormaPago\":1,\"medioDePago\":\"Transferencia\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void guardarFormaPago_ya_existe() throws Exception {
        //Arrange
        when(service.guardarFormaPago(formaPago)).thenThrow(new RuntimeException("Ya existe la forma de pago: Transferencia"));

        //Act + Assert
        mock.perform(post("/api/v1/facturacion/formas-pago/guardar")
            .contentType("application/json")
            .content("{\"idFormaPago\":1,\"medioDePago\":\"Transferencia\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void actualizarFormaPago_existente() throws Exception {
        //Arrange
        when(service.actualizarFormaPago(1, formaPago)).thenReturn(formaPago);

        //Act + Assert
        mock.perform(patch("/api/v1/facturacion/formas-pago/actualizar/id/1")
            .contentType("application/json")
            .content("{\"idFormaPago\":1,\"medioDePago\":\"Transferencia\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void actualizarFormaPago_no_encontrada() throws Exception {
        //Arrange
        when(service.actualizarFormaPago(99, formaPago)).thenThrow(new RuntimeException("Forma de pago con id: 99 no encontrada"));

        //Act + Assert
        mock.perform(patch("/api/v1/facturacion/formas-pago/actualizar/id/99")
            .contentType("application/json")
            .content("{\"idFormaPago\":1,\"medioDePago\":\"Transferencia\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void eliminarFormaPago_existente() throws Exception {
        //Arrange
        //metodo void, no hace falta "when"

        //Act + Assert
        mock.perform(delete("/api/v1/facturacion/formas-pago/eliminar/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void eliminarFormaPago_no_encontrada() throws Exception {
        //Arrange
        doThrow(new RuntimeException("Forma de pago con id: 99 no encontrada")).when(service).eliminarFormaPago(99);

        //Act + Assert
        mock.perform(delete("/api/v1/facturacion/formas-pago/eliminar/id/99"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    // COMUNICACION CON OTRO MS (DTO)
    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarDTO_encontrado() throws Exception {
        //Arrange
        ComprobanteDTO dto = new ComprobanteDTO(1, "CMP-001", 50000, true);
        when(service.buscarComprobanteDTOPorId(1)).thenReturn(dto);

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/dto/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarDTO_no_encontrado() throws Exception {
        //Arrange
        when(service.buscarComprobanteDTOPorId(99)).thenThrow(new RuntimeException("Comprobante con id: 99 no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/facturacion/dto/99"))
            .andExpect(status().isNotFound());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}