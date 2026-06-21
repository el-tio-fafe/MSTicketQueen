package cl.duoc.msDireccion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa solo el nombre de una Comuna en el sistema")

public class ComunaUpdateDTO {

    @Schema(description = "Nombre de una Comuna en el sistema", example = "Quilicura")
    private String nombreComuna;
}
