package cl.duoc.msVentaTicket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompradorDTO {

    private Integer idCliente;
    private String rutCliente;
    private String nombreCliente;
    private String apPaternoCliente;
    private String correoCliente;

}
