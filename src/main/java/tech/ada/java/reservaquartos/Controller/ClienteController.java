package tech.ada.java.reservaquartos.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.reservaquartos.Request.ClienteRequest;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class ClienteController {
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public ClienteController(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/cliente")
    public ResponseEntity<?> cadastrarCliente(@RequestBody ClienteRequest clienteRequest) {

        String mensagemErro = Cliente.validarCPF(clienteRequest.getCpf());
        if (mensagemErro != null) {
            ErrorResponse errorResponse = new ErrorResponse(mensagemErro);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        Cliente clienteConvertido = modelMapper.map(clienteRequest, Cliente.class);
        Optional<Cliente> clienteExistente = clienteRepository.findByCpf(clienteRequest.getCpf());

        if (clienteExistente.isPresent()) {
            ErrorResponse errorResponse = new ErrorResponse("Já existe um cliente cadastrado com este CPF.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        Cliente novoCliente = clienteRepository.save(clienteConvertido);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @GetMapping("/cliente")
    public List<Cliente> buscarTodosClientes() {
        List<Cliente> listaTodosClientes = clienteRepository.findAll();
        return listaTodosClientes;
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
            @RequestBody ClienteRequest request) {

        Optional<Cliente> optionalCliente = clienteRepository.findById(id);

        if (optionalCliente.isPresent()) {
            Cliente clienteModificado = optionalCliente.get();

            if (request.getNomeCompleto() != null) clienteModificado.setNomeCompleto(request.getNomeCompleto());
            if (request.getCpf() != null) clienteModificado.setCpf(request.getCpf());
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


}