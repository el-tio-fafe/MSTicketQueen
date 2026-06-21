package cl.duoc.msEvento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO, entidad que representa un evento en el sistema. Contiene información específica como el ID, cógido, nombre y estado del evento.")
public class EventoDTO {

    @Schema(description = "ID del evento, generado automáticamente por la base de datos.", 
                example = "1")
    private Integer idEvento;

    @Schema(description = "Código del evento.", example = "EVT-001")
    private String codigoEvento;

    @Schema(description = "Nombre del evento.", example = "50 años de Trayectoria Los Bunkers")
    private String nombreEvento;

    @Schema(description = "Estado del evento.", example = "APROBADO")
    private String estadoEvento;



}
