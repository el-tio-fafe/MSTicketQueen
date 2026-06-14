package cl.duoc.msAsiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msAsiento.model.ReservaTemporal;
import cl.duoc.msAsiento.service.ReservaTemporalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/reservas")
@Tag(name = "ReservaTemporalController", description = "Controlador para gestionar las reservas temporales en el sistema")
public class ReservaTemporalController {

    @Autowired
    private ReservaTemporalService reservaTemporalService;

    @GetMapping
    @Operation(
        summary = "Listar reservas temporales",
        description = "Obtiene la lista de todas las reservas temporales registradas en el sistema")
    public ResponseEntity<?> listarReservasTemporales(){
        List<ReservaTemporal> listaReservas = reservaTemporalService.listarReservasTemporales();
        if(listaReservas.isEmpty()){
            return ResponseEntity.badRequest().body("No hay reservas registradas");
        }else{
            return ResponseEntity.ok(listaReservas);
        }
    }

    @PostMapping("/{idAsiento}")
    @Operation(
        summary = "Crear reserva temporal",
        description = "Crea una nueva reserva temporal para un asiento específico utilizando su identificador único")
    public ResponseEntity<?> crearReserva(@PathVariable Integer idAsiento){
        try{
            return ResponseEntity.ok(reservaTemporalService.crearReservaTemporal(idAsiento));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/cancelar/{numeroAsiento}")
    @Operation(
        summary = "Cancelar reserva temporal",
        description = "Cancela una reserva temporal existente utilizando el número del asiento asociado a la reserva")
    public ResponseEntity<?> cancelarReserva(@PathVariable String numeroAsiento){
        try {
            return ResponseEntity.ok(reservaTemporalService.cancelarReservaPorNumAsiento(numeroAsiento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/verificar/{idAsiento}")
    @Operation(
        summary = "Verificar reserva temporal por ID de asiento",
        description = "Verifica si existe una reserva temporal activa para un asiento específico utilizando su identificador único")
    public ResponseEntity<String> verificarPorIdAsiento(@PathVariable Integer idAsiento){
        try {
            return ResponseEntity.ok(reservaTemporalService.verificarExpiracionPorIdAsiento(idAsiento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    

    @GetMapping("/verificar/numero/{numeroAsiento}")
    @Operation(
        summary = "Verificar reserva temporal por número de asiento",
        description = "Verifica si existe una reserva temporal activa para un asiento específico utilizando su número")
    public ResponseEntity<String> verificarPorNumAsiento(@PathVariable String numeroAsiento) {
        try {
            return ResponseEntity.ok(reservaTemporalService.verificarExpiracionPorNumAsiento(numeroAsiento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // @PutMapping("/confirmar/{idReserva}")  //lo quité porque el frontend no tiene el idReserva, sino el numeroAsiento
    // public ResponseEntity<String> confirmarCompra(@PathVariable Integer idReserva) {
    //     try {
    //         return ResponseEntity.ok(reservaTemporalService.confirmarCompra(idReserva));
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }


    @PutMapping("/confirmar/numero/{numeroAsiento}")
    @Operation(
        summary = "Confirmar compra por número de asiento",
        description = "Confirma la compra de un asiento específico utilizando su número, actualizando el estado de la reserva temporal y del asiento asociado")
    public ResponseEntity<String> confirmarPorNumAsiento(@PathVariable String numeroAsiento) {
        try {
            return ResponseEntity.ok(reservaTemporalService.confirmarCompraPorNumAsiento(numeroAsiento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
}



