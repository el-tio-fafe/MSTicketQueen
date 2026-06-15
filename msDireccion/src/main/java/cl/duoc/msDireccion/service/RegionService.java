package cl.duoc.msDireccion.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msDireccion.dto.CalleUpdateDTO;
import cl.duoc.msDireccion.dto.CiudadProvinciaUpdateDTO;
import cl.duoc.msDireccion.dto.ComunaUpdateDTO;
import cl.duoc.msDireccion.dto.RegionDTO;
import cl.duoc.msDireccion.dto.RegionUpdateDTO;
import cl.duoc.msDireccion.model.Calle;
import cl.duoc.msDireccion.model.CiudadProvincia;
import cl.duoc.msDireccion.model.Comuna;
import cl.duoc.msDireccion.model.Region;
import cl.duoc.msDireccion.repository.CalleRepository;
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

    @Autowired
    private CalleRepository calleRepository;


//REGIONES

    public List<Region> listarRegiones(){
        return regionRepository.findAll();
    }

    public Region buscarRegionCompletaPorId(Integer idRegion){
        return regionRepository.findById(idRegion)
            .orElseThrow(() -> new RuntimeException("Region con id: " + idRegion + " no encontrada"));
    }

    public RegionDTO buscarRegionPorId(Integer idRegion){
        Region region = regionRepository.findById(idRegion)
            .orElseThrow(() -> new RuntimeException("Region con id: " + idRegion + " no encontrada"));
            return new RegionDTO(region.getIdRegion() , region.getNombreRegion());
    }

    public RegionDTO buscarRegionPorNombre(String nombreRegion){
        Region region = regionRepository.findByNombreRegion(nombreRegion)
            .orElseThrow(() -> new RuntimeException("Region: " + nombreRegion + " no encontrada"));
            return new RegionDTO(region.getIdRegion(), region.getNombreRegion());
    }

    //ESTE METODO DEVUELVE LA REGION COMPLETA
    // public Region buscarRegionCompletaPorNombre(String nombreRegion){
    //     return regionRepository.findByNombreRegion(nombreRegion)
    //         .orElseThrow(() -> new RuntimeException("Region: " + nombreRegion + " no encontrada"));
    // }

    public Region guardarRegion(Region region){
        if(regionRepository.findByNombreRegion(region.getNombreRegion()).isPresent()){
            throw new RuntimeException("Ya existe la region: " + region.getNombreRegion());
        }
        return regionRepository.save(region);
    }


    //USO ESTE METODO PARA QUE AL ACTUALIZAR UNA REGION NO ME DEVUELVA LA REGION MAS LA LISTA 
    //COMPLETA DE CIUDADES O PROVINCIAS QUE TIENE ESA REGION.
    public RegionUpdateDTO actualizarRegionPorId(Integer idRegion, Region regionActualizada){
        Region region = regionRepository.findById(idRegion)
            .orElseThrow(() -> new RuntimeException("Region con id: " + idRegion + " no encontrada"));
        region.setNombreRegion(regionActualizada.getNombreRegion());
        Region regionGuardada = regionRepository.save(region);
        return new RegionUpdateDTO(regionGuardada.getNombreRegion());
    }

    //SOLO PARA EFECTOS DE APRENDIZAJE Y HACER PRUEBAS EN POSTMAN HICE ESTE METODO Y FUNCIONÓ .. :)
    // public void eliminarRegionPorId(Integer idRegion){
    //     if(!regionRepository.existsById(idRegion)){
    //         throw new RuntimeException("Region con id: " + idRegion + " no encontrada");
    //     }
    //     regionRepository.deleteById(idRegion);
    // }

    // public void eliminarRegionPorNombre(String nombreRegion){
    //     Region region = regionRepository.findByNombreRegion(nombreRegion)
    //         .orElseThrow(() -> new RuntimeException("Region: " + nombreRegion + " no encontrada"));
    //     regionRepository.deleteById(region.getIdRegion());        
    // }


//********************************************************************************************************/

//CIUDADES PROVINCIAS

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
            .orElseThrow(() -> new RuntimeException("Region con nombre: " + nombreRegion + " no encontrada"));
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
        Integer idRegion = ciudadProvincia.getRegion().getIdRegion();
        regionRepository.findById(idRegion)
            .orElseThrow(() -> new RuntimeException("No se puede guardar la Ciudad/Provincia: " 
                                                    + ciudadProvincia.getNombreCiudadProvincia() 
                                                    + " porque la región con id: " + idRegion + " no existe"));
        return ciudadProvinciaRepository.save(ciudadProvincia);
    }

    public CiudadProvincia actualizarCiudadProvinciaPorId(Integer idCiudadProvincia, CiudadProvinciaUpdateDTO ciuProvUpdateDTO){
        CiudadProvincia ciudadProvincia = ciudadProvinciaRepository.findById(idCiudadProvincia)
            .orElseThrow(() -> new RuntimeException("Ciudad/Provincia con id: " + idCiudadProvincia + " no encontrada"));
        ciudadProvincia.setNombreCiudadProvincia(ciuProvUpdateDTO.getNombreCiudadProvincia());
        return ciudadProvinciaRepository.save(ciudadProvincia);
    }

    //SOLO PARA EFECTOS DE APRENDIZAJE Y HACER PRUEBAS EN POSTMAN HICE ESTE METODO Y FUNCIONÓ .. :)
    // public void eliminarCiudadPorId(Integer idCiudadProvincia){
    //     if(!ciudadProvinciaRepository.existsById(idCiudadProvincia)){
    //         throw new RuntimeException("Ciudad/Provincia con id: " + idCiudadProvincia + " no encontrada");
    //     }
    //     ciudadProvinciaRepository.deleteById(idCiudadProvincia);
    // }

