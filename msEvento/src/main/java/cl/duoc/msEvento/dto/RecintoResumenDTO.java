package cl.duoc.msEvento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO, entidad que representa solo el nombre del recinto en el sistema.")
public class RecintoResumenDTO {

    @Schema(description = "Nombre del recinto.", example = "Estadio Nacional")
    private String nombreRecinto;

}
