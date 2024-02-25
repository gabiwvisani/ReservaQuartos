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
import tech.ada.java.reservaquartos.Service.ReservaService;
//import tech.ada.java.reservaquartos.domain.Entidades.Reserva;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ReservaController {
    private final ReservaRepository reservaRepository;
    private final ReservaService reservaService;
    private final QuartoRepository quartoRepository;
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public ReservaController(ReservaRepository reservaRepository,
                             QuartoRepository quartoRepository,
                             ClienteRepository clienteRepository,
                             ReservaService reservaService,
                             ModelMapper modelMapper) {
        this.reservaRepository = reservaRepository;
        this.quartoRepository = quartoRepository;
        this.clienteRepository = clienteRepository;
        this.reservaService = reservaService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/reserva")
    public ResponseEntity<?> cadastrarQuarto(@RequestParam Integer idQuarto,
                                                   @RequestParam Integer idCliente,
                                                   @RequestBody ReservaRequest reservaRequest){
        Quarto quarto = quartoRepository.findById(idQuarto)
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        LocalDate dataEntrada = reservaRequest.getDataEntrada();
        LocalDate dataSaida = reservaRequest.getDataSaida();
        boolean hasConflicts = reservaService.verificaConflitosReserva(idQuarto, dataEntrada, dataSaida, null);
        boolean dataValida = reservaService.validaData(dataEntrada, dataSaida);
        if (!hasConflicts) {
            if(dataValida) {
                Reserva reservaConvertido = modelMapper.map(reservaRequest, Reserva.class);
                reservaConvertido.setQuarto(quarto);
                reservaConvertido.setCliente(cliente);
                reservaConvertido.setValorTotalReserva();
                reservaConvertido.setDataAtualizacaoReserva();
                reservaConvertido.setDataRealizacaoReserva();
                Reserva novaReserva = reservaRepository.save(reservaConvertido);
                //novaReserva.setValorTotalReserva();
                return ResponseEntity.status(HttpStatus.CREATED).body(novaReserva);
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de entrada deve ser igual ou maior a data de hoje e a data de saída deve ser maior ou igual a data de entrada.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("O quarto já está reservado para as datas especificadas");
        }
    }


    @GetMapping("/reserva")
    public List<Reserva> findAllReservas(){
        List<Reserva> listaReservas = reservaRepository.findAll();
        return listaReservas;
    }


    @GetMapping("/reserva/{id}")
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

    @PatchMapping("/reserva/{id}")
    public ResponseEntity<?> alterarReserva(
            @PathVariable Integer id,
            @RequestParam Integer idQuarto,
            @RequestParam Integer idCliente,
            @RequestBody ReservaRequest request){

        Optional<Reserva> optionalReserva = reservaRepository.findById(id);

        if(optionalReserva.isPresent()){
            Reserva reservaModificada = optionalReserva.get();
            if (request.getDataEntrada() != null ||request.getDataSaida() != null) {
                boolean hasConflicts = reservaService.verificaConflitosReserva(idQuarto, request.getDataEntrada()!= null ?request.getDataEntrada():reservaModificada.getDataEntrada(), request.getDataSaida()!= null ?request.getDataSaida():reservaModificada.getDataSaida(),reservaModificada);
                if (hasConflicts) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("O quarto já está reservado para as datas especificadas");
                }
                boolean dataValida = reservaService.validaData(request.getDataEntrada()!= null ?request.getDataEntrada():reservaModificada.getDataEntrada(), request.getDataSaida()!= null ?request.getDataSaida():reservaModificada.getDataSaida());
                if (!dataValida) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de entrada deve ser igual ou maior a data de hoje e a data de saída deve ser maior ou igual a data de entrada.");
                }
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
            if (request.getFormaPagamento() != null) {
                reservaModificada.setFormaPagamento(request.getFormaPagamento());
            }
            reservaModificada.setDataAtualizacaoReserva();
            Reserva reservaAtualizada = reservaRepository.save(reservaModificada);
            return ResponseEntity.ok(reservaAtualizada);

        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensagem", "Não foi possível localizar a reserva pelo ID");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
//    @PutMapping("/reserva/{id}")
//    public ResponseEntity<?> alterarTudoReserva(
//            @PathVariable Integer id,
//            @RequestParam Integer idQuarto,
//            @RequestParam Integer idCliente,
//            @RequestBody ReservaRequest request){
//
//        Optional<Reserva> optionalReserva = reservaRepository.findById(id);
//
//        if(optionalReserva.isPresent()){
//            Reserva reservaModificada = optionalReserva.get();
//            Boolean hasConflicts = reservaService.verificaConflitosReserva(idQuarto, request.getDataEntrada(), request.getDataSaida(),reservaModificada);
//            if (hasConflicts) {
//                return ResponseEntity.status(HttpStatus.CONFLICT).body("O quarto já está reservado para as datas especificadas");
//            }
//            boolean dataValida = reservaService.validaData(request.getDataEntrada(), request.getDataSaida());
//            if (!dataValida) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de entrada deve ser igual ou maior a data de hoje e a data de saída deve ser maior ou igual a data de entrada.");
//            }
//            reservaModificada.setIdentificadorReserva(request.getIdentificadorReserva());
//            if(request.getDataRealizacaoReserva()==null){
//                reservaModificada.setDataRealizacaoReserva();
//            }else {
//                reservaModificada.setDataRealizacaoReserva(request.getDataRealizacaoReserva());
//            }
//            reservaModificada.setDataAtualizacaoReserva();
//            reservaModificada.setDataEntrada(request.getDataEntrada());
//            reservaModificada.setDataSaida(request.getDataSaida());
//            reservaModificada.setNumeroHospedes(request.getNumeroHospedes());
//            Quarto quarto = quartoRepository.findById(idQuarto).orElseThrow(() -> new RuntimeException("Quarto não encontrado"));;
//            reservaModificada.setQuarto(quarto);
//            reservaModificada.setValorTotalReserva();
//            Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));;
//            reservaModificada.setCliente(cliente);
//            reservaModificada.setStatusConfirmada(request.getStatusConfirmada());
//            Reserva reservaAtualizada = reservaRepository.save(reservaModificada);
//            return ResponseEntity.ok(reservaAtualizada);
//        } else {
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("mensagem", "Não foi possível localizar a reserva pelo ID");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
//        }
//    }
}
