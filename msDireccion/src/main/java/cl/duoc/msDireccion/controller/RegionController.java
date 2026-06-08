package cl.duoc.msDireccion.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msDireccion.dto.CalleUpdateDTO;
import cl.duoc.msDireccion.dto.CiudadProvinciaDTO;
import cl.duoc.msDireccion.dto.CiudadProvinciaUpdateDTO;
import cl.duoc.msDireccion.dto.ComunaDTO;
import cl.duoc.msDireccion.dto.ComunaUpdateDTO;
import cl.duoc.msDireccion.dto.RegionDTO;
import cl.duoc.msDireccion.model.Calle;
import cl.duoc.msDireccion.model.CiudadProvincia;
import cl.duoc.msDireccion.model.Comuna;
import cl.duoc.msDireccion.model.Region;
import cl.duoc.msDireccion.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/direccion")
@Tag(name = "Direcciones", description = "Operaciones sobre direcciones")

public class RegionController {

    @Autowired
    private RegionService regionService;


//REGIONES

    @GetMapping("/regiones")
    @Operation(
                summary = "Lista de regiones", 
                description = "Retorna la lista de todas las Regiones")
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

    @GetMapping("/buscar/region-completa/id/{idRegion}")
    @Operation(
                summary = "Buscar región por ID", 
                description = "Retorna una Región según el ID proporcionado incluyendo una lista que muestra todas las ciudades o provincias y otra lista que muestra todas las comunas que pertenecen a esa región")
    
