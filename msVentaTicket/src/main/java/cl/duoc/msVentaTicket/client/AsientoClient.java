package cl.duoc.msVentaTicket.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import cl.duoc.msVentaTicket.dto.AsientoDTO;

@FeignClient(name = "msAsiento", url = "http://localhost:8081")
public interface AsientoClient {

    @GetMapping("/api/v1/asientos/dto/id/{id}")
    AsientoDTO buscarAsientoPorId(@PathVariable("id") Integer id);

    @GetMapping("/api/v1/asientos/dto/numero/{numeroAsiento}")
    AsientoDTO buscarAsientoPorNum(@PathVariable("numeroAsiento") String numAsiento);

    @PostMapping("/api/v1/reservas/{idAsiento}")
    Object crearReservaTemporal(@PathVariable Integer idAsiento);

    @PutMapping("/api/v1/reservas/confirmar/numero/{numeroAsiento}")
    String confirmarCompra(@PathVariable String numeroAsiento);


}
