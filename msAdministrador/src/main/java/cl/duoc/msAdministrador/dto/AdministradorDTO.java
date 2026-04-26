package cl.duoc.msAdministrador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdministradorDTO {

    private Integer idAdm;
    private String nombreAdm;
    private String apPatAdm;
    private String rutAdm;

}
