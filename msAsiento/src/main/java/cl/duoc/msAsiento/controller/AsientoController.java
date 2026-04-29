package cl.duoc.msAsiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<Asiento> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(asientoService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("numero/{numAsiento}")
    public ResponseEntity<Asiento> buscarPorNumero(@PathVariable String numAsiento) {
        try {
            return ResponseEntity.ok(asientoService.buscarPorNumAsiento(numAsiento));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Asiento> guardar(@RequestBody Asiento asiento) {
        return ResponseEntity.ok(asientoService.guardar(asiento));
    }

    //NO NECESITAMOS ELIMINAR ASIENTOS EN UN EVENTO, DE TODAS FORMAS ESTE SERÍA EL METODO PARA HACERLO:
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
    //     try {
    //         asientoService.eliminarPorId(id);
    //         return ResponseEntity.noContent().build();
    //     } catch (Exception e) {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

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

 @PutMapping("/{idAsiento}")
    public ResponseEntity<Asiento> actualizar(@PathVariable Integer idAsiento, @RequestBody Asiento asiento){
        try {
            Asiento asientoActualizado = asientoService.actualizar(idAsiento, asiento);
            return ResponseEntity.ok(asientoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
