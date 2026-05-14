package cl.duoc.msEvento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msEvento.model.Evento;
import cl.duoc.msEvento.service.EventoService;


@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public ResponseEntity<?> listarEventos(){
        try {
            List<Evento> lista = eventoService.listarEventos();
            if(lista.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/id/{idEvento}")
    public ResponseEntity<?> buscarEventoPorId(@PathVariable Integer idEvento){
        try {
            return ResponseEntity.ok(eventoService.buscarEventoPorId(idEvento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/estado/{estadoEvento}")
    public ResponseEntity<?> listarEventosPorEstado(@PathVariable String estadoEvento){
        try {
            List<Evento> lista = eventoService.listarEventosPorEstado(estadoEvento);
            if(lista.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

     @GetMapping
    public ResponseEntity<?> listarEventosPorProductora(@PathVariable Integer idProd){
        try {
            List<Evento> lista = eventoService.listarEventosPorProductora(idProd);
            if(lista.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity<?> crearEvento(@RequestBody Evento evento){
        try {
            return ResponseEntity.ok(eventoService.crearEvento(evento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PatchMapping("/aprobar/id/{idEvento}")
    public ResponseEntity<?> aprobarEventoPorId(@PathVariable Integer idEvento, @RequestParam Integer idAdministrador){
        try {
            return ResponseEntity.ok(eventoService.aprobarEvento(idEvento, idAdministrador));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PatchMapping("/rechazar/id/{idEvento}")
    public ResponseEntity<?> rechazarEventoPorId(@PathVariable Integer idEvento, @RequestParam Integer idAdministrador){
        try {
            return ResponseEntity.ok(eventoService.rechazarEvento(idEvento, idAdministrador));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/delete/id/{idEvento}")
    public ResponseEntity<?> eliminarEventoPorId(@PathVariable Integer idEvento){
        try {
            eventoService.eliminarEvento(idEvento);
            return ResponseEntity.ok("Evento id: " + idEvento + " eliminado con exito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
