package cl.duoc.msAdministrador.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msAdministrador.dto.EventoDTO;

@FeignClient(name = "msEvento", url = "http://localhost:8085")
public interface EventoClient {

    @GetMapping("/api/v1/eventos/dto/{idEvento}")
    EventoDTO buscarEventoDTO(@PathVariable Integer idEvento);

    @GetMapping("/api/v1/eventos/listartodos")
    List<EventoDTO> listarEventos();

    @GetMapping("/api/v1/eventos/listar/estado/{estadoEvento}")
    List<EventoDTO> listarEventosPorEstado(@PathVariable String estadoEvento);

}
