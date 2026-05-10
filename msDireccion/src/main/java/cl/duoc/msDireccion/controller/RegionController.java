package cl.duoc.msDireccion.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msDireccion.dto.CiudadProvinciaDTO;
import cl.duoc.msDireccion.dto.ComunaDTO;
import cl.duoc.msDireccion.dto.RegionDTO;
import cl.duoc.msDireccion.model.CiudadProvincia;
import cl.duoc.msDireccion.model.Comuna;
import cl.duoc.msDireccion.model.Region;
import cl.duoc.msDireccion.service.RegionService;

@RestController
@RequestMapping("/api/v1/direccion")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/regiones")
    public ResponseEntity <?> listarRegiones(){
        List<Region> listarRegiones = regionService.listarRegiones();
        if(listarRegiones.isEmpty()){
            return ResponseEntity.badRequest().body("No hay regiones registradas");
        }else{
            List<RegionDTO> listaRegionDTO = listarRegiones.stream()
                .map(reg -> new RegionDTO(
                    reg.getIdRegion(),
                    reg.getNombreRegion()
                ))
                .collect(Collectors.toList());
            return ResponseEntity.ok(listaRegionDTO);
        }
    }

    @GetMapping("/buscar/region/id/{idRegion}")
    public ResponseEntity<?> buscarRegionPorId(@PathVariable Integer idRegion){
        try {
            return ResponseEntity.ok(regionService.buscarRegionPorId(idRegion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/region/nombre/{nombreRegion}")
    public ResponseEntity<?> buscarRegionPorNombre(@PathVariable String nombreRegion){
        try {
            return ResponseEntity.ok(regionService.buscarRegionPorNombre(nombreRegion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/guardar/region")
    public ResponseEntity<?> guardarRegion(@RequestBody Region region){
        try {
            return ResponseEntity.ok(regionService.guardarRegion(region));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/region/id/{idRegion}")
    public ResponseEntity<?> eliminarRegionPorId(@PathVariable Integer idRegion){
        try {
            regionService.eliminarRegionPorId(idRegion);
            return ResponseEntity.ok("Region con id: " + idRegion + " eliminada con exito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/region/id/{idRegion}")
    public ResponseEntity<?> actualizarRegionPorId(@PathVariable Integer idRegion, @RequestBody Region region){
        try {
            return ResponseEntity.ok(regionService.actualizarRegionPorId(idRegion, region));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    
    
    @GetMapping("/ciudad-provincia/listar")
    public ResponseEntity <?> listarTodasCiudadesProvincias(){
        List<CiudadProvincia> listarCiudadesProvincias = regionService.listarCiudadesOProvincias();
        if(listarCiudadesProvincias.isEmpty()){
            return ResponseEntity.badRequest().body("No hay ciudades o provincias registradas");
        }else{
            List<CiudadProvinciaDTO> listaCiudadProvinciaDTO = listarCiudadesProvincias.stream()
                .map(ciud -> new CiudadProvinciaDTO(
                    ciud.getIdCiudadProvincia(),
                    ciud.getNombreCiudadProvincia()
                ))
                .collect(Collectors.toList());
            return ResponseEntity.ok(listaCiudadProvinciaDTO); 
        } 
    }

    @GetMapping("/ciudad-provincia/listar/id/{idRegion}")
    public ResponseEntity<?> listarCiudadesProvinciasPorIdRegion(@PathVariable Integer idRegion){
        try {
            List<CiudadProvincia> lista = regionService.listarCiudadesProvinciasPorIdRegion(idRegion);
            if(lista.isEmpty()){
                return ResponseEntity.badRequest().body("La region no tiene ciudades/provincias registradas");
            }
            List<CiudadProvinciaDTO> listaDTO = lista.stream()
                .map(cp -> new CiudadProvinciaDTO(
                    cp.getIdCiudadProvincia(),
                    cp.getNombreCiudadProvincia()
                ))
                .collect(Collectors.toList());
            return ResponseEntity.ok(listaDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/ciudad-provincia/listar/nombre/{nombreRegion}")
    public ResponseEntity<?> listarCiudadesProvinciasPorNombreRegion(@PathVariable String nombreRegion){
        try {
            List<CiudadProvincia> lista = regionService.listarCiudadesProvinciasPorNombreRegion(nombreRegion);
            if(lista.isEmpty()){
                return ResponseEntity.badRequest().body("La region no tiene ciudades/provincias registradas");
            }
            List<CiudadProvinciaDTO> listaDTO = lista.stream()
                .map(cp -> new CiudadProvinciaDTO(
                    cp.getIdCiudadProvincia(),
                    cp.getNombreCiudadProvincia()
                ))
                .collect(Collectors.toList());
            return ResponseEntity.ok(listaDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/ciudad-provincia/id/{idCiudadProvincia}")
    public ResponseEntity<?> buscarCiudadProvinciaPorId(@PathVariable Integer idCiudadProvincia){
        try {
            return ResponseEntity.ok(regionService.buscarCiudadProvinciaPorId(idCiudadProvincia));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/ciudad-provincia/nombre/{nombreCiudadProvincia}")
    public ResponseEntity<?> buscarCiudadProvinciaPorNombre(@PathVariable String nombreCiudadProvincia){
        try {
            return ResponseEntity.ok(regionService.buscarCiudadProvinciaPorNombre(nombreCiudadProvincia));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/guardar/ciudad-provincia")
    public ResponseEntity<?> guardarCiudadProvincia(@RequestBody CiudadProvincia ciudadProvincia){
        try {
            return ResponseEntity.ok(regionService.guardarCiudadProvincia(ciudadProvincia));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/ciudad-provincia/id/{idCiudadProvincia}")
    public ResponseEntity<?> eliminarCiudadProvinciaPorId(@PathVariable Integer idCiudadProvincia){
        try {
            regionService.eliminarCiudadPorId(idCiudadProvincia);
            return ResponseEntity.ok("Ciudad/Provincia con id: " + idCiudadProvincia + " eliminada con exito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/ciudad-provincia/id/{idCiudadProvincia}")
    public ResponseEntity<?> actualizarCiudadProvinciaPorId(@PathVariable Integer idCiudadProvincia, @RequestBody CiudadProvincia ciudadProvincia){
        try {
            return ResponseEntity.ok(regionService.actualizarCiudadProvinciaPorId(idCiudadProvincia, ciudadProvincia));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    



    @GetMapping("/comunas")
    public ResponseEntity<?> listarComunas(){
        List<Comuna> listarComunas = regionService.listarComunas();
        if(listarComunas.isEmpty()){
            return ResponseEntity.badRequest().body("No hay comunas registradas");
        }else{
            List<ComunaDTO> listaComunaDTO = listarComunas.stream()
                .map(comu -> new ComunaDTO(
                    comu.getIdComuna(),
                    comu.getNombreComuna()
                ))
                .collect(Collectors.toList());
            return ResponseEntity.ok(listaComunaDTO);
        }
    }

    @GetMapping("/buscar/comuna/id/{idComuna}")
    public ResponseEntity <?> buscarComunaPorId(@PathVariable Integer idComuna){
        try {
            return ResponseEntity.ok(regionService.buscarComunaPorId(idComuna));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/comuna/nombre/{nombreComuna}")
    public ResponseEntity<?> buscarComunaPorNombre(@PathVariable String nombreComuna){
        try {
            return ResponseEntity.ok(regionService.buscarComunaPorNombre(nombreComuna));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/guardar/comuna")
    public ResponseEntity<?> guardarComuna(@RequestBody Comuna comuna){
        try {
            return ResponseEntity.ok(regionService.guardarComuna(comuna));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());   
        }
    }

    @DeleteMapping("/eliminar/comuna/id/{idComuna}")
    public ResponseEntity<?> eliminarComunaPorId(@PathVariable Integer idComuna){
        try {
            regionService.eliminarComunaPorId(idComuna);
            return ResponseEntity.ok("Comuna con id: " + idComuna + " eliminada con exito");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/comuna/id/{idComuna}")
    public ResponseEntity<?> actualizarComunaPorId(@PathVariable Integer idComuna, @RequestBody Comuna comuna){
        try {
            return ResponseEntity.ok(regionService.actualizarComunaPorId(idComuna, comuna));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}