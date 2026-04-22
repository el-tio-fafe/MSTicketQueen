package cl.duoc.msAtenciones.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.msAtenciones.Model.Atencion;

@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Integer> {

}
