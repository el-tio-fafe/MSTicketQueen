package cl.duoc.msDireccion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CalleDTO {
    
    private Integer idCalle;
    private String nombre;
    private Integer numeroCalle;
    private Integer numeroDepto;
    private String letraDepto;

}
