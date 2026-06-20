package cl.duoc.msAdministrador.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "administrador")
@Schema(description = "Entidad que representa a un administrador en el sistema de control de evntos. Contiene información personal del administrador, como su nombre, apellido, RUT, correo electrónico y número de teléfono. Además, cada administrador puede tener asociadas múltiples auditorías, que registran las acciones realizadas por el administrador en el sistema.")

public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del administrador, generado automáticamente por la base de datos.", example = "1")
    private Integer idAdm;

    @Column(nullable = false, unique = true)
    @Schema(description = "RUT del administrador.", example = "16517585-2")
    private String rutAdm;

    @Column(nullable = false)
    @Schema(description = "Nombre del administrador.", example = "Maria")
    private String nombreAdm;

    @Column(nullable = false)
    @Schema(description = "Apellido paterno del administrador.", example = "Cruz")
    private String apPatAdm;

    @Column(nullable = false)
    @Schema(description = "Apellido materno del administrador.", example = "Recabarren")
    private String apMatAdm;

    @Column(nullable = false, unique = true)
    @Schema(description = "Correo electrónico del administrador.", example = "maria.r@gmail.com")
    private String correoAdm;

    @Column(nullable = false)
    @Schema(description = "Número de teléfono del administrador.", example = "+56949854785")
    private String telefonoAdm;

    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Schema(description = "Lista de auditorías asociadas al administrador.")
    private List<Auditoria> auditoria;
    
}
