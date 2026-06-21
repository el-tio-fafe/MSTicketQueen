package cl.duoc.msEvento.model;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "evento")
@Schema(description = "Entidad que representa un evento en el sistema. Contiene información general del evento, como el ID, cógido, nombre, fecha, hora y estado del evento. Además, cada evento puede tener asociado un productor, un administrador, un recinto y un tipo de evento en el sistema.")

    public class Evento {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(description = "ID del evento, generado automáticamente por la base de datos.", 
                example = "1")
        private Integer idEvento;


        @Column(nullable = false, unique = true)
        @Schema(description = "Código del evento.", example = "EVT-001")
        private String codigoEvento;

        @Column(nullable = false)
        @Schema(description = "Nombre del evento.", example = "50 años de Trayectoria Los Bunkers")
        private String nombreEvento;

        @Column(nullable = false)
        @Schema(description = "Fecha del evento.", example = "2026, 8, 15")
        private LocalDate fechaEvento;

        @Column(nullable = false)
        @Schema(description = "Hora del evento.", example = "20, 0")
        private LocalTime horaEvento;

        @Column(nullable = false)
        @Schema(description = "Estado del evento.", example = "APROBADO")
        private String estadoEvento;  //QUE PUEDEN SER PENDIENTE, APROBADO O RECHAZADO



        //CONEXION CON LOS OTROS MS:
        @Column(nullable = false)
        @Schema(description = "ID del productor del evento.", example = "1")
        private Integer idProd;

        @Column(nullable = true)           //ESTE VALOR PUEDE SER NULL PORQUE EL ADM SE
        @Schema(description = "ID del administrador del evento.", example = "1") //ASIGNA SOLO CUANDO APRUEBA O RECHAZA UN EVENTO
        private Integer idAdministrador;   


        @ManyToOne
        @JoinColumn(name = "idRecinto", nullable = false)
        @Schema(description = "Recinto en donde se realizará el evento.", example = "Recinto1")
        private Recinto recinto;


        @ManyToOne
        @JoinColumn(name = "idTipoEvento", nullable = false)
        @Schema(description = "Tipo de Evento.", example = "Recital")
        private TipoEvento tipoEvento;



        //necesita estos dos:
        //private Productora


        //private Administrador

}
