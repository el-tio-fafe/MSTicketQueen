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
import cl.duoc.msComprador.dto.CompradorDTO;
import cl.duoc.msComprador.model.Comprador;
import cl.duoc.msComprador.service.CompradorService;

@RestController
@RequestMapping("/api/v1/compradores")
public class CompradorController {

    @Autowired
    private CompradorService compradorService;


    @GetMapping
    public ResponseEntity<?> listarClientes(){
        List<Comprador> lista = compradorService.listarCompradores();
        if(lista.isEmpty()){
            return ResponseEntity.badRequest().body("No hay compradores/Clientes registrados");
        }
        return ResponseEntity.ok(lista);
    }


    @PostMapping
    public ResponseEntity<?> guardarCliente(@RequestBody Comprador comprador){
        try {
            return ResponseEntity.ok(compradorService.guardarComprador(comprador));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/id/{idCliente}")
    public ResponseEntity<?> buscarClientePorId(@PathVariable Integer idCliente){
        try {
            Comprador comprador = compradorService.buscarCompradorPorId(idCliente);
            return ResponseEntity.ok(comprador);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //METODO QUE SE COMUNICA CON OTRO MS
    @GetMapping("/dto/{idCliente}")
    public ResponseEntity<CompradorDTO> buscarDTO(@PathVariable Integer idCliente){
        try {
            return ResponseEntity.ok(compradorService.buscarCompradorDTOPorId(idCliente));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/correo/{correoCliente}")
    public ResponseEntity<CompradorDTO> buscarDTOPorCorreo(@PathVariable String correoCliente) {
        try {
            return ResponseEntity.ok(compradorService.buscarCompradorDTOPorCorreo(correoCliente));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



    @GetMapping("/rut/{rutCliente}")
    public ResponseEntity<?> buscarClientePorRut(@PathVariable String rutCliente){
        try {
            Comprador cli = compradorService.buscarCompradorPorRut(rutCliente);
            return ResponseEntity.ok(cli);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/actualizar/id/{idCliente}")
    public ResponseEntity<?> actualizarClientePorId(@PathVariable Integer idCliente, @RequestBody Comprador comprador){
        try {
            Comprador cliActualizado = compradorService.actualizarCompradorPorId(idCliente, comprador);
            return ResponseEntity.ok(cliActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/actualizar/rut/{rutCliente}")
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
    public ResponseEntity<?> actualizarCorreoClientePorRut(@PathVariable String rutCliente, @RequestBody ActualizarCorreoDTO correoDto){
        try {
            return ResponseEntity.ok(compradorService.actualizarCorreoPorRut(rutCliente, correoDto.getCorreoCliente()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PatchMapping("/actualizar/telefono/{rutCliente}")
    public ResponseEntity<?> actualizarTelefonoPorRut(@PathVariable String rutCliente, @RequestBody ActualizarTelefonoDTO telefonoDto){
        try {
            return ResponseEntity.ok(compradorService.actualizarTelefonoPorRut(rutCliente, telefonoDto.getTelefonoCliente()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/eliminar/id/{idCliente}")
    public ResponseEntity<?> eliminarClientePorId(@PathVariable Integer idCliente){
        try {
            compradorService.eliminarCompradorPorId(idCliente);
            return ResponseEntity.ok("Comprador/Cliente con id: " + idCliente + " eliminado con exito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @DeleteMapping("/eliminar/rut/{rutCliente}")
    public ResponseEntity<?> eliminarClientePorRut(@PathVariable String rutCliente){
        try {
            compradorService.eliminarCompradorPorRut(rutCliente);
            return ResponseEntity.ok("Comprador/Cliente con rut: " + rutCliente + " eliminado con exito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
