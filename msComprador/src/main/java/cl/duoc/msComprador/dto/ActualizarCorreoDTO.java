package cl.duoc.msComprador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa solo el correo del cliente en el sistema")
public class ActualizarCorreoDTO {

    @Schema(description = "Correo del cliente", example = "mati@gmail.com")
    private String correoCliente;
}
