package cl.duoc.msEvento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msEvento.client.AdministradorClient;
import cl.duoc.msEvento.client.DireccionClient;
import cl.duoc.msEvento.client.ProductoraClient;
import cl.duoc.msEvento.dto.DireccionDTO;
import cl.duoc.msEvento.dto.EventoDTO;
import cl.duoc.msEvento.dto.RecintoDTO;
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

    @Autowired
    ProductoraClient productoraClient;

    @Autowired
    AdministradorClient administradorClient;

    @Autowired
    DireccionClient direccionClient;



    public List<Evento> listarEventos(){
        return eventoRepository.findAll();
    }

    public List<Evento> listarEventosPorEstado(String estadoEvento){
        List<String> estadosValidos = List.of("PENDIENTE", "APROBADO", "RECHAZADO");
        if(!estadosValidos.contains(estadoEvento.toUpperCase())){
            throw new RuntimeException("Estado: " + estadoEvento + " no válido. Use PENDIENTE, APROBADO O RECHAZADO");
        }
        return eventoRepository.findByEstadoEvento(estadoEvento.toUpperCase());
    }

    public Evento buscarEventoPorId(Integer idEvento){
        return eventoRepository.findById(idEvento)
            .orElseThrow(() -> new RuntimeException("Evento con id: " + idEvento + " no encontrado"));
    }


    //METODO CON EL EVENTO DTO PARA LLAMARLO EN OTRO MS
    public EventoDTO buscarEventoDTOPorId(Integer idEvento){
        Evento evento = buscarEventoPorId(idEvento);

        EventoDTO dto = new EventoDTO();
        dto.setIdEvento(evento.getIdEvento());
        dto.setCodigoEvento(evento.getCodigoEvento());
        dto.setNombreEvento(evento.getNombreEvento());

        return dto;
    }


    public RecintoDTO buscarRecintoConDireccion(Integer idRecinto) {
        Recinto recinto = recintoRepository.findById(idRecinto)
            .orElseThrow(() -> new RuntimeException("Recinto con id: " + idRecinto + " no encontrado."));

        DireccionDTO direccion;
        
            try {
                direccion = direccionClient.buscarDireccionDTO(recinto.getIdCalle());
            } catch (Exception e) {
                throw new RuntimeException("No se pudo obtener la dirección del recinto.");
            }
        

        RecintoDTO dto = new RecintoDTO();
        dto.setIdRecinto(recinto.getIdRecinto());
        dto.setNombreRecinto(recinto.getNombreRecinto());
        dto.setCapacidadRecinto(recinto.getCapacidadRecinto());
        dto.setDireccion(direccion);
    

        return dto;
    }

    

    public List<Evento> listarEventosPorProductora(Integer idProd){
        List<Evento> eventos = eventoRepository.findByIdProd(idProd);
        if(eventos.isEmpty()){
            throw new RuntimeException("No se encontraron eventos para la productora con id: " + idProd);
        }
        return eventos;
    }

    //AL CREAR UN EVENTO SIEMPRE COMIENZA CON EL ESTADO PENDIENTE PORQUE NECESITA LA APROBACION DEL ADM
    public Evento crearEvento(Evento evento){
        if(eventoRepository.findByCodigoEvento(evento.getCodigoEvento()).isPresent()){
            throw new RuntimeException("Ya existe un evento con código: " + evento.getCodigoEvento());
        }

        //VALIDAMOS QUE LA PRODUCTORA EXISTE EN EL msGestionArtistica
        try {
            productoraClient.buscarProductoraDTO(evento.getIdProd());

        } catch (Exception e) {
            throw new RuntimeException("No se puede crear el evento porque la productora id: " + evento.getIdProd() + " No existe" );
        }

        //BUSCAMOS EL RECINTO PRIMERO, SI EXISTE PODEMOS CREAR
        Recinto recinto = recintoRepository.findById(evento.getRecinto().getIdRecinto())
            .orElseThrow(() -> new RuntimeException("Recinto con id: " 
                + evento.getRecinto().getIdRecinto() + " no encontrado."));

        //BUSCAMOS EL TIPO DE EVENTO PARA ASIGNARLO, Y SI EXISTE LO CREAMOS
        TipoEvento tipoEvento = tipoEventoRepository.findById(evento.getTipoEvento().getIdTipoEvento())
            .orElseThrow(() -> new RuntimeException("TipoEvento con id: " 
                + evento.getTipoEvento().getIdTipoEvento() + " no encontrado."));

        evento.setRecinto(recinto);
        evento.setTipoEvento(tipoEvento);

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

        //VALIDAMOS QUE EL ADM EXISTE EN EL msAdministrador
        try {
            administradorClient.buscarAdministradorDTO(idAdministrador);
        } catch (Exception e) {
            throw new RuntimeException("No se puede aprobar el evento porque el administrador con id: " + idAdministrador + " no existe.");
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
            throw new RuntimeException("Solo se pueden rechazar eventos en estado PENDIENTE");
        }

        try {
            administradorClient.buscarAdministradorDTO(idAdministrador);
        } catch (Exception e) {
            throw new RuntimeException("No se puede rechazar el evento porque el administrador con id: " + idAdministrador + " no existe.");
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
            .orElseThrow(() -> new RuntimeException("Tipo de Evento con id: " + idTipoEvento + " no encontrado"));
        tipoEvento.setDescripcion(tipoEventoActualizado.getDescripcion());
        return tipoEventoRepository.save(tipoEvento);
    }

    public void eliminarTipoEvento(Integer idTipoEvento){
        tipoEventoRepository.findById(idTipoEvento)
            .orElseThrow(() -> new RuntimeException("Tipo de Evento con id: " + idTipoEvento + " no encontrado"));
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
