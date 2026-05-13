package cl.duoc.MSFacturacion.model;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    private Integer id;
    @Column(nullable = false)
    private Date fechaEmision;
    @Column(nullable = false)
    private int montoTotal;
    @Column(nullable = false)
    private String metodopago;
    @Column(nullable = false)
    private boolean estadopago;
    @OneToOne
    private formaPago formaPago;
    @OneToOne
    private banco banco;
    
}
