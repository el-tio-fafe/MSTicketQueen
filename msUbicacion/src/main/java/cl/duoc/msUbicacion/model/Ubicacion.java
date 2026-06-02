package cl.duoc.msUbicacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ubicacion")
@Schema(description = "Entidad que representa una ubicación en el sistema de control de ubicaciones. Contiene información sobre el nombre, precio, capacidad, stock disponible y si tiene asientos o no.")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la ubicación, generado automáticamente por la base de datos.", example = "1")
    private Integer idUbi;//uso "Ubi" para acortar Ubicacion

    @Column(nullable = false)
    @Schema(description = "Nombre de la ubicación.", example = "Cancha Central")
    private String nombreUbi;

    @Column(nullable = false)
    @Schema(description = "Precio de la ubicación.", example = "50.00")
    private Double precioUbi;

    @Column(nullable = false)
    @Schema(description = "Capacidad de la ubicación.", example = "100")
    private Integer capacidadUbi;

    @Column(nullable = false)
    @Schema(description = "Stock disponible de la ubicación.", example = "50")
    private Integer stockDisponibleUbi;

    @Column(nullable = false)
    @Schema(description = "Indica si la ubicación tiene asientos disponibles.", example = "true")
    private Boolean tieneAsiento; 
}
