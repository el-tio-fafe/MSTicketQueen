package cl.duoc.msEvento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa la información de un administrador. Contiene el ID, nombre, apellido paterno, RUT y correo electrónico del administrador.")

public class AdministradorDTO {

    @Schema(description = "ID del administrador.", example = "1")
    private Integer idAdm;

    @Schema(description = "Nombre del administrador.", example = "Maria")
    private String nombreAdm;

    @Schema(description = "Apellido paterno del administrador.", example = "Cruz")
    private String apPatAdm;

    @Schema(description = "RUT del administrador.", example = "16517585-2")
    private String rutAdm;

    @Schema(description = "Correo electrónico del administrador.", example = "maria.r@gmail.com")
    private String correoAdm;

}
