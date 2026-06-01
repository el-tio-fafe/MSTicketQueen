package cl.duoc.msGestionArtistica.model;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "manager")
@Schema(description = "Entidad que representa a un manager en el sistema de gestión artística. Contiene información personal y de contacto del manager.")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del manager, generado automáticamente por la base de datos.", example = "1")
    private Integer idMngr;// uso "Mngr" para acortar Manager

    @Column(nullable = false)
    @Schema(description = "RUT del manager.", example = "12.345.678-9")
    private String rutMngr;

    @Column(nullable = false)
    @Schema(description = "Nombre completo del manager.", example = "María González")
    private String nombreMngr;

    @Column(nullable = false)
    @Schema(description = "Apellido paterno del manager.", example = "González")
    private String apPaternoMngr;

    @Column(nullable = false)
    @Schema(description = "Apellido materno del manager.", example = "Pérez")
    private String apMaternoMngr;

    @Column(nullable = false)
    @Schema(description = "Correo electrónico del manager.", example = "maria.gonzalez@ejemplo.com")
    private String correoMngr;

    @Column(nullable = false)
    @Schema(description = "Número de teléfono del manager.", example = "987654321")
    private String telefonoMngr;

    @ManyToMany                                         // antertiormente era @OneToMany, pero se cambió a @ManyToMany para reflejar la relación real entre Manager y Artista 
                                                        // (ya que un Manager puede manejar a varios Artistas) 
    @JoinTable(name = "manager_artista",                // nombre de la tabla intermedia
    joinColumns = @JoinColumn(name = "idMngr"),         // columna que referencia a Manager
    inverseJoinColumns = @JoinColumn(name = "idArt"))   // columna que referencia a Artista
    private List<Artista> artistas = new ArrayList<>(); // lista que guardara los artistas asociados a cada manager, es decir, los artistas que maneja cada manager
}