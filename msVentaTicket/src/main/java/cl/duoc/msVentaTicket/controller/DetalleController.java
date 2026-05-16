package cl.duoc.msVentaTicket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msVentaTicket.model.Detalle;
import cl.duoc.msVentaTicket.service.DetalleService;

@RestController
@RequestMapping("/api/v1/detalles")
public class DetalleController {

    @Autowired
    private DetalleService detalleService;

    @GetMapping("/id/idDetalle")
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
    public ResponseEntity<?> buscarDetallePorId(@PathVariable Integer idDetalle){
        try {
            return ResponseEntity.ok(detalleService.buscarDetallePorId(idDetalle));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/listar/detalles-por-evento/{idEvento}")
    public ResponseEntity<?> listarDetallesPorEvento(@PathVariable Integer idEvento){
        try {
            List<Detalle> lista = detalleService.listarDetallePorEvento(idEvento);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
