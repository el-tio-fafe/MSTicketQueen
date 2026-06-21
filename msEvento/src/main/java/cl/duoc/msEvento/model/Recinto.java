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
@Table(name = "recinto")
@Schema(description = "Entidad que representa un recinto en el sistema. Contiene información general como su ID, rut, nombre, capacidad, teléfono y correo del recinto en el sistema.")
public class Recinto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del recinto, generado automáticamente por la base de datos.", 
                example = "1")
    private Integer idRecinto;


    @Column(nullable = false, unique = true)
    @Schema(description = "Rut del recinto.", 
                example = "76123456-7")
    private String rutRecinto;
    
    @Column(nullable = false)
    @Schema(description = "Nombre del recinto.", example = "Estadio Nacional")
    private String nombreRecinto;
    
    @Column(nullable = false)
    @Schema(description = "Capacidad del recinto, que corresponde a la cantidad de personas que pueden estar dentro del recinto.", example = "60000")
    private Integer capacidadRecinto;
    
    @Column(nullable = false, unique = true)
    @Schema(description = "Teléfono del recinto.", example = "+56223456789")
    private String telefonoRecinto;
    
    @Column(nullable = false, unique = true)
    @Schema(description = "Correo del recinto.", example = "estadionacional@gmail.com")
    private String correoRecinto;

    //el recinto necesita una calle direcion
    //private Calle calle;





}
