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
@Table(name = "forma_pago")
@Schema(description = "Entidad que representa la forma de pago en el sistema de control de eventos. Contiene información como su id y medio de pago. Además, cada forma de pago puede tener asociado un comprobante en el sistema.")
public class FormaPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la forma de pago, generado automáticamente por la base de datos.", example = "1")
    private Integer idFormaPago;

    @Column(nullable = false, unique = true)
    @Schema(description = "Medio de pago.", example = "Efectivo")
    private String medioDePago;
    
    @OneToMany(mappedBy = "formaPago", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Lista de comprobantes asociados a esta forma de pago. Excluido de la respuesta JSON por rendimiento y evitar recursividad.")
    private List<Comprobante> comprobante;
 
}
