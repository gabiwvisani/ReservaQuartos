package tech.ada.java.reservaquartos.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Repository.ClienteRepository;
import tech.ada.java.reservaquartos.Repository.ReservaRepository;
import tech.ada.java.reservaquartos.Request.ClienteRequest;
import tech.ada.java.reservaquartos.Service.ClienteService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteService clienteService;
    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ClienteController clienteController;
    private MockMvc mockMvc;
    List<Cliente> clientes;

    Cliente cliente1;
    Cliente cliente2;

    ClienteRequest cliente3 = new ClienteRequest();
    Optional<Cliente> clienteOptional1;
    Optional<Cliente> clienteOptional2;
    ClienteRequest request = new ClienteRequest();
    @Mock
    private ModelMapper modelMapper;


    @BeforeEach
    public void setup() {

        cliente1 = new Cliente(
                "Maria Silva",
                "12545458954",
                "27150-574",
                "Rua Prefeito Alguma Coisa",
                "(52) 99885-4612",
                "mariasilva@gmail.com");

        cliente1.setIdentificadorCliente(1);

        cliente2 = new Cliente(
                "João Souza",
                "98765432100",
                "27150-123",
                "Rua dos Testes",
                "(52) 12345-6789",
                "joaosouza@gmail.com");

        cliente3.setNomeCompleto("gabriela visani");
        cliente3.setCpf("12342578902");
        cliente3.setCep("12345-578");
        cliente3.setEndereco("Rua  123");
        cliente3.setTelefone("91234-5678");
        cliente3.setEmail("fulano.ciclano@example.com");

        clienteOptional1 = Optional.of(cliente1);
        clienteOptional2 = Optional.of(cliente2);

        clientes = Arrays.asList(cliente1, cliente2);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();

    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void buscarTodosClientesTest() throws Exception {

        when(clienteRepository.findAll()).thenReturn(clientes);

        mockMvc.perform(MockMvcRequestBuilders.get("/cliente").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(content().json(asJsonString(clientes)));
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    public void buscarClientePorCPFTest() throws Exception {
        when(clienteRepository.findByCpf("12545458954")).thenReturn(clienteOptional1);
        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/{cpf}", "12545458954").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
        verify(clienteRepository, times(1)).findByCpf("12545458954");
    }


    @Test
    public void cadastrarClienteTest() throws Exception {

        when(modelMapper.map(any(), any())).thenReturn(cliente1);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente1);

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cliente1)))
                .andExpect(status().isCreated());

        response.andExpect(jsonPath(("$.nomeCompleto"), equalTo("Maria Silva")));

        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    public void testAlterarClienteExistente() {


        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente1));
        when(clienteRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<?> responseEntity = clienteController.alterarCliente(1, cliente3);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("gabriela visani", ((Cliente) responseEntity.getBody()).getNomeCompleto());
        assertEquals("12342578902", ((Cliente) responseEntity.getBody()).getCpf());
        assertEquals("12345-578", ((Cliente) responseEntity.getBody()).getCep());
        assertEquals("Rua  123", ((Cliente) responseEntity.getBody()).getEndereco());
        assertEquals("91234-5678", ((Cliente) responseEntity.getBody()).getTelefone());
        assertEquals("fulano.ciclano@example.com", ((Cliente) responseEntity.getBody()).getEmail());

    }
    @Test
    public void testAlterarClienteNaoExistente() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = clienteController.alterarCliente(1, cliente3);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    @Test
    public void deletarClienteExistenteTest() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente1));

        ResponseEntity<?> responseEntity = clienteController.deletarCliente(1);
        verify(clienteRepository, times(1)).deleteById(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Cliente excluído com sucesso.", responseEntity.getBody());
    }

    @Test
    public void deletarClienteNaoExistenteTest() {
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = clienteController.deletarCliente(1);

        verify(clienteRepository, never()).deleteById(1);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Cliente não encontrado.", responseEntity.getBody());
    }

    @Test
    public void testBuscarTodosClientesListaVazia() {
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<?> responseEntity = clienteController.buscarTodosClientes();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertEquals("A lista de clientes está vazia", errorResponse.getMessage());
    }
    @Test
    public void testBuscarClientePorCPFClienteNaoEncontrado() {
        String cpf = "12345678900";
        when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = clienteController.buscarClientePorCPF(cpf);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertEquals("Não foi localizado um cliente com este CPF.", errorResponse.getMessage());
    }

    @Test
    public void testCadastrarCpfDuplicado() {
        String novoCpf = "98765432100";
        request.setCpf(novoCpf);
        when(clienteRepository.findById(1)).thenReturn(clienteOptional1);
        doThrow(new IllegalArgumentException("CPF já cadastrado")).when(clienteService).verificarDuplicidadeCpf(novoCpf);
        ResponseEntity<?> responseEntity = clienteController.alterarCliente(1, request);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertEquals("CPF já cadastrado", errorResponse.getMessage());
    }

    @Test
    public void testAlterarClienteCPFInvalido() {
        ClienteRequest request = new ClienteRequest();
        request.setCpf("ABC");
        when(clienteRepository.findById(1)).thenReturn(Optional.of(new Cliente()));
        ResponseEntity<?> responseEntity = clienteController.alterarCliente(1, request);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertEquals("O CPF deve conter exatamente 11 dígitos numéricos.", errorResponse.getMessage());
    }
}