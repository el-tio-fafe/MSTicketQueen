package cl.duoc.msEvento.model;

import java.time.LocalDate;
import java.time.LocalTime;

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
        private LocalDate fechaEvento;

        @Column(nullable = false)
        private LocalTime horaEvento;

        @Column(nullable = false)
        private String estadoEvento;  //QUE PUEDEN SER PENDIENTE, APROBADO O RECHAZADO



        //CONEXION CON LOS OTROS MS:
        @Column(nullable = false)
        private Integer idProd;

        @Column(nullable = true)           //ESTE VALOR PUEDE SER NULL PORQUE EL ADM SE 
        private Integer idAdministrador;   //ASIGNA SOLO CUANDO APRUEBA O RECHAZA UN EVENTO


        @ManyToOne
        @JoinColumn(name = "idRecinto", nullable = false)
        private Recinto recinto;


        @ManyToOne
        @JoinColumn(name = "idTipoEvento", nullable = false)
        private TipoEvento tipoEvento;



        //necesita estos dos:
        //private Productora


        //private Administrador

}
