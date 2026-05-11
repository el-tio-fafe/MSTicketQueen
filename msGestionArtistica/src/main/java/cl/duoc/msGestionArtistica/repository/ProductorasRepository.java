package cl.duoc.msGestionArtistica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.msGestionArtistica.model.Productoras;

public interface ProductorasRepository extends JpaRepository<Productoras, Integer> {

    Optional <Productoras> findByNombreProd(String nombreProd);

    Optional <Productoras> findByRutProd(String rutProd); 

}
