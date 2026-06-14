package cl.duoc.msAdministrador.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidad que representa una auditoría, que registra las acciones realizadas por los administradores en el sistema de control de eventos. Contiene información sobre el nombre del responsable de la acción, la fecha en que se realizó, el tipo de acción y una descripción detallada de la misma. Además, cada auditoría está asociada a un administrador específico, lo que permite rastrear quién realizó cada acción.")

public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la auditoría", example = "1")
    private Integer IdAuditoria;

    @Column(nullable = false)
    @Schema(description = "Nombre del responsable de la acción", example = "Matias Gomez")
    private String nombreResponsable;

    @Column(nullable = false)
    @Schema(description = "Fecha en que se realizó la acción", example = ("2026-03-28"))
    private Date fecha;

    @Column(nullable = false)
    @Schema(description = "Tipo de acción realizada", example = "CREAR")
    private String tipoAccion;

    @Column(nullable = false)
    @Schema(description = "Descripción detallada de la acción", example = "Se crea evento '50 años de Trayectoria' Los Bunkers")
    private String descripcion; 

    @ManyToOne
    @JoinColumn(name = "id_administrador")
    @JsonBackReference
    @Schema(description = "Administrador asociado a la auditoría, que realizó la acción registrada en esta auditoría.")
    private Administrador administrador; // Relación con Administrador (opcional)

}
