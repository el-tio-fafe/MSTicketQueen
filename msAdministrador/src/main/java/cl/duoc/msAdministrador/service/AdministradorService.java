package cl.duoc.msAdministrador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msAdministrador.client.EventoClient;
import cl.duoc.msAdministrador.dto.AdministradorDTO;
import cl.duoc.msAdministrador.dto.AdministradorEmailDTO;
import cl.duoc.msAdministrador.dto.EventoDTO;
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

    @Autowired
    EventoClient eventoClient;

    public List<Administrador> listarAdministradores() {
        return administradorRepository.findAll();
    }

    public List<Auditoria> listarAuditorias() {
        return auditoriaRepository.findAll();
    }

    public List<Auditoria> listarAuditoriasPorAdm(Integer idAdm) {
        Administrador administrador = administradorRepository.findById(idAdm)
            .orElseThrow(() -> new RuntimeException("Administrador con id: " + idAdm + " no encontrado."));

        return auditoriaRepository.findByAdministrador(administrador)
            .orElseThrow(() -> new RuntimeException("No hay auditorías para el administrador con id: " + idAdm));
    }

    public Auditoria buscarAuditoriaPorId(Integer idAuditoria) {
        return auditoriaRepository.findById(idAuditoria)
            .orElseThrow(() -> new RuntimeException("Auditoría con id: " + idAuditoria + " no encontrada."));
    }



    public List<Auditoria> buscarAuditoriaPorRutAdm(String rutAdm){
        return auditoriaRepository.findByAdministrador_RutAdm(rutAdm);
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


    //METODO QUE SE COMUNICA CON OTRO MS
    public AdministradorDTO buscarAdministradorDTOPorId(Integer idAdm) {
        Administrador administrador = administradorRepository.findById(idAdm)
            .orElseThrow(() -> new RuntimeException("Administrador con id: " + idAdm + " no encontrado."));

        return new AdministradorDTO(
            administrador.getIdAdm(),
            administrador.getNombreAdm(),
            administrador.getApPatAdm(),
            administrador.getRutAdm(),
            administrador.getCorreoAdm()
        );
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


    public AdministradorDTO actualizarEmailAdm(Integer idAdm, AdministradorEmailDTO emailDTO){
        Administrador administrador = administradorRepository.findById(idAdm)
            .orElseThrow(() -> new RuntimeException("Administrador con id: " + idAdm + " no encontrado"));
        administrador.setCorreoAdm(emailDTO.getCorreoAdm());
        Administrador correoAdmActual = administradorRepository.save(administrador);
        return new AdministradorDTO(
            correoAdmActual.getIdAdm(),
            correoAdmActual.getNombreAdm(),
            correoAdmActual.getApPatAdm(),
            correoAdmActual.getRutAdm(),
            correoAdmActual.getCorreoAdm()
        );
    }

    public Auditoria guardarAuditoria(Auditoria auditoria){
        Administrador administrador = administradorRepository.findById(auditoria.getAdministrador().getIdAdm())
            .orElseThrow(()-> new RuntimeException("Administrador con id: " + auditoria.getAdministrador().getIdAdm() + " no se encuentra o no existe"));
        auditoria.setAdministrador(administrador);
        return auditoriaRepository.save(auditoria);
    }



    //METODO QUE SE COMUNICA CON EL msEvento PARA QUE EL ADMINISTRADOR PUEDA REVISAR LOS EVENTOS
    public EventoDTO buscarEventoDTO(Integer idEvento) {
        try {
            return eventoClient.buscarEventoDTO(idEvento);
        } catch (Exception e) {
            throw new RuntimeException("No se encontró el evento con id: " + idEvento);
        }
    }


    //METODO QUE PERMITE AL ADMINISTRADOR REVISAR LOS EVENTOS
    public List<EventoDTO> listarEventos() {
        try {
            return eventoClient.listarEventos();
        } catch (Exception e) {
            throw new RuntimeException("No se pudieron obtener los eventos.");
        }
    }

    //METODO QUE PERMITE CONECTARSE CON EL msLogin
    public AdministradorDTO buscarAdministradorDTOPorCorreo(String correoAdm) {
        Administrador administrador = administradorRepository.findByCorreoAdm(correoAdm)
            .orElseThrow(() -> new RuntimeException("Administrador con correo: " + correoAdm + " no encontrado."));

        return new AdministradorDTO(
            administrador.getIdAdm(),
            administrador.getNombreAdm(),
            administrador.getApPatAdm(),
            administrador.getRutAdm(),
            administrador.getCorreoAdm()
        );
    }




    public List<EventoDTO> listarEventosPendientes() {
        try {
            return eventoClient.listarEventosPorEstado("PENDIENTE");
        } catch (Exception e) {
            throw new RuntimeException("No se pudieron obtener los eventos pendientes.");
        }
    }


}
