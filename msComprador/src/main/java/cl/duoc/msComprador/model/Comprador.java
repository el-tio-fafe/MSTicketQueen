package cl.duoc.msComprador.model;

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
public class Comprador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(nullable = false, unique = true)
    private String rutCliente;

    @Column(nullable = false)
    private String nombreCliente;

    @Column(nullable = false)
    private String apPaternoCliente;

    @Column(nullable = false)
    private String apMaternoCliente;

    @Column(nullable = false)
    private String correoCliente;

    @Column(nullable = false)
    private String telefonoCliente;

    @Column(nullable = false)
    private String passCliente;


}
