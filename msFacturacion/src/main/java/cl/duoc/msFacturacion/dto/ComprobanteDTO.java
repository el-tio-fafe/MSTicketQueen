package cl.duoc.msFacturacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa un comprobante en el sistema de facturación, utilizado para transferir información relevante sobre los comprobantes entre microservicios.")

public class ComprobanteDTO {

    @Schema(description = "Identificador único del comprobante.", example = "1")
    private Integer idComprobante;

    @Schema(description = "Número del comprobante.", example = "CMP-001")
    private String numeroComprobante;

    @Schema(description = "Monto total del comprobante.", example = "50000")
    private int montoTotal;

    @Schema(description = "Indica si el comprobante tiene el pago realizado.", example = "true")
    private boolean estadoPago;
}