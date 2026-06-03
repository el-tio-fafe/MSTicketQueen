package cl.duoc.msVentaTicket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private Integer idTicket;

    private Integer numeroAsiento;

    private String nombreUbicacion;

}
