package cl.duoc.MSFacturacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.MSFacturacion.model.banco;
import cl.duoc.MSFacturacion.repository.bancoRepository;

@Service
public class BancoService {

    @Autowired
    private bancoRepository bancoRepository;
// listar bancos 
    public List<banco> listarBancos() {
        return bancoRepository.findAll();
    }
// guardar bancos
    public banco guardarBanco(banco banco) {
        if (banco.getNombre() == null || banco.getNombre().isBlank()) {
            throw new RuntimeException("El nombre del banco no puede estar vacío.");
        }
        return bancoRepository.save(banco);
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
        bancoExistente.setNombre(bancoActualizado.getNombre());
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
        bancoRepository.deleteById(bancoExistente.getId());
    }

// emitir comprobantes 
public void emitirComprobante() {
    
    
   
}
// anular comprobantes 
public void anularComprobante (){

}
// obtener historial por cliente
public void obtenerHistorialPorCliente() {

} 
// genera resumen diario
public void generarResumenDiario() {

}
//validar disponibilidad de pago
public void validarDisponibilidadDePago() {

}
}