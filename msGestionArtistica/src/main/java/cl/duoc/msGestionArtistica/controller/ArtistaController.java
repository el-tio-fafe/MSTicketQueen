package cl.duoc.msGestionArtistica.controller;

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

import cl.duoc.msGestionArtistica.model.Artista;
import cl.duoc.msGestionArtistica.service.ArtistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/artistas")
@Tag(name = "Artistas", description = "Endpoints relacionados con los artistas, que permiten realizar operaciones CRUD sobre los artistas y asignar managers a los artistas.")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @GetMapping("/ver_artistas")
    @Operation(
        summary = "Obtener todos los artistas", 
        description = "Endpoint para obtener una lista de todos los artistas registrados en el sistema de gestión artística.")
    public ResponseEntity<?> getArtistas() {// Endpoint para mostrar a todos los artistas

        List<Artista> artistas = artistaService.getAllArtistas();

        if (artistas.isEmpty()) {// verifica si la lista de artistas esta vacia, si lo esta tira error
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(artistas);// en caso de que si haya artistas tira la lista de artistas con un status 200 OK
    }

    @GetMapping("/ver_artistas/find_id/{id}")
    @Operation(
        summary = "Obtener un artista por ID", 
        description = "Endpoint para obtener un artista específico por su ID.")
    public ResponseEntity<?> getArtistaById(@PathVariable Integer id) {// Endpoint para buscar un artista por ID

        try {
            Artista artista = artistaService.getArtistaById(id);// intenta obtener el artista por ID,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok(artista);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/ver_artistas/find_rut/{rut}")
    @Operation(
        summary = "Obtener un artista por RUT", 
        description = "Endpoint para obtener un artista específico por su RUT.")
    public ResponseEntity<?> getArtistaByRut(@PathVariable String rut) {// endpoint para buscar un artista por RUT

        try {
            Artista artista = artistaService.getArtistaByRut(rut);// intenta obtener el artista por RUT,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok(artista);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }    

    @GetMapping("/ver_artistas/find_correo/{correo}")
    @Operation(
        summary = "Obtener un artista por correo", 
        description = "Endpoint para obtener un artista específico por su correo.")
    public ResponseEntity<?> getArtistaByCorreo(@PathVariable String correo) {// endpoint para buscar un artista por correo

        try {
            Artista artista = artistaService.getArtistaByCorreo(correo);// intenta obtener el artista por correo,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok(artista);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/crear_artista")
    @Operation(
        summary = "Crear un nuevo artista", 
        description = "Endpoint para crear un nuevo artista en el sistema de gestión artística.")
    public ResponseEntity<?> saveArtista(@RequestBody Artista artista) {// Endpoint para crear un nuevo artista

        try {
            Artista artistaNuevo = artistaService.saveArtista(artista);// intenta guardar el nuevo artista,
            //  si ya existe un artista con el mismo RUT o correo lanza una excepción que es capturada en el bloque catch y devuelve un status 400 Bad Request
            return ResponseEntity.status(HttpStatus.CREATED).body(artistaNuevo);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/actualizar_artista/id/{id}")
    @Operation(
        summary = "Actualizar un artista existente", 
        description = "Endpoint para actualizar la información de un artista existente por su ID.")
    public ResponseEntity<?> updateArtista(@PathVariable Integer id, @RequestBody Artista artista) {// Endpoint para actualizar un artista existente por ID

        try {
            Artista artistaActualizado = artistaService.updateArtista(id, artista);// intenta actualizar el artista,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok(artistaActualizado);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }   

    @DeleteMapping("/eliminar_artista/id/{id}")
    @Operation(
        summary = "Eliminar un artista", 
        description = "Endpoint para eliminar un artista específico por su ID.")
    public ResponseEntity<?> deleteArtista(@PathVariable Integer id) {// Endpoint para eliminar un artista por ID
        try {
            artistaService.deleteArtista(id);// intenta eliminar el artista por ID,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok("Artista con id: " + id + " eliminado con exito.");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
