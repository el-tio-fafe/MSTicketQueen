package cl.duoc.msAsiento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa un asiento en el sistema")

public class AsientoDTO {

    @Schema(description = "Identificador único del asiento", example = "1")
    private Integer idAsiento;

    @Schema(description = "Número del asiento", example = "A100")
    private String numeroAsiento;
    
    @Schema(description = "Estado del asiento", example = "VENDIDO")
    private String estadoAsiento;
    
}
