package cl.duoc.msVentaTicket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msVentaTicket.model.Ticket;
import cl.duoc.msVentaTicket.service.TicketService;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public ResponseEntity<?> listarTickets(){
        try {
            List<Ticket> lista = ticketService.listarTickets();
            if(lista.isEmpty()){
                return ResponseEntity.badRequest().body("No hay tickets registrados");
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/id/{idTicket}")
    public ResponseEntity<?> buscarTicketsPorIdTicket(@PathVariable Integer idTicket){
        try {
            return ResponseEntity.ok(ticketService.buscarTicketPorId(idTicket));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar/evento/id/{idEvento}")
    public ResponseEntity<?> listarTicketPorIdEvento(@PathVariable Integer idEvento){
        try {
            List<Ticket> lista = ticketService.listarTicketPorEvento(idEvento);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/listar/ubicacion/nombre/{nombreUbicacion}")
    public ResponseEntity<?> listarTicketsPorUbicacion(@PathVariable String nombreUbicacion){
        try {
            List<Ticket> lista = ticketService.listarTicketsPorUbicacion(nombreUbicacion);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/buscar/asiento/{numeroAsiento}")
    public ResponseEntity<?> buscarTicketPorNumAsiento(@PathVariable String numeroAsiento) {
        try {
            return ResponseEntity.ok(ticketService.buscarTicketPorNumeroAsiento(numeroAsiento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
