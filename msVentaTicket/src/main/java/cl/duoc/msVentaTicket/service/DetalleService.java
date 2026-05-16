package cl.duoc.msVentaTicket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msVentaTicket.model.Detalle;
import cl.duoc.msVentaTicket.repository.DetalleRepository;

@Service
public class DetalleService {

    @Autowired
    private DetalleRepository detalleRepository;

    public List<Detalle> listarDetalles(){
        return detalleRepository.findAll();
    }

    public Detalle buscarDetallePorId(Integer idDetalle) {
        return detalleRepository.findById(idDetalle)
            .orElseThrow(() -> new RuntimeException("Detalle con id: " + idDetalle + " no encontrado."));
    }

    public List<Detalle> listarDetallePorEvento(Integer idEvento){
        List<Detalle> lista = detalleRepository.findByTicket_IdEvento(idEvento);
        if(lista.isEmpty()){
            throw new RuntimeException("No hay detalles para el evento id: " + idEvento);
        }
        return lista;
    }




}
