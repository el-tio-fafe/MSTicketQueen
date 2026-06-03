package cl.duoc.msGestionArtistica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ManagerDTO {

    private Integer idManager;
    private String nombreManager;
    private String apellidoPManager;
    private String correoManager;

}
