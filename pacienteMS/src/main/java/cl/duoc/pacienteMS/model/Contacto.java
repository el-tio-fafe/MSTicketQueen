package cl.duoc.pacienteMS.model;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacto")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String email;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
}