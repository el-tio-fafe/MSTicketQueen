package cl.duoc.msLogin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa el tipo de usuario en el sistema de login, utilizado para transferir información relevante sobre los permisos del usuario entre microservicios.")
public class TipoUsuarioDTO {

    @Schema(description = "Identificador único del tipo de usuario.", example = "1")
    private Integer id;
    @Schema(description = "Nombre del tipo de usuario.", example = "ADMIN")
    private String nombreTipoUsuario; // ADMIN, COMPRADOR, ARTISTA, etc.

}