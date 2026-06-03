package cl.duoc.msEvento.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoDetalleDTO {

    private Integer idEvento;
    private String nombreEvento;
    private LocalDate fechaEvento;
    private String estadoEvento;
    private ProductoraDTO productora;
    private AdministradorDTO administrador;

}
