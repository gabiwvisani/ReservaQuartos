package tech.ada.java.reservaquartos.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.reservaquartos.Domain.Reserva;
import tech.ada.java.reservaquartos.Repository.ReservaRepository;
import tech.ada.java.reservaquartos.Request.ClienteRequest;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Repository.ClienteRepository;
import tech.ada.java.reservaquartos.Service.ClienteService;

import java.util.List;
import java.util.Optional;

@RestController
public class ClienteController {
    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;
   // private final Cliente cliente;
    private final ReservaRepository reservaRepository;
    private final ReservaController reservaController;
    private final ModelMapper modelMapper;
    @Autowired
    public ClienteController(ClienteRepository clienteRepository,ClienteService clienteService,ReservaRepository reservaRepository,ReservaController reservaController, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteService= clienteService;
        this.reservaRepository= reservaRepository;
        this.reservaController =reservaController;
        this.modelMapper = modelMapper;
       // this.cliente = cliente;
    }

    @PostMapping("/cliente")
    public ResponseEntity<?> cadastrarCliente(@jakarta.validation.Valid @RequestBody ClienteRequest clienteRequest) {

        String mensagemErro = Cliente.validarCPF(clienteRequest.getCpf());
        if (mensagemErro != null) {
            ErrorResponse errorResponse = new ErrorResponse(mensagemErro);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            clienteService.verificarDuplicidadeCpf(clienteRequest.getCpf());
        } catch (IllegalArgumentException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        Cliente clienteConvertido = modelMapper.map(clienteRequest, Cliente.class);
        Cliente novoCliente = clienteRepository.save(clienteConvertido);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @GetMapping("/cliente")
    public ResponseEntity<?> buscarTodosClientes() {
        List<Cliente> listaTodosClientes = clienteRepository.findAll();
        if (listaTodosClientes.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse("A lista de clientes está vazia");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(listaTodosClientes);
      }

    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<?> buscarClientePorCPF(@PathVariable String cpf) {
        Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);

        if (clienteOptional.isPresent()) {
            return ResponseEntity.ok(clienteOptional.get());
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Não foi localizado um cliente com este CPF.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PatchMapping("/cliente/{id}")
    public ResponseEntity<?> alterarCliente(
            @PathVariable Integer id,
            @jakarta.validation.Valid @RequestBody ClienteRequest request) {

        Optional<Cliente> optionalCliente = clienteRepository.findById(id);

        if (optionalCliente.isPresent()) {
            Cliente clienteModificado = optionalCliente.get();

            if (request.getNomeCompleto() != null) clienteModificado.setNomeCompleto(request.getNomeCompleto());
            if (request.getCpf() != null  &&
                   // !request.getCpf().equals(null) //&&
                    !request.getCpf().equals(clienteModificado.getCpf() )
            )
            {
                try
                {
                    clienteService.verificarDuplicidadeCpf(request.getCpf());
                } catch (IllegalArgumentException ex) {
                    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
                }

                String mensagemErro = Cliente.validarCPF(request.getCpf());
                if (mensagemErro != null)
                {
                    ErrorResponse errorResponse = new ErrorResponse(mensagemErro);
                    return ResponseEntity.badRequest().body(errorResponse);
                }


                clienteModificado.setCpf(request.getCpf());
            }

            if (request.getCep() != null) clienteModificado.setCep(request.getCep());
            if (request.getEndereco() != null) clienteModificado.setEndereco(request.getEndereco());
            if (request.getTelefone() != null) clienteModificado.setTelefone(request.getTelefone());
            if (request.getEmail() != null) clienteModificado.setEmail(request.getEmail());

            Cliente clienteAtualizado = clienteRepository.save(clienteModificado);
            return ResponseEntity.ok(clienteAtualizado);

        } else {
            ErrorResponse errorResponse = new ErrorResponse("Não foi localizado um cliente com este ID.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Integer id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            List<Reserva> reservasDoCliente = reservaRepository.findByCliente(cliente);
            for (Reserva reserva : reservasDoCliente) {
                reservaController.deletarReserva(reserva.getIdentificadorReserva());
            }
            clienteRepository.deleteById(id);
            return ResponseEntity.ok("Cliente excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }
    }


}