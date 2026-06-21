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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/eventos")
@Tag(name = "Eventos", description = "Endpoints relacionados con los eventos, recinto y tipo de evento que permiten realizar operaciones CRUD sobre los eventos y consultar el recinto y el tipo asociado a ellos.")
public class EventoController {

    @Autowired
    private EventoService eventoService;


//EVENTO

    @GetMapping("/listartodos")
    @Operation(
        summary = "Obtener todos los eventos", 
        description = "Endpoint para obtener una lista de todos los eventos registrados en el sistema de control de eventos.")
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


    @GetMapping("buscar/id/{idEvento}")
    @Operation(
        summary = "Buscar todos los eventos por su ID", 
        description = "Endpoint para obtener un evento registrado en el sistema según su número de ID en el control de eventos.")
    public ResponseEntity<?> buscarEventoPorId(@PathVariable Integer idEvento){
        try {
            return ResponseEntity.ok(eventoService.buscarEventoPorId(idEvento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("listar/estado/{estadoEvento}")  //PENDIENTE, APROBADO O RECHAZADO
    @Operation(
        summary = "Listar todos los eventos por su estado", 
        description = "Endpoint para obtener los eventos registrados en el sistema según el estado en el control de eventos.")
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

    @GetMapping("/listar-eventos/por-productora/id/{idProd}")
    @Operation(
        summary = "Listar todos los eventos según la productora que realiza el evento", 
        description = "Endpoint para obtener los eventos registrados en el sistema según la productora que organiza el evento en el control de eventos.")
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


    @PostMapping("/crear")
    @Operation(
        summary = "Crear nuevo evento en el sistema", 
        description = "Endpoint para crear un nuevo evento en el sistema de control de eventos.")
    public ResponseEntity<?> crearEvento(@RequestBody Evento evento){
        try {
            return ResponseEntity.ok(eventoService.crearEvento(evento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PatchMapping("/aprobar/id/{idEvento}")
    @Operation(
        summary = "Aprobar un evento por su número de ID", 
        description = "Endpoint para aprobar un evento registrado en el sistema según su número de ID en el control de eventos.")
    public ResponseEntity<?> aprobarEventoPorId(@PathVariable Integer idEvento, @RequestParam Integer idAdministrador){
        try {
            return ResponseEntity.ok(eventoService.aprobarEvento(idEvento, idAdministrador));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PatchMapping("/rechazar/id/{idEvento}")
    @Operation(
        summary = "Rechazar un evento por su número de ID", 
        description = "Endpoint para rechazar un evento registrado en el sistema según su número de ID en el control de eventos.")
    public ResponseEntity<?> rechazarEventoPorId(@PathVariable Integer idEvento, @RequestParam Integer idAdministrador){
        try {
            return ResponseEntity.ok(eventoService.rechazarEvento(idEvento, idAdministrador));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/delete/id/{idEvento}")
    @Operation(
        summary = "Eliminar evento por su número de ID", 
        description = "Endpoint para eliminar un evento registrado en el sistema según el número de ID en el control de eventos.")
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
    @Operation(
        summary = "Listar todos los tipos de eventos registrados en el sistema", 
        description = "Endpoint para listar todos los tipos de eventos registrados en el sistema de control de eventos.")
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


    @GetMapping("/tiposEvento/buscar/id/{idTipoEvento}")
    @Operation(
        summary = "Buscar todos los tipos de eventos por su ID", 
        description = "Endpoint para obtener un evento registrado en el sistema según su número de ID registrado en el sistema de control de eventos.")
    public ResponseEntity<?> buscarTiposEvento(@PathVariable Integer idTipoEvento){
        try {
            return ResponseEntity.ok(eventoService.buscarEventoPorId(idTipoEvento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    } 

    @PostMapping("/tiposEvento")
    @Operation(
        summary = "Guardar un nuevo tipo de evento", 
        description = "Endpoint para crear un nuevo tipo de evento y que quede guardado en el sistema.")
    public ResponseEntity<?> guardarTiposEvento(@RequestBody TipoEvento tipoEvento){
        try {
            return ResponseEntity.ok(eventoService.guardarTipoEvento(tipoEvento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("tipoEvento/id/{idTipoEvento}")
    @Operation(
        summary = "Actualizar un tipo de evento por su número de ID", 
        description = "Endpoint para actualizar un tipo de evento registrado en el sistema según el número de ID en el control de eventos.")
    public ResponseEntity<?> actualizarTiposEvento(@PathVariable Integer idTipoEvento, @RequestBody TipoEvento tipoEvento){
        try {
            return ResponseEntity.ok(eventoService.actualizarTipoEvento(idTipoEvento, tipoEvento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/tipoEvento/id/{idTipoEvento}")
    @Operation(
        summary = "Eliminar un tipo de evento por su número de ID", 
        description = "Endpoint para eliminar un tipo de evento registrado en el sistema según el número de ID en el control de eventos.")
    public ResponseEntity<?> eliminarTiposEvento(@PathVariable Integer idTipoEvento){
        try {
            eventoService.eliminarEvento(idTipoEvento);
            return ResponseEntity.ok("Tipo de Evento con id: " + idTipoEvento + " eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



//RECINTO

    @GetMapping("/recinto/listar")
    @Operation(
        summary = "Listar todos los recintos registrados en el sistema", 
        description = "Endpoint para listar todos los recintos registrados en el sistema de control de eventos.")

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


    @GetMapping("/recinto/buscar/id/{idRecinto}")
    @Operation(
        summary = "Buscar un recinto específico por su ID", 
        description = "Endpoint para obtener un recinto registrado en el sistema según su número de ID.")
    public ResponseEntity<?> buscarRecintoPorId(@PathVariable Integer idRecinto){
        try {
            return ResponseEntity.ok(eventoService.buscarRecintoPorId(idRecinto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    } 


    @GetMapping("/recinto/buscar/nombre/{nombreRecinto}")
    @Operation(
        summary = "Buscar un recinto específico por su nombre", 
        description = "Endpoint para obtener un recinto según su nombre registrado en el sistema de control de eventos.")
    public ResponseEntity<?> buscarRecintoPorNombre(@PathVariable String nombreRecinto){
        try {
            return ResponseEntity.ok(eventoService.buscarRecintoPorNombre(nombreRecinto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    } 

    @PostMapping("/recinto/guardar")
    @Operation(
        summary = "Guardar un nuevo recinto", 
        description = "Endpoint para crear un nuevo recinto y que quede guardado en el sistema.")
    public ResponseEntity<?> guardarRecinto(@RequestBody Recinto recinto){
        try {
            return ResponseEntity.ok(eventoService.guardarRecinto(recinto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/recinto/actualizar/id/{idRecinto}")
    @Operation(
        summary = "Actualizar un recinto por su número de ID", 
        description = "Endpoint para actualizar un recinto registrado en el sistema según el número de ID en el control de eventos.")
    public ResponseEntity<?> actualizarRecinto(@PathVariable Integer idRecinto, @RequestBody Recinto recinto){
        try {
            return ResponseEntity.ok(eventoService.actualizarRecinto(idRecinto, recinto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/recinto/eliminar/id/{idRecinto}")
    @Operation(
        summary = "Eliminar un recinto por su número de ID", 
        description = "Endpoint para eliminar un recinto registrado en el sistema según el número de ID en el control de eventos.")

    public ResponseEntity<?> eliminarRecinto(@PathVariable Integer idRecinto){
        try {
            eventoService.eliminarRecinto(idRecinto);;
            return ResponseEntity.ok("Recinto con id: " + idRecinto + " eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }





}
