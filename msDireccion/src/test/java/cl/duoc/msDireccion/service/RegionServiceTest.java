package cl.duoc.msDireccion.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private Region regionEjemplo;

    @BeforeEach
    void setUp(){
        regionEjemplo = new Region();
        regionEjemplo.setIdRegion(1);
        regionEjemplo.setNombreRegion("Metropolitana");
        regionEjemplo.setCiudadesProvincias(new ArrayList<CiudadProvincia>()); 
        regionEjemplo.setComunas(new ArrayList<Comuna>());
    }


    @Test
    void buscarPorId_encontrado(){
        //ARRANGE: preparamos la prueba, le decimos que hacer
        Optional<Region> optionalRegion = Optional.of(regionEjemplo);
        when(regionRepository.findById(1)).thenReturn(optionalRegion);

        //ACT: llamamos el estado real
        Region resultado = regionService.buscarRegionCompletaPorId(null);

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

        assertEquals("Region no encontrada", exception.getMessage());
        
    }



    


}
