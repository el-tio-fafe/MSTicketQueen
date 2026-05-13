package cl.duoc.msEvento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipoEvento")
public class TipoEvento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoEvento;

    @Column(nullable = false)
    private String descripcion;

    @OneToOne
    private Evento evento;





    

}
