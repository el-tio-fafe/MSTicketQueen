package cl.duoc.MSFacturacion.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.MSFacturacion.model.Banco;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Integer> {
    
    Optional<Banco> findByNombreBanco(String nombreBanco);

}
