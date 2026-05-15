package cl.duoc.msLogin.model;


import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(unique = true , nullable = false)
    private String nombreUsuario;


    @Column(unique = true , nullable = false)
    private String correo;


    @Column(nullable = false)
    private String password;


    @ManyToOne
    @JoinColumn(name = "tipo_usuario_id")
    private TipoUsuario tipoUsuario;



    

}
