package cl.duoc.msEvento.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un evento en el sistema. Contiene información general del evento, como el ID, cógido, nombre, fecha, hora y estado del evento. Además, cada evento puede tener asociado un productor, un administrador, un recinto y un tipo de evento en el sistema.")
public class EventoListarDTO {

    @Schema(description = "ID del evento, generado automáticamente por la base de datos.", example = "1")
    private Integer idEvento;

    @Schema(description = "Nombre del evento.", example = "50 años de Trayectoria Los Bunkers")
    private String nombreEvento;

    @Schema(description = "Fecha del evento.", example = "2026, 8, 15")
    private LocalDate fechaEvento;

    @Schema(description = "Estado del evento.", example = "APROBADO")
    private String estadoEvento;

    @Schema(description = "Identificador único de la productora, generado automáticamente por la base de datos.", example = "1")
    private Integer idProd;

    @Schema(description = "ID del administrador, generado automáticamente por la base de datos.", example = "1")
    private Integer idAdministrador;


}
