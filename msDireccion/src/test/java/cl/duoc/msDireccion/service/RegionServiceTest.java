package cl.duoc.msDireccion.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.msDireccion.model.Region;
import cl.duoc.msDireccion.repository.RegionRepository;

@ExtendWith(MockitoExtension.class)

public class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService regionService;

    private Region regionEjemplo;

    @BeforeEach
    void setUp(){
        regionEjemplo = new Region();
        regionEjemplo.setIdRegion(1);
        regionEjemplo.setNombreRegion("Metropolitana");
    }


    @Test
    void buscarPorId_encontrado(){
        //ARRANGE: preparamos la prueba, le decimos que hacer
        Optional<Region> optionalRegion = Optional.of(regionEjemplo);
        when(regionRepository.findById(1)).thenReturn(optionalRegion);

        //ACT: llamamos el estado real
        Region resultado = regionService.buscarRegionPorId(1);

    }




}
