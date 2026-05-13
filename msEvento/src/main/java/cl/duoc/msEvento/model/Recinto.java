package cl.duoc.msEvento.model;

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
@Table(name = "recinto")

public class Recinto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRecinto;


    @Column(nullable = false, unique = true)
    private String rutRecinto;
    
    @Column(nullable = false)
    private String nombreRecinto;
    
    @Column(nullable = false)
    private String capacidadRecinto;
    
    @Column(nullable = false, unique = true)
    private String telefonoRecinto;
    
    @Column(nullable = false, unique = true)
    private String correoRecinto;

    //el recinto necesita una calle direcion
    //private Calle calle;





}
