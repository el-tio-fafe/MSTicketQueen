package cl.duoc.msDireccion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa solo el nombre de una Ciudad o Provincia en el sistema")
public class CiudadProvinciaUpdateDTO {

    @Schema(description = "Nombre de una Ciudad o Provincia", example = "Santiago")
    private String nombreCiudadProvincia;
    
}
