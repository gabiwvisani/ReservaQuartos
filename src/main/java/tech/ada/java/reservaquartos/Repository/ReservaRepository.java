package tech.ada.java.reservaquartos.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.java.reservaquartos.Domain.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
}
