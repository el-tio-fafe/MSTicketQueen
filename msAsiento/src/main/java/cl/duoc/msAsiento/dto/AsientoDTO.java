package cl.duoc.msAsiento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AsientoDTO {

    private Integer idAsiento;
    private String numeroAsiento;
    private String estadoAsiento;
    
}
