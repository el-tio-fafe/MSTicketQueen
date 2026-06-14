package cl.duoc.msAsiento.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asientos")
@Schema(description = "Entidad que representa un asiento en el sistema") 

public class Asiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del asiento", example = "1")
    private Integer idAsiento;

    @Column(nullable = false, unique = true)
    @Schema(description = "Número del asiento", example = "A100")
    private String numeroAsiento;

    @Column(nullable = false)
    @Schema(description = "Estado del asiento", example = "VENDIDO")
    private String estadoAsiento;  //DISPONIBLE, RESERVADO O VENDIDO


}
 