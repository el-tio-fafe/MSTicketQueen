
package cl.duoc.msVentaTicket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msVentaTicket.model.Ticket;
import cl.duoc.msVentaTicket.repository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> listarTickets(){
        return ticketRepository.findAll();
    }

    public Ticket buscarTicketPorId(Integer idTicket){
        return ticketRepository.findById(idTicket)
            .orElseThrow(() -> new RuntimeException("Ticket con id: " + idTicket + " no encontrado" ));
    }

    public List<Ticket> listarTicketPorEvento(Integer idEvento){
        List<Ticket> lista = ticketRepository.findByIdEvento(idEvento);
        if(lista.isEmpty()){
            throw new RuntimeException("No hay tickets para el evento con id: " + idEvento);
        }
        return lista;
    }

    public List<Ticket> listarTicketsPorUbicacion(String nombreUbicacion){
        List<Ticket> lista = ticketRepository.findByNombreUbicacion(nombreUbicacion);
        if(lista.isEmpty()){
            throw new RuntimeException("No hay tickets para la ubicacion: " + nombreUbicacion);
        }
        return lista;
    }

    public Ticket buscarTicketPorNumeroAsiento(String numeroAsiento){
        return ticketRepository.findByNumeroAsiento(numeroAsiento)
            .orElseThrow(() -> new RuntimeException("No existe un ticket para el asiento numero: " + numeroAsiento));
    }




}
