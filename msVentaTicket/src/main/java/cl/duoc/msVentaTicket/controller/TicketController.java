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

import cl.duoc.msVentaTicket.model.Ticket;
import cl.duoc.msVentaTicket.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tickets")
@Tag(name = "Tickets", description = "Endpoints relacionados con los tickets, que permiten realizar operaciones CRUD sobre los tickets y consultarlos por evento, ubicación o asiento.")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los tickets", 
        description = "Endpoint para obtener una lista de todos los tickets registrados en el sistema de venta de tickets.")
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
    @Operation(
        summary = "Obtener un ticket por ID", 
        description = "Endpoint para obtener un ticket específico por su ID.")
    public ResponseEntity<?> buscarTicketsPorIdTicket(@PathVariable Integer idTicket){
        try {
            return ResponseEntity.ok(ticketService.buscarTicketPorId(idTicket));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listar/evento/id/{idEvento}")
    @Operation(
        summary = "Obtener tickets por evento", 
        description = "Endpoint para obtener una lista de tickets asociados a un evento específico por su ID.")
    public ResponseEntity<?> listarTicketPorIdEvento(@PathVariable Integer idEvento){
        try {
            List<Ticket> lista = ticketService.listarTicketPorEvento(idEvento);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/listar/ubicacion/nombre/{nombreUbicacion}")
    @Operation(
        summary = "Obtener tickets por ubicación", 
        description = "Endpoint para obtener una lista de tickets asociados a una ubicación específica por su nombre.")
    public ResponseEntity<?> listarTicketsPorUbicacion(@PathVariable String nombreUbicacion){
        try {
            List<Ticket> lista = ticketService.listarTicketsPorUbicacion(nombreUbicacion);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/buscar/asiento/{numeroAsiento}")
    @Operation(
        summary = "Obtener un ticket por número de asiento", 
        description = "Endpoint para obtener un ticket específico por su número de asiento.")
    public ResponseEntity<?> buscarTicketPorNumAsiento(@PathVariable String numeroAsiento) {
        try {
            return ResponseEntity.ok(ticketService.buscarTicketPorNumeroAsiento(numeroAsiento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping
    @Operation(
        summary = "Crear un nuevo ticket", 
        description = "Endpoint para crear un nuevo ticket en el sistema de venta de tickets.")
    public ResponseEntity<?> crearTicket(@RequestBody Ticket ticket) {
        try {
            return ResponseEntity.ok(ticketService.crearTicket(ticket));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}