package cl.duoc.MSFacturacion.model;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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
    private Integer idComprobante;
    @Column(nullable = false, unique = true)
    private String numeroComprobante;
    @Column(nullable = false)
    private Date fechaEmision;
    @Column(nullable = false)
    private int montoTotal;
    @Column(nullable = false)
    private String metodopago;
    @Column(nullable = false)
    private boolean estadopago;
    @ManyToOne
    @JoinColumn(name = "idFormaPago", nullable = false)
    private formaPago formaPago;
    @ManyToOne
    @JoinColumn(name = "idBanco", nullable = false)
    private banco banco;
 
}
    
