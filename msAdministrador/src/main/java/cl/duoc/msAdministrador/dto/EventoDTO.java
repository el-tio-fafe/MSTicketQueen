package cl.duoc.msAdministrador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {

    private Integer idEvento;

    private String codigoEvento;

    private String nombreEvento;

    private String estadoEvento;

}
