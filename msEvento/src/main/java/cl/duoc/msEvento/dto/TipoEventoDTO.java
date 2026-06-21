package cl.duoc.msEvento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO, entidad que representa un tipo de evento DTO en el sistema.")
public class TipoEventoDTO {

    @Schema(description = "ID del tipo de evento, generado automáticamente por la base de datos.", 
                example = "1")
    private Integer idTipoEvento;

    @Schema(description = "Corresponde a la categoría a la que corresponde el evento.", 
                example = "Concierto")
    private String descripcion;

}
