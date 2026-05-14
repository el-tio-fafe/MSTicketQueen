package cl.duoc.msEvento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msEvento.model.Evento;
import cl.duoc.msEvento.model.Recinto;
import cl.duoc.msEvento.model.TipoEvento;
import cl.duoc.msEvento.repository.EventoRepository;
import cl.duoc.msEvento.repository.RecintoRepository;
import cl.duoc.msEvento.repository.TipoEventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired TipoEventoRepository tipoEventoRepository;

    @Autowired RecintoRepository recintoRepository;

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
        if(eventoRepository.findByCodigoEvento(evento.getCodigoEvento()).isPresent()){
            throw new RuntimeException("Ya existe un evento con código: " + evento.getCodigoEvento());
        }
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


//TIPO DE EVENTO

    public List<TipoEvento> listarTiposEvento(){
        return tipoEventoRepository.findAll();
    }

    public TipoEvento buscarTipoEventoPorId(Integer idTipoEvento){
        return tipoEventoRepository.findById(idTipoEvento)
            .orElseThrow(() -> new RuntimeException("Tipo de Evento con id: " + idTipoEvento + " no encontrado o no existe"));
    }

    public TipoEvento guardarTipoEvento(TipoEvento tipoEvento){
        if(tipoEventoRepository.findByDescripcion(tipoEvento.getDescripcion()).isPresent()){
            throw new RuntimeException("Ya existe el tipo de evento: " + tipoEvento.getDescripcion());
        }
        return tipoEventoRepository.save(tipoEvento);
    }

    public TipoEvento actualizarTipoEvento(Integer idTipoEvento, TipoEvento tipoEventoActualizado){
        TipoEvento tipoEvento = tipoEventoRepository.findById(idTipoEvento)
            .orElseThrow(() -> new RuntimeException("Tipo de Evento con id: " + idTipoEvento + "No encontrado"));
        tipoEvento.setDescripcion(tipoEventoActualizado.getDescripcion());
        return tipoEventoRepository.save(tipoEvento);
    }

    public void eliminarTipoEvento(Integer idTipoEvento){
        tipoEventoRepository.findById(idTipoEvento)
            .orElseThrow(() -> new RuntimeException("Tipo de Evento con id: " + idTipoEvento + "No encontrado"));
        tipoEventoRepository.deleteById(idTipoEvento);
    }


//RECINTO

    public List<Recinto> listarRecintos(){
        return recintoRepository.findAll();
    }

    public Recinto buscarRecintoPorId(Integer idRecinto){
        return recintoRepository.findById(idRecinto)
            .orElseThrow(() -> new RuntimeException("Recinto con id: " + idRecinto + " no encontrado"));
    }

    public Recinto buscarRecintoPorNombre(String nombreRecinto){
        return recintoRepository.findByNombreRecinto(nombreRecinto)
            .orElseThrow(() -> new RuntimeException("Recinto con nombre: " + nombreRecinto + " no encontrado"));
    }

    public Recinto guardarRecinto(Recinto recinto){
        if(recintoRepository.findByRutRecinto(recinto.getRutRecinto()).isPresent()){
            throw new RuntimeException("Ya existe un recinto con rut: " + recinto.getRutRecinto());
        }
        if(recintoRepository.findByNombreRecinto(recinto.getNombreRecinto()).isPresent()){
            throw new RuntimeException("Ya existe un recinto con nombre: " + recinto.getNombreRecinto());
        }
        return recintoRepository.save(recinto);
    }

    public Recinto actualizarRecinto(Integer idRecinto, Recinto recintoActualizado){
        Recinto recinto = recintoRepository.findById(idRecinto)
            .orElseThrow(() -> new RuntimeException("Recinto con id: " + idRecinto + " no encontrado"));
        recinto.setNombreRecinto(recintoActualizado.getNombreRecinto());
        recinto.setCapacidadRecinto(recintoActualizado.getCapacidadRecinto());
        recinto.setTelefonoRecinto(recintoActualizado.getTelefonoRecinto());
        recinto.setCorreoRecinto(recintoActualizado.getCorreoRecinto());

        return recintoRepository.save(recinto);
    }


    public void eliminarRecinto(Integer idRecinto){
        recintoRepository.findById(idRecinto)
            .orElseThrow(() -> new RuntimeException("Recinto con id: " + idRecinto + " no encontrado"));
        recintoRepository.deleteById(idRecinto);
    }

    
}
