package cl.duoc.msEvento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msEvento.model.Evento;
import cl.duoc.msEvento.repository.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> listarEventos(){
        return eventoRepository.findAll();
    }

    public Evento buscarEventoPorId(Integer idEvento){
        return eventoRepository.findById(idEvento)
            .orElseThrow(() -> new RuntimeException("Evento con id: " + idEvento + " no encontrado"));
    }

    public List<Evento> listarEventosPorEstado(String estado){
        return eventoRepository.findByEstadoEvento(estado);
    }

    public List<Evento> listarEventosPorProductora(Integer idProd){
        return eventoRepository.findByIdProd(idProd);
    }

    //AL CREAR UN EVENTO SIEMPRE COMIENZA CON EL ESTADO PENDIENTE PORQUE NECESITA LA APROBACION DEL ADM
    public Evento crearEvento(Evento evento){
        evento.setEstadoEvento("PENDIENTE");
        return eventoRepository.save(evento);
    }

    //AHORA EL ADM APRUEBA EL EVENTO
    public Evento aprobarEvento(Integer idEvento, Integer idAdministrador){
        Evento evento = eventoRepository.findById(idEvento)
            .orElseThrow(() -> new RuntimeException("Evento con id: " + idEvento + " no encontrado"));
        
        if(!evento.getEstadoEvento().equals("PENDIENTE")){
            throw new RuntimeException("Solo se pueden aprobar eventos en estado PENDIENTE");
        }

        evento.setEstadoEvento("APROBADO");
        evento.setIdAdministrador(idAdministrador);
        return eventoRepository.save(evento);
    }


    //RECHAZAR EL EVENTO
    public Evento rechazarEvento(Integer idEvento, Integer idAdministrador){
        Evento evento = eventoRepository.findById(idEvento)
            .orElseThrow(() -> new RuntimeException("Evento con id: " + idEvento + " no encontrado"));
        
        if(!evento.getEstadoEvento().equals("PENDIENTE")){
            throw new RuntimeException("Solo se pueden aprobar eventos en estado PENDIENTE");
        }

        evento.setEstadoEvento("RECHAZADO");
        evento.setIdAdministrador(idAdministrador);
        return eventoRepository.save(evento);
    }

    public void eliminarEvento(Integer idEvento){
        eventoRepository.findById(idEvento)
            .orElseThrow(() -> new RuntimeException("Evento con id: " + idEvento + " no encontrado"));
        eventoRepository.deleteById(idEvento);
    }






}
