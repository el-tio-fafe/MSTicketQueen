package cl.duoc.msVentaTicket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")


public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTicket;

    @Column(nullable = false, unique = true)
    private String codigoQR;

    @Column(nullable = false)
    private Integer idEvento;


    @Column(nullable = true)
    private String numeroAsiento;

    @Column(nullable = true)
    private String nombreUbicacion; //ESTE EL NOMBRE DE LA ZONA POR EJ: CANCHA VIP, CANCHA GENERAL, PLATEA, PALCO, TRIBUNA ETC

    @PrePersist
    public void prePersist(){
        this.codigoQR = java.util.UUID.randomUUID().toString();
    }

    

}
