package cl.duoc.MSFacturacion.Config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.MSFacturacion.model.Comprobante;
import cl.duoc.MSFacturacion.model.Banco;
import cl.duoc.MSFacturacion.model.FormaPago;
import cl.duoc.MSFacturacion.repository.ComprobanteRepository;
import cl.duoc.MSFacturacion.repository.FormaPagoRepository;
import cl.duoc.MSFacturacion.repository.BancoRepository;

@Configuration
public class Dataloader {

   @Bean
   CommandLineRunner initDataBase(BancoRepository bancoRepo , ComprobanteRepository comprobanteRepo , FormaPagoRepository formaDePagoRepo ){
   return arg -> {
         if(bancoRepo.count() > 0){

            System.out.println("No se cargaron datos porque ya existian datos en la base de datos");

         }else{

            Banco banco1 = new Banco(null, "Estado", null);
            Banco banco2 = new Banco(null, "Falabella", null);
            Banco banco3 = new Banco(null, "Santander", null);

            bancoRepo.save(banco1);
            bancoRepo.save(banco2);
            bancoRepo.save(banco3);

            
            FormaPago forma1 = new FormaPago(null, "Efectivo", null);
            FormaPago forma2 = new FormaPago(null, "Tarjeta de credito", null);
            FormaPago forma3 = new FormaPago(null, "Tarjeta Debito", null);
            
            formaDePagoRepo.save(forma1);
            formaDePagoRepo.save(forma2);
            formaDePagoRepo.save(forma3);

            
            // Comprobante comp1 = new Comprobante(null, "Comp-001",null, 20000, "Tarjeta de Crédito", true, forma2, banco3);
            // Comprobante comp2 = new Comprobante(null, "Comp-002",null, 10000, "Efectivo", true, forma1, banco2);
            // Comprobante comp3 = new Comprobante(null, "Comp-003",null, 30000, "Tarjeta de Débito", true, forma3, banco1);
            
            Comprobante comp1 = new Comprobante();
            comp1.setNumeroComprobante("COMP-001");
            comp1.setMontoTotal(20000);
            comp1.setMetodoPago("Tarjeta de Credito");
            comp1.setEstadoPago(true);
            comp1.setFormaPago(forma2);
            comp1.setBanco(banco3);
            comprobanteRepo.save(comp1);

            Comprobante comp2 = new Comprobante();
            comp2.setNumeroComprobante("COMP-002");
            comp2.setMontoTotal(10000);
            comp2.setMetodoPago("Efectivo");
            comp2.setEstadoPago(true);
            comp2.setFormaPago(forma1);
            comp2.setBanco(banco2);
            comprobanteRepo.save(comp2);

            Comprobante comp3 = new Comprobante();
            comp3.setNumeroComprobante("COMP-003");
            comp3.setMontoTotal(30000);
            comp3.setMetodoPago("Tarjeta Debito");
            comp3.setEstadoPago(true);
            comp3.setFormaPago(forma3);
            comp3.setBanco(banco1);
            comprobanteRepo.save(comp3);
            

            System.out.println("Datos cargados con exito a la base de datos");
         }

   };
}
    

}
