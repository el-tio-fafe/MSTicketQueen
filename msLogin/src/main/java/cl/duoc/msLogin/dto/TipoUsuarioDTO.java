package cl.duoc.msLogin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoUsuarioDTO {

    private Integer id;
    private String nombreTipoUsuario; // ADMIN, COMPRADOR, ARTISTA, etc.

}
