package cl.duoc.msGestionArtistica.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msGestionArtistica.repository.ArtistaRepository;
import cl.duoc.msGestionArtistica.repository.ManagerRepository;
import cl.duoc.msGestionArtistica.model.Artista;
import cl.duoc.msGestionArtistica.model.Manager;

@Service
public class ManagerService {

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ArtistaRepository artistaRepository;

    public List<Manager> getAllManagers() {//metodo para obtener todos los managers
        return managerRepository.findAll();
    }

    public Manager getManagerById(Integer id) {//metodo para obtener un manager por su ID
        return managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager con id: " + id + " no encontrado."));
    }

    public Manager getManagerByRut(String rut) {//metodo para obtener un manager por su rut

        return managerRepository.findByRutMngr(rut)
                .orElseThrow(() -> new RuntimeException("Manager con rut: " + rut + " no encontrado."));
    }

    public Manager getManagerByCorreo(String correo) {//metodo para obtener un manager por su correo
        return managerRepository.findByCorreoMngr(correo)
                .orElseThrow(() -> new RuntimeException("Manager con correo: " + correo + " no encontrado."));
    }

    public Manager saveManager(Manager manager) {//metodo para guardar un nuevo manager
        if (managerRepository.findByRutMngr(manager.getRutMngr()).isPresent()) {//revisa si hay un manager con el mismo rut, si lo hay tira error
            throw new RuntimeException("Ya existe un manager con el rut: " + manager.getRutMngr());
        }
        if (managerRepository.findByCorreoMngr(manager.getCorreoMngr()).isPresent()) {//revisa si hay un manager con el mismo correo, si lo hay tira error
            throw new RuntimeException("Ya existe un manager con el correo: " + manager.getCorreoMngr());
        }
        return managerRepository.save(manager);
    }

    public Manager updateManager(Integer id, Manager manager) {//metodo para actualizar un manager existente por su ID
        Manager managerCambiar = managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager con id: " + id + " no encontrado. No se puede actualizar."));
        managerCambiar.setRutMngr(manager.getRutMngr());              //actualiza el rut del manager a cambiar con el nuevo valor
        managerCambiar.setNombreMngr(manager.getNombreMngr());        //actualiza el nombre del manager a cambiar con el nuevo valor
        managerCambiar.setApPaternoMngr(manager.getApPaternoMngr());  //actualiza el apellido paterno del manager a cambiar con el nuevo valor
        managerCambiar.setApMaternoMngr(manager.getApMaternoMngr());  //actualiza el apellido materno del manager a cambiar con el nuevo valor
        managerCambiar.setCorreoMngr(manager.getCorreoMngr());        //actualiza el correo del manager a cambiar con el nuevo valor
        managerCambiar.setTelefonoMngr(manager.getTelefonoMngr());    //actualiza el teléfono del manager a cambiar con el nuevo valor
        return managerRepository.save(managerCambiar);
    }

    public void deleteManager(Integer id) {//metodo para eliminar un manager por su ID
        if (!managerRepository.existsById(id)) {
            throw new RuntimeException("Manager con id: " + id + " no encontrado. No se puede eliminar.");
        }
        managerRepository.deleteById(id);
    }

    public Manager asignarArtista(Integer idManager, Integer idArtista) {
        Manager manager = managerRepository.findById(idManager)// busca el manager por su ID
                .orElseThrow(() -> new RuntimeException("Manager con id: " + idManager + " no encontrado. No se puede asignar artista."));

        Artista agregArtista = artistaRepository.findById(idArtista)// busca el artista por su ID
                .orElseThrow(() -> new RuntimeException("Artista con id: " + idArtista + " no encontrado. No se puede asignar al manager."));

        if (manager.getArtistas().contains(agregArtista)) {// Verificar si el artista ya está asignado al manager, si lo está tira error
            throw new RuntimeException("El artista con id: " + idArtista + " ya está asignado al manager con id: " + idManager + ".");   
        }

        manager.getArtistas().add(agregArtista);// una vez verificado que el artista no está asignado al manager, se agrega el artista a la lista de artistas del manager
        
        return managerRepository.save(manager);
    }
}
