package tech.ada.java.reservaquartos.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.java.reservaquartos.Repository.ReservaRepository;
import tech.ada.java.reservaquartos.Request.ReservaRequest;
import tech.ada.java.reservaquartos.domain.Entidades.Reserva;

@RestController
public class ReservaController {
    private final ReservaRepository reservaRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public ReservaController(ReservaRepository reservaRepository, ModelMapper modelMapper) {
        this.reservaRepository = reservaRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/reserva")
    public ResponseEntity<Reserva> cadastrarQuarto(@RequestBody ReservaRequest reservaRequest){
        Reserva reservaConvertido = modelMapper.map(reservaRequest, Reserva.class);
        Reserva novaReserva = reservaRepository.save(reservaConvertido);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
    }
}
