package cl.duoc.MSFacturacion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "banco")
public class banco {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String nombre;

    

}
