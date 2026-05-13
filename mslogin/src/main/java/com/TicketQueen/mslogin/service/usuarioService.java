package com.TicketQueen.mslogin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TicketQueen.mslogin.model.Usuario;
import com.TicketQueen.mslogin.repository.UsuarioRepository;

@Service

public class usuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    // listar usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // buscar usuario por correo
    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }

    // crear usuario
    public Usuario crearUsuario(Usuario nuevoUsuario) {
        return usuarioRepository.save(nuevoUsuario);
    }

    // actualizar usuario
    public Usuario actualizarUsuario(Integer id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente != null) {
            usuarioExistente.setNombreUsuario(usuarioActualizado.getNombreUsuario());
            usuarioExistente.setEmail(usuarioActualizado.getEmail());
            usuarioExistente.setPassword(usuarioActualizado.getPassword());
            usuarioExistente.setTipoUsuario(usuarioActualizado.getTipoUsuario());
            return usuarioRepository.save(usuarioExistente);
        }
        return null;
    }

    // eliminar usuario
    public void eliminarUsuario(Integer id) {
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
