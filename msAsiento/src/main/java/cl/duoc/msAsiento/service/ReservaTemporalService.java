package cl.duoc.msAsiento.service;

import java.time.LocalDateTime;

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

    public ReservaTemporal crearReservaTemporal(Integer idAsiento){
        Asiento asiento = asientoRepository.findById(idAsiento)
            .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));

        if(! asiento.getEstadoAsiento().equals("Disponible")){
            throw new RuntimeException("El asiento no está disponible");
        }

        asiento.setEstadoAsiento("Reservado");
        asientoRepository.save(asiento);

        ReservaTemporal reserva = new ReservaTemporal();
        reserva.setAsiento(asiento);
        reserva.setFechaHoraReserva(LocalDateTime.now());
        reserva.setFechaExpiracion(LocalDateTime.now().plusMinutes(10));
        reserva.setEstado("Reservado");

        return reservaTemporalRepository.save(reserva);
    }

    public String verificarExpiracion(Integer idReserva){

        ReservaTemporal reserva = reservaTemporalRepository.findByIdReserva(idReserva)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if(LocalDateTime.now().isAfter(reserva.getFechaExpiracion())){
            reserva.setEstado("Cancelado");
            reserva.getAsiento().setEstadoAsiento("Disponible");
            asientoRepository.save(reserva.getAsiento());
            reservaTemporalRepository.save(reserva);
            return "Reserva expirada, asiento liberado";
        }
        return "Reserva vigente";

    }


    public String confirmarCompra(Integer idReserva) {

    ReservaTemporal reserva = reservaTemporalRepository.findById(idReserva)
        .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

    // Verificar que no haya expirado
    if (LocalDateTime.now().isAfter(reserva.getFechaExpiracion())) {
        // Liberar el asiento porque expiró
        reserva.setEstado("Cancelado");
        reserva.getAsiento().setEstadoAsiento("Disponible");
        asientoRepository.save(reserva.getAsiento());
        reservaTemporalRepository.save(reserva);
        throw new RuntimeException("La reserva expiró, el asiento fue liberado");
    }

    // Si no expiró, confirmar la venta
    reserva.setEstado("Pagado");  
    reserva.getAsiento().setEstadoAsiento("Vendido");
    asientoRepository.save(reserva.getAsiento());
    reservaTemporalRepository.save(reserva);

    return "Compra confirmada, asiento vendido";
}


}
