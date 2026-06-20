package cl.duoc.msComprador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa solo el teléfono del cliente en el sistema")
public class ActualizarTelefonoDTO {

    @Schema(description = "Teléfono del cliente", example = "+569 85478562")
    private String telefonoCliente;
}
