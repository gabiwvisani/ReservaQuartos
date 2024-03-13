package tech.ada.java.reservaquartos.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Repository.ClienteRepository;
import tech.ada.java.reservaquartos.Service.ClienteService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ModelMapper modelMapper;

    List<Cliente> clientes;

    Cliente cliente1;
    Cliente cliente2;
    Optional<Cliente> clienteOptional1;
    Optional<Cliente> clienteOptional2;

    @BeforeEach
    public void setup(){

        cliente1 = new Cliente(
                "Maria Silva",
                "12545458954",
                "27150-574",
                "Rua Prefeito Alguma Coisa",
                "(52) 99885-4612",
                "mariasilva@gmail.com");

        cliente2 = new Cliente(
                "João Souza",
                "98765432100",
                "27150-123",
                "Rua dos Testes",
                "(52) 12345-6789",
                "joaosouza@gmail.com");

        clienteOptional1 = Optional.of(cliente1);
        clienteOptional2 = Optional.of(cliente2);

        clientes = Arrays.asList(cliente1, cliente2);
    }

    @Test
    public void verificarDuplicidadeCpfTeste(){
        when(clienteRepository.findByCpf(cliente1.getCpf())).thenReturn(Optional.of(cliente1));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.verificarDuplicidadeCpf(cliente1.getCpf());
        });
        assertEquals("Já existe um cliente cadastrado com este CPF.", exception.getMessage());
        verify(clienteRepository, times(1)).findByCpf(cliente1.getCpf());
    }

    @Test
    public void verificarDuplicidadeCpfTeste2(){
     //   when(clienteRepository.findByCpf(cliente2.getCpf())).thenReturn(Optional.of(cliente1));
        when(clienteRepository.findByCpf(cliente2.getCpf())).thenReturn(Optional.empty());
        Cliente cliente = clienteService.verificarDuplicidadeCpf(cliente2.getCpf());
        assertNull(cliente);
        verify(clienteRepository, times(1)).findByCpf(cliente2.getCpf());
    }


}