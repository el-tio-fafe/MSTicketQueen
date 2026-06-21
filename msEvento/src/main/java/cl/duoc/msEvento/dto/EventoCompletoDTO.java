package cl.duoc.msEvento.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un evento completo en el sistema. Contiene toda la información del evento, como el ID, cógido, nombre, fecha, hora, estado, id del productor, id del administrador, nombre del recinto y su descripción que es la categoría como por ejemplo 'Concierto'.")
public class EventoCompletoDTO {

    @Schema(description = "ID del evento, generado automáticamente por la base de datos.", example = "1")
    private Integer idEvento;

    @Schema(description = "Código del evento.", example = "EVT-001")
    private String codigoEvento;

    @Schema(description = "Nombre del evento.", example = "50 años de Trayectoria Los Bunkers")
    private String nombreEvento;

    @Schema(description = "Fecha del evento.", example = "2026, 8, 15")
    private LocalDate fechaEvento;

    @Schema(description = "Hora del evento.", example = "20, 0")
    private LocalTime horaEvento;

    @Schema(description = "Estado del evento.", example = "APROBADO")
    private String estadoEvento;

    @Schema(description = "ID del productor del evento.", example = "1")
    private Integer idProd;

    @Schema(description = "ID del administrador del evento.", example = "1")
    private Integer idAdministrador;

    @Schema(description = "Nombre del recinto.", example = "Estadio Nacional")
    private RecintoResumenDTO nombreRecinto;

    @Schema(description = "Corresponde a la categoría a la que corresponde el evento.", example = "Concierto")
    private TipoEventoResumenDTO descripcion;


}
