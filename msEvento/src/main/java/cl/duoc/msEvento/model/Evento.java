package cl.duoc.msEvento.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "evento")

    public class Evento {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idEvento;

        @Column(nullable = false, unique = true)
        private String codigoEvento;

        @Column(nullable = false)
        private String nombreEvento;

        @Column(nullable = false)
        private LocalDateTime fechaEvento;

        @Column(nullable = false)
        private LocalDateTime horaEvento;


        

        private Recinto recinto;

        private TipoEvento tipoEvento;



        //necesita estos dos:
        //private Productora


        //private Administrador

}
