package cl.duoc.MSFacturacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.MSFacturacion.model.banco;
import cl.duoc.MSFacturacion.repository.bancoRepository;

@Service
public class BancoService {

    @Autowired
    private bancoRepository bancoRepository;

    public List<banco> listarBancos() {
        return bancoRepository.findAll();
    }

    public banco guardarBanco(banco banco) {
        if (banco.getNombre() == null || banco.getNombre().isBlank()) {
            throw new RuntimeException("El nombre del banco no puede estar vacío.");
        }
        return bancoRepository.save(banco);
    }

    public banco buscarBancoPorId(Integer id) {
        return bancoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Banco con id " + id + " no encontrado."));
    }

    public banco buscarBancoPorNombre(String nombre) {
        return bancoRepository.findByNombre(nombre)
            .orElseThrow(() -> new RuntimeException("Banco con nombre \"" + nombre + "\" no encontrado."));
    }

    public banco actualizarBanco(Integer id, banco bancoActualizado) {
        banco bancoExistente = buscarBancoPorId(id);
        bancoExistente.setNombre(bancoActualizado.getNombre());
        return bancoRepository.save(bancoExistente);
    }

    public void eliminarBancoPorId(Integer id) {
        buscarBancoPorId(id);
        bancoRepository.deleteById(id);
    }

    public void eliminarBancoPorNombre(String nombre) {
        banco bancoExistente = buscarBancoPorNombre(nombre);
        bancoRepository.deleteById(bancoExistente.getId());
    }
}
