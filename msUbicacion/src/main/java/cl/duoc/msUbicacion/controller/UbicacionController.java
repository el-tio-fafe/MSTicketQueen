package cl.duoc.msUbicacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msUbicacion.model.Ubicacion;
import cl.duoc.msUbicacion.service.UbicacionService;

@RestController
@RequestMapping("/api/v1/ubicaciones")
public class UbicacionController {

    @Autowired
    private UbicacionService ubicacionService;

    @GetMapping("/ver_ubicaciones")
    public ResponseEntity<?> getUbicaciones() {// Endpoint para mostrar a todas las ubicaciones

        List<Ubicacion> ubicaciones = ubicacionService.getAllUbicaciones();

        if (ubicaciones.isEmpty()) {// verifica si la lista de ubicaciones esta vacia, si lo esta tira error
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ubicaciones);// en caso de que si haya ubicaciones tira la lista de ubicaciones con un
                                              // status 200 OK
    }

    @GetMapping("/ver_ubicaciones/find_id/{id}")
    public ResponseEntity<?> getUbicacionById(@PathVariable Integer id) {

        try {
            Ubicacion ubicacion = ubicacionService.getUbicacionById(id);// intenta obtener la ubicacion por ID,
            // si no lo encuentra lanza una excepción que es capturada en el bloque catch y
            // devuelve un status 404 Not Found
            return ResponseEntity.ok(ubicacion);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping("/ver_ubicaciones/find_nombre/{nombre}")
    public ResponseEntity<?> getUbicacionByNombre(@PathVariable String nombre) {
        try {
            Ubicacion ubicacion = ubicacionService.getUbicacionByNombre(nombre);// intenta obtener la ubicacion por
                                                                                // nombre,
            // si no lo encuentra lanza una excepción que es capturada en el bloque catch y
            // devuelve un status 404 Not Found
            return ResponseEntity.ok(ubicacion);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/ver_ubicaciones/{id}/asiento")
    public ResponseEntity<?> tieneAsiento(@PathVariable Integer id) {
        try {
            Boolean tieneAsiento = ubicacionService.tieneAsiento(id);
            return ResponseEntity.ok(tieneAsiento
                    ? "La ubicación con id: " + id + " tiene asientos"
                    : "La ubicación con id: " + id + " no tiene asientos");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/ver_ubicaciones/disponibles")
    public ResponseEntity<?> getUbicacionesDisponibles() {
        List<Ubicacion> ubicacionesDisponibles = ubicacionService.getUbicacionesDisponibles();

        if (ubicacionesDisponibles.isEmpty()) {// verifica si la lista de ubicaciones disponibles esta vacia, si lo esta
                                               // tira error
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ubicacionesDisponibles);// en caso de que si haya ubicaciones disponibles tira la lista
                                                         // de ubicaciones disponibles con un status 200 OK
    }

    @PostMapping("/crear_ubicacion")
    public ResponseEntity<?> saveUbicacion(@RequestBody Ubicacion ubicacion) {
        try {
            Ubicacion ubicacionNueva = ubicacionService.saveUbicacion(ubicacion);// intenta guardar la nueva ubicacion,
            // si ya existe una ubicacion con el mismo nombre lanza una excepción que es
            // capturada en el bloque catch y devuelve un status 400 Bad Request
            return ResponseEntity.status(HttpStatus.CREATED).body(ubicacionNueva);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/actualizar_ubicacion/{id}")
    public ResponseEntity<?> updateUbicacion(@PathVariable Integer id, @RequestBody Ubicacion ubicacion) {
        try {
            Ubicacion ubicacionActualizada = ubicacionService.updateUbicacion(id, ubicacion);// intenta actualizar la
                                                                                             // ubicacion por ID,
            // si no lo encuentra lanza una excepción que es capturada en el bloque catch y
            // devuelve un status 404 Not Found
            return ResponseEntity.ok(ubicacionActualizada);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar_ubicacion/{id}")
    public ResponseEntity<?> deleteUbicacion(@PathVariable Integer id) {
        try {
            ubicacionService.deleteUbicacion(id);// intenta eliminar la ubicacion por ID,
            // si no lo encuentra lanza una excepción que es capturada en el bloque catch y
            // devuelve un status 404 Not Found
            return ResponseEntity.ok("Ubicación eliminada correctamente");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
