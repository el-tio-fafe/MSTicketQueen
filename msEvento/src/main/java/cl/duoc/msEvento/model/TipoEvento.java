package cl.duoc.msEvento.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "tipoEvento")
@Schema(description = "Entidad que representa un Tipo de Evento en el sistema. Contiene información del tipo de evento, como el ID y su descripción.")
public class TipoEvento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del tipo de evento, generado automáticamente por la base de datos.", 
                example = "1")
    private Integer idTipoEvento;

    @Column(nullable = false)
    @Schema(description = "Corresponde a la categoría a la que corresponde el evento.", 
                example = "Concierto")
    private String descripcion;




}
