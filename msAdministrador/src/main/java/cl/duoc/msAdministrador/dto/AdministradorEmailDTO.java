package cl.duoc.msAdministrador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa la información de un administrador para consultas por correo electrónico. Contiene el correo electrónico del administrador.")
public class AdministradorEmailDTO {

    @Schema(description = "Correo electrónico del administrador.", example = "maria.r@gmail.com")
    private String correoAdm;
}
