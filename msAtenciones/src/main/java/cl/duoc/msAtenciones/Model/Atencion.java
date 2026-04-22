package cl.duoc.msAtenciones.Model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "atencion")
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date fechaAtencion;


    @Column(nullable = false)
    private String diagnostico;


    // Datos que se obtienen de otros microservicios
    @Column(name = "doctor_id", nullable = false)
    private Integer doctorID;


    @Column(name = "paciente_id", nullable = false)
    private Integer pacienteID;
 
    // Relación con tipo atencion
    @ManyToOne
    @JoinColumn(name = "tipo_atencion_id", nullable = false)
    private TipoAtencion tipoAtencion;

}
