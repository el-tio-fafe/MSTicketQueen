package cl.duoc.msEvento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO, entidad que representa un recinto en el sistema. Contiene información específica del recinto donde se realizará el evento.")
public class RecintoDTO {

    @Schema(description = "ID del recinto, generado automáticamente por la base de datos.", 
                example = "1")
    private Integer idRecinto;

    @Schema(description = "Nombre del recinto.", example = "Estadio Nacional")
    private String nombreRecinto;

    @Schema(description = "Capacidad del recinto, que corresponde a la cantidad de personas que pueden estar dentro del recinto.", example = "60000")
    private Integer capacidadRecinto;

    @Schema(description = "DTO de Dirección en donde se realizará el evento, incluye los siguientes datos de la Direccion como la Región, Ciudad/Provincia, Comuna, Calle, Número y Depto si corresponde", examples = ("Metropolitana, Santiago, Quilicura, Américo Vespucio, 1500, 402, C"))
    private DireccionDTO direccion;



}
