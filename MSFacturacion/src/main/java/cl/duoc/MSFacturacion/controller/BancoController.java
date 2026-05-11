package cl.duoc.MSFacturacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.MSFacturacion.model.banco;
import cl.duoc.MSFacturacion.service.BancoService;

@RestController
@RequestMapping("/api/v1/bancos")
public class BancoController {

    @Autowired
    private BancoService bancoService;

    @GetMapping
    public ResponseEntity<List<banco>> listarBancos() {
        List<banco> bancos = bancoService.listarBancos();
        if (bancos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bancos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerBancoPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(bancoService.buscarBancoPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Object> obtenerBancoPorNombre(@PathVariable String nombre) {
        try {
            return ResponseEntity.ok(bancoService.buscarBancoPorNombre(nombre));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> crearBanco(@RequestBody banco banco) {
        try {
            return ResponseEntity.status(201).body(bancoService.guardarBanco(banco));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarBanco(@PathVariable Integer id, @RequestBody banco bancoActualizado) {
        try {
            return ResponseEntity.ok(bancoService.actualizarBanco(id, bancoActualizado));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarBanco(@PathVariable Integer id) {
        try {
            bancoService.eliminarBancoPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/nombre/{nombre}")
    public ResponseEntity<Object> eliminarBancoPorNombre(@PathVariable String nombre) {
        try {
            bancoService.eliminarBancoPorNombre(nombre);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
