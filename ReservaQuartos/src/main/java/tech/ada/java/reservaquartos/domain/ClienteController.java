package tech.ada.java.reservaquartos.domain;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/cliente")
public class ClienteController {
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public ClienteController(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/cliente")
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody ClienteRequest clienteRequest) {
        Cliente cliente = modelMapper.map(clienteRequest, Cliente.class);
        Cliente novoCliente = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @GetMapping("/cliente")
    public List<Cliente> buscarTodosClientes() {
        List<Cliente> listaTodosClientes = clienteRepository.findAll();
        return listaTodosClientes;
    }

    @PatchMapping("/cliente/{id}")
    public ResponseEntity<Cliente> alterarCliente(
            @PathVariable Integer id,
            @RequestBody ClienteRequest request) {

        Optional<Cliente> optionalCliente = clienteRepository.findById(id);

        if(optionalCliente.isPresent()) {
            Cliente clienteModificado = optionalCliente.get();

            if(request.nomeCompleto() != null) clienteModificado.setNomeCompleto(request.nomeCompleto());
            if(request.cpf() != null) clienteModificado.setCpf(request.cpf());
            if(request.cep() != null) clienteModificado.setCep(request.cep());
            if(request.endereco() != null) clienteModificado.setEndereco(request.endereco());
            if(request.telefone() != null) clienteModificado.setTelefone(request.telefone());
            if(request.email() != null) clienteModificado.setEmail(request.email());

            Cliente clienteAtualizado = clienteRepository.save(clienteModificado);
            return ResponseEntity.ok(clienteAtualizado);

        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
