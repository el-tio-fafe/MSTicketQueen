package cl.duoc.msAsiento.controller;

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

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaTemporalController {

    @Autowired
    private ReservaTemporalService reservaTemporalService;

    @PostMapping("/{idAsiento}")
    public ResponseEntity<ReservaTemporal> crearReserva(@PathVariable Integer idAsiento){
        return ResponseEntity.ok(reservaTemporalService.crearReservaTemporal(idAsiento));
    }

    @GetMapping("/verificar/{idReserva}")
    public ResponseEntity<String> verificarPorIdReserva(@PathVariable Integer idReserva){
        return ResponseEntity.ok(reservaTemporalService.verificarExpiracionPorIdReserva(idReserva));
    }

    @GetMapping("/verificar/numero/{numeroAsiento}")
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
    public ResponseEntity<String> confirmarPorNumAsiento(@PathVariable String numeroAsiento) {
        try {
            return ResponseEntity.ok(reservaTemporalService.confirmarCompraPorNumAsiento(numeroAsiento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    
}



