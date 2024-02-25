package tech.ada.java.reservaquartos.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Domain.Quarto;
import tech.ada.java.reservaquartos.Domain.Reserva;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    @Query("SELECT r FROM Reserva r WHERE r.quarto.identificadorQuarto = :idQuarto " +
            "AND ((r.dataEntrada BETWEEN :dataEntrada AND :dataSaida) OR " +
            "(r.dataSaida BETWEEN :dataEntrada AND :dataSaida) OR " +
            "(:dataEntrada BETWEEN r.dataEntrada AND r.dataSaida) OR " +
            "(:dataSaida BETWEEN r.dataEntrada AND r.dataSaida)) " +
            "AND r.statusConfirmada = true")
    List<Reserva> encontraConflitosReserva(Integer idQuarto, LocalDate dataEntrada, LocalDate dataSaida);

    List<Reserva> findByCliente(Cliente cliente);
    List<Reserva> findByQuarto(Quarto quarto);
}
