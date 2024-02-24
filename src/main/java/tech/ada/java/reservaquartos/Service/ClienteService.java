package tech.ada.java.reservaquartos.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Repository.ClienteRepository;

import java.util.Optional;

@Service
public class ClienteService {
    private static ClienteRepository clienteRepository;
    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public static Cliente verificarDuplicidadeCpf(String cpf) {
        Optional<Cliente> clienteExistente = clienteRepository.findByCpf(cpf);
        if (clienteExistente.isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF.");
        }
        return clienteExistente.orElse(null);
    }
}
