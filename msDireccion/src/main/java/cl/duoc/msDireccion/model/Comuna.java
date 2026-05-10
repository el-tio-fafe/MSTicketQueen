package cl.duoc.msDireccion.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comuna")

public class Comuna {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComuna;

    @Column(nullable = false, unique = true)
    private String nombreComuna;

    @ManyToOne
    @JoinColumn(name = "idRegion", nullable = false)
    @JsonBackReference("region-comuna")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "idCiudadProvincia", nullable = false)
    @JsonBackReference("ciudad-comuna")
    private CiudadProvincia ciudadProvincia; 



}
