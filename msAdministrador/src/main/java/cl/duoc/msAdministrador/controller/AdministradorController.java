package cl.duoc.msAdministrador.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import cl.duoc.msAdministrador.dto.AdministradorDTO;
import cl.duoc.msAdministrador.dto.AdministradorEmailDTO;
import cl.duoc.msAdministrador.model.Administrador;
import cl.duoc.msAdministrador.model.Auditoria;
import cl.duoc.msAdministrador.service.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/administradores")
@Tag(name = "Administradores", description = "Endpoints relacionados con los administradores y sus auditorías, que permiten realizar operaciones CRUD sobre los administradores y consultar las auditorías asociadas a ellos.")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping
    @Operation(
        summary = "Obtener todos los administradores", 
        description = "Endpoint para obtener una lista de todos los administradores registrados en el sistema de control de eventos.")
    public ResponseEntity<List<AdministradorDTO>> listarAdministradores(){
        List<Administrador> listarAdministradores = administradorService.listarAdministradores();
        if (listarAdministradores.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            List<AdministradorDTO> listaDTO = listarAdministradores.stream()
                .map(admin -> new AdministradorDTO(
                    admin.getIdAdm(),
                    admin.getNombreAdm(),
                    admin.getApPatAdm(),
                    admin.getRutAdm(),
                    admin.getCorreoAdm()
                ))
                .collect(Collectors.toList());
            return ResponseEntity.ok(listaDTO);
        }
    }

    @GetMapping("/auditorias")
    @Operation(
        summary = "Obtener todas las auditorías", 
        description = "Endpoint para obtener una lista de todas las auditorías registradas en el sistema de control de eventos.")
    public ResponseEntity<?> listarAuditorias() {
        try {
            List<Auditoria> lista = administradorService.listarAuditorias();
            if (lista.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/auditorias/buscar/{idAuditoria}")
    @Operation(
        summary = "Buscar auditoría por ID", 
        description = "Endpoint para buscar una auditoría específica por su ID.")
    public ResponseEntity<?> buscarAuditoriaPorId(@PathVariable Integer idAuditoria) {
        try {
            return ResponseEntity.ok(administradorService.buscarAuditoriaPorId(idAuditoria));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/auditorias/buscar/rut/{rutAdm}")
    @Operation(
        summary = "Buscar auditorías por Administrador", 
        description = "Endpoint para buscar auditorías específicas por el RUT del administrador.")  
    public ResponseEntity<?> buscarAuditoriasPorAdm(@PathVariable String rutAdm){
        try {
            List<Auditoria> lista = administradorService.buscarAuditoriaPorRutAdm(rutAdm);
            if(lista.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @GetMapping("/auditorias/listar/{idAdm}")
    @Operation(
        summary = "Listar auditorías por ID de administrador", 
        description = "Endpoint para listar todas las auditorías asociadas a un administrador específico por su ID.")
    public ResponseEntity<?> listarAuditoriaPorAdm(@PathVariable Integer idAdm) {
        try {
            List<Auditoria> lista = administradorService.listarAuditoriasPorAdm(idAdm);
            if (lista.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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
    @Operation(
        summary = "Obtener administrador por ID", 
        description = "Endpoint para obtener un administrador específico por su ID.")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer idAdm) {
        try {
            Administrador admin = administradorService.buscarPorIdAdm(idAdm);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/rut/{rutAdm}")
    @Operation(
        summary = "Obtener administrador por RUT", 
        description = "Endpoint para obtener un administrador específico por su RUT.")
    public ResponseEntity<?> buscarPorRut(@PathVariable String rutAdm){
        try {
            Administrador admin = administradorService.buscarPorRutAdm(rutAdm);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    @Operation(
        summary = "Guardar nuevo administrador", 
        description = "Endpoint para guardar un nuevo administrador en el sistema de control de eventos, proporcionando los datos necesarios en el cuerpo de la solicitud.")
    public ResponseEntity<?> guardar(@RequestBody Administrador administrador){
        try {
            return ResponseEntity.ok(administradorService.guardarAdministrador(administrador));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @DeleteMapping("eliminar/id/{idAdm}")
    @Operation(
        summary = "Eliminar administrador por ID", 
        description = "Endpoint para eliminar un administrador específico por su ID.")
    public ResponseEntity<?> eliminarPorId(@PathVariable Integer idAdm){
        try {
            administradorService.eliminarPorId(idAdm);
            return ResponseEntity.ok("Administrador con id: " + idAdm + " eliminado con exito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("eliminar/rut/{rutAdm}")
    @Operation(
        summary = "Eliminar administrador por RUT", 
        description = "Endpoint para eliminar un administrador específico por su RUT.")
    public ResponseEntity<?> eliminarPorRut(@PathVariable String rutAdm){
        try {
            administradorService.eliminarPorRut(rutAdm);
            return ResponseEntity.ok("Administrador con rut: " + rutAdm + " eliminado con exito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("actualizar/id/{idAdm}")
    @Operation(
        summary = "Actualizar administrador por ID", 
        description = "Endpoint para actualizar la información de un administrador específico por su ID, proporcionando los nuevos datos en el cuerpo de la solicitud.")
    public ResponseEntity<?> actualizarPorId(@PathVariable Integer idAdm, @RequestBody Administrador administrador){
        try {
            Administrador admActualizado = administradorService.actualizarAdmPorId(idAdm, administrador);
            return ResponseEntity.ok(admActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("actualizar/rut/{rutAdm}")
    @Operation(
        summary = "Actualizar administrador por RUT", 
        description = "Endpoint para actualizar la información de un administrador específico por su RUT, proporcionando los nuevos datos en el cuerpo de la solicitud.")
    public ResponseEntity<?> actualizarPorRut(@PathVariable String rutAdm, @RequestBody Administrador administrador){
        try {
            Administrador admActualizado = administradorService.actualizarAdmPorRut(rutAdm, administrador);
            return ResponseEntity.ok(admActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PatchMapping("actualizar/email/id/{idAdm}")
    @Operation(
        summary = "Actualizar email de administrador por ID", 
        description = "Endpoint para actualizar únicamente el email de un administrador específico por su ID, proporcionando el nuevo email en el cuerpo de la solicitud.")
    public ResponseEntity<?> actualizarEmailPorId(@PathVariable Integer idAdm, @RequestBody AdministradorEmailDTO emailDTO){
        try {
            return ResponseEntity.ok(administradorService.actualizarEmailAdm(idAdm, emailDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/auditorias/guardar")
    @Operation(
        summary = "Guardar nueva auditoría", 
        description = "Endpoint para guardar una nueva auditoría en el sistema de control de eventos, proporcionando los datos necesarios en el cuerpo de la solicitud.")
    public ResponseEntity<?> guardarAuditoria(@RequestBody Auditoria auditoria){
        try {
            return ResponseEntity.ok(administradorService.guardarAuditoria(auditoria));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
