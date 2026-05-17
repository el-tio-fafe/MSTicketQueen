package cl.duoc.msEvento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.msEvento.dto.ProductoraDTO;

@FeignClient(name = "msGestionArtistica", url = "http://localhost:8082")
public interface ProductoraClient {

    @GetMapping("/api/v1/productoras/dto/{idProd}")
    ProductoraDTO buscarProductoraDTO(@PathVariable Integer idProd);

}


