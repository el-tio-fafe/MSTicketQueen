package cl.duoc.msDireccion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msDireccion.dto.RegionDTO;
import cl.duoc.msDireccion.dto.RegionUpdateDTO;
import cl.duoc.msDireccion.model.CiudadProvincia;
import cl.duoc.msDireccion.model.Comuna;
import cl.duoc.msDireccion.model.Region;
import cl.duoc.msDireccion.repository.RegionRepository;


@ExtendWith(MockitoExtension.class)
public class RegionServiceTest {

    @Mock //crea un objeto simulado de RegionRepository para ser utilizado en las pruebas
    private RegionRepository regionRepository;

    @InjectMocks //crea una instancia de RegionService e inyecta el objeto simulado de RegionRepository en ella
    private RegionService regionService;

    private Region regionEjemplo;

    @BeforeEach
    void setUp(){

        regionEjemplo = new Region();
        regionEjemplo.setIdRegion(1);
        regionEjemplo.setNombreRegion("Metropolitana");
    
    }


    @Test
    void listarRegiones_retornaListaDeEntidades() {
    // ARRANGE
    List<Region> listaMock = new ArrayList<>();
    listaMock.add(regionEjemplo); 

    when(regionRepository.findAll()).thenReturn(listaMock);

    // ACT
    List<Region> resultado = regionService.listarRegiones();

    // ASSERT
    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals("Metropolitana", resultado.get(0).getNombreRegion());
    
    verify(regionRepository, times(1)).findAll();
    }

    @Test
    void listarRegiones_vacia_retornaListaVacia() {
        // ARRANGE
        when(regionRepository.findAll()).thenReturn(new ArrayList<>());

        // ACT
        List<Region> resultado = regionService.listarRegiones();

        // ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        
        verify(regionRepository, times(1)).findAll();
    }




    @Test
    void buscarRegionCompletaPorId_encontrado() {
    // ARRANGE: Construimos una región completa con relaciones simuladas
    Region regionCompletaMock = new Region();
    regionCompletaMock.setIdRegion(1);
    regionCompletaMock.setNombreRegion("Metropolitana");

    // Simulamos una lista de Ciudades/Provincias
    List<CiudadProvincia> ciudadesMock = new ArrayList<>();
    CiudadProvincia stgo = new CiudadProvincia();
    stgo.setNombreCiudadProvincia("Santiago"); // Asumiendo que tiene este setter
    ciudadesMock.add(stgo);
    regionCompletaMock.setCiudadesProvincias(ciudadesMock);

    // Simulamos una lista de Comunas
    List<Comuna> comunasMock = new ArrayList<>();
    Comuna quilicura = new Comuna();
    quilicura.setNombreComuna("Quilicura"); // Asumiendo que tiene este setter
    comunasMock.add(quilicura);
    regionCompletaMock.setComunas(comunasMock);

    // Programamos el Mockito para que retorne nuestra región bien armada
    when(regionRepository.findById(1)).thenReturn(Optional.of(regionCompletaMock));

    // ACT: Ejecutamos el método del servicio
    Region resultado = regionService.buscarRegionCompletaPorId(1);

    // ASSERT: Validamos los atributos básicos y las listas internas
    assertNotNull(resultado);
    assertEquals(1, resultado.getIdRegion());
    assertEquals("Metropolitana", resultado.getNombreRegion());
    
    // Validamos que contenga las relaciones mapeadas
    assertNotNull(resultado.getCiudadesProvincias());
    assertEquals(1, resultado.getCiudadesProvincias().size());
    
    assertNotNull(resultado.getComunas());
    assertEquals(1, resultado.getComunas().size());

    verify(regionRepository, times(1)).findById(1);
    }


    @Test
    void buscarRegionCompletaPorId_noEncontrado_lanzaRuntimeException() {
    // ARRANGE: Forzamos a que retorne un Optional vacío para el ID 99
    when(regionRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT: Verificamos que dispare la excepción esperada
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.buscarRegionCompletaPorId(99);
    });

    // Validamos que el mensaje del orElseThrow de este método coincida perfectamente
    assertEquals("Region con id: 99 no encontrada", exception.getMessage());
    
