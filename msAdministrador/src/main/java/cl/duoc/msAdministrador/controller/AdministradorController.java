package cl.duoc.msAdministrador.controller;

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

import cl.duoc.msAdministrador.model.Administrador;
import cl.duoc.msAdministrador.service.AdministradorService;

@RestController
@RequestMapping("/api/v1/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping
    public ResponseEntity<List<Administrador>> listarAdministradores(){
        List<Administrador> listarAdministradores = administradorService.listarAdministradores();
        if (listarAdministradores.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(listarAdministradores);
        }
    }

    // @GetMapping("/id/{idAdm}")
    // public ResponseEntity<Administrador> buscarPorId(@PathVariable Integer idAdm){
    //     try {
    //         Administrador admin = administradorService.buscarPorIdAdm(idAdm);
    //         return ResponseEntity.ok(admin);
    //     } catch (Exception e) {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @GetMapping("/id/{idAdm}")
    public ResponseEntity<Administrador> buscarPorId(@PathVariable Integer idAdm) {
    Administrador admin = administradorService.buscarPorIdAdm(idAdm);
    
    if (admin != null) {
        return ResponseEntity.ok(admin);
    } else {
        // Esto le dirá a Postman: 404 Not Found
        return ResponseEntity.notFound().build();
    }
}


    @GetMapping("/rut/{rutAdm}")
    public ResponseEntity<Administrador> buscarPorRut(@PathVariable String rutAdm){
        try {
            Administrador admin = administradorService.buscarPorRutAdm(rutAdm);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Administrador> guardar(@RequestBody Administrador administrador){
        Administrador nuevoAdmministrador = administradorService.guardarAdministrador(administrador);
        return ResponseEntity.ok(nuevoAdmministrador);
    }

    @DeleteMapping("/{idAdm}")
    public ResponseEntity<?> eliminar(@PathVariable Integer idAdm){
        try {
            administradorService.eliminarAdministrador(idAdm);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idAdm}")
    public ResponseEntity<Administrador> actualizar(@PathVariable Integer idAdm, @RequestBody Administrador administrador){
        try {
            Administrador admActualizado = administradorService.actualizarAdministrador(idAdm, administrador);
            return ResponseEntity.ok(admActualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
