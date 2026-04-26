package cl.duoc.msAsiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msAsiento.model.Asiento;
import cl.duoc.msAsiento.repository.AsientoRepository;

@Service
public class AsientoService {

    @Autowired
    private AsientoRepository asientoRepository;


    public List<Asiento> listarAsientos(){
        return asientoRepository.findAll();
    }

    public Asiento buscarPorId(Integer idAsiento){
        return asientoRepository.findByIdAsiento(idAsiento)
        .orElseThrow ( () -> new 
        RuntimeException ("Asiento id: " + idAsiento + " no encontrado"));
    }


    public Asiento buscarPorNumAsiento(String numAsiento){
        return asientoRepository.findByNumeroAsiento(numAsiento)
        .orElseThrow ( () -> new
        RuntimeException("Asiento numero: " + numAsiento + " no encontrado"));
    }

    public Asiento guardar(Asiento asiento){
        return asientoRepository.save(asiento);
    }

    public void eliminarPorId(Integer idAsiento){
        if(!asientoRepository.existsById(idAsiento)){
            throw new RuntimeException("Asiento id: " + idAsiento + " no existe");
        }
        asientoRepository.deleteById(idAsiento);
    }

    // public boolean eliminarPorNumAsiento(String numAsiento){
    //     asientoRepository.eliminarPorNumeroAsiento(numAsiento);
    //     return true;
    // }

    public void eliminarPorNumAsiento(String numAsiento){
        if(!asientoRepository.findByNumeroAsiento(numAsiento).isPresent()){
            throw new RuntimeException("Asiento numero: " + numAsiento + " no existe");
        }
        asientoRepository.eliminarPorNumeroAsiento(numAsiento);
    }


    public Asiento actualizar(Integer idAsiento, Asiento asientoActualizado){
        Asiento asiento = asientoRepository.findById(idAsiento)
            .orElseThrow( () -> new RuntimeException("Asiento id: " + idAsiento + " no encontrado"));

        asiento.setNumeroAsiento(asientoActualizado.getNumeroAsiento());
        asiento.setEstadoAsiento(asientoActualizado.getEstadoAsiento());
        
        return asientoRepository.save(asiento);

    }


   


}
