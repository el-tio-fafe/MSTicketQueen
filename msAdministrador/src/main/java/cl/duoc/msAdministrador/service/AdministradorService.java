package cl.duoc.msAdministrador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msAdministrador.model.Administrador;
import cl.duoc.msAdministrador.model.Auditoria;
import cl.duoc.msAdministrador.repository.AdministradorRepository;
import cl.duoc.msAdministrador.repository.AuditoriaRepository;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    public List<Administrador> listarAdministradores() {
        return administradorRepository.findAll();
    }

    public Administrador guardarAdministrador(Administrador administrador) {
        if(administradorRepository.findByRutAdm(administrador.getRutAdm()).isPresent()){
            throw new RuntimeException("Ya existe un administrador con el rut: " + administrador.getRutAdm());
        }

        Administrador nuevo = administradorRepository.save(administrador);

        Auditoria auditoria = new Auditoria();
        auditoria.setNombreResponsable(nuevo.getNombreAdm() + " " + nuevo.getApPatAdm());
        auditoria.setFecha(new java.sql.Date(System.currentTimeMillis()));
        auditoria.setTipoAccion("CREAR");
        auditoria.setDescripcion("Se creo el administrador: " + nuevo.getNombreAdm() + " " + nuevo.getApPatAdm());
        auditoria.setAdministrador(nuevo);
        auditoriaRepository.save(auditoria);
        return nuevo;

    }

    public Administrador buscarPorIdAdm(Integer idAdm){
        return administradorRepository.findById(idAdm)
        .orElseThrow(() -> new RuntimeException("Administrador con id: " + idAdm + " no encontrado."));
    }

    public Administrador buscarPorRutAdm(String rutAdm){
        return administradorRepository.findByRutAdm(rutAdm)
        .orElseThrow(() -> new RuntimeException("Administrador con rut: " + rutAdm + " no encontrado."));
    }

    public void eliminarPorId(Integer idAdm){
        Administrador administrador = administradorRepository.findById(idAdm)
        .orElseThrow(() -> new RuntimeException("Administrador con id " + idAdm + " no encontrado."));

        String nombreCompleto = administrador.getNombreAdm() + " " + administrador.getApPatAdm();
        administradorRepository.deleteById(idAdm);

        Auditoria auditoria = new Auditoria();
        auditoria.setNombreResponsable(nombreCompleto);
        auditoria.setFecha(new java.sql.Date(System.currentTimeMillis()));
        auditoria.setTipoAccion("ELIMINAR");
        auditoria.setDescripcion("Se eliminó el administrador: " + nombreCompleto);
        auditoria.setAdministrador(null);
        auditoriaRepository.save(auditoria);
    }


    public void eliminarPorRut(String rutAdm){
        Administrador administrador = administradorRepository.findByRutAdm(rutAdm)
        .orElseThrow(() -> new RuntimeException("Administrador con rut: " + rutAdm + " no encontrado"));

        String nombreCompleto = administrador.getNombreAdm() + " " + administrador.getApPatAdm();
        administradorRepository.deleteById(administrador.getIdAdm());

        Auditoria auditoria = new Auditoria();
        auditoria.setNombreResponsable(nombreCompleto);
        auditoria.setFecha(new java.sql.Date(System.currentTimeMillis()));
        auditoria.setTipoAccion("ELIMINAR");
        auditoria.setDescripcion("Se eliminó el administrador: " + nombreCompleto);
        auditoria.setAdministrador(null);
        auditoriaRepository.save(auditoria);
    }

    public Administrador actualizarAdmPorId(Integer idAdm, Administrador administradorActualizado){
        Administrador administrador = administradorRepository.findById(idAdm).orElseThrow(() 
        -> new RuntimeException("Administrador con id " + idAdm + " no encontrado."));
            
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

        Administrador actualizado = administradorRepository.save(administrador);
        Auditoria auditoria = new Auditoria();
        auditoria.setNombreResponsable(actualizado.getNombreAdm() + " " + actualizado.getApPatAdm());
        auditoria.setFecha(new java.sql.Date(System.currentTimeMillis()));
        auditoria.setTipoAccion("ACTUALIZAR");
        auditoria.setDescripcion("Se actualizó el administrador: " + actualizado.getNombreAdm() + " " + actualizado.getApPatAdm());
        auditoria.setAdministrador(actualizado);
        auditoriaRepository.save(auditoria);

        return actualizado;
    }


    public Administrador actualizarAdmPorRut(String rutAdm, Administrador administradorActualizado){
        Administrador administrador = administradorRepository.findByRutAdm(rutAdm).orElseThrow(() 
        -> new RuntimeException("Administrador con rut " + rutAdm + " no encontrado."));
            
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

        Administrador actualizado = administradorRepository.save(administrador);
        Auditoria auditoria = new Auditoria();
        auditoria.setNombreResponsable(actualizado.getNombreAdm() + " " + actualizado.getApPatAdm());
        auditoria.setFecha(new java.sql.Date(System.currentTimeMillis()));
        auditoria.setTipoAccion("ACTUALIZAR");
        auditoria.setDescripcion("Se actualizó el administrador: " + actualizado.getNombreAdm() + " " + actualizado.getApPatAdm());
        auditoria.setAdministrador(actualizado);
        auditoriaRepository.save(auditoria);

        return actualizado;
    }

}
