package cl.duoc.msVentaTicket.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msVentaTicket.dto.EventoDTO;

@FeignClient(name = "msEvento", url = "http://localhost:8085")
public interface EventoClient {

    @GetMapping("/api/v1/eventos/dto/{idEvento}")
    EventoDTO buscarEventoPorId(@PathVariable("idEvento") Integer idEvento);

    

}

