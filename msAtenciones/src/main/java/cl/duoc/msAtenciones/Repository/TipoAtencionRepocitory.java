package cl.duoc.msAtenciones.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msAtenciones.Model.TipoAtencion;

@Repository
public interface TipoAtencionRepocitory extends JpaRepository<TipoAtencion, Integer> {

}
