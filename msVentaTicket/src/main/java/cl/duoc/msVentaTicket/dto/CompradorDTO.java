package cl.duoc.msVentaTicket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa un comprador en el sistema de venta de tickets, utilizado para transferir información relevante sobre los compradores entre microservicios.")
public class CompradorDTO {

    @Schema(description = "Identificador único del cliente.", example = "1")
    private Integer idCliente;
    @Schema(description = "RUT del cliente.", example = "12.345.678-9")
    private String rutCliente;
    @Schema(description = "Nombre del cliente.", example = "Juan")
    private String nombreCliente;
    @Schema(description = "Apellido paterno del cliente.", example = "Pérez")
    private String apPaternoCliente;
    @Schema(description = "Correo electrónico del cliente.", example = "juan.perez@ejemplo.com")
    private String correoCliente;

}