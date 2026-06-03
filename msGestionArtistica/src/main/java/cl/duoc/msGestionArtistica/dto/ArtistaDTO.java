package cl.duoc.msGestionArtistica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ArtistaDTO {

    private Integer idArtista;
    private String nombreArtista;
    private String correoArtista;

}
