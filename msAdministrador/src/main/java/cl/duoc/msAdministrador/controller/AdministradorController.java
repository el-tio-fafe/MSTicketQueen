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

@RestController
@RequestMapping("/api/v1/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping
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
    public ResponseEntity<?> buscarAuditoriaPorId(@PathVariable Integer idAuditoria) {
        try {
            return ResponseEntity.ok(administradorService.buscarAuditoriaPorId(idAuditoria));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/auditorias/buscar/rut/{rutAdm}")
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
    public ResponseEntity<?> buscarPorId(@PathVariable Integer idAdm) {
        try {
            Administrador admin = administradorService.buscarPorIdAdm(idAdm);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //METODO QUE SE COMUNICA CON OTRO MS
    @GetMapping("/dto/{idAdm}")
    public ResponseEntity<AdministradorDTO> buscarDTO(@PathVariable Integer idAdm) {
        try {
            return ResponseEntity.ok(administradorService.buscarAdministradorDTOPorId(idAdm));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    //METODO QUE SE COMUNICA CON EL smsLogin
    @GetMapping("/dto/correo/{correoAdm}")
    public ResponseEntity<AdministradorDTO> buscarDTOPorCorreo(@PathVariable String correoAdm) {
        try {
            return ResponseEntity.ok(administradorService.buscarAdministradorDTOPorCorreo(correoAdm));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }





    @GetMapping("/rut/{rutAdm}")
    public ResponseEntity<?> buscarPorRut(@PathVariable String rutAdm){
        try {
            Administrador admin = administradorService.buscarPorRutAdm(rutAdm);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Administrador administrador){
        try {
            return ResponseEntity.ok(administradorService.guardarAdministrador(administrador));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    @DeleteMapping("eliminar/id/{idAdm}")
    public ResponseEntity<?> eliminarPorId(@PathVariable Integer idAdm){
        try {
            administradorService.eliminarPorId(idAdm);
            return ResponseEntity.ok("Administrador con id: " + idAdm + " eliminado con exito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("eliminar/rut/{rutAdm}")
    public ResponseEntity<?> eliminarPorRut(@PathVariable String rutAdm){
        try {
            administradorService.eliminarPorRut(rutAdm);
            return ResponseEntity.ok("Administrador con rut: " + rutAdm + " eliminado con exito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("actualizar/id/{idAdm}")
    public ResponseEntity<?> actualizarPorId(@PathVariable Integer idAdm, @RequestBody Administrador administrador){
        try {
            Administrador admActualizado = administradorService.actualizarAdmPorId(idAdm, administrador);
            return ResponseEntity.ok(admActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("actualizar/rut/{rutAdm}")
    public ResponseEntity<?> actualizarPorRut(@PathVariable String rutAdm, @RequestBody Administrador administrador){
        try {
            Administrador admActualizado = administradorService.actualizarAdmPorRut(rutAdm, administrador);
            return ResponseEntity.ok(admActualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PatchMapping("actualizar/email/id/{idAdm}")
    public ResponseEntity<?> actualizarEmailPorId(@PathVariable Integer idAdm, @RequestBody AdministradorEmailDTO emailDTO){
        try {
            return ResponseEntity.ok(administradorService.actualizarEmailAdm(idAdm, emailDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/auditorias/guardar")
    public ResponseEntity<?> guardarAuditoria(@RequestBody Auditoria auditoria){
        try {
            return ResponseEntity.ok(administradorService.guardarAuditoria(auditoria));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/eventos")
    public ResponseEntity<?> listarEventos() {
        try {
            return ResponseEntity.ok(administradorService.listarEventos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/eventos/pendientes")
    public ResponseEntity<?> listarEventosPendientes() {
        try {
            return ResponseEntity.ok(administradorService.listarEventosPendientes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/eventos/id/{idEvento}")
    public ResponseEntity<?> buscarEventoDTO(@PathVariable Integer idEvento) {
        try {
            return ResponseEntity.ok(administradorService.buscarEventoDTO(idEvento));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
