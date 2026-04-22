package cl.duoc.pacienteMS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paciente")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String rut;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private Integer edad;

    // Relación con Dirección
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
    private Direccion direccion;

    // Relación con Contacto
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
    private Contacto contacto;

    

}
