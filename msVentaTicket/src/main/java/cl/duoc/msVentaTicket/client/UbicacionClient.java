package cl.duoc.msVentaTicket.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msVentaTicket.dto.UbicacionDTO;

@FeignClient(name = "msUbicacion", url = "http://localhost:8088")
public interface UbicacionClient {

    @GetMapping("/api/v1/ubicaciones/dto/{idUbi}")
    UbicacionDTO buscarUbicacionDTO(@PathVariable Integer idUbi);

    @GetMapping("/api/v1/ubicaciones/dto/buscar-por-nombre/{nombreUbi}")
    UbicacionDTO buscarUbicacionDTOPorNombre(@PathVariable String nombreUbi);

    @PatchMapping("/api/v1/ubicaciones/reducir-stock/{idUbi}")
    Object reducirStock(@PathVariable Integer idUbi);

}
