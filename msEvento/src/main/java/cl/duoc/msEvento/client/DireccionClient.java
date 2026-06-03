package cl.duoc.msEvento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msEvento.dto.DireccionDTO;

@FeignClient(name = "msDireccion", url = "http://localhost:8084")
public interface DireccionClient {


    @GetMapping("/api/v1/direccion/dto/{idCalle}")
    DireccionDTO buscarDireccionDTO(@PathVariable Integer idCalle);


}
