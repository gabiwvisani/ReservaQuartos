package tech.ada.java.reservaquartos.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.reservaquartos.Repository.ReservaRepository;
import tech.ada.java.reservaquartos.Request.ReservaRequest;
import tech.ada.java.reservaquartos.Domain.Reserva;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


    @GetMapping("/reservas")
    public ResponseEntity<List<Reserva>> findAllReservas(){
        List<Reserva> listaReservas = reservaRepository.findAll();
        return ResponseEntity.ok(listaReservas);
    }

    @GetMapping("/reservas/{id}")
    public ResponseEntity<?> findReservaById(@PathVariable Integer id){
        Optional<Reserva> reservaOptional = reservaRepository.findById(id);

        if(reservaOptional.isPresent()){
            return ResponseEntity.ok(reservaOptional.get());
        } else{
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensagem", "Não foi possível localizar a reserva pelo ID");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }



}
