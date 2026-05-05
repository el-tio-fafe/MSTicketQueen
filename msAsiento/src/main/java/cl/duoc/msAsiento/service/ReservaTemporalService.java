package cl.duoc.msAsiento.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msAsiento.model.Asiento;
import cl.duoc.msAsiento.model.ReservaTemporal;
import cl.duoc.msAsiento.repository.AsientoRepository;
import cl.duoc.msAsiento.repository.ReservaTemporalRepository;

@Service
public class ReservaTemporalService {

    @Autowired
    private ReservaTemporalRepository reservaTemporalRepository;

    @Autowired
    private AsientoRepository asientoRepository;

    public List<ReservaTemporal> listarReservasTemporales(){
        return reservaTemporalRepository.findAll();
    }

    public ReservaTemporal crearReservaTemporal(Integer idAsiento){
        Asiento asiento = asientoRepository.findById(idAsiento)
            .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));

        if(asiento.getEstadoAsiento().equals("VENDIDO")){
            throw new RuntimeException("El asiento " + idAsiento + " ya está vendido");
        }

        if(asiento.getEstadoAsiento().equals("RESERVADO")){
            throw new RuntimeException("El asiento " + idAsiento + " ya tiene una reserva activa");
        }

        if(! asiento.getEstadoAsiento().equals("DISPONIBLE")){
            throw new RuntimeException("El asiento no está disponible");
        }

        asiento.setEstadoAsiento("RESERVADO");
        asientoRepository.save(asiento);

        ReservaTemporal reserva = new ReservaTemporal();
        reserva.setAsiento(asiento);
        reserva.setFechaHoraReserva(LocalDateTime.now());
        reserva.setFechaExpiracion(LocalDateTime.now().plusMinutes(10));
        reserva.setEstado("RESERVADO");

        return reservaTemporalRepository.save(reserva);
    }


    public String cancelarReservaPorNumAsiento(String numeroAsiento){
        Asiento asiento = asientoRepository.findByNumeroAsiento(numeroAsiento)
        .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));

    if(asiento.getEstadoAsiento().equals("DISPONIBLE")){
        throw new RuntimeException("El asiento " + numeroAsiento + " no tiene una reserva activa");
    }

    if(asiento.getEstadoAsiento().equals("VENDIDO")){
        throw new RuntimeException("El asiento " + numeroAsiento + " ya está vendido, no se puede cancelar");
    }

    ReservaTemporal reserva = reservaTemporalRepository
        .findByAsientoNumeroAsientoAndEstado(numeroAsiento, "RESERVADO")
        .orElseThrow(() -> new RuntimeException("No hay reserva activa para el asiento: " + numeroAsiento));

    reserva.setEstado("CANCELADO");
    reserva.getAsiento().setEstadoAsiento("DISPONIBLE");
    asientoRepository.save(reserva.getAsiento());
    reservaTemporalRepository.save(reserva);

    return "Reserva cancelada, asiento " + numeroAsiento + " liberado";
    }


    public String verificarExpiracionPorIdAsiento(Integer idAsiento){
        Asiento asiento = asientoRepository.findById(idAsiento)
        .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));

        if (asiento.getEstadoAsiento().equals("VENDIDO")) {
            throw new RuntimeException("El asiento id: " + idAsiento + " ya está vendido");
        }

        if (asiento.getEstadoAsiento().equals("DISPONIBLE")) {
            throw new RuntimeException("El asiento id: "  + idAsiento + " no tiene una reserva activa");
        }

        ReservaTemporal reserva = reservaTemporalRepository.findByAsientoIdAsientoAndEstado(idAsiento, "RESERVADO" )
            .orElseThrow(() -> new RuntimeException("No hay una reserva activa para el asiento id: " + idAsiento));

        if (reserva.getEstado().equals("PAGADO")) {
            return "La reserva ya fue pagada, asiento vendido";
        }

        if(LocalDateTime.now().isAfter(reserva.getFechaExpiracion())){
            reserva.setEstado("CANCELADO");
            reserva.getAsiento().setEstadoAsiento("DISPONIBLE");
            asientoRepository.save(reserva.getAsiento());
            reservaTemporalRepository.save(reserva);
            return "Reserva expirada, asiento liberado";
        }
        return "Reserva vigente";
    }


    public String verificarExpiracionPorNumAsiento(String numeroAsiento) {
        Asiento asiento = asientoRepository.findByNumeroAsiento(numeroAsiento)
        .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));

        if (asiento.getEstadoAsiento().equals("VENDIDO")) {
            throw new RuntimeException("El asiento " + numeroAsiento + " ya está vendido");
        }

        if (asiento.getEstadoAsiento().equals("DISPONIBLE")) {
            throw new RuntimeException("El asiento " + numeroAsiento + " no tiene una reserva activa");
        }


        ReservaTemporal reserva = reservaTemporalRepository
            .findByAsientoNumeroAsientoAndEstado(numeroAsiento, "RESERVADO")
            .orElseThrow(() -> new RuntimeException("No hay reserva activa para el asiento: " + numeroAsiento));

        if (reserva.getEstado().equals("PAGADO")) {
            return "La reserva ya fue pagada, asiento vendido";
        }

        if (LocalDateTime.now().isAfter(reserva.getFechaExpiracion())) {
            reserva.setEstado("CANCELADO");
            reserva.getAsiento().setEstadoAsiento("DISPONIBLE");
            asientoRepository.save(reserva.getAsiento());
            reservaTemporalRepository.save(reserva);
            return "Reserva expirada, asiento liberado";
        }
        return "Reserva aún vigente";
    }
