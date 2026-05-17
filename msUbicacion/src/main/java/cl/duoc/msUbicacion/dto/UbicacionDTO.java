package cl.duoc.msUbicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UbicacionDTO {

    private Integer idUbi;
    private String nombreUbi;
    private Double precioUbi;
    private Integer stockDisponibleUbi;
    private Boolean tieneAsiento;

}
