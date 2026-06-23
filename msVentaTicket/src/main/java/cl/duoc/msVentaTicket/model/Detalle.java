package cl.duoc.msVentaTicket.model;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidad que representa el detalle de una compra de ticket en el sistema de venta de tickets. Contiene información sobre la cantidad, el precio, el descuento y el ticket asociado.")

public class Detalle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del detalle, generado automáticamente por la base de datos.", example = "1")
    private Integer idDetalle;

    @Column(nullable = false)
    @Schema(description = "Cantidad de tickets comprados en este detalle.", example = "1")
    private Integer cantidad;

    @Column(nullable = false)
    @Schema(description = "Precio unitario del ticket.", example = "10000")
    private Integer precioUnitario;
    
    @Column(nullable = false)
    @Schema(description = "Descuento aplicado al ticket.", example = "0")
    private Integer descuento;

    @Column(nullable = false)
    @Schema(description = "Subtotal calculado automáticamente en base al precio, descuento y cantidad.", example = "10000")
    private Integer subTotal;

    @OneToOne
    @JoinColumn(name = "id_ticket", nullable = false)
    @Schema(description = "Ticket asociado a este detalle.")
    private Ticket ticket;

    @PrePersist
    public void prePersist(){
        this.subTotal = (this.precioUnitario - this.descuento) * this.cantidad; 
    }


}