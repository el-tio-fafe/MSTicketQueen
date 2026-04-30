package cl.duoc.msAsiento.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity 
@Table(name = "reserva_temporal")
public class ReservaTemporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer idReserva;

    @Column(nullable = false)
    private LocalDateTime fechaHoraReserva;  

    @Column(nullable = false)
    private LocalDateTime fechaExpiracion;  //esta es la fechaHoraReserva + los 10 min 

    @Column(nullable = false)
    private String estado;  //RESERVADO, CANCELADO O PAGADO

    @ManyToOne
    @JoinColumn(name = "id_asiento", nullable = false)
    private Asiento asiento;
}
