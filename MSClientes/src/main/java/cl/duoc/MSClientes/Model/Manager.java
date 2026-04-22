package cl.duoc.MSClientes.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "manager")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMngr;//uso "Mngr" para acortar Manager

    @Column(nullable = false)
    private String rutMngr;

    @Column(nullable = false)
    private String nombreMngr;

    @Column(nullable = false)
    private String apPaternoMngr;//nombre largo apellido paterno manager

    @Column(nullable = false)
    private String apMaternoMngr;//nombre largo apellido materno manager

    @Column(nullable = false)
    private String correoMngr;

    @Column(nullable = false)
    private String telefonoMngr;

    @ManyToOne
    @JoinColumn(name = "idArt", nullable = false)
    private Artista artista;
}
