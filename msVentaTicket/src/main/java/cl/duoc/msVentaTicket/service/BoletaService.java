package cl.duoc.msVentaTicket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.duoc.msVentaTicket.client.AsientoClient;
import cl.duoc.msVentaTicket.client.CompradorClient;
import cl.duoc.msVentaTicket.client.EventoClient;
import cl.duoc.msVentaTicket.client.FacturacionClient;
import cl.duoc.msVentaTicket.client.UbicacionClient;
import cl.duoc.msVentaTicket.dto.AsientoDTO;
import cl.duoc.msVentaTicket.dto.BoletaDTO;
import cl.duoc.msVentaTicket.dto.CompradorDTO;
import cl.duoc.msVentaTicket.dto.EventoDTO;
import cl.duoc.msVentaTicket.dto.UbicacionDTO;
import cl.duoc.msVentaTicket.model.Boleta;
import cl.duoc.msVentaTicket.model.Detalle;
import cl.duoc.msVentaTicket.repository.BoletaRepository;
import cl.duoc.msVentaTicket.repository.DetalleRepository;

@Service

public class BoletaService {

    @Autowired
    private BoletaRepository boletaRepository;

    @Autowired
    private DetalleRepository detalleRepository;

    @Autowired
    private EventoClient eventoClient;

    @Autowired
    private CompradorClient compradorClient;

    @Autowired
    private AsientoClient asientoClient;

    @Autowired
    private FacturacionClient facturacionClient;

    @Autowired
    private UbicacionClient ubicacionClient;

    public List<Boleta> listarBoletas(){
        return boletaRepository.findAll();
    }

    //CONECCION CON OTRO MS POR EL DTO
    public BoletaDTO mostrarDetalleBoleta(Integer idBoleta){
        Boleta boleta = boletaRepository.findById(idBoleta).orElseThrow(() -> new RuntimeException("Boleta id: " + idBoleta + "no encontrada"));
        
        CompradorDTO comprador = compradorClient.buscarCompradorPorId(boleta.getIdComprador());
        List<EventoDTO> eventos = boleta.getDetalles().stream().map(d -> eventoClient.buscarEventoPorId(d.getTicket().getIdEvento()))
            .distinct().toList();

        BoletaDTO dto = new BoletaDTO();
        dto.setNumeroBoleta(boleta.getNumeroBoleta());
        dto.setFecha(boleta.getFechaEmision());
        dto.setTotalBoleta(boleta.getTotalBoleta());
        dto.setComprador(comprador);
        dto.setEventos(eventos);

        return dto;

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



    @Transactional
    public Boleta crearBoleta(Boleta boleta) {

        // VALIDAR QUE EL COMPRADOR EXISTE
        try {
            compradorClient.buscarCompradorPorId(boleta.getIdComprador());
        } catch (Exception e) {
            throw new RuntimeException("No se puede crear la boleta porque el comprador id: " + boleta.getIdComprador() + " no existe");
        }

        // VALIDAR DETALLE VACIO
        if (boleta.getDetalles() == null || boleta.getDetalles().isEmpty()) {
            throw new RuntimeException("La boleta debe tener al menos una compra");
        }

        // BUSCA CADA DETALLE EN LA BD
        List<Detalle> detalleCompleto = boleta.getDetalles().stream()
            .map(d -> detalleRepository.findById(d.getIdDetalle())
                .orElseThrow(() -> new RuntimeException("Detalle con id: " + d.getIdDetalle() + " no encontrado")))
            .toList();

        // VALIDAR CADA DETALLE
        detalleCompleto.forEach(d -> {

            // 1. VALIDAR EL EVENTO PRIMERO
            try {
                EventoDTO evento = eventoClient.buscarEventoPorId(d.getTicket().getIdEvento());

                if (!evento.getEstadoEvento().equals("APROBADO")) {
                    throw new RuntimeException("No se pueden vender tickets para el evento id: "
                        + d.getTicket().getIdEvento() + " porque no está APROBADO.");
                }

            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("No se puede crear la boleta porque el evento con id: "
                    + d.getTicket().getIdEvento() + " no existe");
            }

            // 2. SI TIENE ASIENTO NUMERADO, VALIDAR Y RESERVAR
            if (d.getTicket().getNumeroAsiento() != null) {
                try {
                    AsientoDTO asiento = asientoClient.buscarAsientoPorNum(d.getTicket().getNumeroAsiento());

                    if (!asiento.getEstadoAsiento().equals("DISPONIBLE")) {
                        throw new RuntimeException("El asiento: " + d.getTicket().getNumeroAsiento() + " no está disponible.");
                    }

                    asientoClient.crearReservaTemporal(asiento.getIdAsiento());

                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    throw new RuntimeException("No se puede crear la boleta porque el asiento: "
                        + d.getTicket().getNumeroAsiento() + " no existe.");
                }

            // 3. SI NO TIENE ASIENTO NUMERADO, VALIDAR UBICACION Y STOCK
            } else {
                String nombreUbicacion = d.getTicket().getNombreUbicacion();
                if (nombreUbicacion != null && !nombreUbicacion.isBlank()) {
                    try {
                        UbicacionDTO ubicacion = ubicacionClient.buscarUbicacionDTOPorNombre(nombreUbicacion);

                        if (ubicacion.getStockDisponibleUbi() <= 0) {
                            throw new RuntimeException("No hay stock disponible para la ubicación: " + nombreUbicacion);
                        }

                        ubicacionClient.reducirStock(ubicacion.getIdUbi());

                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new RuntimeException("No se puede crear la boleta porque la ubicación: "
                            + nombreUbicacion + " no existe.");
                    }
                }
            }
        });

        // CALCULAMOS EL TOTAL DE LA BOLETA CON LOS DATOS COMPLETOS
        Integer total = detalleCompleto.stream()
            .mapToInt(d -> (d.getPrecioUnitario() - d.getDescuento()) * d.getCantidad()).sum();

        boleta.setDetalles(detalleCompleto);
        boleta.setTotalBoleta(total);

        Boleta boletaGuardada = boletaRepository.save(boleta);
        boletaGuardada.setNumeroBoleta(boletaGuardada.getIdBoleta());
        return boletaRepository.save(boletaGuardada);
    }



    public Boleta asociarComprobante(Integer idBoleta, Integer idComprobante) {
        Boleta boleta = buscarBoletaPorId(idBoleta);

        if (boleta.getIdComprobante() != null) {
            throw new RuntimeException("La boleta ya tiene un comprobante asociado");
        }

        // VALIDAMOS QUE EL COMPROBANTE EXISTA EN EL MSFACTURACION
        try {
            facturacionClient.buscarComprobanteDTOPorId(idComprobante);
        } catch (Exception e) {
            throw new RuntimeException("No se puede asociar porque el comprobante con id: "
                + idComprobante + " no existe.");
        }

        // CONFIRMAR COMPRA DE CADA ASIENTO
        boleta.getDetalles().forEach(d -> {
            if (d.getTicket().getNumeroAsiento() != null) {
                try {
                    asientoClient.confirmarCompra(d.getTicket().getNumeroAsiento());
                } catch (Exception e) {
                    throw new RuntimeException("No se pudo confirmar la compra del asiento: "
                        + d.getTicket().getNumeroAsiento());
                }
            }
        });

        boleta.setIdComprobante(idComprobante);
        return boletaRepository.save(boleta);
    }
}


