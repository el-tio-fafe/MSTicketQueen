package com.TicketQueen.mslogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TicketQueen.mslogin.model.TipoUsuario;
@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {
    Optional<TipoUsuario> findByNombre(Integer id);

}
