package cl.duoc.msAdministrador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa la información de un evento. Contiene el ID, código, nombre y estado del evento.")
public class EventoDTO {

    @Schema(description = "ID del evento.", example = "1")
    private Integer idEvento;

    @Schema(description = "Código del evento.", example = "EVT-001")
    private String codigoEvento;

    @Schema(description = "Nombre del evento.", example = "50 años de Trayectoria Los Bunkers")
    private String nombreEvento;

    @Schema(description = "Estado del evento.", example = "APROBADO")
    private String estadoEvento;

}
