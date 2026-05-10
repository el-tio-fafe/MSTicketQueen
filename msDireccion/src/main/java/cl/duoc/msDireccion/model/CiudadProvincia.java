package cl.duoc.msDireccion.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ciudad_provincia")

public class CiudadProvincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCiudadProvincia;

    @Column(nullable = false, unique = true)
    private String nombreCiudadProvincia;

    @ManyToOne
    @JoinColumn(name = "idRegion", nullable = false)
    @JsonBackReference
    private Region region;

    @OneToMany(mappedBy = "ciudad", cascade = CascadeType.ALL)
    @JsonManagedReference("ciudad-comuna")
    private List<Comuna> comunas;


}
