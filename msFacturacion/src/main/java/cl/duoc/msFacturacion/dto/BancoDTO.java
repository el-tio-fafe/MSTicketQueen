package cl.duoc.msFacturacion.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa un banco en el sistema de facturación, utilizado para transferir información relevante sobre los bancos entre microservicios.")

public class BancoDTO {

    @Schema(description = "Nombre del banco.", example = "Banco de Chile")
    private String nombreBanco;
    
}