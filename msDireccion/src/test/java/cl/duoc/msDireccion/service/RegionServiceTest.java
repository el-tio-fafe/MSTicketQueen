package cl.duoc.msDireccion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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


@ExtendWith(MockitoExtension.class)
public class RegionServiceTest {

    @Mock //crea un objeto simulado de RegionRepository para ser utilizado en las pruebas
    private RegionRepository regionRepository;

    @Mock
    private CiudadProvinciaRepository ciudadProvinciaRepository;

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private CalleRepository calleRepository;

    @InjectMocks //crea una instancia de RegionService e inyecta el objeto simulado de RegionRepository en ella
    private RegionService regionService;

    private Region regionEjemplo;
    private CiudadProvincia ciudadProvinciaEjemplo;
    private Comuna comunaEjemplo1;
    private Comuna comunaEjemplo2;
    private Calle calleEjemplo1;
    private Calle calleEjemplo2;

    @BeforeEach
    void setUp(){
        
        regionEjemplo = new Region();
        regionEjemplo.setIdRegion(1);
        regionEjemplo.setNombreRegion("Metropolitana");

        ciudadProvinciaEjemplo = new CiudadProvincia();
        ciudadProvinciaEjemplo.setIdCiudadProvincia(1);
        ciudadProvinciaEjemplo.setNombreCiudadProvincia("Santiago");

        comunaEjemplo1 = new Comuna();
        comunaEjemplo1.setIdComuna(1);
        comunaEjemplo1.setNombreComuna("Quilicura");

        comunaEjemplo2 = new Comuna();
        comunaEjemplo2.setIdComuna(2);
        comunaEjemplo2.setNombreComuna("Huechuraba");

        calleEjemplo1 = new Calle();
        calleEjemplo1.setIdCalle(1);
        calleEjemplo1.setNombreCalle("Av. Manuel Antonio Matta");
        calleEjemplo1.setNumeroCalle("1230");
        calleEjemplo1.setNumeroDepto(null);
        calleEjemplo1.setComuna(comunaEjemplo1);

        
        calleEjemplo2 = new Calle();
        calleEjemplo2.setIdCalle(2);
        calleEjemplo2.setNombreCalle("Américo Vespucio");
        calleEjemplo2.setNumeroCalle("1500");
        calleEjemplo2.setNumeroDepto("Depto 402");
        calleEjemplo2.setComuna(comunaEjemplo2);
    }

//****************************************************************************************************** */
//TEST REGION

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
    // ARRANGE: 
    // Simulamos una lista de Ciudades/Provincias
    List<CiudadProvincia> ciudadesMock = new ArrayList<>();
    CiudadProvincia stgo = new CiudadProvincia();
    stgo.setNombreCiudadProvincia("Santiago"); 
    ciudadesMock.add(stgo);
    regionEjemplo.setCiudadesProvincias(ciudadesMock);

    // Simulamos una lista de Comunas
    List<Comuna> comunasMock = new ArrayList<>();
    Comuna quilicura = new Comuna();
    quilicura.setNombreComuna("Quilicura"); 
    comunasMock.add(quilicura);
    regionEjemplo.setComunas(comunasMock);

    // Programamos el Mockito para que retorne nuestra región bien armada
    when(regionRepository.findById(1)).thenReturn(Optional.of(regionEjemplo));

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
    void buscarRegionPorId_noEncontrado(){
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
        // ARRANGE: 
        // Al buscar "Metropolitana", simulamos que no existe (Optional.empty())
        when(regionRepository.findByNombreRegion("Metropolitana")).thenReturn(Optional.empty());
        // Al guardar la región nueva, simulamos que el repositorio responde exitosamente con el objeto persistido
        when(regionRepository.save(regionEjemplo)).thenReturn(regionEjemplo);

        // ACT: Ejecutamos la acción real del service
        Region resultado = regionService.guardarRegion(regionEjemplo);

        // ASSERT: Validamos que la respuesta contenga los datos esperados
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdRegion());
        assertEquals("Metropolitana", resultado.getNombreRegion());
        
