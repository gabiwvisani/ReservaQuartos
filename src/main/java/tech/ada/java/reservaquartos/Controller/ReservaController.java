package tech.ada.java.reservaquartos.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Domain.Quarto;
import tech.ada.java.reservaquartos.Domain.Reserva;
import tech.ada.java.reservaquartos.Repository.ClienteRepository;
import tech.ada.java.reservaquartos.Repository.QuartoRepository;
import tech.ada.java.reservaquartos.Repository.ReservaRepository;
import tech.ada.java.reservaquartos.Request.ReservaRequest;
//import tech.ada.java.reservaquartos.domain.Entidades.Reserva;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ReservaController {
    private final ReservaRepository reservaRepository;
    private final QuartoRepository quartoRepository;
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public ReservaController(ReservaRepository reservaRepository,
                             QuartoRepository quartoRepository,
                             ClienteRepository clienteRepository,
                             ModelMapper modelMapper) {
        this.reservaRepository = reservaRepository;
        this.quartoRepository = quartoRepository;
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/reserva")
    public ResponseEntity<Reserva> cadastrarQuarto(@RequestParam Integer idQuarto,
                                                   @RequestParam Integer idCliente,
            @RequestBody ReservaRequest reservaRequest){
        Quarto quarto = quartoRepository.findById(idQuarto)
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Reserva reservaConvertido = modelMapper.map(reservaRequest, Reserva.class);
        reservaConvertido.setQuarto(quarto);
        reservaConvertido.setCliente(cliente);
        reservaConvertido.setValorTotalReserva();
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

    @PatchMapping("/reservas/{id}")
    public ResponseEntity<?> alterarReserva(
            @PathVariable Integer id,
            @RequestParam Integer idQuarto,
            @RequestParam Integer idCliente,
            @RequestBody ReservaRequest request){

        Optional<Reserva> optionalReserva = reservaRepository.findById(id);

        if(optionalReserva.isPresent()){
            Reserva reservaModificada = optionalReserva.get();

            if (request.getIdentificadorReserva() != null) {
                reservaModificada.setIdentificadorReserva(request.getIdentificadorReserva());
            }
            if (request.getDataRealizacaoReserva() != null) {
                reservaModificada.setDataRealizacaoReserva(request.getDataRealizacaoReserva());
            }
            if (request.getDataAtualizacaoReserva() != null) {
                reservaModificada.setDataAtualizacaoReserva(request.getDataAtualizacaoReserva());
            }
            if (request.getDataEntrada() != null) {
                reservaModificada.setDataEntrada(request.getDataEntrada());
                reservaModificada.setValorTotalReserva();
            }
            if (request.getDataSaida() != null) {
                reservaModificada.setDataSaida(request.getDataSaida());
                reservaModificada.setValorTotalReserva();
            }
            if (request.getNumeroHospedes() != null) {
                reservaModificada.setNumeroHospedes(request.getNumeroHospedes());
            }
            if (idQuarto != null) {
                Quarto quarto = quartoRepository.findById(idQuarto).orElseThrow(() -> new RuntimeException("Quarto não encontrado"));;
                reservaModificada.setQuarto(quarto);
                reservaModificada.setValorTotalReserva();

            }
            if (idCliente != null) {
                Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));;
                reservaModificada.setCliente(cliente);
            }
            if (request.getStatusConfirmada() != null) {
                reservaModificada.setStatusConfirmada(request.getStatusConfirmada());
            }

            Reserva reservaAtualizada = reservaRepository.save(reservaModificada);
            return ResponseEntity.ok(reservaAtualizada);

        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensagem", "Não foi possível localizar a reserva pelo ID");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @PutMapping("/reservas/{id}")
    public ResponseEntity<?> alterarTudoReserva(
            @PathVariable Integer id,
            @RequestParam Integer idQuarto,
            @RequestParam Integer idCliente,
            @RequestBody ReservaRequest request){

        Optional<Reserva> optionalReserva = reservaRepository.findById(id);

        if(optionalReserva.isPresent()){
            Reserva reservaModificada = optionalReserva.get();

                reservaModificada.setIdentificadorReserva(request.getIdentificadorReserva());
                reservaModificada.setDataRealizacaoReserva(request.getDataRealizacaoReserva());
                reservaModificada.setDataAtualizacaoReserva(request.getDataAtualizacaoReserva());
                reservaModificada.setDataEntrada(request.getDataEntrada());
                reservaModificada.setDataSaida(request.getDataSaida());
                reservaModificada.setNumeroHospedes(request.getNumeroHospedes());
                Quarto quarto = quartoRepository.findById(idQuarto).orElseThrow(() -> new RuntimeException("Quarto não encontrado"));;
                reservaModificada.setQuarto(quarto);
                reservaModificada.setValorTotalReserva();
                Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));;
                reservaModificada.setCliente(cliente);
                reservaModificada.setStatusConfirmada(request.getStatusConfirmada());
            Reserva reservaAtualizada = reservaRepository.save(reservaModificada);
            return ResponseEntity.ok(reservaAtualizada);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensagem", "Não foi possível localizar a reserva pelo ID");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
