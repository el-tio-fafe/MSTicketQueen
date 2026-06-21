package cl.duoc.msDireccion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa el id y el nombre de una Región en el sistema")
public class RegionDTO {

    @Schema(description = "Id de una Región", example = "1")
    private Integer idRegion;
    
    @Schema(description = "Nombre de una Región", example = "Metropolitana")
    private String nombreRegion;
}
