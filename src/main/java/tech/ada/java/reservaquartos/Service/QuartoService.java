package tech.ada.java.reservaquartos.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ada.java.reservaquartos.Domain.Quarto;
import tech.ada.java.reservaquartos.Repository.QuartoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class QuartoService {
    private final QuartoRepository quartoRepository;

    @Autowired
    public QuartoService(QuartoRepository quartoRepository) {
        this.quartoRepository = quartoRepository;
    }

    public List<Quarto> buscarQuartosDisponiveis(LocalDate dataEntrada, LocalDate dataSaida) {
        return quartoRepository.findQuartosDisponiveis(dataEntrada, dataSaida);
    }
}
