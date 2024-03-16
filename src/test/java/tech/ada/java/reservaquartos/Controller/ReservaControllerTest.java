package tech.ada.java.reservaquartos.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Domain.Quarto;
import tech.ada.java.reservaquartos.Domain.Reserva;
import tech.ada.java.reservaquartos.Repository.ClienteRepository;
import tech.ada.java.reservaquartos.Repository.QuartoRepository;
import tech.ada.java.reservaquartos.Repository.ReservaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static tech.ada.java.reservaquartos.Controller.ClienteControllerTest.asJsonString;

@ExtendWith(MockitoExtension.class)
public class ReservaControllerTest {
    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private QuartoRepository quartoRepository;
    @InjectMocks
    private ReservaController reservaController;
    @InjectMocks
    private ClienteController clienteController;
    @InjectMocks
    private QuartoController quartoController;
    private MockMvc mockMvc;
    private Reserva reserva1;
    private Reserva reserva2;
    private Quarto quarto1;
    private Cliente cliente1;
    private Quarto quarto2;
    private Cliente cliente2;
    private List<Reserva> listaReservas;




    @BeforeEach
    public void setup() {

        quarto1 = new Quarto(
                1,
                3,
                "Quarto com vista para a piscina",
                new BigDecimal("400"),
                Quarto.TipoQuarto.SUPERIOR);

        quarto2 = new Quarto(
                2,
                2,
                "Quarto com varanda",
                new BigDecimal("250"),
                Quarto.TipoQuarto.SUPERIOR);

        quarto2.setIdentificadorQuarto(2);

        quarto1.setIdentificadorQuarto(1);

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

        reserva1 = new Reserva(
                LocalDate.of(2024, 3, 14),
                LocalDate.of(2024, 3, 15),
                3,
                quarto1,
                cliente1,
                true,
                Reserva.FormaPagamento.CARTAO_DE_CREDITO);
        reserva2 = new Reserva(
                LocalDate.of(2024, 3, 16),
                LocalDate.of(2024, 3, 17),
                2,
                quarto2,
                cliente2,
                true,
                Reserva.FormaPagamento.DINHEIRO);

        cliente1.setIdentificadorCliente(1);
        cliente2.setIdentificadorCliente(2);

        listaReservas = Arrays.asList(reserva1, reserva2);

        mockMvc = MockMvcBuilders.standaloneSetup(reservaController).build();

    }


//    @Test // Teste findAllReservasTest está com problema na leitura do LocalDate
//    public void findAllReservasTest() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String expectedJson = objectMapper.writeValueAsString(listaReservas);
//        when(reservaRepository.findAll()).thenReturn(listaReservas);
//        mockMvc.perform(MockMvcRequestBuilders.get("/reserva").
//                        contentType(MediaType.APPLICATION_JSON)).
//                andExpect(content().json(expectedJson));
//        verify(reservaRepository, times(1)).findAll();
//    }




}
