package cl.duoc.msEvento.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO, Entidad que representa el detalle de un evento en el sistema. Contiene información específica del evento, como el ID, nombre, fecha, estado, productor y un administrador.")
public class EventoDetalleDTO {

    @Schema(description = "ID del evento, generado automáticamente por la base de datos.", 
                example = "1")
    private Integer idEvento;

    @Schema(description = "Nombre del evento.", example = "50 años de Trayectoria Los Bunkers")
    private String nombreEvento;

    @Schema(description = "Fecha del evento.", example = "2026, 8, 15")
    private LocalDate fechaEvento;

    @Schema(description = "Estado del evento.", example = "APROBADO")
    private String estadoEvento;

    @Schema(description = "Productora que realiza el evento. Muestra sólo el ID, nombre y correo.", example = "Id: 1, Nombre: Bizarro Producciones, Correo: bizarro@gmail.com")
    private ProductoraDTO productora;
    
    @Schema(description = "Administrador que autoriza el evento. Muestra el ID, nombre, apellido paterno, rut y correo.", example = "Id: 1, Nombre: Maria, Apellido Paterno: Cruz, Rut: 16517585-2, Correo: maria.r@gmail.com")
    private AdministradorDTO administrador;

}
