package cl.duoc.msVentaTicket.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "boleta")

public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBoleta;

    @Column(nullable = true, unique = true)
    private Integer numeroBoleta;

    @Column(nullable = false)
    private LocalDate fechaEmision;

    @Column(nullable = false)
    private LocalTime horaEmision;
    
    @Column(nullable = true)
    private Integer totalBoleta;  //SE CALCULA AUTOMATICAMENTE

    //REFENCIA CON msComprador
    @Column(nullable = false)
    private Integer idComprador;

    //REFERENCIA CON msFacturacion
    @Column(nullable = true)
    private Integer idComprobante;

    @OneToMany
    @JoinColumn(name = "id_boleta")
    private List<Detalle> detalles;


    @PrePersist
    public void prePersist(){
        this.fechaEmision = LocalDate.now();
        this.horaEmision = LocalTime.now();

        this.totalBoleta = detalles.stream().mapToInt(Detalle :: getSubTotal).sum();
    }


}
