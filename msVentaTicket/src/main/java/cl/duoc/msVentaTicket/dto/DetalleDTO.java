package cl.duoc.msVentaTicket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa el detalle de una compra en el sistema de venta de tickets, utilizado para transferir información relevante sobre la cantidad entre microservicios.")
public class DetalleDTO {

    @Schema(description = "Cantidad de tickets comprados en este detalle.", example = "1")
    private Integer cantidad;

}