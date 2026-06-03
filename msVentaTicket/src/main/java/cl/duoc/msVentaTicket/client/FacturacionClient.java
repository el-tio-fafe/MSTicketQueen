package cl.duoc.msVentaTicket.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msVentaTicket.dto.ComprobanteDTO;

@FeignClient(name = "msFacturacion", url = "http://localhost:8086")
public interface FacturacionClient {

    @GetMapping("/api/v1/facturacion/dto/{idComprobante}")
    ComprobanteDTO buscarComprobanteDTOPorId(@PathVariable("idComprobante") Integer idComprobante);


}
