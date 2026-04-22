package cl.duoc.MSClientes.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "artista")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idArt;//uso "Art" para acortar Artista

    @Column(nullable = false)
    private String rutArt;

    @Column(nullable = false)
    private String nombreArt;

    @Column(nullable = false)
    private String correoArt;

    @Column(nullable = false)
    private String telefonoArt;
}
