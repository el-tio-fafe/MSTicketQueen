package com.TicketQueen.msLogin.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TicketQueen.mslogin.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
Optional<Usuario> findByCorreo(String correo);
}
