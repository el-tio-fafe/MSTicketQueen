package cl.duoc.pacienteMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.pacienteMS.model.Paciente;
import cl.duoc.pacienteMS.repository.PacienteRepository;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    //Listar todos los pacientes
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    // Buscar paciente por ID
    public Paciente buscarPorId(Integer id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    //Buscar paciente por RUT
    public Paciente buscarPorRut(String rut) {
        return pacienteRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    // guardar paciente completo (Paciente + Dirección + Contacto)
    public Paciente guardar(Paciente paciente) {

        // Relación bidireccional Dirección
        if (paciente.getDireccion() != null) {
            paciente.getDireccion().setPaciente(paciente);
        }

        // Relación bidireccional Contacto
        if (paciente.getContacto() != null) {
            paciente.getContacto().setPaciente(paciente);
        }

        return pacienteRepository.save(paciente);
    }

    //Actualizar paciente
    public Paciente actualizar(Integer id, Paciente pacienteActualizado) {

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        paciente.setRut(pacienteActualizado.getRut());
        paciente.setNombre(pacienteActualizado.getNombre());
        paciente.setApellido(pacienteActualizado.getApellido());
        paciente.setEdad(pacienteActualizado.getEdad());

        // Dirección
        if (pacienteActualizado.getDireccion() != null) {
            pacienteActualizado.getDireccion().setPaciente(paciente);
            paciente.setDireccion(pacienteActualizado.getDireccion());
        }

        // Contacto
        if (pacienteActualizado.getContacto() != null) {
            pacienteActualizado.getContacto().setPaciente(paciente);
            paciente.setContacto(pacienteActualizado.getContacto());
        }

        return pacienteRepository.save(paciente);
    }

    // Eliminar paciente
    public void eliminar(Integer id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente no existe");
        }
        pacienteRepository.deleteById(id);
    }
}