    verify(regionRepository, times(1)).findById(99);
    }



    //AQUI RETORNAMOS UNA REGION DTO QUE SOLO TIENE EL ID Y EL NOMBRE DE LA REGION, SIN LAS RELACIONESDE CIUDAD/PROVINCIA Y COMUNAS
    @Test
    void buscarRegionPorId_encontrado(){
        //ARRANGE: preparamos la prueba, le decimos que hacer
        Optional<Region> optionalRegion = Optional.of(regionEjemplo);
        when(regionRepository.findById(1)).thenReturn(optionalRegion);

        //ACT: llamamos el estado real
        RegionDTO resultado = regionService.buscarRegionPorId(1);

        //Assert
        assertEquals(1, resultado.getIdRegion());
        assertEquals("Metropolitana", resultado.getNombreRegion());

    }

    @Test
    void buscarPorId_noEncontrado(){
        //ARRANGE: preparamos la prueba, para qe retorne una region vacia
        Optional<Region> regionVacia = Optional.empty();
        when(regionRepository.findById(99)).thenReturn(regionVacia);

        //ACT: llamamos el estado real
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            regionService.buscarRegionPorId(99);
        });

        assertEquals("Region con id: 99 no encontrada", exception.getMessage());
        
    }




    @Test
    void buscarRegionResumidaPorNombre_encontrado(){
        //ARRANGE: preparamos la prueba, le decimos que hacer
        Optional<Region> optionalRegion = Optional.of(regionEjemplo);
        when(regionRepository.findByNombreRegion("Metropolitana")).thenReturn(optionalRegion);

        //ACT: llamamos el estado real
        RegionDTO resultado = regionService.buscarRegionPorNombre("Metropolitana");

        //Assert
        assertEquals(1, resultado.getIdRegion());
        assertEquals("Metropolitana", resultado.getNombreRegion());

    }

    @Test
    void buscarRegionResumidaPorNombre_noEncontrado(){
        //ARRANGE: preparamos la prueba, para qe retorne una region vacia
        Optional<Region> regionVacia = Optional.empty();
        when(regionRepository.findByNombreRegion("Nunca Jamás")).thenReturn(regionVacia);

        //ACT: llamamos el estado real
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            regionService.buscarRegionPorNombre("Nunca Jamás");
        });

        assertEquals("Region: Nunca Jamás no encontrada", exception.getMessage());
        
    }




    @Test
    void guardarRegion_exitoso(){
        // ARRANGE: Preparamos una región nueva (sin ID) para enviar al servicio
        Region regionNueva = new Region();
        regionNueva.setNombreRegion("Valparaíso");

        // Simulamos la región que nos devolverá el repositorio una vez guardada (con ID asignado)
        Region regionGuardada = new Region();
        regionGuardada.setIdRegion(2);
        regionGuardada.setNombreRegion("Valparaíso");

        
        // 1. Al buscar "Valparaíso", simulamos que no existe (Optional.empty())
        when(regionRepository.findByNombreRegion("Valparaíso")).thenReturn(Optional.empty());
        // 2. Al guardar la región nueva, simulamos que el repositorio responde exitosamente con el objeto persistido
        when(regionRepository.save(regionNueva)).thenReturn(regionGuardada);

        // ACT: Ejecutamos la acción real del service
        Region resultado = regionService.guardarRegion(regionNueva);

        // ASSERT: Validamos que la respuesta contenga los datos esperados
        assertNotNull(resultado);
        assertEquals(2, resultado.getIdRegion());
        assertEquals("Valparaíso", resultado.getNombreRegion());
        
        // Verificamos que se interactuó con el repositorio de la forma correcta
        verify(regionRepository, times(1)).save(regionNueva);

    }


    @Test
    void guardarRegion_errorRegionYaExiste_lanzaRuntimeException() {
        // ARRANGE: Vamos a intentar guardar una región que ya existe (usamos "Metropolitana")
        Region regionIntentoGuardar = new Region();
        regionIntentoGuardar.setNombreRegion("Metropolitana");

        // Simulamos que al buscar "Metropolitana", el repositorio responde que SÍ existe (usando tu regionEjemplo)
        when(regionRepository.findByNombreRegion("Metropolitana")).thenReturn(Optional.of(regionEjemplo));

        // ACT & ASSERT: Esperamos que lance la RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            regionService.guardarRegion(regionIntentoGuardar);
        });

        // Verificamos el mensaje exacto que configuraste en tu Service
        assertEquals("Ya existe la region: Metropolitana", exception.getMessage());

        // Verificación crítica de seguridad: Nos aseguramos de que NUNCA llamó al método save()
        verify(regionRepository, never()).save(any(Region.class));
    }




    @Test
    void actualizarRegionPorId_exitoso() {
    // ARRANGE: 
    Integer idBuscado = 1;
    
    // Creamos el objeto con los nuevos datos que envía el usuario
    Region datosNuevos = new Region();
    datosNuevos.setNombreRegion("Metropolitana Modificada");

    // Simulamos la región que actualmente está en la BD usando la metropolitana del ejemplo
    Optional<Region> optionalRegion = Optional.of(regionEjemplo);
    when(regionRepository.findById(idBuscado)).thenReturn(optionalRegion);

    // Simulamos el comportamiento del save. Al guardar, Mockito devolverá la región con el nombre ya cambiado
    Region regionGuardadaEnBD = new Region();
    regionGuardadaEnBD.setIdRegion(idBuscado);
    regionGuardadaEnBD.setNombreRegion("Metropolitana Modificada");
    when(regionRepository.save(any(Region.class))).thenReturn(regionGuardadaEnBD);

    // ACT: Ejecutamos el método del servicio
    RegionUpdateDTO resultado = regionService.actualizarRegionPorId(idBuscado, datosNuevos);

    // ASSERT: Verificamos que el DTO de salida no sea nulo y contenga el nuevo nombre
    assertNotNull(resultado);
    assertEquals("Metropolitana Modificada", resultado.getNombreRegion());

    // Verificaciones de comportamiento
    verify(regionRepository, times(1)).findById(idBuscado);
    verify(regionRepository, times(1)).save(any(Region.class));
    }


    @Test
    void actualizarRegionPorId_noEncontrado_lanzaRuntimeException() {
    // ARRANGE: Intentamos actualizar un ID que no existe
    Integer idInexistente = 99;
    Region datosNuevos = new Region();
    datosNuevos.setNombreRegion("Biobío");

    // Simulamos que el repositorio no encuentra nada (Optional.empty())
    when(regionRepository.findById(idInexistente)).thenReturn(Optional.empty());

    // ACT & ASSERT: Esperamos que salte la excepción al no encontrar el ID
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.actualizarRegionPorId(idInexistente, datosNuevos);
    });

    // Validamos el mensaje exacto del .orElseThrow()
    assertEquals("Region con id: 99 no encontrada", exception.getMessage());

    // Verificación crítica: Si no existe, nunca debe intentar guardar nada en el repositorio
    verify(regionRepository, times(1)).findById(idInexistente);
    verify(regionRepository, never()).save(any(Region.class));
}




}


