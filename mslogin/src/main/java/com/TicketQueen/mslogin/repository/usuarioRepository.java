package com.TicketQueen.mslogin.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TicketQueen.mslogin.model.usuario;

@Repository
public interface usuarioRepository extends JpaRepository<usuario, Integer> {
Optional<usuario> findByCorreo(String correo);
}