        verify(regionRepository, times(1)).save(regionEjemplo);

    }

    @Test
    void guardarRegion_errorRegionYaExiste_lanzaRuntimeException() {
        // ARRANGE: 
        // Simulamos que al buscar "Metropolitana", el repositorio responde que SÍ existe (usando tu regionEjemplo)
        when(regionRepository.findByNombreRegion("Metropolitana")).thenReturn(Optional.of(regionEjemplo));

        // ACT & ASSERT: Esperamos que lance la RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            regionService.guardarRegion(regionEjemplo);
        });

        // Verificamos el mensaje exacto
        assertEquals("Ya existe la region: Metropolitana", exception.getMessage());

        verify(regionRepository, never()).save(any(Region.class));
    }




    @Test
    void actualizarRegionPorId_exitoso() {
    // ARRANGE: 
    // Creamos el objeto con los nuevos datos que envía el usuario
    Region datosNuevos = new Region();
    datosNuevos.setNombreRegion("Metropolitana Modificada");

    // Simulamos la región que actualmente está en la BD usando la metropolitana del ejemplo
    Optional<Region> optionalRegion = Optional.of(regionEjemplo);
    when(regionRepository.findById(1)).thenReturn(optionalRegion);

    // Simulamos el comportamiento del save. Al guardar, Mockito devolverá la región con el nombre ya cambiado
    Region regionGuardadaEnBD = new Region();
    regionGuardadaEnBD.setIdRegion(1);
    regionGuardadaEnBD.setNombreRegion("Metropolitana Modificada");
    when(regionRepository.save(any(Region.class))).thenReturn(regionGuardadaEnBD);

    // ACT: Ejecutamos el método del servicio
    RegionUpdateDTO resultado = regionService.actualizarRegionPorId(1, datosNuevos);

    // ASSERT: Verificamos que el DTO de salida no sea nulo y contenga el nuevo nombre
    assertNotNull(resultado);
    assertEquals("Metropolitana Modificada", resultado.getNombreRegion());

    // Verificaciones
    verify(regionRepository, times(1)).findById(1);
    verify(regionRepository, times(1)).save(any(Region.class));
    }

    @Test
    void actualizarRegionPorId_noEncontrado_lanzaRuntimeException() {
    // ARRANGE: Intentamos actualizar un ID que no existe
    Region datosNuevos = new Region();
    datosNuevos.setNombreRegion("Biobío");

    // Simulamos que el repositorio no encuentra nada (Optional.empty())
    when(regionRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT: Esperamos que salte la excepción al no encontrar el ID
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.actualizarRegionPorId(99, datosNuevos);
    });

    // Validamos el mensaje exacto del .orElseThrow()
    assertEquals("Region con id: 99 no encontrada", exception.getMessage());

    // Verificación crítica: Si no existe, nunca debe intentar guardar nada en el repositorio
    verify(regionRepository, times(1)).findById(99);
    verify(regionRepository, never()).save(any(Region.class));
}

