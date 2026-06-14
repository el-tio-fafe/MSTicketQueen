package cl.duoc.msFacturacion.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "banco")

public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBanco;

    @Column(nullable = false, unique = true)
    private String nombreBanco;

    @OneToMany(mappedBy = "banco", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comprobante> comprobantes;

}
