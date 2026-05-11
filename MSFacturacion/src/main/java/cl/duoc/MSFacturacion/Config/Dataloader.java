package cl.duoc.MSFacturacion.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import cl.duoc.MSFacturacion.model.banco;
import cl.duoc.MSFacturacion.model.formaPago;
import cl.duoc.MSFacturacion.repository.ComprobanteRepository;
import cl.duoc.MSFacturacion.repository.FormaDePagoRepository;
import cl.duoc.MSFacturacion.repository.bancoRepository;

@Configuration
public class Dataloader {
    CommandLineRunner initDataBase(bancoRepository bancoRepo , ComprobanteRepository comprobanteRepo , FormaDePagoRepository formaDePagoRepo ){
   return arg -> {
banco banco1 =
bancoRepo.save(banco1);

   }
}
    

}
