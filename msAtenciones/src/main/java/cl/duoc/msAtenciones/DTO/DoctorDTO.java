package cl.duoc.msAtenciones.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {

    private Integer id;
    private String nombre;
    private String especialidad; //solo el nombre de la especialidad a

}
