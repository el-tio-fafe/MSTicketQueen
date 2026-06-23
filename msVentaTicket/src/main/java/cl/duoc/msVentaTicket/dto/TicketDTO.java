package cl.duoc.msVentaTicket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa un ticket en el sistema de venta de tickets, utilizado para transferir información relevante sobre los tickets entre microservicios.")
public class TicketDTO {

    @Schema(description = "Identificador único del ticket.", example = "1")
    private Integer idTicket;

    @Schema(description = "Número de asiento asociado al ticket.", example = "1")
    private Integer numeroAsiento;

    @Schema(description = "Nombre de la ubicación del ticket.", example = "Platea")
    private String nombreUbicacion;

}