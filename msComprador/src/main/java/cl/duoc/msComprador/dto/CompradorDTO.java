package cl.duoc.msComprador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa el id, rut, nombre, apellido paterno y correo del cliente en el sistema")
public class CompradorDTO {

    @Schema(description = "ID (Identificador único) del cliente", example = "1")
    private Integer idCliente;

    @Schema(description = "RUT del cliente", example = "26586516-8")
    private String rutCliente;
    
    @Schema(description = "Nombre del cliente", example = "Matias")
    private String nombreCliente;

    @Schema(description = "Apellido Paterno del cliente", example = "Gutierrez")
    private String apPaternoCliente;

    @Schema(description = "Correo del cliente", example = "matias@correo.com")
    private String correoCliente;
}
