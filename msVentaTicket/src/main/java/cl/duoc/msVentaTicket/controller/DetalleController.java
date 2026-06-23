package cl.duoc.msVentaTicket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msVentaTicket.model.Detalle;
import cl.duoc.msVentaTicket.service.DetalleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/detalles")
@Tag(name = "Detalles", description = "Endpoints relacionados con los detalles de compra, que permiten realizar operaciones CRUD sobre los detalles y consultarlos por evento.")
public class DetalleController {

    @Autowired
    private DetalleService detalleService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los detalles", 
        description = "Endpoint para obtener una lista de todos los detalles registrados en el sistema de venta de tickets.")
    public ResponseEntity<?> listarDetalles(){
        try {
            List<Detalle> lista = detalleService.listarDetalles();
            if(lista.isEmpty()){
                return ResponseEntity.badRequest().body("No hay detalles registrados");
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/id/{idDetalle}")
    @Operation(
        summary = "Obtener un detalle por ID", 
        description = "Endpoint para obtener un detalle específico por su ID.")
    public ResponseEntity<?> buscarDetallePorId(@PathVariable Integer idDetalle){
        try {
            return ResponseEntity.ok(detalleService.buscarDetallePorId(idDetalle));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/listar/detalles-por-evento/{idEvento}")
    @Operation(
        summary = "Obtener detalles por evento", 
        description = "Endpoint para obtener una lista de detalles asociados a un evento específico por su ID.")
    public ResponseEntity<?> listarDetallesPorEvento(@PathVariable Integer idEvento){
        try {
            List<Detalle> lista = detalleService.listarDetallePorEvento(idEvento);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping
    @Operation(
        summary = "Crear un nuevo detalle", 
        description = "Endpoint para crear un nuevo detalle de compra en el sistema de venta de tickets.")
    public ResponseEntity<?> crearDetalle(@RequestBody Detalle detalle) {
        try {
            return ResponseEntity.ok(detalleService.crearDetalle(detalle));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}