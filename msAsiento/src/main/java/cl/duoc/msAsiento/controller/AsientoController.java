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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/asientos")
@Tag(name = "AsientoController", description = "Controlador para gestionar los asientos en el sistema")
public class AsientoController {

    @Autowired
    private AsientoService asientoService; 

    @GetMapping
    @Operation(
        summary = "Listar asientos",
        description = "Obtiene la lista de todos los asientos")
    public ResponseEntity<List<Asiento>> listar() {
        List<Asiento> lista = asientoService.listarAsientos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }


    @GetMapping("/estado/{estadoAsiento}")
    @Operation(
        summary = "Listar asientos por estado",
        description = "Obtiene la lista de asientos filtrados por su estado (DISPONIBLE, RESERVADO o VENDIDO)")
    public ResponseEntity<?> listarPorEstado(@PathVariable String estadoAsiento){
        List<Asiento> listaEstados = asientoService.listarPorEstado(estadoAsiento);
        if (listaEstados.isEmpty()){
            return ResponseEntity.badRequest().body("No hay asientos en estado: " + estadoAsiento);
        }
        return ResponseEntity.ok(listaEstados);
    }


    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar asiento por ID",
        description = "Obtiene un asiento específico utilizando su identificador único")
    public ResponseEntity<Asiento> buscarPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(asientoService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("numero/{numAsiento}")
    @Operation(
        summary = "Buscar asiento por número",
        description = "Obtiene un asiento específico utilizando su número")
    public ResponseEntity<Asiento> buscarPorNumero(@PathVariable String numAsiento) {
        try {
            return ResponseEntity.ok(asientoService.buscarPorNumAsiento(numAsiento));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    @Operation(
        summary = "Guardar asiento",
        description = "Crea un nuevo asiento en el sistema")
    public ResponseEntity<?> guardar(@RequestBody Asiento asiento) {
        try {
            return ResponseEntity.ok(asientoService.guardar(asiento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
    @Operation(
        summary = "Obtener AsientoDTO por ID",
        description = "Obtiene un AsientoDTO específico utilizando el identificador del asiento")
    public ResponseEntity<?> obtenerAsientoDTO(@PathVariable("id") Integer idAsiento) {
        try {
            Asiento asiento = asientoService.buscarPorId(idAsiento);
            AsientoDTO dto = new AsientoDTO(
                    asiento.getIdAsiento(),
                    asiento.getNumeroAsiento(),
                    asiento.getEstadoAsiento()
            );
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Retorna 404 si no existe
        }
    }


    @PutMapping("/{idAsiento}")
    @Operation(
        summary = "Actualizar asiento",
        description = "Actualiza la información de un asiento específico")
    public ResponseEntity<Asiento> actualizar(@PathVariable Integer idAsiento, @RequestBody Asiento asiento){
        try {
            Asiento asientoActualizado = asientoService.actualizar(idAsiento, asiento);
            return ResponseEntity.ok(asientoActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
