package cl.duoc.msVentaTicket.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msVentaTicket.dto.CompradorDTO;

@FeignClient(name = "msComprador", url = "http://localhost:8083")
public interface CompradorClient {

    @GetMapping("/api/v1/compradores/dto/{idCliente}")
    CompradorDTO buscarCompradorPorId(@PathVariable("idCliente") Integer idCliente);


}
