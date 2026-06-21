package cl.duoc.msEvento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa una dirección completa en el sistema")
public class DireccionDTO {

    @Schema(description = "Nombre de la Región, el cual es único", examples = ("Metropolitana"))
    private String nombreRegion;

    @Schema(description = "Nombre único de la Ciudad o Provincia que es único", examples = ("Santiago"))
    private String nombreCiudadProvincia;

    @Schema(description = "Nombre de la Comuna, el cual es único", examples = ("Quilicura"))
    private String nombreComuna;

    @Schema(description = "Nombre de la calle", example = "Américo Vespucio")
    private String nombreCalle;

    @Schema(description = "Número de la calle", example = "1500")
    private Integer numeroCalle;

    @Schema(description = "Número del departamento si corresponde", example = "402")
    private Integer numeroDepto;

    @Schema(description = "Letra del departamento si corresponde", example = "C")
    private String letraDepto;

}
