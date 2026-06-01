package cl.duoc.msGestionArtistica.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "artista")
@Schema(description = "Entidad que representa a un artista en el sistema de gestión artística. Contiene información personal y de contacto del artista.")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del artista, generado automáticamente por la base de datos.", example = "1")
    private Integer idArt;//uso "Art" para acortar Artista

    @Column(nullable = false)
    @Schema(description = "RUT del artista.", example = "12.345.678-9")
    private String rutArt;

    @Column(nullable = false)
    @Schema(description = "Nombre completo del artista.", example = "Juan Pérez")
    private String nombreArt;

    @Column(nullable = false)
    @Schema(description = "Correo electrónico del artista.", example = "juan.perez@ejemplo.com")
    private String correoArt;

    @Column(nullable = false)
    @Schema(description = "Número de teléfono del artista.", example = "987654321")
    private String telefonoArt;
    
}
