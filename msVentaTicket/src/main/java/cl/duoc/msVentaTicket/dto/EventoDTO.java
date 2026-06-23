package cl.duoc.msVentaTicket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa un evento en el sistema de venta de tickets, utilizado para transferir información relevante sobre los eventos entre microservicios.")

public class EventoDTO {

    @Schema(description = "Identificador único del evento.", example = "1")
    private Integer idEvento;

    @Schema(description = "Código del evento.", example = "EVT-001")
    private String codigoEvento;

    @Schema(description = "Nombre del evento.", example = "Concierto Los Bunkers")
    private String nombreEvento;

    @Schema(description = "Estado del evento.", example = "APROBADO")
    private String estadoEvento;

}