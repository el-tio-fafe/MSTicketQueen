package cl.duoc.msLogin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msLogin.model.TipoUsuario;
import cl.duoc.msLogin.model.Usuario;
import cl.duoc.msLogin.repository.TipoUsuarioRepository;
import cl.duoc.msLogin.repository.UsuarioRepository;

@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

//USUARIO

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario con id: " + id + " no encontrado."));
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("Usuario con email: " + correo + " no encontrado."));
    }


    public Usuario crearUsuario(Usuario nuevoUsuario) {
        return usuarioRepository.save(nuevoUsuario);
    }


    public Usuario actualizarUsuario(Integer id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario con id: " + id + " no encontrado."));
        usuarioExistente.setNombreUsuario(usuarioActualizado.getNombreUsuario());
        usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
        usuarioExistente.setPassword(usuarioActualizado.getPassword());
        usuarioExistente.setTipoUsuario(usuarioActualizado.getTipoUsuario());
        return usuarioRepository.save(usuarioExistente);
    }


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


//TIPO USUARIO

    public List<TipoUsuario> listarTiposUsuario() {
        return tipoUsuarioRepository.findAll();
    }

    public TipoUsuario buscarTipoUsuarioPorId(Integer id) {
        return tipoUsuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TipoUsuario con id: " + id + " no encontrado."));
    }


//ESTE METODO LO COMENTÉ PORQUE PIENSO QUE NO ES NECESARIO AGREGAR UN NUEVO TIPO DE USUARIO 
//YA QUE DISEÑAMOS LA API PARA 3 TIPOS DE USUARIOS (ADMINISTRADOR, PRODUCTORA Y CLIENTE)
    // public TipoUsuario guardarTipoUsuario(TipoUsuario tipoUsuario) {
    //     if (tipoUsuarioRepository.findByNombreTipoUsuario(tipoUsuario.getNombreTipoUsuario()).isPresent()) {
    //         throw new RuntimeException("Ya existe el tipo de usuario: " + tipoUsuario.getNombreTipoUsuario());
    //     }
    //     return tipoUsuarioRepository.save(tipoUsuario);
    // }



    public TipoUsuario actualizarTipoUsuario(Integer id, TipoUsuario tipoUsuarioActualizado) {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("TipoUsuario con id: " + id + " no encontrado."));
        tipoUsuario.setNombreTipoUsuario(tipoUsuarioActualizado.getNombreTipoUsuario());
        return tipoUsuarioRepository.save(tipoUsuario);
    }

    
//OJO NO ELIMINO UN TIPO DE USUARIO PORQUE NO ES NECESARIO QUE EL SISTEMA HAGA ESA ACCION


}
