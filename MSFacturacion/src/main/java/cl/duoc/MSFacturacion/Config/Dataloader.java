package cl.duoc.MSFacturacion.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.MSFacturacion.model.Comprobante;
import cl.duoc.MSFacturacion.model.banco;
import cl.duoc.MSFacturacion.model.formaPago;
import cl.duoc.MSFacturacion.repository.ComprobanteRepository;
import cl.duoc.MSFacturacion.repository.FormaDePagoRepository;
import cl.duoc.MSFacturacion.repository.bancoRepository;

@Configuration
public class Dataloader {

   @Bean
   CommandLineRunner initDataBase(bancoRepository bancoRepo , ComprobanteRepository comprobanteRepo , FormaDePagoRepository formaDePagoRepo ){
   return arg -> {
         if(bancoRepo.count() > 0){

            System.out.println("No se cargaron datos porque ya existian datos en la base de datos");

         }else{

            banco banco1 = new banco(null, "Estado", null);
            banco banco2 = new banco(null, "Santander", null);
            banco banco3 = new banco(null, "Falabella", null);

            bancoRepo.save(banco1);
            bancoRepo.save(banco2);
            bancoRepo.save(banco3);

            //CARGAR DATOS DE COMPROBANTE
            Comprobante comprobante1 = new Comprobante(null, "123456", null, 20000, null, false, null, banco3);
            Comprobante comprobante2 = new Comprobante(null, "654321", null, 10000, null, false, null, banco2);
            Comprobante comprobante3 = new Comprobante(null, "789012", null, 30000, null, false, null, banco1);
            comprobanteRepo.save(comprobante1);
            comprobanteRepo.save(comprobante2);
            comprobanteRepo.save(comprobante3);
            //CARGAR DATOS DE FORMA DE PAGO
            formaPago forma1 = new formaPago(null, "Efectivo", null);
            formaPago forma2 = new formaPago(null, "Tarjeta de credito", null);
            formaPago forma3 = new formaPago(null, "Tarjeta Debito", null);
            formaDePagoRepo.save(forma1);
            formaDePagoRepo.save(forma2);
            formaDePagoRepo.save(forma3);
            System.out.println("Datos cargados con exito a la base de datos");
         }

   };
}
    

}
