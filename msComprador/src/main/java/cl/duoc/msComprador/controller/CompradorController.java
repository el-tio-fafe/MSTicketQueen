package cl.duoc.msComprador.controller;

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
    public ResponseEntity<?> guardarCliente(Comprador comprador){
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
