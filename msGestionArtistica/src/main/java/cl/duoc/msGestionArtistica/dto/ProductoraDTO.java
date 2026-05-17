package cl.duoc.msGestionArtistica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductoraDTO {

    private Integer idProd;
    private String nombreProd;
    private String correoProd;

}
