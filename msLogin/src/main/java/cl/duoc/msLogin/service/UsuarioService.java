package cl.duoc.msLogin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msLogin.model.Usuario;
import cl.duoc.msLogin.repository.UsuarioRepository;

@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    // listar usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario con id: " + id + " no encontrado."));
    }

    // buscar usuario por correo
    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("Usuario con email: " + correo + " no encontrado."));
    }

    // crear usuario
    public Usuario crearUsuario(Usuario nuevoUsuario) {
        return usuarioRepository.save(nuevoUsuario);
    }

    // actualizar usuario
    public Usuario actualizarUsuario(Integer id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario con id: " + id + " no encontrado."));
        usuarioExistente.setNombreUsuario(usuarioActualizado.getNombreUsuario());
        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
        usuarioExistente.setPassword(usuarioActualizado.getPassword());
        usuarioExistente.setTipoUsuario(usuarioActualizado.getTipoUsuario());
        return usuarioRepository.save(usuarioExistente);
    }

    // eliminar usuario
    public void eliminarUsuario(Integer id) {
        usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario con id: " + id + " no encontrado."));
        usuarioRepository.deleteById(id);
    }

    // verificar si la contraseña es correcta y el correo existe
    public boolean verificarCredenciales(String correo, String password) {
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);
        if (usuario != null) {
            return usuario.getPassword().equals(password);
        }
        return false;
    }


}
