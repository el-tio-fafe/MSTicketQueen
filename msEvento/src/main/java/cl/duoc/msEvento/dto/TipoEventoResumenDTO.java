package cl.duoc.msEvento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO, entidad que representa solo la descripción del tipo de evento en el sistema.")
public class TipoEventoResumenDTO {

    @Schema(description = "Corresponde a la categoría a la que corresponde el evento.", 
                example = "Concierto")
    private String descripcion;
}
