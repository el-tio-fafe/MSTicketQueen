package cl.duoc.msDireccion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msDireccion.model.Region;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer>{

    Optional <Region> findByNombreRegion(String nombreRegion);

}
