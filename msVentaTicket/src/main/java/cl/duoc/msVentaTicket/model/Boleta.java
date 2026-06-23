package cl.duoc.msVentaTicket.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "boleta")
@Schema(description = "Entidad que representa una boleta en el sistema de venta de tickets. Contiene información sobre la emisión, el total, el comprador, el comprobante asociado y los detalles de la compra.")

public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la boleta, generado automáticamente por la base de datos.", example = "1")
    private Integer idBoleta;

    @Column(nullable = true, unique = true)
    @Schema(description = "Número de la boleta.", example = "1")
    private Integer numeroBoleta;

    @Column(nullable = false)
    @Schema(description = "Fecha de emisión de la boleta, generada automáticamente.", example = "2026-06-01")
    private LocalDate fechaEmision;

    @Column(nullable = false)
    @Schema(description = "Hora de emisión de la boleta, generada automáticamente.", example = "10:00:00")
    private LocalTime horaEmision;
    
    @Column(nullable = true)
    @Schema(description = "Total de la boleta, calculado automáticamente en base a los detalles.", example = "10000")
    private Integer totalBoleta;  //SE CALCULA AUTOMATICAMENTE

    //REFENCIA CON msComprador
    @Column(nullable = false)
    @Schema(description = "Identificador del comprador asociado a la boleta.", example = "1")
    private Integer idComprador;

    //REFERENCIA CON msFacturacion
    @Column(nullable = true)
    @Schema(description = "Identificador del comprobante asociado a la boleta.", example = "1")
    private Integer idComprobante;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_boleta")
    @Schema(description = "Lista de detalles de compra asociados a la boleta.")
    private List<Detalle> detalles;


    @PrePersist
    public void prePersist(){
        this.fechaEmision = LocalDate.now();
        this.horaEmision = LocalTime.now();

    }


}