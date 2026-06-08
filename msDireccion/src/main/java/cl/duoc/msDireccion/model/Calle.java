package cl.duoc.msDireccion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "calle")
@Schema(description = "Representa una Calle en el sistema")
public class Calle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la calle que es Autoincrementable")
    private Integer idCalle;

    @Column(nullable = false)
    @Schema(description = "Nombre de la calle")
    private String nombreCalle;

    @Column(nullable = false)
    @Schema(description = "Número de la calle")
    private String numeroCalle;

    @Column(nullable = true)
    @Schema(description = "Número del departamento si corresponde")
    private String numeroDepto;
    
    @ManyToOne
    @JoinColumn(name = "idComuna", nullable = false)
    @JsonBackReference("comuna-calle")
    @Schema(description = "Nombre de la Comuna a la cual pertenece la Calle")
    private Comuna comuna;

    
}
