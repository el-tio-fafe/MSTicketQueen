package cl.duoc.msDireccion.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @InjectMocks //crea una instancia de RegionService e inyecta el objeto simulado de ArtistaRepository en ella
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



}
