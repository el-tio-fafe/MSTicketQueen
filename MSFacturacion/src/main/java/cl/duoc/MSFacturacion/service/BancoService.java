package cl.duoc.MSFacturacion.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.MSFacturacion.model.Banco;
import cl.duoc.MSFacturacion.repository.BancoRepository;
import cl.duoc.MSFacturacion.repository.ComprobanteRepository;
import cl.duoc.MSFacturacion.repository.FormaPagoRepository;
import cl.duoc.MSFacturacion.model.Comprobante;
import cl.duoc.MSFacturacion.model.FormaPago;

@Service
public class BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private FormaPagoRepository formaPagoRepository;

//BANCO
    public List<Banco> listarBancos() {
        return bancoRepository.findAll();
    }

    public Banco buscarBancoPorId(Integer id) {
        return bancoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Banco con id: " + id + " no encontrado."));
    }

    public Banco buscarBancoPorNombre(String nombre) {
        return bancoRepository.findByNombreBanco(nombre)
            .orElseThrow(() -> new RuntimeException("Banco con nombre: " + nombre + " no encontrado."));
    }

    public Banco guardarBanco(Banco nuevoBanco) {
        if (bancoRepository.findByNombreBanco(nuevoBanco.getNombreBanco()).isPresent()) {
            throw new RuntimeException("Ya existe un banco con el nombre: " + nuevoBanco.getNombreBanco() + ".");
        }
        return bancoRepository.save(nuevoBanco);
    }


    public Banco actualizarBanco(Integer id, Banco bancoActualizado) {
        Banco bancoExistente = buscarBancoPorId(id);
        bancoExistente.setNombreBanco(bancoActualizado.getNombreBanco());
        return bancoRepository.save(bancoExistente);
    }


    public void eliminarBancoPorId(Integer id) {
        buscarBancoPorId(id);
        bancoRepository.deleteById(id);
    }


    public void eliminarBancoPorNombre(String nombreBanco) {
        Banco bancoExistente = buscarBancoPorNombre(nombreBanco);
        bancoRepository.deleteById(bancoExistente.getIdBanco());

    }



//COMPROBANTE

    public List<Comprobante> listarTodosComprobantes(){
        return comprobanteRepository.findAll();
    }
   
    public List<Comprobante> listarComprobantesPorBanco(Integer idBanco) {
        Banco bancoExistente = buscarBancoPorId(idBanco);
        return comprobanteRepository.findByBanco(bancoExistente);
    }


    public Comprobante generarComprobante(Comprobante comprobante) {
        if(comprobanteRepository.findByNumeroComprobante(comprobante.getNumeroComprobante()).isPresent()){
            throw new RuntimeException("Ya existe un comprobante con número: " + comprobante.getNumeroComprobante());
        }
        buscarBancoPorId(comprobante.getBanco().getIdBanco());

        return comprobanteRepository.save(comprobante);
    }
    

    public Comprobante buscarComprobantePorNumero(String numeroComprobante) {
        return comprobanteRepository.findByNumeroComprobante(numeroComprobante)
                .orElseThrow(() -> new RuntimeException("Comprobante con número: " + numeroComprobante + " no encontrado."));
    }

    // anular comprobante por numero de comprobante dejando el numero de comprobante con el estado anulado
    public Comprobante anularComprobantePorNumero(String numeroComprobante) {
        Comprobante comprobante = buscarComprobantePorNumero(numeroComprobante);
        comprobante.setEstadoPago(false);
        return comprobanteRepository.save(comprobante);
    }

    // filtrar comprobantes por fecha de emision
    public List<Comprobante> filtrarComprobantesPorFechaEmision(Integer idBanco, Date fechaEmision) {
        Banco banco = buscarBancoPorId(idBanco);
        return comprobanteRepository.findByBancoAndFechaEmision(banco, fechaEmision);
                
    }


//FORMA DE PAGO

    public List<FormaPago> listarTodasFormasPago(){
        return formaPagoRepository.findAll();
    }

    public List<FormaPago> listarFormasDePagoPorIdBanco(Integer idBanco) {
        Banco banco = buscarBancoPorId(idBanco);
        List<Comprobante> comprobantes = comprobanteRepository.findByBanco(banco);
        return comprobantes.stream()
                .map(Comprobante::getFormaPago)
                .distinct()
                .toList();
    }


    public List<FormaPago> listarFormasDePagoPorNombreBanco(String nombreBanco) {
        Banco banco = buscarBancoPorNombre(nombreBanco);
        List<Comprobante> comprobantes = comprobanteRepository.findByBanco(banco);
        return comprobantes.stream()
                .map(Comprobante::getFormaPago)
                .distinct()
                .toList();
    }



    public FormaPago buscarFormaPagoPorId(Integer idFormaPago){
        return formaPagoRepository.findById(idFormaPago)
            .orElseThrow(() -> new RuntimeException("Forma de pago con id: " + idFormaPago + " no encontrada"));
    }

    public FormaPago buscarFormaPagoPorMedio(String medioDePago) {
        return formaPagoRepository.findByMedioDePago(medioDePago)
            .orElseThrow(() -> new RuntimeException("Forma de pago: " + medioDePago + " no encontrada."));
    }

    public List<FormaPago> buscarFormaPagoPorNombreBanco(String nombreBanco) {
        Banco banco = buscarBancoPorNombre(nombreBanco);
        List<Comprobante> comprobantes = comprobanteRepository.findByBanco(banco);
        if(comprobantes.isEmpty()){
            throw new RuntimeException("No hay comprobantes para el banco: " + nombreBanco);
        }
        return comprobantes.stream()
            .map(Comprobante::getFormaPago)
            .distinct().toList();
    }

    public FormaPago guardarFormaPago(FormaPago formaPago) {
        if (formaPagoRepository.findByMedioDePago(formaPago.getMedioDePago()).isPresent()) {
            throw new RuntimeException("Ya existe la forma de pago: " + formaPago.getMedioDePago());
        }
        return formaPagoRepository.save(formaPago);
    }

    public FormaPago actualizarFormaPago(Integer id, FormaPago formaPagoActualizado) {
        FormaPago formaPago = buscarFormaPagoPorId(id);
        formaPago.setMedioDePago(formaPagoActualizado.getMedioDePago());
        return formaPagoRepository.save(formaPago);
    }

    public void eliminarFormaPago(Integer id) {
        buscarFormaPagoPorId(id);
        formaPagoRepository.deleteById(id);
    }


}

        
    
    
    
    
    
   