    public ResponseEntity<?> buscarRegionPorIdCompleto(@PathVariable Integer idRegion){
        try {
            return ResponseEntity.ok(regionService.buscarRegionCompletaPorId(idRegion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/region-resumida/id/{idRegion}")
    @Operation(
                summary = "Buscar región por ID", 
                description = "Retorna una Región según el ID proporcionado")
    public ResponseEntity<?> buscarRegionPorId(@PathVariable Integer idRegion){
        try {
            return ResponseEntity.ok(regionService.buscarRegionPorId(idRegion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/region/nombre/{nombreRegion}")
    @Operation(
                summary = "Buscar región por Nombre de la Región", 
                description = "Retorna una Región según el nombre proporcionado")
    public ResponseEntity<?> buscarRegionResumidaPorNombre(@PathVariable String nombreRegion){
        try {
            return ResponseEntity.ok(regionService.buscarRegionPorNombre(nombreRegion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //ESTE METODO QUE VIENE DEL SERVICE DEVUELVE LA REGION COMPLETA
    // @GetMapping("/buscar/region/nombre/{nombreRegion}")
    // public ResponseEntity<?> buscarRegionPorNombre(@PathVariable String nombreRegion){
    //     try {
    //         return ResponseEntity.ok(regionService.buscarRegionCompletaPorNombre(nombreRegion));
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }

    @PostMapping("/guardar/region")
    @Operation(
                summary = "Guardar región", 
                description = "Guarda una nueva Región")
    public ResponseEntity<?> guardarRegion(@RequestBody Region region){
        try {
            return ResponseEntity.ok(regionService.guardarRegion(region));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/actualizar/region/id/{idRegion}")   //con el @PutMapping se actualizan todos los atributos
    @Operation(
                summary = "Actualizar región por ID", 
                description = "Actualiza una Región según el ID proporcionado")
    public ResponseEntity<?> actualizarRegionPorId(@PathVariable Integer idRegion, @RequestBody Region region){
        try {
            return ResponseEntity.ok(regionService.actualizarRegionPorId(idRegion, region));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //SOLO PARA EFECTOS DE APRENDIZAJE Y HACER PRUEBAS EN POSTMAN HICE ESTE METODO Y FUNCIONÓ .. :)
    // @DeleteMapping("/eliminar/region/id/{idRegion}")
    // public ResponseEntity<?> eliminarRegionPorId(@PathVariable Integer idRegion){
    //     try {
    //         regionService.eliminarRegionPorId(idRegion);
    //         return ResponseEntity.ok("Region con id: " + idRegion + " eliminada con exito");
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }
    

//**************************************************************************************************************************//

//CIUDADES PROVINCIAS
    
    
    @GetMapping("/ciudad-provincia/listar")
    @Operation(
                summary = "Lista todas las Ciudades o Provincias", 
                description = "Retorna una lista de Ciudades o Provincias registradas en el sistema")
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
    @Operation(
                summary = "Listar Ciudades o Provincias por el ID de la Región", 
                description = "Retorna una Lista de Ciudades o Provincias según el ID de la Región proporcionada")
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
    @Operation(
                summary = "Listar Ciudades o Provincias por nombre de Región", 
                description = "Retorna una Lista de Ciudades o Provincias según el nombre de la Región proporcionada")
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
    @Operation(
                summary = "Buscar Ciudades o Provincias por ID de la Ciudad o Provincia", 
                description = "Retorna la Ciudad o Provincia según el ID proporcionado")    
    public ResponseEntity<?> buscarCiudadProvinciaPorId(@PathVariable Integer idCiudadProvincia){
        try {
            return ResponseEntity.ok(regionService.buscarCiudadProvinciaPorId(idCiudadProvincia));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/ciudad-provincia/nombre/{nombreCiudadProvincia}")
    @Operation(
                summary = "Buscar Ciudades o Provincias por nombre", 
                description = "Retorna la Ciudad o Provincia según el nombre proporcionado")
    public ResponseEntity<?> buscarCiudadProvinciaPorNombre(@PathVariable String nombreCiudadProvincia){
        try {
            return ResponseEntity.ok(regionService.buscarCiudadProvinciaPorNombre(nombreCiudadProvincia));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/guardar/ciudad-provincia")
    @Operation(
                summary = "Guardar Ciudad o Provincia", 
                description = "Guarda la nueva Ciudad o Provincia") 
    public ResponseEntity<?> guardarCiudadProvincia(@RequestBody CiudadProvincia ciudadProvincia){
        try {
            return ResponseEntity.ok(regionService.guardarCiudadProvincia(ciudadProvincia));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/actualizar/ciudad-provincia/id/{idCiudadProvincia}")
    @Operation(
                summary = "Actualizar Ciudades o Provincias por ID de la Ciudad o Provincia", 
                description = "Actualiza la Ciudad o Provincia según el ID proporcionado") 
    public ResponseEntity<?> actualizarCiudadProvinciaPorId(@PathVariable Integer idCiudadProvincia, @RequestBody CiudadProvinciaUpdateDTO ciudadProvinciaUpdate){
        try {
            return ResponseEntity.ok(regionService.actualizarCiudadProvinciaPorId(idCiudadProvincia, ciudadProvinciaUpdate));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //SOLO PARA EFECTOS DE APRENDIZAJE Y HACER PRUEBAS EN POSTMAN HICE ESTE METODO Y FUNCIONÓ .. :)
    // @DeleteMapping("/eliminar/ciudad-provincia/id/{idCiudadProvincia}")
    // public ResponseEntity<?> eliminarCiudadProvinciaPorId(@PathVariable Integer idCiudadProvincia){
    //     try {
    //         regionService.eliminarCiudadPorId(idCiudadProvincia);
    //         return ResponseEntity.ok("Ciudad/Provincia con id: " + idCiudadProvincia + " eliminada con exito");
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }
    

//***************************************************************************************************************/
    
//COMUNAS

    @GetMapping("/comunas")
    @Operation(
                summary = "Listar Comunas registradas", 
                description = "Retorna una lista de las Comunas registradas en el sistema") 
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

    @GetMapping("/comuna/listar/id/{idCiudadProvincia}")
    @Operation(
                summary = "Listar Comunas por ID de la Ciudad o Provincia", 
                description = "Retorna una lista de Comunas que pertenecen al ID de la Ciudad/Provincia proporcionado") 
    public ResponseEntity<?> listarComunasPorIdCiudadProvincia(@PathVariable Integer idCiudadProvincia){
        try {
            List<Comuna> lista = regionService.listarComunasPorIdCiudadProvin(idCiudadProvincia);
            if(lista.isEmpty()){
                return ResponseEntity.badRequest().body("La Ciudad / Provincia: " + idCiudadProvincia + " no tiene comunas registradas");
            }
            List<ComunaDTO> listaDTO = lista.stream()
                .map(cp -> new ComunaDTO(
                    cp.getIdComuna(),
                    cp.getNombreComuna()
                ))
                .collect(Collectors.toList());
            return ResponseEntity.ok(listaDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/comuna/listar/nombre/{nombreCiudadProvincia}")
    @Operation(
                summary = "Listar Comunas por nombre de la Ciudad o Provincia", 
                description = "Retorna una lista de Comunas que pertenecen al nombre de la Ciudad/Provincia proporcionado")
    public ResponseEntity<?> listarComunasPorNombreCiudadProvincia(@PathVariable String nombreCiudadProvincia){
        try {
            List<Comuna> lista = regionService.listarComunasPorNombreCiudadProvin(nombreCiudadProvincia);
            if(lista.isEmpty()){
                return ResponseEntity.badRequest().body("La Ciudad / Provincia: " + nombreCiudadProvincia + " no tiene comunas registradas");
            }
            List<ComunaDTO> listaDTO = lista.stream()
                .map(cp -> new ComunaDTO(
                    cp.getIdComuna(),
                    cp.getNombreComuna()
                ))
                .collect(Collectors.toList());
            return ResponseEntity.ok(listaDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/buscar/comuna/id/{idComuna}")
    @Operation(
                summary = "Buscar Comunas por su ID", 
                description = "Busca la Comuna según el ID proporcionado")
    public ResponseEntity <?> buscarComunaPorId(@PathVariable Integer idComuna){
        try {
            return ResponseEntity.ok(regionService.buscarComunaPorId(idComuna));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscar/comuna/nombre/{nombreComuna}")
    @Operation(
                summary = "Buscar Comunas por su nombre", 
                description = "Busca la Comuna según el nombre proporcionado")
    public ResponseEntity<?> buscarComunaPorNombre(@PathVariable String nombreComuna){
        try {
            return ResponseEntity.ok(regionService.buscarComunaPorNombre(nombreComuna));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/guardar/comuna")
    @Operation(
                summary = "Guardar Comuna", 
                description = "Guarda una nueva Comuna en el sistema")
    public ResponseEntity<?> guardarComuna(@RequestBody Comuna comuna){
        try {
            return ResponseEntity.ok(regionService.guardarComuna(comuna));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());   
        }
    }

    @PatchMapping("/actualizar/comuna/id/{idComuna}")
    @Operation(
                summary = "Actualizar Comunas por su ID", 
                description = "Actualiza una Comuna según el ID proporcionado")
    public ResponseEntity<?> actualizarComunaPorId(@PathVariable Integer idComuna, @RequestBody ComunaUpdateDTO comunaUpdateDTO){
        try {
            return ResponseEntity.ok(regionService.actualizarComunaPorId(idComuna, comunaUpdateDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //SOLO PARA EFECTOS DE APRENDIZAJE Y HACER PRUEBAS EN POSTMAN HICE ESTE METODO Y FUNCIONÓ .. :)
    // @DeleteMapping("/eliminar/comuna/id/{idComuna}")
    // public ResponseEntity<?> eliminarComunaPorId(@PathVariable Integer idComuna){
    //     try {
    //         regionService.eliminarComunaPorId(idComuna);
    //         return ResponseEntity.ok("Comuna con id: " + idComuna + " eliminada con exito");
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body(e.getMessage());
    //     }
    // }


//******************************************************************************************************************/
//CALLE

    @GetMapping("/calles")
    @Operation(
                summary = "Listar Calles", 
                description = "Retorna una lista de Calles registradas en el sistema")
    public ResponseEntity<?> listarCalles() {
        List<Calle> lista = regionService.listarCalles();
        if (lista.isEmpty()) {
            return ResponseEntity.badRequest().body("No hay calles registradas");
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/buscar/calle/id/{idCalle}")
    @Operation(
                summary = "Buscar Calle por su ID", 
                description = "Busca una Calle según el ID proporcionado")
    public ResponseEntity<?> buscarCallePorId(@PathVariable Integer idCalle) {
        try {
            return ResponseEntity.ok(regionService.buscarCallePorId(idCalle));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/calle/listar/comuna/{idComuna}")
    @Operation(
                summary = "Listar Calle por el ID de la Comuna", 
                description = "Retorna una Lista de las Calles que pertenecen al ID de la Comuna proporcionado")
    public ResponseEntity<?> listarCallesPorComuna(@PathVariable Integer idComuna) {
        try {
            List<Calle> lista = regionService.listarCallesPorComuna(idComuna);
            if (lista.isEmpty()) {
                return ResponseEntity.badRequest().body("La comuna no tiene calles registradas");
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/guardar/calle")
    @Operation(
                summary = "Guardar Calle", 
                description = "Guarda una nueva Calle en el sistema")
    public ResponseEntity<?> guardarCalle(@RequestBody Calle calle) {
        try {
            return ResponseEntity.ok(regionService.guardarCalle(calle));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/actualizar/calle/id/{idCalle}")
    @Operation(
                summary = "Actualizar Calle por su ID", 
                description = "Actualiza una Calle según el ID proporcionado")
    public ResponseEntity<?> actualizarNombreCalle(@PathVariable Integer idCalle, @RequestBody CalleUpdateDTO calleUpdateDTO) {
        try {
            return ResponseEntity.ok(regionService.actualizarNombreCalle(idCalle, calleUpdateDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}