package cl.duoc.msDireccion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comuna")
@Schema(description = "Representa una Comuna en el sistema")
public class Comuna {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la Región que es AutoIncrementable")
    private Integer idComuna;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre de la Comuna, el cual es único")
    private String nombreComuna;

    @ManyToOne
    @JoinColumn(name = "idRegion", nullable = false)
    @JsonBackReference("region-comuna")
    @Schema(description = "Nombre de la Región a la cual pertenece la comuna")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "idCiudadProvincia", nullable = false)
    @JsonBackReference("ciudad-comuna")
    @Schema(description = "Nombre de la Ciudad o Provencia a la cual pertenece la comuna")
    private CiudadProvincia ciudadProvincia; 



}
