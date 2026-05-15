package cl.duoc.msEvento.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoListarDTO {

    private Integer idEvento;
    private String nombreEvento;
    private LocalDate fechaEvento;
    private String estadoEvento;
    private Integer idProd;
    private Integer idAdministrador;


}
