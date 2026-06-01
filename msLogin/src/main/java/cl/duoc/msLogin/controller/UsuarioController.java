package cl.duoc.msLogin.controller;

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

import cl.duoc.msLogin.model.Usuario;
import cl.duoc.msLogin.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "usuario",description = "Operaciones Sobre Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

     @GetMapping
     @Operation(summary = "Lista Los Usuarios", description = "Muestra Los Usuarios")
    public ResponseEntity<?> listarUsuarios() {
        try {
            List<Usuario> lista = usuarioService.listarUsuarios();
            if (lista.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar Usuario por ID", description = "Retorna Un Paciente Segun El ID Proporcionado ")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/correo/{correo}")
    @Operation(summary = "Buscar Usuario por Correo", description = "Retorna Un Paciente Segun El Correo Proporcionado ")
    public ResponseEntity<?> obtenerUsuarioPorCorreo(@PathVariable String correo) {
        try {
            return ResponseEntity.ok(usuarioService.buscarUsuarioPorCorreo(correo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Crear Usuario ", description = "Crea un Usuario Nuevo ")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario nuevoUsuario) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(nuevoUsuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza Un Usuario", description = "Edita un Usuario ya creado ")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioActualizado) {
        try {
            return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuarioActualizado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "elimina un usuario Un Usuario", description = "elimina un Usuario ya creado ")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario con id: " + id + " eliminado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "ingresar al usuario", description = "verifica que el usuario y la contraseña que ingresa es correcto")
    public ResponseEntity<?> login(@RequestBody Usuario credenciales) {
        try {
            boolean valido = usuarioService.verificarCredenciales(credenciales.getCorreo(), credenciales.getPassword());
            if (valido) {
                return ResponseEntity.ok("Credenciales válidas");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña inválidos");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }





}
