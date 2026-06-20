package cl.duoc.msComprador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import cl.duoc.msComprador.dto.ActualizarCorreoDTO;
import cl.duoc.msComprador.dto.ActualizarTelefonoDTO;
import cl.duoc.msComprador.model.Comprador;
import cl.duoc.msComprador.service.CompradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/compradores")
@Tag(name = "CompradorController", description = "Controlador para gestionar los compradores en el sistema")
public class CompradorController {

    @Autowired
    private CompradorService compradorService;


    @GetMapping
    @Operation(
        summary = "Listar compradores o clientes",
        description = "Obtiene la lista de todos los compradores")
    public ResponseEntity<?> listarClientes(){
        List<Comprador> lista = compradorService.listarCompradores();
        if(lista.isEmpty()){
            return ResponseEntity.badRequest().body("No hay compradores/Clientes registrados");
        }
        return ResponseEntity.ok(lista);
    }


    @PostMapping
    @Operation(
        summary = "Guardar Compradores o Clientes",
        description = "Crea un nuevo comprador y lo guarda en el sistema")
    public ResponseEntity<?> guardarCliente(@RequestBody Comprador comprador){
        try {
            return ResponseEntity.ok(compradorService.guardarComprador(comprador));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/id/{idCliente}")
     @Operation(
        summary = "Buscar cliente por ID",
        description = "Obtiene un comprador específico utilizando su identificador único")
    public ResponseEntity<?> buscarClientePorId(@PathVariable Integer idCliente){
        try {
            Comprador comprador = compradorService.buscarCompradorPorId(idCliente);
            return ResponseEntity.ok(comprador);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @GetMapping("/rut/{rutCliente}")
    @Operation(
        summary = "Buscar cliente por número de RUT",
        description = "Obtiene un comprador específico utilizando su número de RUT")
    public ResponseEntity<?> buscarClientePorRut(@PathVariable String rutCliente){
        try {
            Comprador cli = compradorService.buscarCompradorPorRut(rutCliente);
            return ResponseEntity.ok(cli);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/actualizar/id/{idCliente}")
    @Operation(
        summary = "Actualizar cliente",
        description = "Actualiza la información de un comprador específico por su número de ID")
    public ResponseEntity<?> actualizarClientePorId(@PathVariable Integer idCliente, @RequestBody Comprador comprador){
        try {
            Comprador cliActualizado = compradorService.actualizarCompradorPorId(idCliente, comprador);
            return ResponseEntity.ok(cliActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/actualizar/rut/{rutCliente}")
    @Operation(
        summary = "Actualizar cliente",
        description = "Actualiza la información de un comprador específico por su número de RUT")
    public ResponseEntity<?> actualizarClientePorRut(@PathVariable String rutCliente, @RequestBody Comprador comprador){
        try {
            Comprador cliAct = compradorService.actualizarCompradorPorRut(rutCliente, comprador);
            return ResponseEntity.ok(cliAct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //ACTUALIZAR SOLO EL CORREO DE UN CLIENTE POR RUT (se debe crear un dto con solo el nuevo correo para que se vea mas limpio en el postman)
    //SI QUISIERA ACTUALIZAR EL CORREO DIRECTO EN LA URL (SIN BODY) TENDRIA QUE USAR UN @RequestParam EN VEZ DE @RequestBody, 
    // Y EN EL FRONTEND (postman) EN VEZ DE ENVIAR UN OBJETO JSON CON EL NUEVO CORREO, SOLO ENVIARIA EL NUEVO CORREO COMO PARAMETRO
    // EN LA URL: http://localhost:8083/api/v1/compradores/actualizar/correo/25008098-0?nuevoCorreo=nuevocorreo@gmail.com
    @PatchMapping("/actualizar/correo/{rutCliente}")
    @Operation(
        summary = "Actualizar correo del cliente",
        description = "Actualiza sólo el correo de un comprador específico por su número de RUT")
    public ResponseEntity<?> actualizarCorreoClientePorRut(@PathVariable String rutCliente, @RequestBody ActualizarCorreoDTO correoDto){
        try {
            return ResponseEntity.ok(compradorService.actualizarCorreoPorRut(rutCliente, correoDto.getCorreoCliente()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PatchMapping("/actualizar/telefono/{rutCliente}")
    @Operation(
        summary = "Actualizar teléfono del cliente",
        description = "Actualiza sólo el teléfono de un comprador específico por su número de RUT")
    public ResponseEntity<?> actualizarTelefonoPorRut(@PathVariable String rutCliente, @RequestBody ActualizarTelefonoDTO telefonoDto){
        try {
            return ResponseEntity.ok(compradorService.actualizarTelefonoPorRut(rutCliente, telefonoDto.getTelefonoCliente()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/eliminar/id/{idCliente}")
    @Operation(
        summary = "Eliminar cliente por su ID",
        description = "Elimina toda la información de un comprador específico en el sistema por su número de ID")
    public ResponseEntity<?> eliminarClientePorId(@PathVariable Integer idCliente){
        try {
            compradorService.eliminarCompradorPorId(idCliente);
            return ResponseEntity.ok("Comprador/Cliente con id: " + idCliente + " eliminado con exito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @DeleteMapping("/eliminar/rut/{rutCliente}")
     @Operation(
        summary = "Eliminar cliente por RUT",
        description = "Elimina toda la información de un comprador específico en el sistema por su número de RUT")
    public ResponseEntity<?> eliminarClientePorRut(@PathVariable String rutCliente){
        try {
            compradorService.eliminarCompradorPorRut(rutCliente);
            return ResponseEntity.ok("Comprador/Cliente con rut: " + rutCliente + " eliminado con exito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
