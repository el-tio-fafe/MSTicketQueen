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

        private Integer idEvento;

        private String codigoEvento;

        private String nombreEvento;

        private LocalDateTime fechaEvento;

        private LocalDateTime horaEvento;



        private Calle calle;

}
