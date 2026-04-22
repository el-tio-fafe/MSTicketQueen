package cl.duoc.msAtenciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO {

    private Integer id;
    private String nombre;
    private String apellido;

}