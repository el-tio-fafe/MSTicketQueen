package cl.duoc.MSFacturacion.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.MSFacturacion.model.banco;
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

            banco banco1 = new banco(null, "Estado");
            banco banco2 = new banco(null, "Santander");
            banco banco3 = new banco(null, "Falabella");

            bancoRepo.save(banco1);
            bancoRepo.save(banco2);
            bancoRepo.save(banco3);

            //CARGAR DATOS DE COMPROBANTE


            //CARGAR DATOS DE FORMA DE PAGO



            System.out.println("Datos cargados con exito a la base de datos");
         }

   };
}
    

}
