package cl.duoc.msEvento.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import cl.duoc.msEvento.dto.EventoDTO;
import cl.duoc.msEvento.dto.EventoDetalleDTO;
import cl.duoc.msEvento.dto.EventoListarDTO;
import cl.duoc.msEvento.model.Evento;
import cl.duoc.msEvento.model.Recinto;
import cl.duoc.msEvento.model.TipoEvento;
import cl.duoc.msEvento.service.EventoService;


@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;


//EVENTO
    //CON ESTE METODO ME TIRABA TODOS LOS DATOS CON LAS LISTAS DE RECINTOS Y LAS LISTAS DEL TIPO DE EVENTO
    // @GetMapping("/listartodos")
    // public ResponseEntity<?> listarEventos(){
    //     try {
    //         List<Evento> lista = eventoService.listarEventos();
    //         if(lista.isEmpty()){
    //             return ResponseEntity.noContent().build();
    //         }
    //         return ResponseEntity.ok(lista);

    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }

    @GetMapping("/listartodos")
    public ResponseEntity<List<EventoListarDTO>> listarEventos(){
        List<Evento> listarEventos = eventoService.listarEventos();
        if (listarEventos.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            List<EventoListarDTO> listaDTO = listarEventos.stream()
                .map(even -> new EventoListarDTO(
                    even.getIdEvento(),
                    even.getNombreEvento(),
                    even.getFechaEvento(),
                    even.getEstadoEvento(),
                    even.getIdProd(),
                    even.getIdAdministrador()
                ))
                .collect(Collectors.toList());
            return ResponseEntity.ok(listaDTO);
        }
    }

    @GetMapping("listar/estado/{estadoEvento}")  //PENDIENTE, APROBADO O RECHAZADO
    public ResponseEntity<List<EventoListarDTO>> listarEventosPorEstado(@PathVariable String estadoEvento){
        try {
            List<Evento> lista = eventoService.listarEventosPorEstado(estadoEvento);
            if(lista.isEmpty()){
                return ResponseEntity.noContent().build();
            } else{
                List<EventoListarDTO> listaDTO = lista.stream()
                .map(even -> new EventoListarDTO(
                    even.getIdEvento(),
                    even.getNombreEvento(),
                    even.getFechaEvento(),
                    even.getEstadoEvento(),
                    even.getIdProd(),
                    even.getIdAdministrador()
                )).collect(Collectors.toList());
                return ResponseEntity.ok(listaDTO);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    //METODO QUE CONECTA CON EL MS ADMINISTRADOR Y EL GESTION ARTISTICA PARA SACAR LA PRODUCTORA
    @GetMapping("/mostrar-detalle/idEvento/{idEvento}")
    public ResponseEntity<EventoDetalleDTO> mostrarDetalleEvento(@PathVariable Integer idEvento){
        try {
            EventoDetalleDTO eventoDetalleDTO = eventoService.mostrarDetalleEvento(idEvento);
            return ResponseEntity.ok(eventoDetalleDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/listar-eventos/por-productora/id/{idProd}")
    public ResponseEntity<List<EventoListarDTO>> listarEventosPorProductora(@PathVariable Integer idProd){
        try {
            List<Evento> lista = eventoService.listarEventosPorProductora(idProd);
            if(lista.isEmpty()){
                return ResponseEntity.noContent().build();
            } else {
                List<EventoListarDTO> listaDTO = lista.stream()
                .map(even -> new EventoListarDTO(
                even.getIdEvento(),
                even.getNombreEvento(),
                even.getFechaEvento(),
                even.getEstadoEvento(),
                even.getIdProd(),
                even.getIdAdministrador()
                )).collect(Collectors.toList());
                return ResponseEntity.ok(listaDTO);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    


    @GetMapping("buscar/id/{idEvento}")
    public ResponseEntity<?> buscarEventoPorId(@PathVariable Integer idEvento){
        try {
            return ResponseEntity.ok(eventoService.buscarEventoPorId(idEvento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //METODO QUE SE COMUNICA CON OTRO MS
    @GetMapping("/dto/{idEvento}")
    public ResponseEntity<EventoDTO> buscarDTO(@PathVariable Integer idEvento){
        try {
            return ResponseEntity.ok(eventoService.buscarEventoDTOPorId(idEvento));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


   


    @PostMapping("/crear")
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


    @GetMapping("/tiposEvento/buscar/id/{idTipoEvento}")
    public ResponseEntity<?> buscarTiposEvento(@PathVariable Integer idTipoEvento){
        try {
            return ResponseEntity.ok(eventoService.buscarTipoEventoPorId(idTipoEvento));
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

    @PatchMapping("tiposEvento/id/{idTipoEvento}")
    public ResponseEntity<?> actualizarTiposEvento(@PathVariable Integer idTipoEvento, @RequestBody TipoEvento tipoEvento){
        try {
            return ResponseEntity.ok(eventoService.actualizarTipoEvento(idTipoEvento, tipoEvento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/tiposEvento/id/{idTipoEvento}")
    public ResponseEntity<?> eliminarTiposEvento(@PathVariable Integer idTipoEvento){
        try {
            eventoService.eliminarTipoEvento(idTipoEvento);
            return ResponseEntity.ok("Tipo de Evento con id: " + idTipoEvento + " eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



//RECINTO

    @GetMapping("/recinto/listar")
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
    public ResponseEntity<?> buscarRecintoPorId(@PathVariable Integer idRecinto){
        try {
            return ResponseEntity.ok(eventoService.buscarRecintoPorId(idRecinto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    } 


    @GetMapping("/recinto/buscar/nombre/{nombreRecinto}")
    public ResponseEntity<?> buscarRecintoPorNombre(@PathVariable String nombreRecinto){
        try {
            return ResponseEntity.ok(eventoService.buscarRecintoPorNombre(nombreRecinto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    } 

    @PostMapping("/recinto/guardar")
    public ResponseEntity<?> guardarRecinto(@RequestBody Recinto recinto){
        try {
            return ResponseEntity.ok(eventoService.guardarRecinto(recinto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/recinto/actualizar/id/{idRecinto}")
    public ResponseEntity<?> actualizarRecinto(@PathVariable Integer idRecinto, @RequestBody Recinto recinto){
        try {
            return ResponseEntity.ok(eventoService.actualizarRecinto(idRecinto, recinto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/recinto/eliminar/id/{idRecinto}")
    public ResponseEntity<?> eliminarRecinto(@PathVariable Integer idRecinto){
        try {
            eventoService.eliminarRecinto(idRecinto);;
            return ResponseEntity.ok("Recinto con id: " + idRecinto + " eliminado con éxito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }





}
