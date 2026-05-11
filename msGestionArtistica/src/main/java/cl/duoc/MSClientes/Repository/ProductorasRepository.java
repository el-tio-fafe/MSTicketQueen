package cl.duoc.msGestionArtistica.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.msGestionArtistica.Model.Productoras;

public interface ProductorasRepository extends JpaRepository<Productoras, Integer> {

    Optional <Productoras> findByNombreProd(String nombreProd);

    Optional <Productoras> findByRutProd(String rutProd); 

}
