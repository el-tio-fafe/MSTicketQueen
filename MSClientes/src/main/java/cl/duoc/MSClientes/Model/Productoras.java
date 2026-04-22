package cl.duoc.MSClientes.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productoras")
public class Productoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProd;

    @Column(nullable = false)
    private String rutProd;

    @Column(nullable = false)
    private String nombreProd;

    @Column(nullable = false)
    private String CorreoProd;

    @Column(nullable = false)
    private String telefonoProd;

    @ManyToOne
    @JoinColumn(name = "idMngr", nullable = false)
    private Manager manager;    
}
