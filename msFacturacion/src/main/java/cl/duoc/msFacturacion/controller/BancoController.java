package cl.duoc.msFacturacion.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.msFacturacion.dto.ComprobanteDTO;
import cl.duoc.msFacturacion.model.Banco;
import cl.duoc.msFacturacion.model.Comprobante;
import cl.duoc.msFacturacion.model.FormaPago;
import cl.duoc.msFacturacion.service.BancoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/facturacion")
@Tag(name = "Facturación", description = "Endpoints relacionados con la facturación, que permiten realizar operaciones CRUD sobre bancos, comprobantes y formas de pago.")
public class BancoController {

    @Autowired
    private BancoService bancoService;

//BANCOS

    @GetMapping("/listarBancos")
    @Operation(
        summary = "Obtener todos los bancos", 
        description = "Endpoint para obtener una lista de todos los bancos registrados en el sistema de facturación.")
    public ResponseEntity<?> listarBancos() {
        try {
            List<Banco> lista = bancoService.listarBancos();
            if(lista.isEmpty()){
                return ResponseEntity.badRequest().body("No hay bancos registrados");
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/bancos/id/{idBanco}")
    @Operation(
        summary = "Obtener un banco por ID", 
        description = "Endpoint para obtener un banco específico por su ID.")
    public ResponseEntity<?> obtenerBancoPorId(@PathVariable Integer idBanco) {
        try {
            return ResponseEntity.ok(bancoService.buscarBancoPorId(idBanco));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/bancos/nombre/{nombreBanco}")
    @Operation(
        summary = "Obtener un banco por nombre", 
        description = "Endpoint para obtener un banco específico por su nombre.")
    public ResponseEntity<?> obtenerBancoPorNombre(@PathVariable String nombreBanco) {
        try {
            return ResponseEntity.ok(bancoService.buscarBancoPorNombre(nombreBanco));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/crearBanco")
    @Operation(
        summary = "Crear un nuevo banco", 
        description = "Endpoint para crear un nuevo banco en el sistema de facturación.")
    public ResponseEntity<?> crearBanco(@RequestBody Banco banco) {
        try {
            return ResponseEntity.ok(bancoService.guardarBanco(banco));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/bancos/id/{idBanco}")
    @Operation(
        summary = "Actualizar un banco existente", 
        description = "Endpoint para actualizar la información de un banco existente por su ID.")
    public ResponseEntity<?> actualizarBanco(@PathVariable Integer idBanco, @RequestBody Banco banco) {
        try {
            return ResponseEntity.ok(bancoService.actualizarBanco(idBanco, banco));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/bancos/eliminar/id/{idBanco}")
    @Operation(
        summary = "Eliminar un banco por ID", 
        description = "Endpoint para eliminar un banco específico por su ID.")
    public ResponseEntity<?> eliminarBancoPorId(@PathVariable Integer idBanco) {
        try {
            bancoService.eliminarBancoPorId(idBanco);
            return ResponseEntity.ok("Banco con id: " + idBanco + " eliminado con éxito!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @DeleteMapping("/bancos/eliminar/nombre/{nombreBanco}")
    @Operation(
        summary = "Eliminar un banco por nombre", 
        description = "Endpoint para eliminar un banco específico por su nombre.")
    public ResponseEntity<?> eliminarBancoPorNombre(@PathVariable String nombreBanco) {
        try {
            bancoService.eliminarBancoPorNombre(nombreBanco);
            return ResponseEntity.ok("Banco con nombre: " + nombreBanco + " eliminado con éxito!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



//COMPROBANTE

    @GetMapping("/comprobantes")
    @Operation(
        summary = "Obtener todos los comprobantes", 
        description = "Endpoint para obtener una lista de todos los comprobantes registrados en el sistema de facturación.")
    public ResponseEntity<?> listarComprobantes() {
        try {
            List<Comprobante> lista = bancoService.listarTodosComprobantes();
            if (lista.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/comprobantes/banco/id/{idBanco}")
    @Operation(
        summary = "Obtener comprobantes por banco", 
        description = "Endpoint para obtener una lista de comprobantes asociados a un banco específico por su ID.")
    public ResponseEntity<?> listarComprobantesPorBanco(@PathVariable Integer idBanco) {
        try {
            List<Comprobante> lista = bancoService.listarComprobantesPorBanco(idBanco);
            if (lista.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/comprobantes/numero/{numeroComprobante}")
    @Operation(
        summary = "Obtener un comprobante por número", 
        description = "Endpoint para obtener un comprobante específico por su número.")
    public ResponseEntity<?> buscarComprobantePorNumero(@PathVariable String numeroComprobante) {
        try {
            return ResponseEntity.ok(bancoService.buscarComprobantePorNumero(numeroComprobante));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/comprobantes/banco/id/{idBanco}/fecha")
    @Operation(
        summary = "Filtrar comprobantes por fecha de emisión", 
        description = "Endpoint para obtener una lista de comprobantes de un banco específico filtrados por su fecha de emisión.")
    public ResponseEntity<?> filtrarComprobantesPorFecha(@PathVariable Integer idBanco, @RequestParam LocalDateTime fechaEmision) {
        try {
            List<Comprobante> lista = bancoService.filtrarComprobantesPorFechaEmision(idBanco, fechaEmision);
            if (lista.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/comprobantes")
    @Operation(
        summary = "Generar un nuevo comprobante", 
        description = "Endpoint para generar un nuevo comprobante en el sistema de facturación.")
    public ResponseEntity<?> generarComprobante(@RequestBody Comprobante comprobante) {
        try {
            return ResponseEntity.ok(bancoService.generarComprobante(comprobante));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/comprobantes/anular/{numeroComprobante}") //ESTE METODO CAMBIA EL ESTADO DE UN COMPROBANTE
    @Operation(
        summary = "Anular un comprobante", 
        description = "Endpoint para anular un comprobante específico por su número, cambiando su estado de pago.")
    public ResponseEntity<?> anularComprobante(@PathVariable String numeroComprobante) {
        try {
            return ResponseEntity.ok(bancoService.anularComprobantePorNumero(numeroComprobante));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




//FORMA DE PAGO

    @GetMapping("/formas-pago")
    @Operation(
        summary = "Obtener todas las formas de pago", 
        description = "Endpoint para obtener una lista de todas las formas de pago registradas en el sistema de facturación.")
    public ResponseEntity<?> listarTodasFormasDePago() {
        try {
            List<FormaPago> lista = bancoService.listarTodasFormasPago();
            if (lista.isEmpty()) {
                return ResponseEntity.badRequest().body("No hay formas de pago registradas");
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/formas-pago/banco/id/{idBanco}")
    @Operation(
        summary = "Obtener formas de pago por ID de banco", 
        description = "Endpoint para obtener una lista de formas de pago utilizadas en un banco específico por su ID.")
    public ResponseEntity<?> listarFormasDePagoPorIdBanco(@PathVariable Integer idBanco) {
        try {
            List<FormaPago> lista = bancoService.listarFormasDePagoPorIdBanco(idBanco);
            if (lista.isEmpty()){
                return ResponseEntity.badRequest().body("No hay formas de pago para el banco id: " + idBanco);
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/formas-pago/banco/nombre/{nombreBanco}")
    @Operation(
        summary = "Obtener formas de pago por nombre de banco", 
        description = "Endpoint para obtener una lista de formas de pago utilizadas en un banco específico por su nombre.")
    public ResponseEntity<?> listarFormasDePagoPorNombreBanco(@PathVariable String nombreBanco) {
        try {
            List<FormaPago> lista = bancoService.listarFormasDePagoPorNombreBanco(nombreBanco);
            if (lista.isEmpty()){
                return ResponseEntity.badRequest().body("No hay formas de pago para el banco: " + nombreBanco);
            }
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @GetMapping("/formas-pago/buscar/id/{idFormaPago}")
    @Operation(
        summary = "Obtener una forma de pago por ID", 
        description = "Endpoint para obtener una forma de pago específica por su ID.")
    public ResponseEntity<?> buscarFormaPagoPorSuId(@PathVariable Integer idFormaPago) {
        try {
            return ResponseEntity.ok(bancoService.buscarFormaPagoPorSuId(idFormaPago));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/formas-pago/medio/{medioDePago}")
    @Operation(
        summary = "Obtener una forma de pago por medio", 
        description = "Endpoint para obtener una forma de pago específica por su medio de pago.")
    public ResponseEntity<?> buscarFormaPagoPorMedio(@PathVariable String medioDePago) {
        try {
            return ResponseEntity.ok(bancoService.buscarFormaPagoPorMedio(medioDePago));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/formas-pago/guardar")
    @Operation(
        summary = "Crear una nueva forma de pago", 
        description = "Endpoint para crear una nueva forma de pago en el sistema de facturación.")
    public ResponseEntity<?> guardarFormaPago(@RequestBody FormaPago formaPago) {
        try {
            return ResponseEntity.ok(bancoService.guardarFormaPago(formaPago));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/formas-pago/actualizar/id/{idFormaPago}")
    @Operation(
        summary = "Actualizar una forma de pago existente", 
        description = "Endpoint para actualizar la información de una forma de pago existente por su ID.")
    public ResponseEntity<?> actualizarFormaPago(@PathVariable Integer idFormaPago, @RequestBody FormaPago formaPago) {
        try {
            return ResponseEntity.ok(bancoService.actualizarFormaPago(idFormaPago, formaPago));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/formas-pago/eliminar/id/{idFormaPago}")
    @Operation(
        summary = "Eliminar una forma de pago", 
        description = "Endpoint para eliminar una forma de pago específica por su ID.")
    public ResponseEntity<?> eliminarFormaPago(@PathVariable Integer idFormaPago) {
        try {
            bancoService.eliminarFormaPago(idFormaPago);
            return ResponseEntity.ok("Forma de pago con id: " + idFormaPago + " eliminada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //CONECCION CON OTRO MS
    @GetMapping("/dto/{idComprobante}")
    @Operation(
        summary = "Obtener DTO de un comprobante por ID", 
        description = "Endpoint para obtener un DTO de un comprobante específico por su ID, que contiene información relevante para otros microservicios.")
    public ResponseEntity<ComprobanteDTO> buscarDTO(@PathVariable Integer idComprobante) {
        try {
            return ResponseEntity.ok(bancoService.buscarComprobanteDTOPorId(idComprobante));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}