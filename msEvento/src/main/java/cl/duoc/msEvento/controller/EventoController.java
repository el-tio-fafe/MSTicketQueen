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
import cl.duoc.msEvento.model.Recinto;
import cl.duoc.msEvento.model.TipoEvento;
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


//TIPO EVENTO

    @GetMapping("/tiposEvento")
    public ResponseEntity<?> listarTiposEvento(){
        try {
            List<TipoEvento> lista = eventoService.listarTiposEvento();
            if(lista.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/tiposEvento/buscar/id/{idTipoEvento")
    public ResponseEntity<?> buscarTiposEvento(@PathVariable Integer idTipoEvento){
        try {
            return ResponseEntity.ok(eventoService.buscarEventoPorId(idTipoEvento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    } 

    @PostMapping("/tiposEvento")
    public ResponseEntity<?> guardarTiposEvento(@RequestBody TipoEvento tipoEvento){
        try {
            return ResponseEntity.ok(eventoService.guardarTipoEvento(tipoEvento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("tipoEvento/id/{idTipoEvento}")
    public ResponseEntity<?> actualizarTiposEvento(@PathVariable Integer idTipoEvento, @RequestBody TipoEvento tipoEvento){
        try {
            return ResponseEntity.ok(eventoService.actualizarTipoEvento(idTipoEvento, tipoEvento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/tipoEvento/id/{idTipoEvento}")
    public ResponseEntity<?> eliminarTiposEvento(@PathVariable Integer idTipoEvento){
        try {
            eventoService.eliminarEvento(idTipoEvento);
            return ResponseEntity.ok("Tipo de Evento con id: " + idTipoEvento + " eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



//RECINTO

    @GetMapping("/recintos")
    public ResponseEntity<?> listarRecintos(){
        try {
            List<Recinto> lista = eventoService.listarRecintos();
            if(lista.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/recinto/buscar/id/{idRecinto")
    public ResponseEntity<?> buscarRecintoPorId(@PathVariable Integer idRecinto){
        try {
            return ResponseEntity.ok(eventoService.buscarRecintoPorId(idRecinto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    } 


    @GetMapping("/recinto/buscar/nombre/{nombreRecinto")
    public ResponseEntity<?> buscarRecintoPorNombre(@PathVariable String nombreRecinto){
        try {
            return ResponseEntity.ok(eventoService.buscarRecintoPorNombre(nombreRecinto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    } 

    @PostMapping("/recinto")
    public ResponseEntity<?> guardarRecinto(@RequestBody Recinto recinto){
        try {
            return ResponseEntity.ok(eventoService.guardarRecinto(recinto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("tipoEvento/id/{idTipoEvento}")
    public ResponseEntity<?> actualizarRecinto(@PathVariable Integer idRecinto, @RequestBody Recinto recinto){
        try {
            return ResponseEntity.ok(eventoService.actualizarRecinto(idRecinto, recinto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/tipoEvento/id/{idTipoEvento}")
    public ResponseEntity<?> eliminarRecinto(@PathVariable Integer idRecinto){
        try {
            eventoService.eliminarRecinto(idRecinto);;
            return ResponseEntity.ok("Recinto con id: " + idRecinto + " eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }





}
