package cl.duoc.msFacturacion.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comprobante")

public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComprobante;

    @Column(nullable = false, unique = true)
    private String numeroComprobante;

    @Column(nullable = false)
    private LocalDateTime fechaEmision;

    @Column(nullable = false)
    private Integer montoTotal;

    @Column(nullable = false)
    private String metodoPago;

    @Column(nullable = false)
    private boolean estadoPago;

    @ManyToOne
    @JoinColumn(name = "id_forma_pago", nullable = false)
    private FormaPago formaPago;

    @ManyToOne
    @JoinColumn(name = "id_banco", nullable = false)
    private Banco banco;

    @PrePersist
    public void prePersist(){
        this.fechaEmision = LocalDateTime.now();
    }
 
}
    
