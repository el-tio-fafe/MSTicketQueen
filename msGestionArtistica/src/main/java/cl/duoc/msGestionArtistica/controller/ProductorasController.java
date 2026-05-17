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

import cl.duoc.msGestionArtistica.dto.ProductoraDTO;
import cl.duoc.msGestionArtistica.model.Productoras;
import cl.duoc.msGestionArtistica.service.ProductorasService;

@RestController
@RequestMapping("/api/v1/productoras")
public class ProductorasController {

    @Autowired
    private ProductorasService productorasService;

    @GetMapping("/ver_productoras")
    public ResponseEntity<?> getProductoras() {// Endpoint para mostrar a todas las productoras

        List<Productoras> productoras = productorasService.getAllProductoras();

        if (productoras.isEmpty()) {// verifica si la lista de productoras esta vacia, si lo esta tira error
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productoras);// en caso de que si haya productoras tira la lista de productoras con un status 200 OK
    }

    @GetMapping("/ver_productoras/find_id/{id}")
    public ResponseEntity<?> getProductoraById(@PathVariable Integer id) {// Endpoint para buscar una productora por ID
        
        try {
            Productoras productora = productorasService.getProductoraById(id);// intenta obtener la productora por ID,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok(productora);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/ver_productoras/find_nombre/{nombre}")
    public ResponseEntity<?> getProductoraByNombre(@PathVariable String nombre) {// Endpoint para buscar una productora por nombre
        
        try {
            Productoras productora = productorasService.getProductoraByNombre(nombre);// intenta obtener la productora por nombre,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok(productora);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }  


    @GetMapping("/ver_productoras/find_rut/{rut}")
    public ResponseEntity<?> getProductoraByRut(@PathVariable String rut) {// Endpoint para buscar una productora por RUT
        
        try {
            Productoras productora = productorasService.getProductoraByRut(rut);// intenta obtener la productora por RUT,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok(productora);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/crear_productora")
    public ResponseEntity<?> saveProductora(@RequestBody Productoras productora) {// Endpoint para crear una nueva productora

        try {
            Productoras productoraNueva = productorasService.saveProductora(productora);// intenta guardar la nueva productora,
            //  si ya existe una productora con el mismo nombre o rut lanza una excepción que es capturada en el bloque catch y devuelve un status 400 Bad Request
            return ResponseEntity.status(HttpStatus.CREATED).body(productoraNueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/actualizar_productora/{id}")
    public ResponseEntity<?> updateProductora(@PathVariable Integer id, @RequestBody Productoras productora) {// Endpoint para actualizar una productora existente por ID

        try {
            Productoras productoraActualizada = productorasService.updateProductora(id, productora);// intenta actualizar la productora,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok(productoraActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar_productora/{id}")
    public ResponseEntity<?> deleteProductora(@PathVariable Integer id) {// Endpoint para eliminar una productora por ID

        try {
            productorasService.deleteProductora(id);// intenta eliminar la productora,
            //  si no lo encuentra lanza una excepción que es capturada en el bloque catch y devuelve un status 404 Not Found
            return ResponseEntity.ok("Productora con id: " + id + " eliminado con exito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/asignar_manager/{idProductora}/{idManager}")
    public ResponseEntity<?> asignarManager(@PathVariable Integer idProductora, @PathVariable Integer idManager) {// Endpoint para asignar un manager a una productora

        try {
            Productoras productoraActualizada = productorasService.asignarManager(idProductora, idManager);// intenta asignar el manager a la productora,
            //  si no encuentra la productora o el manager lanza un error dependiendo de cual no encuentra
            //  si el manager ya esta asignado a la productora lanza otro error, todos estos errores son capturados en el bloque catch y devuelve un status 400 Bad Request
            return ResponseEntity.ok(productoraActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    //CONECCION CON MSEVENTO
    @GetMapping("/dto/{idProd}")
    public ResponseEntity<ProductoraDTO> buscarDTO(@PathVariable Integer idProd) {
        try {
            return ResponseEntity.ok(productorasService.buscarProductoraDTOPorId(idProd));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
