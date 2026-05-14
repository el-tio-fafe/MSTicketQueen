package cl.duoc.MSFacturacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.MSFacturacion.model.banco;
import cl.duoc.MSFacturacion.repository.bancoRepository;
import cl.duoc.MSFacturacion.repository.ComprobanteRepository;
import cl.duoc.MSFacturacion.repository.FormaDePagoRepository;
import cl.duoc.MSFacturacion.model.Comprobante;
import cl.duoc.MSFacturacion.model.formaPago;

@Service
public class BancoService {

    @Autowired
    private bancoRepository bancoRepository;

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private FormaDePagoRepository formaDePagoRepository;

// listar bancos 
    public List<banco> listarBancos() {
        return bancoRepository.findAll();
    }
// guardar bancos 
    public banco guardarBanco(banco nuevoBanco) {
        if (bancoRepository.findByNombre(nuevoBanco.getNombreBanco()).isPresent()) {
            throw new RuntimeException("Ya existe un banco con el nombre \"" + nuevoBanco.getNombreBanco() + "\".");
        }
        return bancoRepository.save(nuevoBanco);
    }

// Buscar banco por id
    public banco buscarBancoPorId(Integer id) {
        return bancoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Banco con id " + id + " no encontrado."));
    }
// Buscar Banco por nombre 
    public banco buscarBancoPorNombre(String nombre) {
        return bancoRepository.findByNombre(nombre)
            .orElseThrow(() -> new RuntimeException("Banco con nombre \"" + nombre + "\" no encontrado."));
    }
// Actualizar bancos
    public banco actualizarBanco(Integer id, banco bancoActualizado) {
        banco bancoExistente = buscarBancoPorId(id);
        bancoExistente.setNombreBanco(bancoActualizado.getNombreBanco());
        return bancoRepository.save(bancoExistente);
    }
// eliminar banco por id 
    public void eliminarBancoPorId(Integer id) {
        buscarBancoPorId(id);
        bancoRepository.deleteById(id);
    }
// Eliminar banco por nombre 
    public void eliminarBancoPorNombre(String nombre) {
        banco bancoExistente = buscarBancoPorNombre(nombre);
        bancoRepository.deleteById(bancoExistente.getIdBanco());

    }


// listar comprobantes por banco    
    public List<Comprobante> listarComprobantesPorBanco(Integer bancoId) {
        banco bancoExistente = buscarBancoPorId(bancoId);
        return comprobanteRepository.findByBanco(bancoExistente);
    }

// listar formas de pago por banco
    public List<formaPago> listarFormasDePagoPorBanco(Integer bancoId) {
        banco bancoExistente = buscarBancoPorId(bancoId);
        List<Comprobante> comprobantes = comprobanteRepository.findByBanco(bancoExistente);
        return comprobantes.stream()
                .map(Comprobante::getFormaPago)
                .distinct()
                .toList();
    }
// generar comprobantes
    public Comprobante generarComprobante(Integer bancoId, String numeroComprobante, String medioDePago) {
        banco bancoExistente = buscarBancoPorId(bancoId);
        formaPago formaPagoExistente = formaDePagoRepository.findByNombre(medioDePago)
                .orElseThrow(() -> new RuntimeException("Forma de pago \"" + medioDePago + "\" no encontrada."));

        Comprobante nuevoComprobante = new Comprobante();
        nuevoComprobante.setNumeroComprobante(numeroComprobante);
        nuevoComprobante.setBanco(bancoExistente);
        nuevoComprobante.setFormaPago(formaPagoExistente);

        return comprobanteRepository.save(nuevoComprobante);
    }
    //buscar comprobante por numero de comprobante
    public Comprobante buscarComprobantePorNumero(String numeroComprobante) {
        return comprobanteRepository.findByNumeroComprobante(numeroComprobante)
                .orElseThrow(() -> new RuntimeException("Comprobante con número \"" + numeroComprobante + "\" no encontrado."));
    }
    // anular comprobante por numero de comprobante dejando el numero de comprobante con el estado anulado
    public Comprobante anularComprobantePorNumero(String numeroComprobante) {
        Comprobante comprobanteExistente = buscarComprobantePorNumero(numeroComprobante);
        comprobanteExistente.setEstadopago(false);
        return comprobanteRepository.save(comprobanteExistente);
    }
// filtrar comprobantes por fecha de emision
    public List<Comprobante> filtrarComprobantesPorFechaEmision(Integer bancoId, String fechaEmision) {
        banco bancoExistente = buscarBancoPorId(bancoId);
        List<Comprobante> comprobantes = comprobanteRepository.findByBanco(bancoExistente);
        return comprobantes.stream()
                .filter(c -> c.getFechaEmision().toString().equals(fechaEmision))
                .toList();
    }
}

        
    
    
    
    
    
   
