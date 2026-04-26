package cl.duoc.msAsiento.model;

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
    private String fechaHoraReserva;  //FORMATO: DD-MM-YYYY HH:MM:SS

    @Column(nullable = false)
    private String estado;  //RESERVADO O CANCELADO

    @ManyToOne
    @JoinColumn(name = "id_asiento", nullable = false)
    private Asiento asiento;



}
