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

@RestController
@RequestMapping("/api/v1/facturacion")
public class BancoController {

    @Autowired
    private BancoService bancoService;

//BANCOS

    @GetMapping("/listarBancos")
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
    public ResponseEntity<?> obtenerBancoPorId(@PathVariable Integer idBanco) {
        try {
            return ResponseEntity.ok(bancoService.buscarBancoPorId(idBanco));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/bancos/nombre/{nombreBanco}")
    public ResponseEntity<?> obtenerBancoPorNombre(@PathVariable String nombreBanco) {
        try {
            return ResponseEntity.ok(bancoService.buscarBancoPorNombre(nombreBanco));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/crearBanco")
    public ResponseEntity<?> crearBanco(@RequestBody Banco banco) {
        try {
            return ResponseEntity.ok(bancoService.guardarBanco(banco));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/bancos/id/{idBanco}")
    public ResponseEntity<?> actualizarBanco(@PathVariable Integer idBanco, @RequestBody Banco banco) {
        try {
            return ResponseEntity.ok(bancoService.actualizarBanco(idBanco, banco));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/bancos/eliminar/id/{idBanco}")
    public ResponseEntity<?> eliminarBancoPorId(@PathVariable Integer idBanco) {
        try {
            bancoService.eliminarBancoPorId(idBanco);
            return ResponseEntity.ok("Banco con id: " + idBanco + " eliminado con éxito!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @DeleteMapping("/bancos/eliminar/nombre/{nombreBanco}")
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
    public ResponseEntity<?> buscarComprobantePorNumero(@PathVariable String numeroComprobante) {
        try {
            return ResponseEntity.ok(bancoService.buscarComprobantePorNumero(numeroComprobante));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/comprobantes/banco/id/{idBanco}/fecha")
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
    public ResponseEntity<?> generarComprobante(@RequestBody Comprobante comprobante) {
        try {
            return ResponseEntity.ok(bancoService.generarComprobante(comprobante));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/comprobantes/anular/{numeroComprobante}") //ESTE METODO CAMBIA EL ESTADO DE UN COMPROBANTE
    public ResponseEntity<?> anularComprobante(@PathVariable String numeroComprobante) {
        try {
            return ResponseEntity.ok(bancoService.anularComprobantePorNumero(numeroComprobante));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




//FORMA DE PAGO

    @GetMapping("/formas-pago")
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
    public ResponseEntity<?> buscarFormaPagoPorSuId(@PathVariable Integer idFormaPago) {
        try {
            return ResponseEntity.ok(bancoService.buscarFormaPagoPorSuId(idFormaPago));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/formas-pago/medio/{medioDePago}")
    public ResponseEntity<?> buscarFormaPagoPorMedio(@PathVariable String medioDePago) {
        try {
            return ResponseEntity.ok(bancoService.buscarFormaPagoPorMedio(medioDePago));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/formas-pago/guardar")
    public ResponseEntity<?> guardarFormaPago(@RequestBody FormaPago formaPago) {
        try {
            return ResponseEntity.ok(bancoService.guardarFormaPago(formaPago));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/formas-pago/actualizar/id/{idFormaPago}")
    public ResponseEntity<?> actualizarFormaPago(@PathVariable Integer idFormaPago, @RequestBody FormaPago formaPago) {
        try {
            return ResponseEntity.ok(bancoService.actualizarFormaPago(idFormaPago, formaPago));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/formas-pago/eliminar/id/{idFormaPago}")
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
    public ResponseEntity<ComprobanteDTO> buscarDTO(@PathVariable Integer idComprobante) {
        try {
            return ResponseEntity.ok(bancoService.buscarComprobanteDTOPorId(idComprobante));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
