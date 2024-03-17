package tech.ada.java.reservaquartos.Controller;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Domain.Quarto;
import tech.ada.java.reservaquartos.Domain.Reserva;
import tech.ada.java.reservaquartos.Repository.ClienteRepository;
import tech.ada.java.reservaquartos.Repository.QuartoRepository;
import tech.ada.java.reservaquartos.Repository.ReservaRepository;
import tech.ada.java.reservaquartos.Request.ReservaRequest;
import tech.ada.java.reservaquartos.Service.ReservaService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class ReservaControllerTest {
    @Mock
    private ReservaRepository reservaRepository;
    @Mock
    private ReservaService reservaService;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private QuartoRepository quartoRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ReservaController reservaController;

    private MockMvc mockMvc;
    private ReservaRequest reserva1;
    private Reserva reserva2;
    private Reserva reserva3;
    private Quarto quarto1;
    private Cliente cliente1;
    private Quarto quarto2;
    private Cliente cliente2;
    private List<Reserva> listaReservas = new ArrayList<>();

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
        cliente1.setIdentificadorCliente(1);
        cliente2.setIdentificadorCliente(2);


        reserva1 = new ReservaRequest(
                LocalDate.of(2024, 4, 14),
                LocalDate.of(2024, 4, 15),
                3,
                quarto1,
                cliente1,
                true,
                Reserva.FormaPagamento.CARTAO_DE_CREDITO);
        reserva2 = new Reserva(
                LocalDate.of(2024, 4, 16),
                LocalDate.of(2024, 4, 17),
                2,
                quarto2,
                cliente2,
                true,
                Reserva.FormaPagamento.DINHEIRO);
        reserva3 = new Reserva(
                LocalDate.of(2024, 4, 14),
                LocalDate.of(2024, 4, 15),
                3,
                quarto1,
                cliente1,
                true,
                Reserva.FormaPagamento.CARTAO_DE_CREDITO);

        listaReservas.add(reserva2);
        listaReservas.add(reserva3);
        mockMvc = MockMvcBuilders.standaloneSetup(reservaController).build();
    }

    @Test
    public void testCadastrarReservaSucesso() {
        when(quartoRepository.findById(1)).thenReturn(Optional.of(quarto1));
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente1));
        when(reservaService.verificaConflitosReserva(anyInt(), any(), any(), any())).thenReturn(false);
        when(reservaService.validaData(any(), any())).thenReturn(true);
        when(modelMapper.map(reserva1, Reserva.class)).thenReturn(reserva2);

        ResponseEntity<?> responseEntity = reservaController.cadastrarQuarto(1, 1, reserva1);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }
    @Test
    public void testCadastrarReservaQuandoQuartoNaoExiste() {
        when(quartoRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = reservaController.cadastrarQuarto(1, 1, reserva1);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    public void testCadastrarReservaQuandoClienteNaoExiste() {
        when(quartoRepository.findById(1)).thenReturn(Optional.of(quarto1));
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = reservaController.cadastrarQuarto(1, 1, reserva1);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    public void testCadastrarReservaQuandoHaConflitos() {
        when(quartoRepository.findById(1)).thenReturn(Optional.of(quarto1));
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente1));
        when(reservaService.verificaConflitosReserva(anyInt(), any(), any(), any())).thenReturn(true);

        ResponseEntity<?> responseEntity = reservaController.cadastrarQuarto(1, 1, reserva1);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }
    @Test
    public void testCadastrarReservaQuandoDatasInvalidas() {
        when(quartoRepository.findById(1)).thenReturn(Optional.of(quarto1));
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente1));
        when(reservaService.verificaConflitosReserva(anyInt(), any(), any(), any())).thenReturn(false);
        when(reservaService.validaData(any(), any())).thenReturn(false);

        ResponseEntity<?> responseEntity = reservaController.cadastrarQuarto(1, 1, reserva1);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    public void testBuscarReservaExistentePorId() {
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva2));
        ResponseEntity<?> responseEntity = reservaController.findReservaById(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(reserva2, responseEntity.getBody());
        verify(reservaRepository, times(1)).findById(1);
    }

    @Test
    public void testBuscarReservaInexistentePorId() {
        when(reservaRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = reservaController.findReservaById(1);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testAlterarReservaExistente() {
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva2));
        when(quartoRepository.findById(1)).thenReturn(Optional.of(quarto1));
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente1));
        when(reservaService.verificaConflitosReserva(anyInt(), any(), any(), any())).thenReturn(false);
        when(reservaService.validaData(any(), any())).thenReturn(true);

        ResponseEntity<?> responseEntity = reservaController.alterarReserva(1, 1, 1, reserva1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    public void testAlterarReservaInexistente() {
        when(reservaRepository.findById(1)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = reservaController.alterarReserva(1, 1, 1, reserva1);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(reservaRepository, never()).save(any(Reserva.class));
    }
    @Test
    public void testAlterarReservaQuandoDatasConflitantes() {
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva2));
        when(reservaService.verificaConflitosReserva(anyInt(), any(), any(), any())).thenReturn(true);

        ResponseEntity<?> responseEntity = reservaController.alterarReserva(1, 1, 1, reserva1);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        verify(reservaRepository, never()).save(any(Reserva.class));
    }
    @Test
    public void testAlterarReservaQuandoQuartoNaoExiste() {
        reserva2.setIdentificadorReserva(1);
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva2)); // Stubbing para simular uma reserva existente
        when(reservaService.validaData(any(), any())).thenReturn(true);
        when(quartoRepository.findById(1)).thenReturn(Optional.empty()); // Stubbing para simular um quarto não encontrado

        ResponseEntity<?> responseEntity = reservaController.alterarReserva(1, 1, 1, reserva1);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Quarto não encontrado", responseEntity.getBody());

    }
    @Test
    public void testAlterarReservaQuandoClienteNaoExiste() {
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva2));
        when(reservaService.validaData(any(), any())).thenReturn(true);
        when(quartoRepository.findById(1)).thenReturn(Optional.of(quarto1));
        when(clienteRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> responseEntity = reservaController.alterarReserva(1, 1, 1, reserva1);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Cliente não encontrado", responseEntity.getBody());
    }
    @Test
    public void testAlterarReservaQuandoDataEntradaMaiorQueDataSaida() {
        reserva1.setDataEntrada(LocalDate.of(2024, 4, 15));
        reserva1.setDataSaida(LocalDate.of(2024, 4, 14));
        when(reservaRepository.findById(1)).thenReturn(Optional.of(reserva2));

        ResponseEntity<?> responseEntity = reservaController.alterarReserva(1, 1, 1, reserva1);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("A data de entrada deve ser igual ou maior a data de hoje e a data de saída deve ser maior ou igual a data de entrada.", responseEntity.getBody());
    }


    @Test
    public void testDeletarReservaExistente() {
        when(reservaRepository.existsById(1)).thenReturn(true);

        ResponseEntity<?> responseEntity = reservaController.deletarReserva(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(reservaRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeletarReservaInexistente() {
        when(reservaRepository.existsById(1)).thenReturn(false);

        ResponseEntity<?> responseEntity = reservaController.deletarReserva(1);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    @Test
    public void findAllReservasTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String expectedJson = objectMapper.writeValueAsString(listaReservas);
        when(reservaRepository.findAll()).thenReturn(listaReservas);
        mockMvc.perform(MockMvcRequestBuilders.get("/reserva").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(content().json(expectedJson));
        verify(reservaRepository, times(1)).findAll();
    }
}
