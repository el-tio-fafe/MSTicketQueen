package cl.duoc.msDireccion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "calle")
public class Calle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCalle;

    @Column(nullable = false)
    private String nombreCalle;

    @Column(nullable = false)
    private String numeroCalle;

    @Column(nullable = true)
    private String numeroDepto;
    
    @ManyToOne
    @JoinColumn(name = "idComuna", nullable = false)
    @JsonBackReference("comuna-calle")
    private Comuna comuna;

    
}