//*******************************************************************************************************************/
    
//COMUNAS

    public List<Comuna> listarComunas(){
        return comunaRepository.findAll();
    }

    public List<Comuna> listarComunasPorIdCiudadProvin(Integer idCiudadProvincia){
        CiudadProvincia ciudadProvincia = ciudadProvinciaRepository.findById(idCiudadProvincia)
            .orElseThrow(() -> new RuntimeException("Ciudad/Provincia con id: " + idCiudadProvincia + " no encontrada"));
        return ciudadProvincia.getComunas();
    }

    public List<Comuna> listarComunasPorNombreCiudadProvin(String nombreCiudadProvincia){
        CiudadProvincia ciudadProvincia = ciudadProvinciaRepository.findByNombreCiudadProvincia(nombreCiudadProvincia)
            .orElseThrow(() -> new RuntimeException("Ciudad/Provincia con nombre: " + nombreCiudadProvincia + " no encontrada"));
        return ciudadProvincia.getComunas();
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
        Integer idRegion = comuna.getRegion().getIdRegion();
        regionRepository.findById(idRegion)
            .orElseThrow(() -> new RuntimeException("No se puede guardar la Comuna: " 
                                                    + comuna.getNombreComuna()
                                                    + " porque la región con id: " + idRegion + " no existe"));

        Integer idCiudadProvincia = comuna.getCiudadProvincia().getIdCiudadProvincia();
        ciudadProvinciaRepository.findById(idCiudadProvincia)
            .orElseThrow(() -> new RuntimeException("No se puede guardar la Comuna: " 
                                                    + comuna.getNombreComuna()
                                                    + " porque la Ciudad/Provincia con id: " + idCiudadProvincia + " no existe"));

        return comunaRepository.save(comuna);
    }

    public Comuna actualizarComunaPorId(Integer idComuna, ComunaUpdateDTO comunaUpdDTOActualizada){
        Comuna comuna = comunaRepository.findById(idComuna)
            .orElseThrow(() -> new RuntimeException("Comuna con id: " + idComuna + " no encontrada"));
        comuna.setNombreComuna(comunaUpdDTOActualizada.getNombreComuna());
        return comunaRepository.save(comuna);
    }

    //SOLO PARA EFECTOS DE APRENDIZAJE Y HACER PRUEBAS EN POSTMAN HICE ESTE METODO Y FUNCIONÓ .. :)
    // public void eliminarComunaPorId(Integer idComuna){
    //     if(!comunaRepository.existsById(idComuna)){
    //         throw new RuntimeException("Comuna con id: " + idComuna + " no encontrada");
    //     }
    //     comunaRepository.deleteById(idComuna);
    // }



//*************************************************************************************************/
//CALLE

    public List<Calle> listarCalles() {
        return calleRepository.findAll();
    }

    public Calle buscarCallePorId(Integer idCalle) {
        return calleRepository.findById(idCalle)
            .orElseThrow(() -> new RuntimeException("Calle con id: " + idCalle + " no encontrada."));
    }

    public List<Calle> listarCallesPorComuna(Integer idComuna) {
        comunaRepository.findById(idComuna)
            .orElseThrow(() -> new RuntimeException("Comuna con id: " + idComuna + " no encontrada."));
        return calleRepository.findByComuna_IdComuna(idComuna);
    }

    public Calle guardarCalle(Calle calle) {
        Integer idComuna = calle.getComuna().getIdComuna();
        comunaRepository.findById(idComuna)
            .orElseThrow(() -> new RuntimeException("No se puede guardar la calle porque la comuna con id: " + idComuna + " no existe."));
        return calleRepository.save(calle);
    }

    public Calle actualizarNombreCalle(Integer idCalle, CalleUpdateDTO calleUpdateDTO) {
        Calle calle = calleRepository.findById(idCalle)
            .orElseThrow(() -> new RuntimeException("Calle con id: " + idCalle + " no encontrada."));
        calle.setNombreCalle(calleUpdateDTO.getNombreCalle());
        return calleRepository.save(calle);
    }


}
