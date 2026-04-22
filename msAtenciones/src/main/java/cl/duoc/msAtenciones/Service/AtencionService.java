package cl.duoc.msAtenciones.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msAtenciones.Clients.DoctorClient;
import cl.duoc.msAtenciones.Clients.PacienteClient;
import cl.duoc.msAtenciones.DTO.AtencionDTO;
import cl.duoc.msAtenciones.DTO.DoctorDTO;
import cl.duoc.msAtenciones.DTO.PacienteDTO;
import cl.duoc.msAtenciones.Model.Atencion;
import cl.duoc.msAtenciones.Model.TipoAtencion;
import cl.duoc.msAtenciones.Repository.AtencionRepository;

@Service
public class AtencionService {

    @Autowired
    private AtencionRepository repo;

    @Autowired
    private PacienteClient pacienteClient;

    @Autowired
    private DoctorClient doctorClient;


    // Método para listar todas las atenciones
    public List<Atencion> Listar() {
        return repo.findAll();
    }

    // Método para guardar una nueva atención
    public void Guardar(Atencion atencion) {
        repo.save(atencion);
    }

    public Atencion BuscarPorID(Integer id) {
        return repo.findById(id)
        .orElseThrow(() -> new RuntimeException("Atención no encontrada"));
    }

    public AtencionDTO obtenerDetallesAtencion(Integer id) {
        Atencion atencion = repo.findById(id).orElseThrow(() -> new RuntimeException("Atención no encontrada"));

        PacienteDTO paciente = pacienteClient.obtenerPaciente(atencion.get().getPacienteId());

        DoctorDTO doctor = doctorClient.obtenerDoctor(atencion.get().getDoctorId());

        TipoAtencionDTO tipoAtencion = new TipoAtencionDTO(atencion.get().getTipoAtencion().getId(), 
                                                           atencion.get().getTipoAtencion().getNombre());

        AtencionDTO atencionCompleta = new AtencionDTO();

        atencionCompleta.setId(atencion.getId());
        atencionCompleta.setDiagnostico(atencion.getDiagnostico());
        atencionCompleta.setDoctor(doctor);
        atencionCompleta.setPaciente(paciente);
        atencionCompleta.setTipoAtencion(tipoAtencion);

        return atencionCompleta;
    }
}
