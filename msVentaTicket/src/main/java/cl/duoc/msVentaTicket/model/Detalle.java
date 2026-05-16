package cl.duoc.msVentaTicket.model;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalle")

public class Detalle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalle;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private int precioUnitario;
    
    @Column(nullable = false)
    private int descuento;

    @Column(nullable = false)
    private Integer subTotal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ticket", nullable = false)
    private Ticket ticket;

    @PrePersist
    public void prePersist(){
        this.subTotal = (this.precioUnitario - this.descuento) * this.cantidad; 
    }


}
