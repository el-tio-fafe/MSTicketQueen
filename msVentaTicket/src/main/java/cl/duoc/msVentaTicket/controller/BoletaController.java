package cl.duoc.msVentaTicket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msVentaTicket.dto.BoletaDTO;
import cl.duoc.msVentaTicket.model.Boleta;
import cl.duoc.msVentaTicket.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/boletas")
@Tag(name = "Boletas", description = "Endpoints relacionados con las boletas, que permiten realizar operaciones CRUD sobre las boletas y asociar comprobantes a las boletas.")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    @GetMapping
    @Operation(
        summary = "Obtener todas las boletas", 
        description = "Endpoint para obtener una lista de todas las boletas registradas en el sistema de venta de tickets.")
    public ResponseEntity<?> listarBoletas() {
        try {
            List<Boleta> lista = boletaService.listarBoletas();
            if (lista.isEmpty()) {
                return ResponseEntity.badRequest().body("No hay boletas registradas.");
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //CONECCION CON OTRO MS POR EL DTO
    @GetMapping("/detalle/{idBoleta}")
    @Operation(
        summary = "Obtener DTO de detalle de una boleta", 
        description = "Endpoint para obtener un DTO con el detalle de una boleta específica por su ID, que contiene información relevante para otros microservicios.")
    public ResponseEntity<?> mostarDetalleBoleta(@PathVariable Integer idBoleta){
        try {
            BoletaDTO boletaDTO = boletaService.mostrarDetalleBoleta(idBoleta);
            return ResponseEntity.ok(boletaDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




    @GetMapping("/id/{idBoleta}")
    @Operation(
        summary = "Obtener una boleta por ID", 
        description = "Endpoint para obtener una boleta específica por su ID.")
    public ResponseEntity<?> buscarBoletaPorId(@PathVariable Integer idBoleta) {
        try {
            return ResponseEntity.ok(boletaService.buscarBoletaPorId(idBoleta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/numero/{numeroBoleta}")
    @Operation(
        summary = "Obtener una boleta por número", 
        description = "Endpoint para obtener una boleta específica por su número.")
    public ResponseEntity<?> buscarBoletaPorNumero(@PathVariable Integer numeroBoleta) {
        try {
            return ResponseEntity.ok(boletaService.buscarBoletaPorNumero(numeroBoleta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/comprador/{idComprador}")
    @Operation(
        summary = "Obtener boletas por comprador", 
        description = "Endpoint para obtener una lista de boletas asociadas a un comprador específico por su ID.")
    public ResponseEntity<?> listarBoletasPorComprador(@PathVariable Integer idComprador) {
        try {
            List<Boleta> lista = boletaService.listarBoletasPorComprador(idComprador);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    @Operation(
        summary = "Crear una nueva boleta", 
        description = "Endpoint para crear una nueva boleta en el sistema de venta de tickets, validando comprador, detalles, evento, asiento y ubicación.")
    public ResponseEntity<?> crearBoleta(@RequestBody Boleta boleta) {
        try {
            return ResponseEntity.ok(boletaService.crearBoleta(boleta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/asociar-comprobante/{idBoleta}")
    @Operation(
        summary = "Asociar un comprobante a una boleta", 
        description = "Endpoint para asociar un comprobante existente a una boleta específica por sus IDs.")
    public ResponseEntity<?> asociarComprobante(@PathVariable Integer idBoleta, @RequestParam Integer idComprobante) {
        try {
            return ResponseEntity.ok(boletaService.asociarComprobante(idBoleta, idComprobante));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}