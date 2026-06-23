package cl.duoc.msVentaTicket.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.msVentaTicket.dto.BoletaDTO;
import cl.duoc.msVentaTicket.dto.CompradorDTO;
import cl.duoc.msVentaTicket.dto.EventoDTO;
import cl.duoc.msVentaTicket.model.Boleta;
import cl.duoc.msVentaTicket.model.Detalle;
import cl.duoc.msVentaTicket.model.Ticket;
import cl.duoc.msVentaTicket.service.BoletaService;

@WebMvcTest(BoletaController.class)
public class BoletaControllerTest {

    @Autowired
    private MockMvc mock;//simula llamadas http

    @MockitoBean
    private BoletaService service;

    private Boleta boleta;
    private Detalle detalle;
    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setIdTicket(1);
        ticket.setCodigoQR("qr-1");
        ticket.setIdEvento(1);
        ticket.setNumeroAsiento("A1");
        ticket.setNombreUbicacion(null);

        detalle = new Detalle();
        detalle.setIdDetalle(1);
        detalle.setCantidad(1);
        detalle.setPrecioUnitario(10000);
        detalle.setDescuento(0);
        detalle.setSubTotal(10000);
        detalle.setTicket(ticket);

        boleta = new Boleta();
        boleta.setIdBoleta(1);
        boleta.setNumeroBoleta(1);
        boleta.setFechaEmision(LocalDate.of(2026, 6, 1));
        boleta.setHoraEmision(LocalTime.of(10, 0));
        boleta.setTotalBoleta(10000);
        boleta.setIdComprador(1);
        boleta.setIdComprobante(null);
        boleta.setDetalles(List.of(detalle));
    }

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarBoletas_conContenido() throws Exception {
        //Arrange
        when(service.listarBoletas()).thenReturn(List.of(boleta));

        //Act + Assert
        mock.perform(get("/api/v1/boletas"))
            .andExpect(status().isOk());
    }

    @Test
    void listarBoletas_vacio() throws Exception {
        //Arrange
        when(service.listarBoletas()).thenReturn(List.of());

        //Act + Assert
        mock.perform(get("/api/v1/boletas"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void mostrarDetalleBoleta_encontrada() throws Exception {
        //Arrange
        CompradorDTO compradorDTO = new CompradorDTO(1, "11111111-1", "Juan", "Pérez", "juan@gmail.com");
        EventoDTO eventoDTO = new EventoDTO(1, "EVT-001", "Concierto Test", "APROBADO");
        BoletaDTO dto = new BoletaDTO(1, LocalDate.of(2026, 6, 1), 10000, compradorDTO, List.of(eventoDTO));
        when(service.mostrarDetalleBoleta(1)).thenReturn(dto);

        //Act + Assert
        mock.perform(get("/api/v1/boletas/detalle/1"))
            .andExpect(status().isOk());
    }

    @Test
    void mostrarDetalleBoleta_no_encontrada() throws Exception {
        //Arrange
        when(service.mostrarDetalleBoleta(99)).thenThrow(new RuntimeException("Boleta id: 99no encontrada"));

        //Act + Assert
        mock.perform(get("/api/v1/boletas/detalle/99"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarBoletaPorId_encontrada() throws Exception {
        //Arrange
        when(service.buscarBoletaPorId(1)).thenReturn(boleta);

        //Act + Assert
        mock.perform(get("/api/v1/boletas/id/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarBoletaPorId_no_encontrada() throws Exception {
        //Arrange
        when(service.buscarBoletaPorId(99)).thenThrow(new RuntimeException("Boleta con id: 99 no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/boletas/id/99"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void buscarBoletaPorNumero_encontrada() throws Exception {
        //Arrange
        when(service.buscarBoletaPorNumero(1)).thenReturn(boleta);

        //Act + Assert
        mock.perform(get("/api/v1/boletas/numero/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarBoletaPorNumero_no_encontrada() throws Exception {
        //Arrange
        when(service.buscarBoletaPorNumero(999)).thenThrow(new RuntimeException("Número boleta: 999 no encontrado."));

        //Act + Assert
        mock.perform(get("/api/v1/boletas/numero/999"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void listarBoletasPorComprador_conContenido() throws Exception {
        //Arrange
        when(service.listarBoletasPorComprador(1)).thenReturn(List.of(boleta));

        //Act + Assert
        mock.perform(get("/api/v1/boletas/comprador/1"))
            .andExpect(status().isOk());
    }

    @Test
    void listarBoletasPorComprador_sin_boletas() throws Exception {
        //Arrange
        when(service.listarBoletasPorComprador(99)).thenThrow(new RuntimeException("El comprador id: 99 no tiene boletas asociadas"));

        //Act + Assert
        mock.perform(get("/api/v1/boletas/comprador/99"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void crearBoleta_exitosa() throws Exception {
        //Arrange
        when(service.crearBoleta(boleta)).thenReturn(boleta);

        //Act + Assert
        mock.perform(post("/api/v1/boletas")
            .contentType("application/json")
            .content("{\"idBoleta\":1,\"numeroBoleta\":1,\"fechaEmision\":\"2026-06-01\",\"horaEmision\":\"10:00:00\",\"totalBoleta\":10000,\"idComprador\":1,\"idComprobante\":null,\"detalles\":[{\"idDetalle\":1,\"cantidad\":1,\"precioUnitario\":10000,\"descuento\":0,\"subTotal\":10000,\"ticket\":{\"idTicket\":1,\"codigoQR\":\"qr-1\",\"idEvento\":1,\"numeroAsiento\":\"A1\",\"nombreUbicacion\":null}}]}"))
            .andExpect(status().isOk());
    }

    @Test
    void crearBoleta_comprador_no_existe() throws Exception {
        //Arrange
        when(service.crearBoleta(boleta)).thenThrow(new RuntimeException("No se puede crear la boleta porque el comprador id: 1 no existe"));

        //Act + Assert
        mock.perform(post("/api/v1/boletas")
            .contentType("application/json")
            .content("{\"idBoleta\":1,\"numeroBoleta\":1,\"fechaEmision\":\"2026-06-01\",\"horaEmision\":\"10:00:00\",\"totalBoleta\":10000,\"idComprador\":1,\"idComprobante\":null,\"detalles\":[{\"idDetalle\":1,\"cantidad\":1,\"precioUnitario\":10000,\"descuento\":0,\"subTotal\":10000,\"ticket\":{\"idTicket\":1,\"codigoQR\":\"qr-1\",\"idEvento\":1,\"numeroAsiento\":\"A1\",\"nombreUbicacion\":null}}]}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void crearBoleta_sin_detalles() throws Exception {
        //Arrange
        Boleta boletaSinDetalles = new Boleta();
        boletaSinDetalles.setIdBoleta(1);
        boletaSinDetalles.setFechaEmision(LocalDate.of(2026, 6, 1));
        boletaSinDetalles.setHoraEmision(LocalTime.of(10, 0));
        boletaSinDetalles.setIdComprador(1);
        boletaSinDetalles.setDetalles(List.of());

        when(service.crearBoleta(boletaSinDetalles)).thenThrow(new RuntimeException("La boleta debe tener al menos una compra"));

        //Act + Assert
        mock.perform(post("/api/v1/boletas")
            .contentType("application/json")
            .content("{\"idBoleta\":1,\"fechaEmision\":\"2026-06-01\",\"horaEmision\":\"10:00:00\",\"idComprador\":1,\"detalles\":[]}"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------------
    @Test
    void asociarComprobante_exitoso() throws Exception {
        //Arrange
        Boleta boletaConComprobante = new Boleta();
        boletaConComprobante.setIdBoleta(1);
        boletaConComprobante.setNumeroBoleta(1);
        boletaConComprobante.setFechaEmision(LocalDate.of(2026, 6, 1));
        boletaConComprobante.setHoraEmision(LocalTime.of(10, 0));
        boletaConComprobante.setTotalBoleta(10000);
        boletaConComprobante.setIdComprador(1);
        boletaConComprobante.setIdComprobante(5);
        boletaConComprobante.setDetalles(List.of(detalle));

        when(service.asociarComprobante(1, 5)).thenReturn(boletaConComprobante);

        //Act + Assert
        mock.perform(patch("/api/v1/boletas/asociar-comprobante/1")
            .param("idComprobante", "5"))
            .andExpect(status().isOk());
    }

    @Test
    void asociarComprobante_boleta_no_encontrada() throws Exception {
        //Arrange
        when(service.asociarComprobante(99, 5)).thenThrow(new RuntimeException("Boleta con id: 99 no encontrado."));

        //Act + Assert
        mock.perform(patch("/api/v1/boletas/asociar-comprobante/99")
            .param("idComprobante", "5"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void asociarComprobante_ya_tiene_comprobante() throws Exception {
        //Arrange
        when(service.asociarComprobante(1, 5)).thenThrow(new RuntimeException("La boleta ya tiene un comprobante asociado"));

        //Act + Assert
        mock.perform(patch("/api/v1/boletas/asociar-comprobante/1")
            .param("idComprobante", "5"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void asociarComprobante_comprobante_no_existe() throws Exception {
        //Arrange
        when(service.asociarComprobante(1, 99)).thenThrow(new RuntimeException("No se puede asociar porque el comprobante con id: 99 no existe."));

        //Act + Assert
        mock.perform(patch("/api/v1/boletas/asociar-comprobante/1")
            .param("idComprobante", "99"))
            .andExpect(status().isBadRequest());
    }
    //-----------------------------------------------------------------------------------------------------------------------------

}