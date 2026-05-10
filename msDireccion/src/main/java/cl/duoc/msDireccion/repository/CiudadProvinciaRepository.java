package cl.duoc.msDireccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msDireccion.model.CiudadProvincia;
import java.util.Optional;


@Repository
public interface CiudadProvinciaRepository extends JpaRepository <CiudadProvincia, Integer>{

    Optional <CiudadProvincia> findByNombreCiudadProvincia(String nombreCiudadProvincia);


}
