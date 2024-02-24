package tech.ada.java.reservaquartos.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.java.reservaquartos.Domain.Quarto;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Integer> {
    public List<Quarto> findByTipoQuarto(Quarto.TipoQuarto tipoQuarto);
    public List<Quarto> findByCapacidadeMaximaDePessoas(Integer capacidadeMaximaDePessoas);
    public List<Quarto> findByPrecoPorNoite(BigDecimal precoPorNoite);
}
