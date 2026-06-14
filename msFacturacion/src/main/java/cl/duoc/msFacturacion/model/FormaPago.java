package cl.duoc.msFacturacion.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "forma_pago")

public class FormaPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFormaPago;

    @Column(nullable = false, unique = true)
    private String medioDePago;
    
    @OneToMany(mappedBy = "formaPago", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comprobante> comprobante;
 
}
