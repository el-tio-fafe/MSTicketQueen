package cl.duoc.msGestionArtistica.Model;

import java.util.List;

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
    private Integer idMngr;// uso "Mngr" para acortar Manager

    @Column(nullable = false)
    private String rutMngr;

    @Column(nullable = false)
    private String nombreMngr;

    @Column(nullable = false)
    private String apPaternoMngr;// nombre largo apellido paterno manager

    @Column(nullable = false)
    private String apMaternoMngr;// nombre largo apellido materno manager

    @Column(nullable = false)
    private String correoMngr;

    @Column(nullable = false)
    private String telefonoMngr;

    @ManyToMany// antertiormente era @OneToMany, pero se cambió a @ManyToMany para reflejar la relación real entre Manager y Artista 
               // (ya que un Manager puede manejar a varios Artistas) 
    @JoinTable(name = "manager_artista",// nombre de la tabla intermedia
    joinColumns = @JoinColumn(name = "idMngr"),// columna que referencia a Manager
    inverseJoinColumns = @JoinColumn(name = "idArt"))// columna que referencia a Artista
    private List<Artista> artistas;
}
