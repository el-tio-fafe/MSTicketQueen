package cl.duoc.msVentaTicket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.msVentaTicket.model.Boleta;
import cl.duoc.msVentaTicket.repository.BoletaRepository;

@Service

public class BoletaService {

    @Autowired
    private BoletaRepository boletaRepository;

    public List<Boleta> listarBoletas(){
        return boletaRepository.findAll();
    }

    public Boleta buscarBoletaPorId(Integer idBoleta) {
        return boletaRepository.findById(idBoleta)
            .orElseThrow(() -> new RuntimeException("Boleta con id: " + idBoleta + " no encontrado."));
    }

    public Boleta buscarBoletaPorNumero(Integer numeroBoleta) {
        return boletaRepository.findByNumeroBoleta(numeroBoleta)
            .orElseThrow(() -> new RuntimeException("Número boleta: " + numeroBoleta + " no encontrado."));
    }

    public List<Boleta> listarBoletasPorComprador(Integer idComprador){
        List<Boleta> lista = boletaRepository.findByIdComprador(idComprador);
        if(lista.isEmpty()){
            throw new RuntimeException("El comprador id: " + idComprador + " no tiene boletas asociadas");
        }
        return lista;
    }

    public Boleta crearBoleta(Boleta boleta){
        if(boleta.getDetalles() == null || boleta.getDetalles().isEmpty()){
            throw new RuntimeException("La boleta debe tener al menos una compra");
        }
        Boleta boletaGuardada = boletaRepository.save(boleta);
        boletaGuardada.setNumeroBoleta(boletaGuardada.getIdBoleta());
        return boletaRepository.save(boletaGuardada);
    }

    public Boleta asociarComprobante(Integer idBoleta, Integer idComprobante){
        Boleta boleta = buscarBoletaPorId(idBoleta);
        if(boleta.getIdComprobante() != null){
            throw new RuntimeException("La boleta ya tiene un comprobante asociado");
        }
        boleta.setIdComprobante(idComprobante);
        return boletaRepository.save(boleta);
    }



}
