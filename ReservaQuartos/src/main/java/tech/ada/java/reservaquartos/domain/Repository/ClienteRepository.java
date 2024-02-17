package tech.ada.java.reservaquartos.domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.java.reservaquartos.domain.Entidades.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
