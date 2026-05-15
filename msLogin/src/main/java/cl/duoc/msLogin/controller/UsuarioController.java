package cl.duoc.msLogin.controller;

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

import cl.duoc.msLogin.model.TipoUsuario;
import cl.duoc.msLogin.model.Usuario;
import cl.duoc.msLogin.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/usuarios")

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

//USUARIO

     @GetMapping
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
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<?> obtenerUsuarioPorCorreo(@PathVariable String correo) {
        try {
            return ResponseEntity.ok(usuarioService.buscarUsuarioPorCorreo(correo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario nuevoUsuario) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(nuevoUsuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuarioActualizado) {
        try {
            return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuarioActualizado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.ok("Usuario con id: " + id + " eliminado con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
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



//TIPO USUARIO

    @GetMapping("/tipo-usuario")
    public ResponseEntity<?> listarTiposUsuario() {
        try {
            List<TipoUsuario> lista = usuarioService.listarTiposUsuario();
            if (lista.isEmpty()) {
                return ResponseEntity.badRequest().body("No hay tipos de usuario registrados");
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tipo-usuario/{id}")
    public ResponseEntity<?> buscarTipoUsuarioPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(usuarioService.buscarTipoUsuarioPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


//METODO COMENTADO PORQUE LA API TRABAJA SOLO CON 3 USUARIOS (ADM, PROD Y CLIEN)
    // @PostMapping("/tipo-usuario")
    // public ResponseEntity<?> guardarTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
    //     try {
    //         return ResponseEntity.ok(usuarioService.guardarTipoUsuario(tipoUsuario));
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }
    

    @PatchMapping("/tipo-usuario/{id}")
    public ResponseEntity<?> actualizarTipoUsuario(@PathVariable Integer id, @RequestBody TipoUsuario tipoUsuario) {
        try {
            return ResponseEntity.ok(usuarioService.actualizarTipoUsuario(id, tipoUsuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
