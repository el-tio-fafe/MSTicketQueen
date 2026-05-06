package cl.duoc.msAdministrador.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "administrador")

public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAdm;

    @Column(nullable = false, unique = true)
    private String rutAdm;

    @Column(nullable = false)
    private String nombreAdm;

    @Column(nullable = false)
    private String apPatAdm;

    @Column(nullable = false)
    private String apMatAdm;

    @Column(nullable = false, unique = true)
    private String correoAdm;

    @Column(nullable = false)
    private String telefonoAdm;

    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Auditoria> auditoria;
    
}
