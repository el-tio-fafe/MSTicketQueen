package cl.duoc.msVentaTicket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EventoDTO {

    private Integer idEvento;

    private String codigoEvento;

    private String nombreEvento;

    private String estadoEvento;

}
