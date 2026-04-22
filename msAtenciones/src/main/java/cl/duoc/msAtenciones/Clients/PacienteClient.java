package cl.duoc.msAtenciones.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msAtenciones.DTO.PacienteDTO;

@FeignClient(name = "msPacientes", url = "http://localhost:8081")
public interface PacienteClient {

    @GetMapping("/api/v1/pacientes/{id}")
    PacienteDTO obtenerPaciente(@PathVariable("id") Integer id);

}