//****************************************************************************************************** */
//TEST CIUDAD-PROVINCIA

    @Test
    void listarCiudadProvincia_retornaListaDeEntidades() {
    // ARRANGE
    List<CiudadProvincia> listaMock = new ArrayList<>();
    listaMock.add(ciudadProvinciaEjemplo); 

    when(ciudadProvinciaRepository.findAll()).thenReturn(listaMock);

    // ACT

    List<CiudadProvincia> resultado = regionService.listarCiudadesOProvincias();

    // ASSERT
    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals("Santiago", resultado.get(0).getNombreCiudadProvincia());
    
    verify(ciudadProvinciaRepository, times(1)).findAll();
    }

    @Test
    void listarCiudadProvincia_vacia_retornaListaVacia() {
        // ARRANGE
        when(ciudadProvinciaRepository.findAll()).thenReturn(new ArrayList<>());

        // ACT
        List<CiudadProvincia> resultado = regionService.listarCiudadesOProvincias();

        // ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        
        verify(ciudadProvinciaRepository, times(1)).findAll();
    }


    @Test
    void listarCiudadesProvinciasPorIdRegion_encontrado_retornaLista() {
    // ARRANGE: 
    //  Creación de la lista de ciudades que simula estar dentro de esa región
    List<CiudadProvincia> ciudadesMock = new ArrayList<>();
    CiudadProvincia santiago = new CiudadProvincia();
    santiago.setIdCiudadProvincia(1);
    santiago.setNombreCiudadProvincia("Santiago");
    santiago.setRegion(regionEjemplo);
    ciudadesMock.add(santiago);

    // Adjuntamos la lista a la región mockeada
    regionEjemplo.setCiudadesProvincias(ciudadesMock);

    // Programamos el repositorio de regiones para que devuelva la región armada
    when(regionRepository.findById(1)).thenReturn(Optional.of(regionEjemplo));

    // ACT: Ejecutamos el método del servicio
    List<CiudadProvincia> resultado = regionService.listarCiudadesProvinciasPorIdRegion(1);

    // ASSERT: Validamos el resultado de la lista
    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals("Santiago", resultado.get(0).getNombreCiudadProvincia());
    assertEquals(1, resultado.get(0).getRegion().getIdRegion());

    verify(regionRepository, times(1)).findById(1);
    }

    @Test
    void listarCiudadesProvinciasPorIdRegion_noEncontrado_lanzaRuntimeException() {
    // ARRANGE:
    when(regionRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT:
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.listarCiudadesProvinciasPorIdRegion(99);
    });

    assertEquals("Region con id: 99 no encontrada", exception.getMessage());

    verify(regionRepository, times(1)).findById(99);
    }


    @Test
    void listarCiudadesProvinciasPorNombreRegion_encontrado_retornaLista() {
    // ARRANGE: 
    //  Creación de la lista de ciudades que simula estar dentro de esa región
    List<CiudadProvincia> ciudadesMock = new ArrayList<>();
    CiudadProvincia santiago = new CiudadProvincia();
    santiago.setIdCiudadProvincia(1);
    santiago.setNombreCiudadProvincia("Santiago");
    santiago.setRegion(regionEjemplo);
    ciudadesMock.add(santiago);

    // Adjuntamos la lista a la región mockeada
    regionEjemplo.setCiudadesProvincias(ciudadesMock);

    // Programamos el repositorio de regiones para que devuelva la región armada
    when(regionRepository.findByNombreRegion("Metropolitana")).thenReturn(Optional.of(regionEjemplo));

    // ACT: Ejecutamos el método del servicio
    List<CiudadProvincia> resultado = regionService.listarCiudadesProvinciasPorNombreRegion("Metropolitana");

    // ASSERT: Validamos el resultado de la lista
    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals("Santiago", resultado.get(0).getNombreCiudadProvincia());
    assertEquals(1, resultado.get(0).getRegion().getIdRegion());

    verify(regionRepository, times(1)).findByNombreRegion("Metropolitana");
    }

    @Test
    void listarCiudadesProvinciasPorNombreRegion_NoEncontrado_LanzaRuntimeException() {
        // ARRANGE
        when(regionRepository.findByNombreRegion("Nunca Jamas")).thenReturn(Optional.empty());

        // ACT & ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            regionService.listarCiudadesProvinciasPorNombreRegion("Nunca Jamas");
        });

        assertEquals("Region con nombre: Nunca Jamas no encontrada", exception.getMessage());
        verify(regionRepository, times(1)).findByNombreRegion("Nunca Jamas");
    }



    @Test
    void buscarCiudadProvinciaPorId_encontrado(){
        //ARRANGE: preparamos la prueba, le decimos que hacer
        Optional<CiudadProvincia> optionalRegion = Optional.of(ciudadProvinciaEjemplo);
        when(ciudadProvinciaRepository.findById(1)).thenReturn(optionalRegion);

        //ACT: llamamos el estado real
        CiudadProvincia resultado = regionService.buscarCiudadProvinciaPorId(1);

        //Assert
        assertEquals(1, resultado.getIdCiudadProvincia());
        assertEquals("Santiago", resultado.getNombreCiudadProvincia());

    }
    
    @Test
    void buscarCiudadProvinciaPorId_noEncontrado(){
        //ARRANGE: preparamos la prueba, para qe retorne una region vacia
        Optional<CiudadProvincia> ciudadProvinciaVacia = Optional.empty();
        when(ciudadProvinciaRepository.findById(99)).thenReturn(ciudadProvinciaVacia);

        //ACT: llamamos el estado real
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            regionService.buscarCiudadProvinciaPorId(99);
        });

        assertEquals("Ciudad/Provincia con id: 99 no encontrada", exception.getMessage());
        
    }



    @Test
    void buscarCiudadProvinciaPorNombre_encontrado(){
        //ARRANGE: preparamos la prueba, le decimos que hacer
        Optional<CiudadProvincia> optionalRegion = Optional.of(ciudadProvinciaEjemplo);
        when(ciudadProvinciaRepository.findByNombreCiudadProvincia("Santiago")).thenReturn(optionalRegion);

        //ACT: llamamos el estado real
        CiudadProvincia resultado = regionService.buscarCiudadProvinciaPorNombre("Santiago");

        //Assert
        assertEquals(1, resultado.getIdCiudadProvincia());
        assertEquals("Santiago", resultado.getNombreCiudadProvincia());

    }

    @Test
    void buscarCiudadProvinciaPorNombre_noEncontrado(){
        //ARRANGE: preparamos la prueba, para qe retorne una region vacia
        Optional<CiudadProvincia> ciudadProvinciaVacia = Optional.empty();
        when(ciudadProvinciaRepository.findByNombreCiudadProvincia("Nunca Jamas")).thenReturn(ciudadProvinciaVacia);

        //ACT: llamamos el estado real
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            regionService.buscarCiudadProvinciaPorNombre("Nunca Jamas");
        });

        assertEquals("Ciudad/Provincia: Nunca Jamas no encontrada", exception.getMessage());
        
    }



    @Test
    void guardarCiudadProvincia_Exitoso() {
    // ARRANGE: 
    // Preparamos una ciudad o provincia nueva (asociándole la región)
    CiudadProvincia ciudadProvinciaNueva = new CiudadProvincia();
    ciudadProvinciaNueva.setNombreCiudadProvincia("Santiago");
    ciudadProvinciaNueva.setRegion(regionEjemplo); 

    // Simulamos la Ciudad o Provincia que nos devolverá el repositorio una vez guardada
    CiudadProvincia ciudadProvinciaGuardada = new CiudadProvincia();
    ciudadProvinciaGuardada.setIdCiudadProvincia(1);
    ciudadProvinciaGuardada.setNombreCiudadProvincia("Santiago");
    ciudadProvinciaGuardada.setRegion(regionEjemplo);


    //Validamos que el nombre no esté duplicado
    when(ciudadProvinciaRepository.findByNombreCiudadProvincia("Santiago")).thenReturn(Optional.empty());
    
    //Validamos que la región asociada realmente exista en la BD
    when(regionRepository.findById(1)).thenReturn(Optional.of(regionEjemplo));
    
    //Simulamos el guardado exitoso
    when(ciudadProvinciaRepository.save(ciudadProvinciaNueva)).thenReturn(ciudadProvinciaGuardada);

    // ACT: Ejecutamos la acción real del service
    CiudadProvincia resultado = regionService.guardarCiudadProvincia(ciudadProvinciaNueva);

    // ASSERT: Validamos que la respuesta contenga los datos esperados
    assertNotNull(resultado);
    assertEquals(1, resultado.getIdCiudadProvincia());
    assertEquals("Santiago", resultado.getNombreCiudadProvincia());
    assertNotNull(resultado.getRegion());
    assertEquals(1, resultado.getRegion().getIdRegion());
    
    
    verify(ciudadProvinciaRepository, times(1)).findByNombreCiudadProvincia("Santiago");
    verify(regionRepository, times(1)).findById(1);
    verify(ciudadProvinciaRepository, times(1)).save(ciudadProvinciaNueva);
    }

    @Test
    void guardarCiudadProvincia_NombreDuplicado_LanzaRuntimeException() {
    // ARRANGE: Preparamos la entidad a guardar
    CiudadProvincia ciudadProvinciaDuplicada = new CiudadProvincia();
    ciudadProvinciaDuplicada.setNombreCiudadProvincia("Santiago");

    // Simulamos que el repositorio ya encuentra una ciudad con ese nombre
    CiudadProvincia ciudadExistente = new CiudadProvincia();
    ciudadExistente.setIdCiudadProvincia(99);
    ciudadExistente.setNombreCiudadProvincia("Santiago");

    when(ciudadProvinciaRepository.findByNombreCiudadProvincia("Santiago"))
        .thenReturn(Optional.of(ciudadExistente));

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.guardarCiudadProvincia(ciudadProvinciaDuplicada);
    });

    assertEquals("Ya existe la ciudad/provincia: Santiago", exception.getMessage());
    
    verify(ciudadProvinciaRepository, times(1)).findByNombreCiudadProvincia("Santiago");
    verify(regionRepository, times(0)).findById(anyInt());
    verify(ciudadProvinciaRepository, times(0)).save(ciudadProvinciaDuplicada);
    }

    @Test
    void guardarCiudadProvincia_RegionNoExiste_LanzaRuntimeException() {
    // ARRANGE: Creamos una región local con un ID simulado
    Region regionInexistente = new Region();
    regionInexistente.setIdRegion(99);

    // Asociamos esta región a la nueva ciudad
    CiudadProvincia ciudadProvinciaNueva = new CiudadProvincia();
    ciudadProvinciaNueva.setNombreCiudadProvincia("Concepción");
    ciudadProvinciaNueva.setRegion(regionInexistente);

    //El nombre está disponible
    when(ciudadProvinciaRepository.findByNombreCiudadProvincia("Concepción"))
        .thenReturn(Optional.empty());
    
    //Al buscar la región por ID, simulamos que retorna un Optional vacío
    when(regionRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.guardarCiudadProvincia(ciudadProvinciaNueva);
    });

    String mensajeEsperado = "No se puede guardar la Ciudad/Provincia: Concepción porque la región con id: 99 no existe";
    assertEquals(mensajeEsperado, exception.getMessage());

    verify(ciudadProvinciaRepository, times(1)).findByNombreCiudadProvincia("Concepción");
    verify(regionRepository, times(1)).findById(99);
    // Aseguramos que jamás se llamó al método save debido al error de la región
    verify(ciudadProvinciaRepository, times(0)).save(ciudadProvinciaNueva);
    }



    @Test
    void actualizarCiudadProvinciaPorId_Exitoso() {
    // ARRANGE: 
    //El DTO que viene desde el Controller con la nueva información
    CiudadProvinciaUpdateDTO updateDTO = new CiudadProvinciaUpdateDTO();
    updateDTO.setNombreCiudadProvincia("Santiago Actualizado");

    //La entidad modificada que esperamos que retorne el repositorio tras el .save()
    CiudadProvincia ciudadProvinciaActualizada = new CiudadProvincia();
    ciudadProvinciaActualizada.setIdCiudadProvincia(1);
    ciudadProvinciaActualizada.setNombreCiudadProvincia("Santiago Actualizado");

    // Comportamiento de los Mocks
    when(ciudadProvinciaRepository.findById(1)).thenReturn(Optional.of(ciudadProvinciaEjemplo));
    when(ciudadProvinciaRepository.save(ciudadProvinciaEjemplo)).thenReturn(ciudadProvinciaActualizada);

    // ACT
    CiudadProvincia resultado = regionService.actualizarCiudadProvinciaPorId(1, updateDTO);

    // ASSERT
    assertNotNull(resultado);
    assertEquals(1, resultado.getIdCiudadProvincia());
    assertEquals("Santiago Actualizado", resultado.getNombreCiudadProvincia()); // Verificamos el cambio de nombre
    
    verify(ciudadProvinciaRepository, times(1)).findById(1);
    verify(ciudadProvinciaRepository, times(1)).save(ciudadProvinciaEjemplo);
    }

    @Test
    void actualizarCiudadProvinciaPorId_NoEncontrado_LanzaRuntimeException() {
    // ARRANGE
    CiudadProvinciaUpdateDTO updateDTO = new CiudadProvinciaUpdateDTO();
    updateDTO.setNombreCiudadProvincia("Providencia");

    when(ciudadProvinciaRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.actualizarCiudadProvinciaPorId(99, updateDTO);
    });

    assertEquals("Ciudad/Provincia con id: 99 no encontrada", exception.getMessage());
    
    verify(ciudadProvinciaRepository, times(1)).findById(99);
    verify(ciudadProvinciaRepository, times(0)).save(any(CiudadProvincia.class));
    }


