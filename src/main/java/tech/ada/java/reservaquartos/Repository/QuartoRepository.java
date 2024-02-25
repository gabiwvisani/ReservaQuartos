package tech.ada.java.reservaquartos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.ada.java.reservaquartos.Domain.Quarto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Integer> {
    public List<Quarto> findByTipoQuarto(Quarto.TipoQuarto tipoQuarto);
    public List<Quarto> findByCapacidadeMaximaDePessoas(Integer capacidadeMaximaDePessoas);
    public List<Quarto> findByPrecoPorNoite(BigDecimal precoPorNoite);
    @Query("SELECT q FROM Quarto q WHERE q NOT IN " +
            "(SELECT r.quarto FROM Reserva r WHERE r.statusConfirmada = true " +
            "AND ((:dataEntrada BETWEEN r.dataEntrada AND r.dataSaida) " +
            "OR (:dataSaida BETWEEN r.dataEntrada AND r.dataSaida) " +
            "OR (r.dataEntrada BETWEEN :dataEntrada AND :dataSaida) " +
            "OR (r.dataSaida BETWEEN :dataEntrada AND :dataSaida)))")
    List<Quarto> findQuartosDisponiveis(@Param("dataEntrada") LocalDate dataEntrada,
                                        @Param("dataSaida") LocalDate dataSaida);
}
