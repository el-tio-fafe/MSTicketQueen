package cl.duoc.MSClientes.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.MSClientes.Model.Artista;
import cl.duoc.MSClientes.Repository.ArtistaRepository;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    public List<Artista> getAllArtistas() {// Método para obtener todos los artistas
        return artistaRepository.findAll();
    }

    public Artista getArtistaById(Integer id) {// Método para obtener un artista por su ID
        return artistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artista con id: " + id + " no encontrado."));//mensaje de error
    }

    public Artista getArtistaByRut(String rut) {// Método para obtener un artista por su RUT
        return artistaRepository.findByRutArt(rut)
                .orElseThrow(() -> new RuntimeException("Artista con rut: " + rut + " no encontrado."));//mensaje de error
    }

    public Artista getArtistaByCorreo(String correo) {// Método para obtener un artista por su correo
        return artistaRepository.findByCorreoArt(correo)
                .orElseThrow(() -> new RuntimeException("Artista con correo: " + correo + " no encontrado."));//mensaje de error 
    }

    public Artista saveArtista(Artista artista) {// Método para guardar un nuevo artista
        if (artistaRepository.findByRutArt(artista.getRutArt()).isPresent()) {// Verificar si ya existe un artista con el mismo rut y si lo hay tira error
            throw new RuntimeException("Ya existe un artista con el rut: " + artista.getRutArt());//mensaje de error
        }
        if (artistaRepository.findByCorreoArt(artista.getCorreoArt()).isPresent()) {// Verificar si ya existe un artista con el mismo correo y si lo hay tira error
            throw new RuntimeException("Ya existe un artista con el correo: " + artista.getCorreoArt());//mensaje de error
        }
        return artistaRepository.save(artista);//si no hay coincidencias, guarda el artista en la base de datos
    }

    
    
    //actualizar()
    //eliminar()
}