//*******************************************************************************************************************/    
//COMUNAS

    @Test
    void listarComunas_RetornaListaDeEntidades() {
    // ARRANGE: 
    List<Comuna> listaMock = new ArrayList<>();
    listaMock.add(comunaEjemplo1); 

    // Mockeamos el repositorio correspondiente
    when(comunaRepository.findAll()).thenReturn(listaMock);

    // ACT: Invocamos al método del service centralizado
    List<Comuna> resultado = regionService.listarComunas();

    // ASSERT: Validaciones físicas y de contenido
    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals("Quilicura", resultado.get(0).getNombreComuna());
    
    verify(comunaRepository, times(1)).findAll();
    }

    @Test
    void listarComunas_vacia_retornaListaVacia() {
        // ARRANGE
        when(comunaRepository.findAll()).thenReturn(new ArrayList<>());

        // ACT
        List<Comuna> resultado = regionService.listarComunas();

        // ASSERT
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        
        verify(comunaRepository, times(1)).findAll();
    }


    @Test
    void listarComunasPorIdCiudadProvincia_encontrado_retornaLista() {
    // ARRANGE: 
    //  Creación de la lista de ciudades que simula estar dentro de esa región
    List<Comuna> comunasMock = new ArrayList<>();
    Comuna quilicura = new Comuna();
    quilicura.setIdComuna(1);
    quilicura.setNombreComuna("Quilicura");
    quilicura.setCiudadProvincia(ciudadProvinciaEjemplo);
    comunasMock.add(quilicura);

    // Adjuntamos la lista a la ciudad o provincia mockeada
    ciudadProvinciaEjemplo.setComunas(comunasMock);

    // Programamos el repositorio de comunas para que devuelva la comuna armada
    when(ciudadProvinciaRepository.findById(1)).thenReturn(Optional.of(ciudadProvinciaEjemplo));

    // ACT: Ejecutamos el método del servicio
    List<Comuna> resultado = regionService.listarComunasPorIdCiudadProvin(1);

    // ASSERT: Validamos el resultado de la lista
    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals("Quilicura", resultado.get(0).getNombreComuna());
    assertEquals(1, resultado.get(0).getCiudadProvincia().getIdCiudadProvincia());

    verify(ciudadProvinciaRepository, times(1)).findById(1);
    }

    @Test
    void listarComunasPorIdCiudadProvincia_NoEncontrado_LanzaRuntimeException() {
    // ARRANGE;
    // Configuramos el repositorio correcto para retornar un Optional vacío
    when(ciudadProvinciaRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.listarComunasPorIdCiudadProvin(99);
    });

    assertEquals("Ciudad/Provincia con id: 99 no encontrada", exception.getMessage());
    
    verify(ciudadProvinciaRepository, times(1)).findById(99);
    }


    @Test
    void listarComunasPorNombreCiudadProvincia_encontrado_retornaLista() {
    // ARRANGE: 
    //  Creación de la lista de ciudades que simula estar dentro de esa región
    List<Comuna> comunasMock = new ArrayList<>();
    Comuna quilicura = new Comuna();
    quilicura.setIdComuna(1);
    quilicura.setNombreComuna("Quilicura");
    quilicura.setCiudadProvincia(ciudadProvinciaEjemplo);
    comunasMock.add(quilicura);

    // Adjuntamos la lista a la ciudad o provincia mockeada
    ciudadProvinciaEjemplo.setComunas(comunasMock);

    // Programamos el repositorio de comunas para que devuelva la comuna armada
    when(ciudadProvinciaRepository.findByNombreCiudadProvincia("Santiago")).thenReturn(Optional.of(ciudadProvinciaEjemplo));

    // ACT: Ejecutamos el método del servicio
    List<Comuna> resultado = regionService.listarComunasPorNombreCiudadProvin("Santiago");

    // ASSERT: Validamos el resultado de la lista
    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals("Quilicura", resultado.get(0).getNombreComuna());
    assertEquals(1, resultado.get(0).getCiudadProvincia().getIdCiudadProvincia());

    verify(ciudadProvinciaRepository, times(1)).findByNombreCiudadProvincia("Santiago");
    }

    @Test
    void listarComunasPorNombreCiudadProvincia_NoEncontrado_LanzaRuntimeException() {
    // ARRANGE
    //String nombreInexistente = "Nunca Jamas";
    
    // Configuramos el repositorio correcto para retornar un Optional vacío
    when(ciudadProvinciaRepository.findByNombreCiudadProvincia("Nunca Jamas")).thenReturn(Optional.empty());

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.listarComunasPorNombreCiudadProvin("Nunca Jamas");
    });

    assertEquals("Ciudad/Provincia con nombre: Nunca Jamas no encontrada", exception.getMessage());
    
    verify(ciudadProvinciaRepository, times(1)).findByNombreCiudadProvincia("Nunca Jamas");
    }



    @Test
    void buscarComunaPorId_Encontrada_DevuelveComuna() {
    // ARRANGE: 
    // Envolvemos en el Optional requerido por el repositorio
    when(comunaRepository.findById(1)).thenReturn(Optional.of(comunaEjemplo1));

    // ACT: Ejecutamos el método real del servicio
    Comuna resultado = regionService.buscarComunaPorId(1);

    // ASSERT: Validamos que la data coincida con lo mockeado
    assertNotNull(resultado);
    assertEquals(1, resultado.getIdComuna());
    assertEquals("Quilicura", resultado.getNombreComuna());
    
    // Verificación de comportamiento
    verify(comunaRepository, times(1)).findById(1);
    }

    @Test
    void buscarComunaPorId_NoEncontrada_LanzaRuntimeException() {
    // ARRANGE
    when(comunaRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.buscarComunaPorId(99);
    });

    // Validamos el mensaje exacto de tu negocio
    assertEquals("Comuna con id: 99 no encontrada", exception.getMessage());
    
    verify(comunaRepository, times(1)).findById(99);
    }



    @Test
    void buscarComunaPorNombre_Encontrada_RetornaComuna() {
    
    // Envolvemos en el Optional requerido por el repositorio
    when(comunaRepository.findByNombreComuna("Quilicura")).thenReturn(Optional.of(comunaEjemplo1));

    // ACT: Ejecutamos el método real del servicio
    Comuna resultado = regionService.buscarComunaPorNombre("Quilicura");

    // ASSERT: Validamos que la data coincida con lo mockeado
    assertNotNull(resultado);
    assertEquals(1, resultado.getIdComuna());
    assertEquals("Quilicura", resultado.getNombreComuna());
    
    verify(comunaRepository, times(1)).findByNombreComuna("Quilicura");
    }

    @Test
    void buscarComunaPorNombre_noEncontrada(){
        //ARRANGE: preparamos la prueba, para qe retorne una region vacia
        Optional<Comuna> comunaVacia = Optional.empty();
        when(comunaRepository.findByNombreComuna("Nunca Jamas")).thenReturn(comunaVacia);

        //ACT: llamamos el estado real
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            regionService.buscarComunaPorNombre("Nunca Jamas");
        });

        assertEquals("Comuna: Nunca Jamas no encontrada", exception.getMessage());
        
    }




    @Test
    void guardarComuna_Exitoso() {
    // ARRANGE: 
    comunaEjemplo1.setRegion(regionEjemplo);
    comunaEjemplo1.setCiudadProvincia(ciudadProvinciaEjemplo);

    // Entidad simulada creada (con ID asignado)
    Comuna comunaGuardada = new Comuna();
    comunaGuardada.setIdComuna(1);
    comunaGuardada.setNombreComuna("Quilicura");
    comunaGuardada.setRegion(regionEjemplo);
    comunaGuardada.setCiudadProvincia(ciudadProvinciaEjemplo);

    // Mocks en orden secuencial de ejecución
    when(comunaRepository.findByNombreComuna("Quilicura")).thenReturn(Optional.empty());
    when(regionRepository.findById(1)).thenReturn(Optional.of(regionEjemplo));
    when(ciudadProvinciaRepository.findById(1)).thenReturn(Optional.of(ciudadProvinciaEjemplo));
    when(comunaRepository.save(comunaEjemplo1)).thenReturn(comunaGuardada);

    // ACT
    Comuna resultado = regionService.guardarComuna(comunaEjemplo1);

    // ASSERT
    assertNotNull(resultado);
    assertEquals(1, resultado.getIdComuna());
    assertEquals("Quilicura", resultado.getNombreComuna());
    assertEquals(1, resultado.getRegion().getIdRegion());
    assertEquals(1, resultado.getCiudadProvincia().getIdCiudadProvincia());

    verify(comunaRepository, times(1)).findByNombreComuna("Quilicura");
    verify(regionRepository, times(1)).findById(1);
    verify(ciudadProvinciaRepository, times(1)).findById(1);
    verify(comunaRepository, times(1)).save(comunaEjemplo1);
    }

    @Test
    void guardarComuna_NombreDuplicado_LanzaRuntimeException() {
    // ARRANGE
    when(comunaRepository.findByNombreComuna("Quilicura")).thenReturn(Optional.of(new Comuna()));

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.guardarComuna(comunaEjemplo1);
    });

    assertEquals("Ya existe la comuna: Quilicura", exception.getMessage());
    verify(comunaRepository, times(1)).findByNombreComuna("Quilicura");
    verify(regionRepository, times(0)).findById(anyInt());
    verify(ciudadProvinciaRepository, times(0)).findById(anyInt());
    verify(comunaRepository, times(0)).save(comunaEjemplo1);
    }

    @Test
    void guardarComuna_RegionNoExiste_LanzaRuntimeException() {
    // ARRANGE
    Region regionInexistente = new Region();
    regionInexistente.setIdRegion(99);

    Comuna comunaNueva = new Comuna();
    comunaNueva.setNombreComuna("Quilicura");
    comunaNueva.setRegion(regionInexistente);

    when(comunaRepository.findByNombreComuna("Quilicura")).thenReturn(Optional.empty());
    when(regionRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.guardarComuna(comunaNueva);
    });

    assertEquals("No se puede guardar la Comuna: Quilicura porque la región con id: 99 no existe", exception.getMessage());
    verify(comunaRepository, times(1)).findByNombreComuna("Quilicura");
    verify(regionRepository, times(1)).findById(99);
    verify(ciudadProvinciaRepository, times(0)).findById(anyInt());
    verify(comunaRepository, times(0)).save(comunaNueva);
    }

    @Test
    void guardarComuna_CiudadProvinciaNoExiste_LanzaRuntimeException() {
    // ARRANGE
    CiudadProvincia ciudadInexistente = new CiudadProvincia();
    ciudadInexistente.setIdCiudadProvincia(99);

    Comuna comunaNueva = new Comuna();
    comunaNueva.setNombreComuna("Quilicura");
    comunaNueva.setRegion(regionEjemplo);
    comunaNueva.setCiudadProvincia(ciudadInexistente);

    when(comunaRepository.findByNombreComuna("Quilicura")).thenReturn(Optional.empty());
    when(regionRepository.findById(1)).thenReturn(Optional.of(regionEjemplo));
    when(ciudadProvinciaRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.guardarComuna(comunaNueva);
    });

    assertEquals("No se puede guardar la Comuna: Quilicura porque la Ciudad/Provincia con id: 99 no existe", exception.getMessage());
    verify(comunaRepository, times(1)).findByNombreComuna("Quilicura");
    verify(regionRepository, times(1)).findById(1);
    verify(ciudadProvinciaRepository, times(1)).findById(99);
    verify(comunaRepository, times(0)).save(comunaNueva);
}



    @Test
    void actualizarComunaPorId_Exitoso() {
    // ARRANGE: Instanciamos el DTO de forma local con el nuevo nombre
    ComunaUpdateDTO updateDTO = new ComunaUpdateDTO();
    updateDTO.setNombreComuna("Quilicura Actualizado");

    // Preparamos la entidad que esperamos recibir del repositorio tras el .save()
    Comuna comunaActualizada = new Comuna();
    comunaActualizada.setIdComuna(1); // Mismo ID del BeforeEach
    comunaActualizada.setNombreComuna("Quilicura Actualizado");
    comunaActualizada.setRegion(regionEjemplo);
    comunaActualizada.setCiudadProvincia(ciudadProvinciaEjemplo);

    // Mocks utilizando el ID de 'comunaEjemplo' (ID: 1)
    when(comunaRepository.findById(1)).thenReturn(Optional.of(comunaEjemplo1));
    when(comunaRepository.save(comunaEjemplo1)).thenReturn(comunaActualizada);

    // ACT: Ejecutamos el método del servicio
    Comuna resultado = regionService.actualizarComunaPorId(1, updateDTO);

    // ASSERT: Validamos que se haya aplicado el cambio correctamente
    assertNotNull(resultado);
    assertEquals(1, resultado.getIdComuna());
    assertEquals("Quilicura Actualizado", resultado.getNombreComuna());

    // Verificaciones de comportamiento
    verify(comunaRepository, times(1)).findById(1);
    verify(comunaRepository, times(1)).save(comunaEjemplo1);
    }

    @Test
    void actualizarComunaPorId_NoEncontrado_LanzaRuntimeException() {
    // ARRANGE
    ComunaUpdateDTO updateDTO = new ComunaUpdateDTO();
    updateDTO.setNombreComuna("Lampa");

    // Configuramos el repositorio para retornar un Optional vacío
    when(comunaRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.actualizarComunaPorId(99, updateDTO);
    });

    // Validamos el mismo msj del Regionservice
    assertEquals("Comuna con id: 99 no encontrada", exception.getMessage());

    verify(comunaRepository, times(1)).findById(99);
    verify(comunaRepository, times(0)).save(any(Comuna.class));
    }


