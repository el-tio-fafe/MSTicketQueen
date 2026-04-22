package cl.duoc.msAdministrador.model;

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

    @Column(nullable = false)
    private String rutAdm;

    @Column(nullable = false)
    private String nombreAdm;

    @Column(nullable = false)
    private String apPatAdm;

    @Column(nullable = false)
    private String apMatAdm;

    @Column(nullable = false)
    private String correoAdm;

    @Column(nullable = false)
    private String telefonoAdm;

    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    private Auditoria auditoria;
    




}
