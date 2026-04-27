package com.TicketQueen.mslogin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TicketQueen.mslogin.model.usuario;
import com.TicketQueen.mslogin.repository.usuarioRepository;
@Service

public class usuarioService {
    @Autowired
    private usuarioRepository usuarioRepository;

    // listar usuarios
    public List<usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // buscar usuario por correo
    public usuario buscarUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }

    // crear usuario
    public usuario crearUsuario(usuario nuevoUsuario) {
        return usuarioRepository.save(nuevoUsuario);
    }

    // actualizar usuario
    public usuario actualizarUsuario(Integer id, usuario usuarioActualizado) {
        usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
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
        usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);
        if (usuario != null) {
            return usuario.getPassword().equals(password);
        }
        return false;
    }
}
