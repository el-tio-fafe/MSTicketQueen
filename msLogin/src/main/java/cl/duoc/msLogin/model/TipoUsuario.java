package cl.duoc.msLogin.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipo_usuario")
@Schema(description = "Representa el Tipo de usuario (rol del usuario en el sistema) ")
public class TipoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Representa el id de tipo Usuario",example = "3")
    private Integer id;


    @Column(nullable = false)
    @Schema(description = "Representa el rol de tipo usuario",example = "Productora")
    private String nombreTipoUsuario;


    @OneToOne
    private Usuario usuario;




}
