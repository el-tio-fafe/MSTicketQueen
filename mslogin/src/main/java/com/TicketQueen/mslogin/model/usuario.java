package com.TicketQueen.mslogin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(unique = true , nullable = false)
    private String nombreUsuario;


    @Column(unique = true , nullable = false)
    private String email;


    @Column(nullable = false)
    private String password;


    @OneToOne
    @JoinColumn(name = "tipo_usuario_id")
    private TipoUsuario tipoUsuario;
    
}
