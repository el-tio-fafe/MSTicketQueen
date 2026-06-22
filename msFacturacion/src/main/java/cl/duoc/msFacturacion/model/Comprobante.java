package cl.duoc.msFacturacion.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comprobante")
@Schema(description = "Entidad que representa un comprobante en el sistema de control de eventos. Contiene información del comprobante, como su id, número de combrobante, fecha de emisión, monto total, método de pago, estado de pago. Además, cada comprobante puede tener asociado un banco y una forma de pago en el sistema.")
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del comprobante, generado automáticamente por la base de datos.", example = "1")
    private Integer idComprobante;

    @Column(nullable = false, unique = true)
    @Schema(description = "Número del comprobante.", example = "COMP-001")
    private String numeroComprobante;

    @Column(nullable = false)
    @Schema(description = "Fecha de Emisión del comprobante.", example = "2026-06-22T12:00:00")
    private LocalDateTime fechaEmision;

    @Column(nullable = false)
    @Schema(description = "Monto total del comprobante.", example = "20000")
    private Integer montoTotal;

    @Column(nullable = false)
    @Schema(description = "Método de pago asociado al procesamiento de pago.", example = "Tarjeta de Crédito")
    private String metodoPago;

    @Column(nullable = false)
    @Schema(description = "Estado de pago.", example = "true")
    private boolean estadoPago;

    @ManyToOne
    @JoinColumn(name = "id_forma_pago", nullable = false)
    @Schema(description = "Detalle de la forma de pago asociada.")
    private FormaPago formaPago;

    @ManyToOne
    @JoinColumn(name = "id_banco", nullable = false)
    @Schema(description = "Banco asociado al procesamiento del comprobante.")
    private Banco banco;

    @PrePersist
    public void prePersist(){
        this.fechaEmision = LocalDateTime.now();
    }
 
}
    
