package cl.duoc.msDireccion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "calle")
public class Calle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numeroCalle;

    @Column(nullable = false)
    private String nombre;
    
    @OneToOne
    @JoinColumn(name = "idComuna", nullable = false)
    private Comuna comuna;

    @OneToOne
    @JoinColumn(name = "idCiudadProvincia", nullable = false)
    private CiudadProvincia ciudadProvincia;

    @OneToOne
    @JoinColumn(name = "idRegion", nullable = false)
    private Region region;
    
}
