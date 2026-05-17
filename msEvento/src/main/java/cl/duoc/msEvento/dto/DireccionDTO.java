package cl.duoc.msEvento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DireccionDTO {

    private String nombreRegion;
    private String nombreCiudadProvincia;
    private String nombreComuna;
    private String nombreCalle;
    private Integer numeroCalle;
    private Integer numeroDepto;
    private String letraDepto;

}
