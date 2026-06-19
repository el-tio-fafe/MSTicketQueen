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
    @Schema(description = "ID único de la calle que es Autoincrementable", example = "1")
    private Integer idCalle;

    @Column(nullable = false)
    @Schema(description = "Nombre de la calle", example = "Américo Vespucio")
    private String nombreCalle;

    @Column(nullable = false)
    @Schema(description = "Número de la calle", example = "1500")
    private String numeroCalle;

    @Column(nullable = true)
    @Schema(description = "Número del departamento si corresponde", example = "Depto 402")
    private String numeroDepto;
    
    @ManyToOne
    @JoinColumn(name = "idComuna", nullable = false)
    @JsonBackReference("comuna-calle")
    @Schema(description = "Nombre de la Comuna a la cual pertenece la Calle", example = "Huechuraba")
    private Comuna comuna;

    
}
