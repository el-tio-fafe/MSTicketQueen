package cl.duoc.msEvento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msEvento.dto.AdministradorDTO;

@FeignClient(name = "msAdministrador", url = "http://localhost:8080")
public interface AdministradorClient {

    @GetMapping("/api/v1/administradores/dto/{idAdm}")
    AdministradorDTO buscarAdministradorDTO(@PathVariable Integer idAdm);

}

