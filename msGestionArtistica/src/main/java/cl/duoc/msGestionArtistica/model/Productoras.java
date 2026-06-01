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
@Table(name = "productoras")
@Schema(description = "Entidad que representa a una productora en el sistema de gestión artística. Contiene información personal y de contacto de la productora, así como la relación con los managers asociados a ella.")
public class Productoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la productora, generado automáticamente por la base de datos.", example = "1")
    private Integer idProd;

    @Column(nullable = false)
    @Schema(description = "RUT de la productora.", example = "12.345.678-9")
    private String rutProd;

    @Column(nullable = false)
    @Schema(description = "Nombre de la productora.", example = "Productora Ejemplo S.A.")
    private String nombreProd;

    @Column(nullable = false)
    @Schema(description = "Correo electrónico de la productora.", example = "productora@ejemplo.com")
    private String correoProd; 

    @Column(nullable = false)
    @Schema(description = "Número de teléfono de la productora.", example = "987654321")
    private String telefonoProd;

    @ManyToMany                                         // anteriormente era @OneToMany, pero se cambió a @ManyToMany para reflejar la relación real entre Productoras y Manager 
                                                        // (ya que una Productora puede tener varios Managers)
    @JoinTable(name = "productoras_manager",            // nombre de la tabla intermedia
    joinColumns = @JoinColumn(name = "idProd"),         // columna que referencia a Productoras
    inverseJoinColumns = @JoinColumn(name = "idMngr"))  // columna que referencia a Manager
    private List<Manager> managers = new ArrayList<>(); // lista que guardara los managers asociados a cada productora, es decir, los managers que trabajan para cada productora
}
    