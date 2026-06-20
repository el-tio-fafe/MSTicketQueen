package cl.duoc.msComprador.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clienteComprador")
@Schema(description = "Entidad que representa un cliente en el sistema")
public class Comprador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID (Identificador único) del cliente", example = "1")
    private Integer idCliente;

    @Column(nullable = false, unique = true)
    @Schema(description = "RUT del cliente", example = "26586516-8")
    private String rutCliente;

    @Column(nullable = false)
    @Schema(description = "Nombre del cliente", example = "Matias")
    private String nombreCliente;

    @Column(nullable = false)
    @Schema(description = "Apellido Paterno del cliente", example = "Gutierrez")
    private String apPaternoCliente;

    @Column(nullable = false)
    @Schema(description = "Apellido Materno del cliente", example = "Cortes")
    private String apMaternoCliente;

    @Column(nullable = false)
    @Schema(description = "Correo del cliente", example = "matias@correo.com")
    private String correoCliente;

    @Column(nullable = false)
    @Schema(description = "Teléfono del cliente", example = "+569 85896877")
    private String telefonoCliente;

    @Column(nullable = false)
    @Schema(description = "Password del cliente", example = "casaleta")
    private String passCliente;


}
