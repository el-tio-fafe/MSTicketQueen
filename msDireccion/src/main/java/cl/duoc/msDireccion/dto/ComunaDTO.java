package cl.duoc.msDireccion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa el id y el nombre de una Comuna en el sistema")
public class ComunaDTO {

    @Schema(description = "Id de una Comuna", example = "1")
    private Integer idComuna;

    @Schema(description = "Nombre de una Comuna", example = "Quilicura")
    private String nombreComuna;

}
