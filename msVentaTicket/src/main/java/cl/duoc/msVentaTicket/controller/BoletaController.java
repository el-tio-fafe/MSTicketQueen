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

import cl.duoc.msVentaTicket.model.Boleta;
import cl.duoc.msVentaTicket.service.BoletaService;

@RestController
@RequestMapping("/api/v1/boletas")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    @GetMapping
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

    @GetMapping("/id/{idBoleta}")
    public ResponseEntity<?> buscarBoletaPorId(@PathVariable Integer idBoleta) {
        try {
            return ResponseEntity.ok(boletaService.buscarBoletaPorId(idBoleta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/numero/{numeroBoleta}")
    public ResponseEntity<?> buscarBoletaPorNumero(@PathVariable Integer numeroBoleta) {
        try {
            return ResponseEntity.ok(boletaService.buscarBoletaPorNumero(numeroBoleta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/comprador/{idComprador}")
    public ResponseEntity<?> listarBoletasPorComprador(@PathVariable Integer idComprador) {
        try {
            List<Boleta> lista = boletaService.listarBoletasPorComprador(idComprador);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearBoleta(@RequestBody Boleta boleta) {
        try {
            return ResponseEntity.ok(boletaService.crearBoleta(boleta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/asociar-comprobante/{idBoleta}")
    public ResponseEntity<?> asociarComprobante(@PathVariable Integer idBoleta, @RequestParam Integer idComprobante) {
        try {
            return ResponseEntity.ok(boletaService.asociarComprobante(idBoleta, idComprobante));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
