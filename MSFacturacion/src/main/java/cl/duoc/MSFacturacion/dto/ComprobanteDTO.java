package cl.duoc.MSFacturacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ComprobanteDTO {

    private String numeroComprobante;

    private int montototal;

    private boolean estadopago;
}
