package tech.ada.java.reservaquartos.Controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Domain.Quarto;
import tech.ada.java.reservaquartos.Repository.ClienteRepository;
import tech.ada.java.reservaquartos.Request.ClienteRequest;
import tech.ada.java.reservaquartos.Service.ClienteService;

import java.util.Arrays;
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
//    private ReservaRepository reservaRepository;
//    private ReservaController reservaController;
//    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteController clienteController;
    private MockMvc mockMvc;
    List<Cliente> clientes;

    Cliente cliente1;
    Cliente cliente2;

    Cliente cliente3;
    Optional<Cliente> clienteOptional1;
    Optional<Cliente> clienteOptional2;

    Optional<Cliente> clienteOptional3;

    String clienteJson;

    @Mock
    private ModelMapper modelMapper;



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

            cliente3 = new Cliente(
                    "gabriela visani",
                    "12342578902",
                    "12345-578",
                    "Rua  123",
                    "91234-5678",
                    "fulano.ciclano@example.com"
            );

            clienteJson = "{\"nomeCompleto\":\"gabriela visani\",\"cpf\":\"12342578902\",\"cep\":\"12345-578\",\"endereco\":\"Rua 123\",\"telefone\":\"91234-5678\",\"email\":\"fulano.ciclano@example.com\"}";

            cliente1.setIdentificadorCliente(1);
            cliente3.setIdentificadorCliente(1);

            clienteOptional1 = Optional.of(cliente1);
            clienteOptional2 = Optional.of(cliente2);
            clienteOptional3 = Optional.of(cliente3);

        clientes = Arrays.asList(cliente1, cliente2);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();

    }



    public static String asJsonString(final Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void buscarTodosClientesTest() throws Exception {

        //Preparar
        when(clienteRepository.findAll()).thenReturn(clientes);

        //Ação
        mockMvc.perform(MockMvcRequestBuilders.get("/cliente").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(content().json(asJsonString(clientes)));
        verify(clienteRepository,times(1)).findAll();
    }

    @Test
    public void buscarClientePorCPFTest() throws Exception {
        when(clienteRepository.findByCpf("12545458954")).thenReturn(clienteOptional1);
        mockMvc.perform(MockMvcRequestBuilders.get("/cliente/{cpf}", "12545458954").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
        verify(clienteRepository,times(1)).findByCpf("12545458954");
    }


    @Test
    public void cadastrarClienteTest() throws Exception {

        when(modelMapper.map(any(), any())).thenReturn(cliente1);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente1);

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cliente1)))
                .andExpect(status().isCreated());

        response.andExpect(jsonPath(("$.nomeCompleto"),equalTo("Maria Silva")));

        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }


//    @Test
//    public void alterarClienteTest() throws Exception {
//
//        when(clienteRepository.findById(1)).thenReturn(Optional.of(new Cliente()));
//        when(modelMapper.map(any(), any())).thenReturn(new Cliente());
//        when(clienteRepository.save(any(Cliente.class))).thenReturn(new Cliente());
//
//        var response = mockMvc.perform(MockMvcRequestBuilders.patch("/cliente/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(clienteJson))
//                .andExpect(status().isOk());
//
//        response.andExpect(jsonPath(("$.nomeCompleto"),equalTo("gabriela visani")));
//
//        verify(clienteRepository, times(1)).save(any(Cliente.class));
//    }

}

//// Mocking
//Integer id = 1;
//ClienteRequest request = new ClienteRequest();
//        request.setNomeCompleto("Nome Modificado");
//Optional<Cliente> optionalCliente = Optional.of(new Cliente());
//when(clienteRepository.findById(id)).thenReturn(optionalCliente);
//when(clienteRepository.save(any(Cliente.class))).thenReturn(new Cliente());
//
//// Execução
//ResponseEntity<?> responseEntity = clienteController.alterarCliente(id, request);