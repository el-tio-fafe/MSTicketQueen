package cl.duoc.msComprador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msComprador.model.Comprador;
import cl.duoc.msComprador.repository.CompradorRepository;

@Service
public class CompradorService {

    @Autowired
    private CompradorRepository compradorRepository;

    public List<Comprador> listarCompradores(){
        return compradorRepository.findAll();
    }


    public Comprador guardarComprador(Comprador comprador){
        if(compradorRepository.existsByRutCliente(comprador.getRutCliente())){
            throw new RuntimeException("Ya existe un comprador(cliente) con el rut: " + comprador.getRutCliente());
        }
        return compradorRepository.save(comprador);
    }


    public Comprador buscarCompradorPorId(Integer idCliente){
        return compradorRepository.findById(idCliente)
            .orElseThrow(() -> new RuntimeException("Comprador/Cliente) con id: " + idCliente + " no encontrado."));
    }



    public Comprador buscarCompradorPorRut(String rutCliente){
        return compradorRepository.findByRutCliente(rutCliente)
            .orElseThrow(() -> new RuntimeException("Comprador/Cliente con rut: " + rutCliente + " no encontrado"));
    }
    


    public Comprador actualizarCompradorPorId(Integer idCliente, Comprador compradorActualizado){
        Comprador comprador = compradorRepository.findById(idCliente)
            .orElseThrow(() -> new RuntimeException("Comprador/Cliente con id: " + idCliente + " no encontrado"));

        comprador.setRutCliente(compradorActualizado.getRutCliente());
        comprador.setNombreCliente(compradorActualizado.getNombreCliente());
        comprador.setApPaternoCliente(compradorActualizado.getApPaternoCliente());
        comprador.setApMaternoCliente(compradorActualizado.getApMaternoCliente());
        comprador.setCorreoCliente(compradorActualizado.getCorreoCliente());
        comprador.setTelefonoCliente(compradorActualizado.getTelefonoCliente());
        comprador.setPassCliente(compradorActualizado.getPassCliente());

        return compradorRepository.save(comprador);
    }



    public Comprador actualizarCompradorPorRut(String rutCliente, Comprador compradorActualizado){
        Comprador comprador = compradorRepository.findByRutCliente(rutCliente)
            .orElseThrow(() -> new RuntimeException("Comprador/Cliente con rut: " + rutCliente + " no encontrado"));

        comprador.setRutCliente(compradorActualizado.getRutCliente());
        comprador.setNombreCliente(compradorActualizado.getNombreCliente());
        comprador.setApPaternoCliente(compradorActualizado.getApPaternoCliente());
        comprador.setApMaternoCliente(compradorActualizado.getApMaternoCliente());
        comprador.setCorreoCliente(compradorActualizado.getCorreoCliente());
        comprador.setTelefonoCliente(compradorActualizado.getTelefonoCliente());
        comprador.setPassCliente(compradorActualizado.getPassCliente());

        return compradorRepository.save(comprador);
    }
    

    public void eliminarCompradorPorId(Integer idCliente){
        if(!compradorRepository.existsById(idCliente)){
            throw new RuntimeException("Comprador/Cliente con id: " + idCliente + " no encontrado.");
        }
        compradorRepository.deleteById(idCliente);
    }


    public void eliminarCompradorPorRut(String rutCliente){
        Comprador comprador = compradorRepository.findByRutCliente(rutCliente)
            .orElseThrow(() -> new RuntimeException("Comprador/Cliente con rut: " + rutCliente + " no encontrado"));
        compradorRepository.deleteById(comprador.getIdCliente());
    }





}
