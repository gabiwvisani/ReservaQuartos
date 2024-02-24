package tech.ada.java.reservaquartos.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ada.java.reservaquartos.Domain.Reserva;
import tech.ada.java.reservaquartos.Repository.ReservaRepository;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public boolean verificaConflitosReserva(Integer idQuarto, LocalDate dataEntrada, LocalDate dataSaida) {
        List<Reserva> conflitos = reservaRepository.encontraConflitosReserva(idQuarto, dataEntrada, dataSaida);
        return !conflitos.isEmpty();
    }
    public boolean validaData(LocalDate dataEntrada, LocalDate dataSaida) {
        LocalDate hoje = LocalDate.now();
        if (dataEntrada.isBefore(hoje)) {
            return false;
        }
        return !dataSaida.isBefore(dataEntrada);
    }
}
