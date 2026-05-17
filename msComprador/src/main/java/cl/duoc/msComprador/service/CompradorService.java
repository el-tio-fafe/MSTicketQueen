package cl.duoc.msComprador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msComprador.dto.CompradorDTO;
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



    //METODO CON EL COMPRADOR DTO PARA LLAMARLO EN OTRO MS
    public CompradorDTO buscarCompradorDTOPorId(Integer idCliente){
        Comprador comprador = buscarCompradorPorId(idCliente);

        CompradorDTO dto = new CompradorDTO();
        dto.setIdCliente(comprador.getIdCliente());
        dto.setRutCliente(comprador.getRutCliente());
        dto.setNombreCliente(comprador.getNombreCliente());
        dto.setApPaternoCliente(comprador.getApPaternoCliente());
        dto.setCorreoCliente(comprador.getCorreoCliente());

        return dto;
    }

    public CompradorDTO buscarCompradorDTOPorCorreo(String correoCliente) {
        Comprador comprador = compradorRepository.findByCorreoCliente(correoCliente)
            .orElseThrow(() -> new RuntimeException("Comprador con correo: " + correoCliente + " no encontrado."));

        CompradorDTO dto = new CompradorDTO();
        dto.setIdCliente(comprador.getIdCliente());
        dto.setRutCliente(comprador.getRutCliente());
        dto.setNombreCliente(comprador.getNombreCliente());
        dto.setApPaternoCliente(comprador.getApPaternoCliente());
        dto.setCorreoCliente(comprador.getCorreoCliente());

        return dto;
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


    //ACTUALIZAR SOLO EL CORREO DEL CLIENTE POR SU NUMERO DE RUT
    public Comprador actualizarCorreoPorRut(String rutCliente, String nuevoCorreo){
        Comprador comprador = compradorRepository.findByRutCliente(rutCliente)
            .orElseThrow(() -> new RuntimeException("Comprador/Cliente con rut: " + rutCliente + " no encontrado"));
            comprador.setCorreoCliente(nuevoCorreo);
        return compradorRepository.save(comprador);
    }



    //ACTUALIZAR SOLO EL TELEFONO DEL CLIENTE POR SU RUT
    public Comprador actualizarTelefonoPorRut(String rutCliente, String nuevoTelefono){
        Comprador comprador = compradorRepository.findByRutCliente(rutCliente)
            .orElseThrow(() -> new RuntimeException("Comprador/Cliente con rut: " + rutCliente + " no encontrado"));
            comprador.setTelefonoCliente(nuevoTelefono);
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
