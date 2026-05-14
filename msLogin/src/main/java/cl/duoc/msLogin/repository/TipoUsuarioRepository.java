package cl.duoc.msLogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msLogin.model.TipoUsuario;


@Repository

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer>{

    Optional<TipoUsuario> findByNombreTipoUsuario(String nombreTipoUsuario);


}
