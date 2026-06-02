package cl.duoc.msUbicacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa una ubicación en el sistema de control de ubicaciones, utilizado para transferir información relevante sobre las ubicaciones entre microservicios.")

public class UbicacionDTO {

    @Schema(description = "Identificador único de la ubicación.", example = "1")
    private Integer idUbi;

    @Schema(description = "Nombre de la ubicación.", example = "Sala 1")
    private String nombreUbi;

    @Schema(description = "Precio de la ubicación.", example = "5000.0")
    private Double precioUbi;

    @Schema(description = "Stock disponible de la ubicación.", example = "10")
    private Integer stockDisponibleUbi;
    
    @Schema(description = "Indica si la ubicación tiene asiento.", example = "true")
    private Boolean tieneAsiento;

}