//****************************************************************************************************** */
//TEST CALLES

    @Test
    void listarCalles_RetornaListaDeEntidades() {
    // ARRANGE: 
    List<Calle> listaMock = new ArrayList<>();
    listaMock.add(calleEjemplo1);
    listaMock.add(calleEjemplo2);

    when(calleRepository.findAll()).thenReturn(listaMock);

    // ACT: Invocamos al método del servicio centralizado
    List<Calle> resultado = regionService.listarCalles();

    // ASSERT: 
    assertNotNull(resultado);
    assertEquals(2, resultado.size());

    assertEquals(1, resultado.get(0).getIdCalle());
    assertEquals("Av. Manuel Antonio Matta", resultado.get(0).getNombreCalle());
    assertEquals("1230", resultado.get(0).getNumeroCalle());
    assertNull(resultado.get(0).getNumeroDepto());
    
    // Verificamos que la relación con la comuna del BeforeEach esté intacta
    assertNotNull(resultado.get(0).getComuna());
    assertEquals(1, resultado.get(0).getComuna().getIdComuna());
    assertEquals("Quilicura", resultado.get(0).getComuna().getNombreComuna());

    assertEquals(2, resultado.get(1).getIdCalle());
    assertEquals("Américo Vespucio", resultado.get(1).getNombreCalle());
    assertEquals("1500", resultado.get(1).getNumeroCalle());
    assertEquals("Depto 402", resultado.get(1).getNumeroDepto());

    assertNotNull(resultado.get(1).getComuna());
    assertEquals(2, resultado.get(1).getComuna().getIdComuna());
    assertEquals("Huechuraba", resultado.get(1).getComuna().getNombreComuna());

    verify(calleRepository, times(1)).findAll();
    }


    @Test
    void listarCalles_SinCallesEnBD_RetornaListaVacia() {
    // ARRANGE: Simulamos que el repositorio no tiene registros (retorna lista vacía)
    List<Calle> listaVaciaMock = new ArrayList<>();
    when(calleRepository.findAll()).thenReturn(listaVaciaMock);

    // ACT
    List<Calle> resultado = regionService.listarCalles();

    // ASSERT: Validamos que la lista no sea null pero sí esté vacía
    assertNotNull(resultado);
    assertTrue(resultado.isEmpty());
    assertEquals(0, resultado.size());

    verify(calleRepository, times(1)).findAll();
    }



    @Test
    void buscarCallePorId_Exitoso_DevuelveCalle() {
    // ARRANGE: 
    when(calleRepository.findById(1)).thenReturn(Optional.of(calleEjemplo1));

    // ACT
    Calle resultado = regionService.buscarCallePorId(1);

    // ASSERT
    assertNotNull(resultado);
    assertEquals(1, resultado.getIdCalle());
    assertEquals("Av. Manuel Antonio Matta", resultado.getNombreCalle());
    verify(calleRepository, times(1)).findById(1);
    }

    @Test
    void buscarCallePorId_NoEncontrada_LanzaRuntimeException() {
    // ARRANGE
    when(calleRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.buscarCallePorId(99);
    });

    assertEquals("Calle con id: 99 no encontrada.", exception.getMessage());
    verify(calleRepository, times(1)).findById(99);
    }



    @Test
    void listarCallesPorComuna_Exitoso_DevuelveLista() {
    // ARRANGE: Usamos comunaEjemplo1 (ID: 1) y metemos calleEjemplo1 en la lista
    calleEjemplo2.setComuna(comunaEjemplo1);
    List<Calle> callesDeComuna = new ArrayList<>();
    callesDeComuna.add(calleEjemplo1);
    callesDeComuna.add(calleEjemplo2);

    when(comunaRepository.findById(1)).thenReturn(Optional.of(comunaEjemplo1));
    when(calleRepository.findByComuna_IdComuna(1)).thenReturn(callesDeComuna);

    // ACT
    List<Calle> resultado = regionService.listarCallesPorComuna(1);

    // ASSERT
    assertNotNull(resultado);
    assertEquals(2, resultado.size()); //aquí decimos que esperamos 2 elementos

    assertEquals(1, resultado.get(0).getIdCalle());
    assertEquals("Av. Manuel Antonio Matta", resultado.get(0).getNombreCalle());
    assertEquals(1, resultado.get(0).getComuna().getIdComuna());

    assertEquals(2, resultado.get(1).getIdCalle());
    assertEquals("Américo Vespucio", resultado.get(1).getNombreCalle());
    assertEquals(1, resultado.get(1).getComuna().getIdComuna());

    verify(comunaRepository, times(1)).findById(1);
    verify(calleRepository, times(1)).findByComuna_IdComuna(1);
    }

    @Test
    void listarCallesPorComuna_ComunaNoExiste_LanzaRuntimeException() {
    // ARRANGE
    when(comunaRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.listarCallesPorComuna(99);
    });

    assertEquals("Comuna con id: 99 no encontrada.", exception.getMessage());
    
    verify(comunaRepository, times(1)).findById(99);
    // Aseguramos el cortocircuito: si la comuna no existe, jamás consulta las calles
    verify(calleRepository, times(0)).findByComuna_IdComuna(anyInt());
    }



    @Test
    void guardarCalle_Exitoso_DevuelveCalleGuardada() {
    // ARRANGE: 
    when(comunaRepository.findById(1)).thenReturn(Optional.of(comunaEjemplo1));
    when(calleRepository.save(calleEjemplo1)).thenReturn(calleEjemplo1);

    // ACT
    Calle resultado = regionService.guardarCalle(calleEjemplo1);

    // ASSERT
    assertNotNull(resultado);
    assertEquals("Av. Manuel Antonio Matta", resultado.getNombreCalle());
    assertEquals(1, resultado.getComuna().getIdComuna());

    verify(comunaRepository, times(1)).findById(1);
    verify(calleRepository, times(1)).save(calleEjemplo1);
    }

    @Test
    void guardarCalle_ComunaNoExiste_LanzaRuntimeException() {
    // ARRANGE:
    Comuna comunaInexistente = new Comuna();
    comunaInexistente.setIdComuna(99);

    Calle calleNueva = new Calle();
    calleNueva.setNombreCalle("Calle Muy Lejana");
    calleNueva.setNumeroCalle("123");
    calleNueva.setComuna(comunaInexistente);

    when(comunaRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.guardarCalle(calleNueva);
    });

    assertEquals("No se puede guardar la calle porque la comuna con id: 99 no existe.", exception.getMessage());
    
    verify(comunaRepository, times(1)).findById(99);
    // Verificamos protección de BD: Jamás se llamó al save
    verify(calleRepository, times(0)).save(calleNueva);
    }

    

    @Test
    void actualizarNombreCalle_Exitoso_DevuelveCalleModificada() {
    // ARRANGE
    CalleUpdateDTO updateDTO = new CalleUpdateDTO();
    updateDTO.setNombreCalle("Nuevo Nombre Matta");

    // Copia modificada esperada del repositorio 
    Calle calleActualizada = new Calle();
    calleActualizada.setIdCalle(1);
    calleActualizada.setNombreCalle("Nuevo Nombre Matta");
    calleActualizada.setNumeroCalle("1230");
    calleActualizada.setComuna(comunaEjemplo1);

    when(calleRepository.findById(1)).thenReturn(Optional.of(calleEjemplo1));
    when(calleRepository.save(calleEjemplo1)).thenReturn(calleActualizada);

    // ACT
    Calle resultado = regionService.actualizarNombreCalle(1, updateDTO);

    // ASSERT
    assertNotNull(resultado);
    assertEquals(1, resultado.getIdCalle());
    assertEquals("Nuevo Nombre Matta", resultado.getNombreCalle());

    verify(calleRepository, times(1)).findById(1);
    verify(calleRepository, times(1)).save(calleEjemplo1);
    }

    @Test
    void actualizarNombreCalle_NoEncontrada_LanzaRuntimeException() {
    // ARRANGE
    CalleUpdateDTO updateDTO = new CalleUpdateDTO();
    updateDTO.setNombreCalle("Cualquier Nombre");

    when(calleRepository.findById(99)).thenReturn(Optional.empty());

    // ACT & ASSERT
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        regionService.actualizarNombreCalle(99, updateDTO);
    });

    assertEquals("Calle con id: 99 no encontrada.", exception.getMessage());
    
    verify(calleRepository, times(1)).findById(99);
    verify(calleRepository, times(0)).save(any(Calle.class));
    }


}


