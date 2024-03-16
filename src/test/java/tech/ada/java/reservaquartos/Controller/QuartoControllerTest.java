package tech.ada.java.reservaquartos.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Domain.Quarto;
import tech.ada.java.reservaquartos.Domain.Reserva;
import tech.ada.java.reservaquartos.Repository.QuartoRepository;

import org.springframework.test.web.servlet.MvcResult;
import tech.ada.java.reservaquartos.Repository.ReservaRepository;
import tech.ada.java.reservaquartos.Request.AlteraValorQuartoRequest;
import tech.ada.java.reservaquartos.Request.QuartoRequest;
import tech.ada.java.reservaquartos.Service.QuartoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static javax.management.Query.or;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.oneOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static tech.ada.java.reservaquartos.Controller.ClienteControllerTest.asJsonString;


@ExtendWith(MockitoExtension.class)
public class QuartoControllerTest {
    @InjectMocks
    private QuartoController quartoController;
    @Mock
    private QuartoRepository quartoRepository;

    @Mock
    private ReservaRepository reservaRepository;
    @Mock
    private ReservaController reservaController;

    @Mock
    private QuartoService quartoService;

    Quarto quarto;
    Quarto quarto1;
    Quarto quarto2;
    List<Quarto> quartos;
    Optional<Quarto> optionalQuarto;
    private MockMvc mockMvc;
    private String tipoQuarto;

    @BeforeEach
    public void setup() {
        quarto = new Quarto();
        optionalQuarto = Optional.of(quarto);
        mockMvc = MockMvcBuilders.standaloneSetup(quartoController).build();
        tipoQuarto = "SUPERIOR";
        quarto1 = new Quarto(
                1,
                3,
                "Quarto com vista para a piscima",
                new BigDecimal("400"),
                Quarto.TipoQuarto.SUPERIOR);

        quarto2 = new Quarto(
                2,
                2,
                "Quarto com ar condicionado",
                new BigDecimal("200"),
                Quarto.TipoQuarto.SUPERIOR);
        quartos = Arrays.asList(quarto1, quarto2);
    }

