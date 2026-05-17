package cl.duoc.msGestionArtistica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msGestionArtistica.dto.ProductoraDTO;
import cl.duoc.msGestionArtistica.model.Manager;
import cl.duoc.msGestionArtistica.model.Productoras;
import cl.duoc.msGestionArtistica.repository.ManagerRepository;
import cl.duoc.msGestionArtistica.repository.ProductorasRepository;

@Service
public class ProductorasService {

    @Autowired
    private ProductorasRepository productorasRepository;

    @Autowired
    private ManagerRepository managerRepository;

    public List<Productoras> getAllProductoras() {// método para obtener todas las productoras
        return productorasRepository.findAll();
    }

    public Productoras getProductoraById(Integer id) {// método para obtener una productora por su ID
        return productorasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Productora con id: " + id + " no encontrada."));
    }

    public Productoras getProductoraByNombre(String nombre) {// método para obtener una productora por su nombre
        return productorasRepository.findByNombreProd(nombre)
                .orElseThrow(() -> new RuntimeException("Productora con nombre: " + nombre + " no encontrada."));
    }

    public Productoras getProductoraByRut(String rut) {// método para obtener una productora por su RUT
        return productorasRepository.findByRutProd(rut)
                .orElseThrow(() -> new RuntimeException("Productora con rut: " + rut + " no encontrada."));
    }

    public Productoras saveProductora(Productoras productora) {// método para guardar una nueva productora
        if (productorasRepository.findByNombreProd(productora.getNombreProd()).isPresent()) {// verifica si ya existe una productora con el mismo nombre, si lo hay tira error
            throw new RuntimeException("Ya existe una productora con el nombre: " + productora.getNombreProd());
        }
        if (productorasRepository.findByRutProd(productora.getRutProd()).isPresent()) {// verifica si ya existe una productora con el mismo rut, si lo hay tira error
            throw new RuntimeException("Ya existe una productora con el rut: " + productora.getRutProd());
        }
        return productorasRepository.save(productora);
    }

    public Productoras updateProductora(Integer id, Productoras productora) {// método para actualizar una productora existente por su ID
        Productoras productoraCambiar = productorasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Productora con id: " + id + " no encontrada. No se puede actualizar."));
        productoraCambiar.setRutProd(productora.getRutProd());           //actualiza el rut de la productora a cambiar con el nuevo valor
        productoraCambiar.setNombreProd(productora.getNombreProd());     //actualiza el nombre de la productora a cambiar con el nuevo valor
        productoraCambiar.setCorreoProd(productora.getCorreoProd());     //actualiza el correo de la productora a cambiar con el nuevo valor
        productoraCambiar.setTelefonoProd(productora.getTelefonoProd()); //actualiza el teléfono de la productora a cambiar con el nuevo valor
        return productorasRepository.save(productoraCambiar);
    }

    public void deleteProductora(Integer id) {// método para eliminar una productora por su ID
        if (!productorasRepository.existsById(id)) {// verifica si la productora existe antes de eliminarla, si no existe tira error
            throw new RuntimeException("Productora con id: " + id + " no encontrada. No se puede eliminar.");
        }
        productorasRepository.deleteById(id);
    }

    public Productoras asignarManager(Integer idProductoras, Integer idManager){
        Productoras productora = productorasRepository.findById(idProductoras)
                .orElseThrow(() -> new RuntimeException("Productora con id: " + idProductoras + " no encontrada. No se puede asignar manager."));

        Manager agregManager = managerRepository.findById(idManager)
                .orElseThrow(() -> new RuntimeException("Manager con id: " + idManager + " no encontrado. No se puede asignar a la productora."));

        if (productora.getManagers().contains(agregManager)) {
            throw new RuntimeException("El manager con id: " + idManager + " ya está asignado a la productora con id: " + idProductoras + ".");
        }

        productora.getManagers().add(agregManager);

        return productorasRepository.save(productora);
    }


    //CONECCION CON MS EVENTO
    public ProductoraDTO buscarProductoraDTOPorId(Integer idProd) {
        Productoras productora = productorasRepository.findById(idProd)
            .orElseThrow(() -> new RuntimeException("Productora con id: " + idProd + " no encontrada."));

        ProductoraDTO dto = new ProductoraDTO();
        dto.setIdProd(productora.getIdProd());
        dto.setNombreProd(productora.getNombreProd());
        dto.setCorreoProd(productora.getCorreoProd());

        return dto;
    }


}