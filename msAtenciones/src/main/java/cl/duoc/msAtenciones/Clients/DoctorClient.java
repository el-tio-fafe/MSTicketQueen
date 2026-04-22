package cl.duoc.msAtenciones.Clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msAtenciones.DTO.DoctorDTO;

@FeignClient(name = "msDoctores", url = "http://localhost:8082")
public interface DoctorClient {

    @GetMapping("/api/v1/doctores/{id}")
    DoctorDTO obtenerDoctor(@PathVariable("id") Integer id);

}
