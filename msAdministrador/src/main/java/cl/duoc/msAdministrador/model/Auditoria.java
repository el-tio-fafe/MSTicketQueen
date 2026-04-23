package cl.duoc.msAdministrador.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auditoria")

public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IdAuditoria;

    @Column(nullable = false)
    private String nombreResponsable;

    @Column(nullable = false)
    private Date fecha;

    @Column(nullable = false)
    private String tipoAccion;

    @Column(nullable = false)
    private String descripcion; 

    @ManyToOne
    @JoinColumn(name = "id_administrador")
    private Administrador administrador; // Relación con Administrador (opcional)

}
