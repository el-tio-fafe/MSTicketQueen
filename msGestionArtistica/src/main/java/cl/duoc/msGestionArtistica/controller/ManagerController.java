package cl.duoc.msGestionArtistica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msGestionArtistica.model.Manager;
import cl.duoc.msGestionArtistica.service.ManagerService;

@RestController
@RequestMapping("/api/v1/managers")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/ver_managers")
    public ResponseEntity<?> getManagers() {// Endpoint para mostrar a todos los managers

        List<Manager> managers = managerService.getAllManagers();

        if (managers.isEmpty()) {// verifica si la lista de managers esta vacia, si lo esta tira error
            return ResponseEntity.noContent().build();     
        }
        return ResponseEntity.ok(managers);// en caso de que si haya managers tira la lista de managers con un status 200 OK
    }

    @GetMapping("/ver_managers/find_id/{id}")
    public ResponseEntity<?> getManagerById(@PathVariable Integer id) {// Endpoint para buscar un manager por ID

        try {
            Manager manager = managerService.getManagerById(id);// intenta obtener el manager por ID,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok(manager);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping("/ver_managers/find_rut/{rut}")
    public ResponseEntity<?> getManagerByRut(@PathVariable String rut) {// Endpoint para buscar un manager por RUT

        try {
            Manager manager = managerService.getManagerByRut(rut);// intenta obtener el manager por RUT,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok(manager);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping("/ver_managers/find_correo/{correo}")
    public ResponseEntity<?> getManagerByCorreo(@PathVariable String correo) {// Endpoint para buscar un manager por correo

        try {
            Manager manager = managerService.getManagerByCorreo(correo);// intenta obtener el manager por correo,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok(manager);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/crear_manager")
    public ResponseEntity<?> saveManager(@RequestBody Manager manager) {// Endpoint para crear un nuevo manager

        try {
            Manager managerNuevo = managerService.saveManager(manager);// intenta guardar el nuevo manager,
            //  si ya existe un manager con el mismo RUT o correo lanza una excepción que es capturada en el bloque catch y devuelve un status 400 Bad Request
            return ResponseEntity.status(HttpStatus.CREATED).body(managerNuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/actualizar_manager/id/{id}")
    public ResponseEntity<?> updateManager(@PathVariable Integer id, @RequestBody Manager manager) {// Endpoint para actualizar un manager existente por ID

        try {
            Manager managerActualizado = managerService.updateManager(id, manager);// intenta actualizar el manager,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok(managerActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar_manager/id/{id}")
    public ResponseEntity<?> deleteManager(@PathVariable Integer id) {// Endpoint para eliminar un manager por ID

        try {
            managerService.deleteManager(id);// intenta eliminar el manager por ID,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok("Manager con id: " + id + " eliminado con exito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/asignar_artista/{idManager}/{idArtista}")
    public ResponseEntity<?> asignarArtista(@PathVariable Integer idManager, @PathVariable Integer idArtista) {// Endpoint para asignar un artista a un manager existente por ID

        try {
            Manager managerActualizado = managerService.asignarArtista(idManager, idArtista);// intenta asignar el artista al manager,
            //  si no encuentra el manager o el artista lanza un error dependiendo de cual no encuentra,
            //  si el artista ya esta asignado al manager lanza otro error, todos estos errores son capturados en el bloque catch y devuelve un status 400 Bad Request
            return ResponseEntity.ok(managerActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
   
}
