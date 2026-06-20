package cl.duoc.msAsiento.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity 
@Table(name = "reserva_temporal")
@Schema(description = "Entidad que representa una reserva temporal en el sistema")
public class ReservaTemporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Schema(description = "Identificador único de la reserva temporal", example = "1")
    private Integer idReserva;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora de la reserva", example = "2023-10-10T10:00:00")
    private LocalDateTime fechaHoraReserva;  

    @Column(nullable = false)
    @Schema(description = "Fecha y hora de expiración de la reserva", example = "2023-10-10T10:10:00")
    private LocalDateTime fechaExpiracion;  //esta es la fechaHoraReserva + los 10 min 

    @Column(nullable = false)
    @Schema(description = "Estado de la reserva", example = "RESERVADO")
    private String estado;  //RESERVADO, CANCELADO O PAGADO

    @ManyToOne
    @JoinColumn(name = "id_asiento", nullable = false)
    @Schema(description = "Datos del Asiento", example = "ID: 1, Número del Asiento: A100 y Estado del Asiento: DISPONIBLE ")
    private Asiento asiento;


    
}
