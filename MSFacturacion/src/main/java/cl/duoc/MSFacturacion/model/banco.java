package cl.duoc.MSFacturacion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "banco")
public class banco {
    @Id
    @GeneratedValue
    private Integer idBanco;
    @Column(nullable = false)
    private String nombreBanco;
    @OneToOne
    private Comprobante comprobante;

    

}
