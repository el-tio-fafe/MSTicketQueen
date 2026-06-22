package cl.duoc.msFacturacion.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "banco")
@Schema(description = "Entidad que representa un Banco en el sistema de control de eventos. Contiene información personal del banco, como su ID y nombre. Además, cada banco puede tener asociado múltiples comprobantes.")
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del Banco, generado automáticamente por la base de datos.", example = "1")
    private Integer idBanco;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre del banco.", example = "Estado")
    private String nombreBanco;

    @OneToMany(mappedBy = "banco", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Lista de comprobantes asociados al banco.")
    private List<Comprobante> comprobantes;

}
