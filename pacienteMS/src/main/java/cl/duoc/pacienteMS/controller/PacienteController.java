package cl.duoc.pacienteMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.pacienteMS.model.Paciente;
import cl.duoc.pacienteMS.service.PacienteService;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    //GET: listar todos
    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        List<Paciente> pacientes = service.listarPacientes();

        if (pacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pacientes);
    }

    //GET: buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Integer id) {
        try {
            Paciente paciente = service.buscarPorId(id);
            return ResponseEntity.ok(paciente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //GET: buscar por RUT
    @GetMapping("/rut/{rut}")
    public ResponseEntity<Paciente> buscarPorRut(@PathVariable String rut) {
        try {
            Paciente paciente = service.buscarPorRut(rut);
            return ResponseEntity.ok(paciente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //POST: crear paciente completo
    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        Paciente nuevo = service.guardar(paciente);
        return ResponseEntity.ok(nuevo);
    }

    //PUT: actualizar paciente
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(
            @PathVariable Integer id,
            @RequestBody Paciente paciente) {

        try {
            Paciente actualizado = service.actualizar(id, paciente);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //DELETE: eliminar paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}