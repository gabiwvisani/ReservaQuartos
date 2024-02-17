package tech.ada.java.reservaquartos.domain;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/cliente")
public class ClienteController {
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public ClienteController(ClienteRepository clienteRepository, ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/clientes")
    public List<Cliente> buscarTodosClientes() {
        List<Cliente> listaTodosClientes = clienteRepository.findAll();
        return listaTodosClientes;
    }

}
