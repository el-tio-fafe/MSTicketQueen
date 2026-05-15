package cl.duoc.msEvento.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoCompletoDTO {

    private Integer idEvento;
    private String codigoEvento;
    private String nombreEvento;
    private LocalDate fechaEvento;
    private LocalTime horaEvento;
    private String estadoEvento;
    private Integer idProd;
    private Integer idAdministrador;
    private RecintoResumenDTO nombreRecinto;
    private TipoEventoResumenDTO descripcion;


}
