package cl.duoc.msFacturacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa una forma de pago en el sistema de facturación, utilizado para transferir información relevante sobre las formas de pago entre microservicios.")

public class FormaPagoDTO {

    @Schema(description = "Medio de pago utilizado.", example = "Transferencia")
    private String medioDePago;

}