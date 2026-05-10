package cl.duoc.msDireccion.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "region")

public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRegion;

    @Column(nullable = false, unique = true)
    private String nombreRegion;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CiudadProvincia> ciudades;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    @JsonManagedReference("region-comuna")
    private List<Comuna> comunas;

}
