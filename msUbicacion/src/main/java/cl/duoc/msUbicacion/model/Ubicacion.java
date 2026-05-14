package cl.duoc.msUbicacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ubicacion")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUbi;//uso "Ubi" para acortar Ubicacion

    @Column(nullable = false)
    private String nombreUbi;

    @Column(nullable = false)
    private Double precioUbi;

    @Column(nullable = false)
    private Integer capacidadUbi;

    @Column(nullable = false)
    private Integer stockDisponibleUbi;

    @Column(nullable = false)
    private Boolean tieneAsiento;
}