//CONFIRMA LA COMPRA POR EL ID DE LA RESERVA
//     public String confirmarCompra(Integer idReserva) {
//     ReservaTemporal reserva = reservaTemporalRepository.findById(idReserva)
//         .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

//     // Verificar que no haya expirado
//     if (LocalDateTime.now().isAfter(reserva.getFechaExpiracion())) {
//         // Liberar el asiento porque expiró
//         reserva.setEstado("Cancelado");
//         reserva.getAsiento().setEstadoAsiento("Disponible");
//         asientoRepository.save(reserva.getAsiento());
//         reservaTemporalRepository.save(reserva);
//         throw new RuntimeException("La reserva expiró, el asiento fue liberado");
//     }

//     // Si no expiró, confirmar la venta
//     reserva.setEstado("Pagado");  
//     reserva.getAsiento().setEstadoAsiento("Vendido");
//     asientoRepository.save(reserva.getAsiento());
//     reservaTemporalRepository.save(reserva);

//     return "Compra confirmada, asiento vendido";
// }

    public String confirmarCompraPorNumAsiento(String numeroAsiento) {
        Asiento asiento = asientoRepository.findByNumeroAsiento(numeroAsiento)
            .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));

        if (asiento.getEstadoAsiento().equals("VENDIDO")) {
            throw new RuntimeException("El asiento " + numeroAsiento + " ya está vendido");
        }

        if (asiento.getEstadoAsiento().equals("DISPONIBLE")) {
            throw new RuntimeException("El asiento " + numeroAsiento + " no tiene una reserva activa");
        }

        // buscamos su reserva activa
        ReservaTemporal reserva = reservaTemporalRepository
            .findByAsientoIdAsientoAndEstado(asiento.getIdAsiento(), "RESERVADO")
            .orElseThrow(() -> new RuntimeException("No hay reserva activa para ese asiento"));

        if (LocalDateTime.now().isAfter(reserva.getFechaExpiracion())) {
            reserva.setEstado("CANCELADO");
            reserva.getAsiento().setEstadoAsiento("DISPONIBLE");
            asientoRepository.save(reserva.getAsiento());
            reservaTemporalRepository.save(reserva);
            throw new RuntimeException("La reserva expiró, el asiento fue liberado");
        }

        reserva.setEstado("PAGADO");
        reserva.getAsiento().setEstadoAsiento("VENDIDO");
        asientoRepository.save(reserva.getAsiento());
        reservaTemporalRepository.save(reserva);

        return "Compra confirmada, asiento vendido";
    }

}
