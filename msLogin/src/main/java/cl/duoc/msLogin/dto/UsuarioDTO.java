package cl.duoc.msLogin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Integer id;
    private String nombreUsuario;
    private String correo;
    private TipoUsuarioDTO tipoUsuario; // para saber qué permisos tiene

}
