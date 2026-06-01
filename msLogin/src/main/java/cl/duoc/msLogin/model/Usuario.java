package cl.duoc.msLogin.model;


import io.swagger.v3.oas.annotations.media.Schema;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
@Schema(description = "Representa un Usuario en el Sistema")

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID unico del usuario auto incrementable", example = "1")
    private Integer id;


    @Column(unique = true , nullable = false)
    @Schema(description = "Representa el nombre del usuario", example = "Juan Silva")
    private String nombreUsuario;


    @Column(unique = true , nullable = false)
    @Schema(description = "Representa el correo del Usuario", example = "juan@gmail.com")
    private String correo;


    @Column(nullable = false)
    @Schema(description = "Representa la contraseña del usuarion ", example = "123456")
    private String password;


    @OneToOne
    @JoinColumn(name = "tipo_usuario_id")
    @Schema(description = "Representa el tipo de Usuario")
    private TipoUsuario tipoUsuario;



    

}
