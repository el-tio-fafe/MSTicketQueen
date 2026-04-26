package cl.duoc.msAsiento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asientos")   

public class Asiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAsiento;

    @Column(nullable = false)
    private String numeroAsiento;

    @Column(nullable = false)
    private String estadoAsiento;  //DISPONIBLE, RESERVADO O VENDIDO


}
