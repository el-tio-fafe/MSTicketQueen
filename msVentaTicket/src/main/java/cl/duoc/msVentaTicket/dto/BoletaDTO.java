package cl.duoc.msVentaTicket.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoletaDTO {

    private Integer numeroBoleta;
    private LocalDate fecha;
    private Integer totalBoleta;
    private CompradorDTO comprador;
    private EventoDTO evento;


    

}