    @Test
    public void buscarQuartoExistentePorIDTest() throws Exception {
        when(quartoRepository.findById(anyInt())).thenReturn(optionalQuarto);
        mockMvc.perform(MockMvcRequestBuilders.get("/quarto/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(quartoRepository, times(1)).findById(anyInt());
    }
    @Test
    public void buscarQuartoInexistentePorIDTest() throws Exception {
        when(quartoRepository.findById(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/quarto/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(quartoRepository, times(1)).findById(anyInt());
    }

    @Test
    public void buscarPorTipoQuartoTest() throws Exception {
        when(quartoRepository.findByTipoQuarto(Quarto.TipoQuarto.valueOf("SUPERIOR"))).thenReturn(quartos);
        mockMvc.perform(get("/quarto").param("tipoQuarto", tipoQuarto))
                .andExpect(status().isOk());
    }

    @Test
    public void buscarPorCapacidadeMaximaComQuartoEncontradoTest() throws Exception {
        int capacidadeMaxima = 3;

        List<Quarto> quartosMockados = Arrays.asList(quarto1);
        when(quartoRepository.findByCapacidadeMaximaDePessoas(capacidadeMaxima)).thenReturn(quartosMockados);

        mockMvc.perform(get("/quarto")
                        .param("capacidadeMaximaDePessoas", String.valueOf(capacidadeMaxima)))
                .andExpect(status().isOk());

        verify(quartoRepository, times(1)).findByCapacidadeMaximaDePessoas(capacidadeMaxima);
    }


    @Test
    public void buscarPorCapacidadeMaximaSemQuartoEncontradoTest() throws Exception {
        int capacidadeMaxima = 3;

        List<Quarto> quartosMockados = Arrays.asList(quarto2); // Quarto 2 tem capacidade 2
        when(quartoRepository.findByCapacidadeMaximaDePessoas(capacidadeMaxima)).thenReturn(quartosMockados);

        MvcResult result = mockMvc.perform(get("/quarto")
                        .param("capacidadeMaximaDePessoas", String.valueOf(capacidadeMaxima)))
                .andReturn();

        verify(quartoRepository, times(1)).findByCapacidadeMaximaDePessoas(capacidadeMaxima);

        String responseContent = result.getResponse().getContentAsString();
        assertFalse(responseContent.contains("\"capacidadeMaximaDePessoas\":3"));
    }

    @Test
    public void buscarPorPrecoPorNoiteQuartoExistenteTest() throws Exception {
        BigDecimal precoPorNoite = new BigDecimal("400");

        List<Quarto> quartosMockados = Arrays.asList(quarto1);
        when(quartoRepository.findByPrecoPorNoite(precoPorNoite)).thenReturn(quartosMockados);

        mockMvc.perform(get("/quarto")
                        .param("precoPorNoite", String.valueOf(precoPorNoite)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));

        verify(quartoRepository, times(1)).findByPrecoPorNoite(precoPorNoite);
    }

    @Test
    public void buscarPorPrecoPorNoiteQuartoInexistenteTest() throws Exception{
        BigDecimal precoPorNoite = new BigDecimal("400");

        when(quartoRepository.findByPrecoPorNoite(precoPorNoite)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/quarto")
                        .param("precoPorNoite", String.valueOf(precoPorNoite)))
                .andExpect(status().isNotFound());

        verify(quartoRepository, times(1)).findByPrecoPorNoite(precoPorNoite);
    }

    @Test
    public void alterarValorQuartoQuartoExistenteTest() throws Exception {
        int quartoId = 1;
        BigDecimal novoPrecoPorNoite = new BigDecimal("500");
        AlteraValorQuartoRequest request = new AlteraValorQuartoRequest(novoPrecoPorNoite);
        Quarto quarto = new Quarto(1, 2, "Quarto de teste", new BigDecimal("300"), Quarto.TipoQuarto.SUPERIOR);
        Optional<Quarto> optionalQuarto = Optional.of(quarto);

        when(quartoRepository.findById(quartoId)).thenReturn(optionalQuarto);
        when(quartoRepository.save(quarto)).thenReturn(quarto);

        ResponseEntity<Quarto> responseEntity = quartoController.alterarValorQuarto(quartoId, request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(novoPrecoPorNoite, responseEntity.getBody().getPrecoPorNoite());
    }

    @Test
    public void alterarValorQuartoQuartoInexistenteTest() throws Exception {
        int quartoId = 1;
        BigDecimal novoPrecoPorNoite = new BigDecimal("500");
        AlteraValorQuartoRequest request = new AlteraValorQuartoRequest(novoPrecoPorNoite);

        Optional<Quarto> optionalQuarto = Optional.empty();
        when(quartoRepository.findById(quartoId)).thenReturn(optionalQuarto);

        ResponseEntity<Quarto> responseEntity = quartoController.alterarValorQuarto(quartoId, request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void atualizarQuartoExistenteTest() {
        QuartoRequest quartoRequest = new QuartoRequest();
        quartoRequest.setNumeroQuarto(1);
        quartoRequest.setCapacidadeMaximaDePessoas(2);
        quartoRequest.setPrecoPorNoite(new BigDecimal("500"));
        quartoRequest.setDescricao("Quarto teste");
        quartoRequest.setTipoQuarto(Quarto.TipoQuarto.SUPERIOR);

        Quarto quartoExistente = new Quarto(1, 2, "Quarto teste", new BigDecimal("300"), Quarto.TipoQuarto.SUPERIOR);

        Optional<Quarto> optionalQuarto = Optional.of(quartoExistente);

        when(quartoRepository.findById(anyInt())).thenReturn(optionalQuarto);
        when(quartoRepository.save(any(Quarto.class))).thenReturn(quartoExistente);

        ResponseEntity<?> responseEntity = quartoController.atualizarQuarto(1, quartoRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(quartoExistente, responseEntity.getBody());
        assertEquals(quartoRequest.getNumeroQuarto(), quartoExistente.getNumeroQuarto());
        assertEquals(quartoRequest.getCapacidadeMaximaDePessoas(), quartoExistente.getCapacidadeMaximaDePessoas());
        assertEquals(quartoRequest.getPrecoPorNoite(), quartoExistente.getPrecoPorNoite());
        assertEquals(quartoRequest.getDescricao(), quartoExistente.getDescricao());
        assertEquals(quartoRequest.getTipoQuarto(), quartoExistente.getTipoQuarto());
    }

    @Test
    public void atualizarQuartoInexistenteTest() {
        QuartoRequest quartoRequest = new QuartoRequest();
        quartoRequest.setNumeroQuarto(1);
        quartoRequest.setCapacidadeMaximaDePessoas(2);
        quartoRequest.setPrecoPorNoite(new BigDecimal("500"));
        quartoRequest.setDescricao("Quarto teste");
        quartoRequest.setTipoQuarto(Quarto.TipoQuarto.SUPERIOR);

        Optional<Quarto> optionalQuarto = Optional.empty();

        when(quartoRepository.findById(anyInt())).thenReturn(optionalQuarto);

        ResponseEntity<?> responseEntity = quartoController.atualizarQuarto(1, quartoRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void deletarQuartoTest() throws Exception{

        quarto1.setIdentificadorQuarto(1);
        Reserva reserva = new Reserva();
        reserva.setIdentificadorReserva(1);
        reserva.setQuarto(quarto1);
        List<Reserva> reservasDoQuarto = new ArrayList<>();
        reservasDoQuarto.add(reserva);

        when(quartoRepository.findById(1)).thenReturn(Optional.of(quarto1));
        when(reservaRepository.findByQuarto(quarto1)).thenReturn(reservasDoQuarto);

        mockMvc.perform(delete("/quarto/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        verify(quartoRepository, times(1)).findById(1);
        verify(reservaRepository, times(1)).findByQuarto(quarto1);

        for (Reserva reservaDaLista : reservasDoQuarto) {
            verify(reservaController, times(1)).deletarReserva(reservaDaLista.getIdentificadorReserva());
        }
        verify(quartoRepository, times(1)).deleteById(1);
    }

    @Test
    public void quartosDisponiveisTest(){
        LocalDate dataEntrada = LocalDate.of(2024, 3, 15);
        LocalDate dataSaida = LocalDate.of(2024, 3, 20);
        List<Quarto> quartosDisponiveis = new ArrayList<>();
        quartosDisponiveis.add(new Quarto(101, 2, "Quarto Standard", BigDecimal.valueOf(100), Quarto.TipoQuarto.STANDARD));
        quartosDisponiveis.add(new Quarto(102, 4, "Quarto Superior", BigDecimal.valueOf(150), Quarto.TipoQuarto.SUPERIOR));

        when(quartoService.buscarQuartosDisponiveis(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(quartosDisponiveis);

        ResponseEntity<List<Quarto>> responseEntity = quartoController.getQuartosDisponiveis(dataEntrada, dataSaida);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(quartosDisponiveis, responseEntity.getBody());
    }

    @Test
    public void semQuartosDisponiveisTest() {
        LocalDate dataEntrada = LocalDate.of(2024, 3, 15);
        LocalDate dataSaida = LocalDate.of(2024, 3, 20);

        when(quartoService.buscarQuartosDisponiveis(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<Quarto>> responseEntity = quartoController.getQuartosDisponiveis(dataEntrada, dataSaida);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}


