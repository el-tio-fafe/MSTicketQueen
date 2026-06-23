package cl.duoc.msLogin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa un administrador en el sistema de login, utilizado para transferir información relevante sobre los administradores entre microservicios.")

public class AdministradorDTO {


    @Schema(description = "Identificador único del administrador.", example = "1")
    private Integer idAdm;
    @Schema(description = "Nombre del administrador.", example = "Juan")
    private String nombreAdm;
    @Schema(description = "Apellido paterno del administrador.", example = "Pérez")
    private String apPatAdm;
    @Schema(description = "RUT del administrador.", example = "12.345.678-9")
    private String rutAdm;
    @Schema(description = "Correo electrónico del administrador.", example = "juan.perez@ejemplo.com")
    private String correoAdm;
}