package cl.duoc.msLogin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa un usuario en el sistema de login, utilizado para transferir información relevante sobre los usuarios y sus permisos entre microservicios.")
public class UsuarioDTO {

    @Schema(description = "Identificador único del usuario.", example = "1")
    private Integer id;
    @Schema(description = "Nombre del usuario.", example = "Juan Silva")
    private String nombreUsuario;
    @Schema(description = "Correo electrónico del usuario.", example = "juan@gmail.com")
    private String correo;
    @Schema(description = "Tipo de usuario, utilizado para saber qué permisos tiene.")
    private TipoUsuarioDTO tipoUsuario; // para saber qué permisos tiene

}