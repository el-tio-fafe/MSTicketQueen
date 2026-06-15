package cl.duoc.msDireccion.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "region")
@Schema(description = "Representa una Región en el sistema")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
        description = "ID único de la Región que es AutoIncrementable", 
        examples = ("1"))
    private Integer idRegion;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre de la Región, el cual es único", examples = ("Metropolitana"))
    private String nombreRegion;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    @Schema(description = "Lista las Ciudades o Provincias de la Región proporcionada", examples = ("Santiago"))
    @JsonManagedReference
    private List<CiudadProvincia> ciudadesProvincias;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    @JsonManagedReference("region-comuna")
    @Schema(description = "Lista de las Comunas pertenecientes a la Región", examples = ("Quilicura, Huechuraba"))
    private List<Comuna> comunas;

}
