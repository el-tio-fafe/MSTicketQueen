package cl.duoc.msDireccion.service;



import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msDireccion.dto.RegionDTO;
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

    private RegionDTO regionDTOEjemplo;

    private Region regionEjemplo;

    @BeforeEach
    void setUp(){

        regionDTOEjemplo = new RegionDTO();
        regionDTOEjemplo.setIdRegion(1);
        regionDTOEjemplo.setNombreRegion("Metropolitana");


        regionEjemplo = new Region();
        regionEjemplo.setIdRegion(1);
        regionEjemplo.setNombreRegion("Metropolitana");
    
    }


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

        // Reglas de Mockito:
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
}


