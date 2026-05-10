package cl.duoc.msDireccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msDireccion.model.CiudadProvincia;
import cl.duoc.msDireccion.model.Comuna;
import cl.duoc.msDireccion.model.Region;
import cl.duoc.msDireccion.repository.CiudadProvinciaRepository;
import cl.duoc.msDireccion.repository.ComunaRepository;
import cl.duoc.msDireccion.repository.RegionRepository;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CiudadProvinciaRepository ciudadProvinciaRepository;

    @Autowired 
    private ComunaRepository comunaRepository;

    public List<Region> listarRegiones(){
        return regionRepository.findAll();
    }

    public Region buscarRegionPorId(Integer idRegion){
        return regionRepository.findById(idRegion)
            .orElseThrow(() -> new RuntimeException("Region con id: " + idRegion + " no encontrada"));
    }

    public Region buscarRegionPorNombre(String nombreRegion){
        return regionRepository.findByNombreRegion(nombreRegion)
            .orElseThrow(() -> new RuntimeException("Region: " + nombreRegion + " no encontrada"));
    }

    public Region guardarRegion(Region region){
        if(regionRepository.findByNombreRegion(region.getNombreRegion()).isPresent()){
            throw new RuntimeException("Ya existe la region: " + region.getNombreRegion());
        }
        return regionRepository.save(region);
    }

    public void eliminarRegionPorId(Integer idRegion){
        if(!regionRepository.existsById(idRegion)){
            throw new RuntimeException("Region con id: " + idRegion + " no encontrada");
        }
        regionRepository.deleteById(idRegion);
    }

    public void eliminarRegionPorNombre(String nombreRegion){
        Region region = regionRepository.findByNombreRegion(nombreRegion)
            .orElseThrow(() -> new RuntimeException("Region: " + nombreRegion + " no encontrada"));
        regionRepository.deleteById(region.getIdRegion());        
    }


    public Region actualizarRegionPorId(Integer idRegion, Region regionActualizada){
        Region region = regionRepository.findById(idRegion)
            .orElseThrow(() -> new RuntimeException("Region con id: " + idRegion + " no encontrada"));
        region.setNombreRegion(regionActualizada.getNombreRegion());
        return regionRepository.save(region);
    }


    public List<CiudadProvincia> listarCiudadesOProvincias(){
        return ciudadProvinciaRepository.findAll();
    }

    public List<CiudadProvincia> listarCiudadesProvinciasPorIdRegion(Integer idRegion){
        Region region = regionRepository.findById(idRegion)
            .orElseThrow(() -> new RuntimeException("Region con id: " + idRegion + " no encontrada"));
        return region.getCiudadesProvincias();
    }

    public List<CiudadProvincia> listarCiudadesProvinciasPorNombreRegion(String nombreRegion){
        Region region = regionRepository.findByNombreRegion(nombreRegion)
            .orElseThrow(() -> new RuntimeException("Region con id: " + nombreRegion + " no encontrada"));
        return region.getCiudadesProvincias();
    }

    public CiudadProvincia buscarCiudadProvinciaPorId(Integer idCiudadProvincia){
        return ciudadProvinciaRepository.findById(idCiudadProvincia)
            .orElseThrow(() -> new RuntimeException("Ciudad/Provincia con id: " + idCiudadProvincia + " no encontrada"));
    }

    public CiudadProvincia buscarCiudadProvinciaPorNombre(String nombreCiudadProvincia){
        return ciudadProvinciaRepository.findByNombreCiudadProvincia(nombreCiudadProvincia)
            .orElseThrow(() -> new RuntimeException("Ciudad/Provincia: " + nombreCiudadProvincia + " no encontrada"));
    }

    public CiudadProvincia guardarCiudadProvincia(CiudadProvincia ciudadProvincia){
        if(ciudadProvinciaRepository.findByNombreCiudadProvincia(ciudadProvincia.getNombreCiudadProvincia()).isPresent()){
            throw new RuntimeException("Ya existe la ciudad/provincia: " + ciudadProvincia.getNombreCiudadProvincia());
        }
        return ciudadProvinciaRepository.save(ciudadProvincia);
    }

    public void eliminarCiudadPorId(Integer idCiudadProvincia){
        if(!ciudadProvinciaRepository.existsById(idCiudadProvincia)){
            throw new RuntimeException("Ciudad/Provincia con id: " + idCiudadProvincia + " no encontrada");
        }
        ciudadProvinciaRepository.deleteById(idCiudadProvincia);
    }

    public CiudadProvincia actualizarCiudadProvinciaPorId(Integer idCiudadProvincia, CiudadProvincia ciudadProvinciaActualizada){
        CiudadProvincia ciudadProvincia = ciudadProvinciaRepository.findById(idCiudadProvincia)
            .orElseThrow(() -> new RuntimeException("Ciudad/Provincia con id: " + idCiudadProvincia + " no encontrada"));
        ciudadProvincia.setNombreCiudadProvincia(ciudadProvinciaActualizada.getNombreCiudadProvincia());
        return ciudadProvinciaRepository.save(ciudadProvincia);
    }


    public List<Comuna> listarComunas(){
        return comunaRepository.findAll();
    }

    public Comuna buscarComunaPorId(Integer idComuna){
        return comunaRepository.findById(idComuna)
            .orElseThrow(() -> new RuntimeException("Comuna con id: " + idComuna + " no encontrada"));
    }

    public Comuna buscarComunaPorNombre(String nombreComuna){
        return comunaRepository.findByNombreComuna(nombreComuna)
            .orElseThrow(() -> new RuntimeException("Comuna: " + nombreComuna + " no encontrada"));
    }

    public Comuna guardarComuna(Comuna comuna){
        if(comunaRepository.findByNombreComuna(comuna.getNombreComuna()).isPresent()){
            throw new RuntimeException("Ya existe la comuna: " + comuna.getNombreComuna());
        }
        return comunaRepository.save(comuna);
    }

    public void eliminarComunaPorId(Integer idComuna){
        if(!comunaRepository.existsById(idComuna)){
            throw new RuntimeException("Comuna con id: " + idComuna + " no encontrada");
        }
        comunaRepository.deleteById(idComuna);
    }

    public Comuna actualizarComunaPorId(Integer idComuna, Comuna comunaActualizada){
        Comuna comuna = comunaRepository.findById(idComuna)
            .orElseThrow(() -> new RuntimeException("Comuna con id: " + idComuna + " no encontrada"));
        comuna.setNombreComuna(comunaActualizada.getNombreComuna());
        return comunaRepository.save(comuna);
    }



}
