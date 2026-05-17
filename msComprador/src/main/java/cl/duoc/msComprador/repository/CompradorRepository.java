package cl.duoc.msComprador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msComprador.model.Comprador;

@Repository
public interface CompradorRepository extends JpaRepository<Comprador, Integer>{

    Optional <Comprador> findByRutCliente(String rutCliente);
    
    Optional <Comprador> findByCorreoCliente(String correoCliente);

    boolean existsByRutCliente(String rutCliente);



}
