package cl.duoc.msAsiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msAsiento.dto.AsientoDTO;
import cl.duoc.msAsiento.model.Asiento;
import cl.duoc.msAsiento.service.AsientoService;

@RestController
@RequestMapping("/api/v1/asientos")
public class AsientoController {

    @Autowired
    private AsientoService asientoService; 

    @GetMapping
    public ResponseEntity<List<Asiento>> listar() {
        List<Asiento> lista = asientoService.listarAsientos();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Asiento> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(asientoService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Asiento> guardar(@RequestBody Asiento asiento) {
        return ResponseEntity.ok(asientoService.guardar(asiento));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            asientoService.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


     @DeleteMapping("/numero-asiento/{numAsiento}")
    public ResponseEntity<Void> eliminarPorNumAsiento(@PathVariable String numAsiento) {
        try {
            asientoService.eliminarPorNumAsiento(numAsiento);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/dto/{id}")
public ResponseEntity<AsientoDTO> obtenerAsientoDTO(@PathVariable Integer idAsiento) {
    Asiento asiento = asientoService.buscarPorId(idAsiento);
    AsientoDTO dto = new AsientoDTO(
            asiento.getIdAsiento(),
            asiento.getNumeroAsiento(),
            asiento.getEstadoAsiento()
    );
    return ResponseEntity.ok(dto);
}












    

}
