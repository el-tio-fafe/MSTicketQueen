package cl.duoc.msFacturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ComprobanteDTO {

    private Integer idComprobante;

    private String numeroComprobante;

    private int montoTotal;

    private boolean estadoPago;
}
