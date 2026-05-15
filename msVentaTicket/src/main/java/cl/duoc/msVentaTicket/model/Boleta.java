package cl.duoc.msVentaTicket.model;

import java.time.LocalDate;

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
@Table(name = "boleta")
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBoleta;
    @Column(nullable = false)
    private String numeroBoleta;
    @Column(nullable = false)
    private LocalDate fechaEmision;
    @Column(nullable = false)
    private int totalBoleta;


}
