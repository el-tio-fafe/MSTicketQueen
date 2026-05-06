package cl.duoc.MSClientes.Model;

import java.util.List;

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
    private String correoProd; 

    @Column(nullable = false)
    private String telefonoProd;

    @ManyToMany// anteriormente era @OneToMany, pero se cambió a @ManyToMany para reflejar la relación real entre Productoras y Manager 
               // (ya que una Productora puede tener varios Managers)
    @JoinTable(name = "productoras_manager",// nombre de la tabla intermedia
    joinColumns = @JoinColumn(name = "idProd"),// columna que referencia a Productoras
    inverseJoinColumns = @JoinColumn(name = "idMngr"))// columna que referencia a Manager
    private List<Manager> managers;
    

}
    