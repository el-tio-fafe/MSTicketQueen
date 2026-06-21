package cl.duoc.msDireccion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa solo el nombre de una Calle en el sistema")
public class CalleUpdateDTO {

    @Schema(description = "Nombre de una Calle", example = "Av. Manuel Antonio Matta")
    private String nombreCalle;

}
