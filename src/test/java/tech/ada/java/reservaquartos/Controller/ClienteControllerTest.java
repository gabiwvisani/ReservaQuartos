package tech.ada.java.reservaquartos.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Repository.ClienteRepository;
import tech.ada.java.reservaquartos.Repository.ReservaRepository;
import tech.ada.java.reservaquartos.Service.ClienteService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteRepository clienteRepository;
//    private ClienteService clienteService;
//    private ReservaRepository reservaRepository;
//    private ReservaController reservaController;
//    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteController clienteController;
    private MockMvc mockMvc;
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



}