package cl.duoc.msAdministrador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msAdministrador.model.Administrador;
import cl.duoc.msAdministrador.repository.AdministradorRepository;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public List<Administrador> listarAdministradores() {
        return administradorRepository.findAll();
    }

    public Administrador guardarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public Administrador buscarPorIdAdm(Integer idAdm){
        return administradorRepository.findById(idAdm).orElse(null);
    }

    public Administrador buscarPorRutAdm(String rutAdm){
        return administradorRepository.findByRutAdm(rutAdm).orElse(null);
    }

    public void eliminarAdministrador(Integer idAdm){
        if(administradorRepository.existsById(idAdm)){
            administradorRepository.deleteById(idAdm);
        }else{
            throw new RuntimeException("Administrador con id " + idAdm + " no encontrado.");
        }
    }

    public Administrador actualizarAdministrador(Integer idAdm, Administrador administradorActualizado){
        Administrador administrador = administradorRepository.findById(idAdm).orElseThrow(() -> new RuntimeException("Administrador con id " + idAdm + " no encontrado."));
            
        administrador.setRutAdm(administradorActualizado.getRutAdm());
        administrador.setNombreAdm(administradorActualizado.getNombreAdm());
        administrador.setApPatAdm(administradorActualizado.getApPatAdm());
        administrador.setApMatAdm(administradorActualizado.getApMatAdm());
        administrador.setCorreoAdm(administradorActualizado.getCorreoAdm());
        administrador.setTelefonoAdm(administradorActualizado.getTelefonoAdm());

        if(administradorActualizado.getAuditoria() != null){
            administradorActualizado.getAuditoria().forEach(auditoria -> auditoria.setAdministrador(administrador));
            administrador.setAuditoria(administradorActualizado.getAuditoria());
        }

        return administradorRepository.save(administrador);
    }

}
