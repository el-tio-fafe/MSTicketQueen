package cl.duoc.msEvento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO, entidad que representa una productora en el sistema. Contiene información específica de la productora que organiza el evento, como el ID, nombre y correo.")
public class ProductoraDTO {

    @Schema(description = "Identificador único de la productora, generado automáticamente por la base de datos.", example = "1")
    private Integer idProd;

    @Schema(description = "Nombre de la productora.", example = "Productora Ejemplo S.A.")
    private String nombreProd;

    @Schema(description = "Correo electrónico de la productora.", example = "productora@ejemplo.com")
    private String correoProd;

}
