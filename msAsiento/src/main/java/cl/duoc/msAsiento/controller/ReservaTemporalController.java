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

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaTemporalController {

    @Autowired
    private ReservaTemporalService reservaTemporalService;

    @GetMapping
    public ResponseEntity<?> listarReservasTemporales(){
        List<ReservaTemporal> listaReservas = reservaTemporalService.listarReservasTemporales();
        if(listaReservas.isEmpty()){
            return ResponseEntity.badRequest().body("No hay reservas registradas");
        }else{
            return ResponseEntity.ok(listaReservas);
        }
    }

    @PostMapping("/{idAsiento}")
    public ResponseEntity<?> crearReserva(@PathVariable Integer idAsiento){
        try{
            return ResponseEntity.ok(reservaTemporalService.crearReservaTemporal(idAsiento));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/cancelar/{numeroAsiento}")
    public ResponseEntity<?> cancelarReserva(@PathVariable String numeroAsiento){
        try {
            return ResponseEntity.ok(reservaTemporalService.cancelarReservaPorNumAsiento(numeroAsiento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/verificar/{idAsiento}")
    public ResponseEntity<String> verificarPorIdAsiento(@PathVariable Integer idAsiento){
        try {
            return ResponseEntity.ok(reservaTemporalService.verificarExpiracionPorIdAsiento(idAsiento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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



