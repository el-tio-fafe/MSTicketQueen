package cl.duoc.msDireccion.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ciudad_provincia")
@Schema(description = "Representa una Ciudad o Provincia en el sistema")

public class CiudadProvincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la Ciudad o Provincia que es AutoIncrementable")
    private Integer idCiudadProvincia;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre único de la Ciudad o Provincia que es único")
    private String nombreCiudadProvincia;

    @ManyToOne
    @JoinColumn(name = "idRegion", nullable = false)
    @JsonBackReference
    @Schema(description = "Región a la cual pertenece la Ciudad o Provincia")
    private Region region;

    @OneToMany(mappedBy = "ciudadProvincia", cascade = CascadeType.ALL)
    @JsonIgnore   
    @Schema(description = "Lista de Comunas que pertenecen a la Ciudad o Provincia")
    private List<Comuna> comunas;


}
