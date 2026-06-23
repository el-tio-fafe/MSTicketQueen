package cl.duoc.msVentaTicket.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")
@Schema(description = "Entidad que representa un ticket en el sistema de venta de tickets. Contiene información sobre el código QR, el evento asociado, el asiento y la ubicación del ticket.")

public class Ticket {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del ticket, generado automáticamente por la base de datos.", example = "1")
    private Integer idTicket;

    @Column(nullable = false, unique = true)
    @Schema(description = "Código QR único del ticket, generado automáticamente.", example = "550e8400-e29b-41d4-a716-446655440000")
    private String codigoQR;

    @Column(nullable = false)
    @Schema(description = "Identificador del evento asociado al ticket.", example = "1")
    private Integer idEvento;


    @Column(nullable = true)
    @Schema(description = "Número de asiento asociado al ticket.", example = "A1")
    private String numeroAsiento;

    @Column(nullable = true)
    @Schema(description = "Nombre de la zona o ubicación del ticket.", example = "Cancha VIP")
    private String nombreUbicacion; //ESTE EL NOMBRE DE LA ZONA POR EJ: CANCHA VIP, CANCHA GENERAL, PLATEA, PALCO, TRIBUNA ETC

    @PrePersist
    public void prePersist(){
        this.codigoQR = java.util.UUID.randomUUID().toString();
    }

    

}