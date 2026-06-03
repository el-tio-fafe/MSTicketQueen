package cl.duoc.msLogin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msLogin.dto.CompradorDTO;

@FeignClient(name = "msComprador", url = "http://localhost:8083")
public interface CompradorClient {

     @GetMapping("/api/v1/compradores/dto/{idCliente}")
    CompradorDTO buscarCompradorDTO(@PathVariable Integer idCliente);

    @GetMapping("/api/v1/compradores/dto/correo/{correoCliente}")
    CompradorDTO buscarCompradorDTOPorCorreo(@PathVariable String correoCliente);

}
