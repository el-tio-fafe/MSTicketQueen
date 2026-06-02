package cl.duoc.msUbicacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msUbicacion.dto.UbicacionDTO;
import cl.duoc.msUbicacion.model.Ubicacion;
import cl.duoc.msUbicacion.repository.UbicacionRepository;

@Service
public class UbicacionService {

    @Autowired
    private UbicacionRepository ubicacionRepository;

    public List<Ubicacion> getAllUbicaciones() {// metodo que retorna todas las ubicaciones
        return ubicacionRepository.findAll();
    }

    public Ubicacion getUbicacionById(Integer id) {// metodo que retorna una ubicacion por su id
        return ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ubicación con id: " + id + " no encontrada"));
    }

    public Ubicacion getUbicacionByNombre(String nombreUbi) {// metodo que retorna una ubicacion por su nombre
        return ubicacionRepository.findByNombreUbi(nombreUbi)
                .orElseThrow(() -> new RuntimeException("ubicación con nombre: " + nombreUbi + " no encontrada"));
    }

    public Boolean tieneAsiento(Integer id) {// metodo que retorna si una ubicacion tiene asiento o no
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ubicación con id: " + id + " no encontrada"));
        return ubicacion.getTieneAsiento();
    }

    public List<Ubicacion> getUbicacionesDisponibles() {// metodo que retorna ubicaciones con stock mayor a 0
        return ubicacionRepository.findByStockDisponibleUbiGreaterThan(0);
    }

    public Ubicacion saveUbicacion(Ubicacion ubicacion) {// metodo que guarda una nueva ubicacion
        if (ubicacionRepository.findByNombreUbi(ubicacion.getNombreUbi()).isPresent()) {
            throw new RuntimeException("Ya existe una ubicación con ese nombre");
        }
        return ubicacionRepository.save(ubicacion);
    }

    public Ubicacion updateUbicacion(Integer id, Ubicacion ubicacion) {// metodo para actualizar una ubicaccion
        Ubicacion existingUbicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación con id: " + id + " no encontrada"));
        existingUbicacion.setNombreUbi(ubicacion.getNombreUbi());
        existingUbicacion.setPrecioUbi(ubicacion.getPrecioUbi());
        existingUbicacion.setCapacidadUbi(ubicacion.getCapacidadUbi());
        existingUbicacion.setStockDisponibleUbi(ubicacion.getStockDisponibleUbi());
        existingUbicacion.setTieneAsiento(ubicacion.getTieneAsiento());
        return ubicacionRepository.save(existingUbicacion);
    }

    public void deleteUbicacion(Integer id) {// metodo para eliminar una ubicacion por su id
        if (!ubicacionRepository.existsById(id)) {// Verificar si la ubicación existe antes de eliminarla, si no existe tira error
            throw new RuntimeException("Ubicación con id: " + id + " no encontrada. No se puede eliminar.");
        }
        ubicacionRepository.deleteById(id);// si la ubicación existe, la elimina de la base de datos
    }

    //COMUNICACION CON OTRO MS
    public UbicacionDTO getUbicacionDTOById(Integer id) {
        Ubicacion ubicacion = getUbicacionById(id);

        UbicacionDTO dto = new UbicacionDTO();
        dto.setIdUbi(ubicacion.getIdUbi());
        dto.setNombreUbi(ubicacion.getNombreUbi());
        dto.setPrecioUbi(ubicacion.getPrecioUbi());
        dto.setStockDisponibleUbi(ubicacion.getStockDisponibleUbi());
        dto.setTieneAsiento(ubicacion.getTieneAsiento());

        return dto;
    }


    public UbicacionDTO getUbicacionDTOPorNombre(String nombreUbi) {
        Ubicacion ubicacion = ubicacionRepository.findByNombreUbi(nombreUbi)
        .orElseThrow(() -> new RuntimeException("Ubicacion con nombre: " + nombreUbi + " no encontrada"));

        UbicacionDTO dto = new UbicacionDTO();
        dto.setIdUbi(ubicacion.getIdUbi());
        dto.setNombreUbi(ubicacion.getNombreUbi());
        dto.setPrecioUbi(ubicacion.getPrecioUbi());
        dto.setStockDisponibleUbi(ubicacion.getStockDisponibleUbi());
        dto.setTieneAsiento(ubicacion.getTieneAsiento());

        return dto;
    }

    // REDUCE EL STOCK CUANDO SE COMPRA UN TICKET DE ZONA GENERAL
    public Ubicacion reducirStock(Integer idUbi) {
        Ubicacion ubicacion = getUbicacionById(idUbi);
        if (ubicacion.getStockDisponibleUbi() <= 0) {
            throw new RuntimeException("No hay stock disponible para la ubicación: " + ubicacion.getNombreUbi());
        }
        ubicacion.setStockDisponibleUbi(ubicacion.getStockDisponibleUbi() - 1);
        return ubicacionRepository.save(ubicacion);
    }

}