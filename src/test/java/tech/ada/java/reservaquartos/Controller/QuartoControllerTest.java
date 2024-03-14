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
import tech.ada.java.reservaquartos.Repository.QuartoRepository;

import org.springframework.test.web.servlet.MvcResult;
import tech.ada.java.reservaquartos.Request.AlteraValorQuartoRequest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static javax.management.Query.or;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.oneOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class QuartoControllerTest {
    @InjectMocks
    private QuartoController quartoController;
    @Mock
    private QuartoRepository quartoRepository;
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
    public void buscarQuartoPorIDTest1() throws Exception {
        when(quartoRepository.findById(anyInt())).thenReturn(optionalQuarto);
        mockMvc.perform(MockMvcRequestBuilders.get("/quarto/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void buscarQuartoPorIDTest2() throws Exception {
        when(quartoRepository.findById(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/quarto/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void buscarPorTipoQuartoTest() throws Exception {
        when(quartoRepository.findByTipoQuarto(Quarto.TipoQuarto.valueOf("SUPERIOR"))).thenReturn(quartos);
        mockMvc.perform(get("/quarto").param("tipoQuarto", tipoQuarto))
                .andExpect(status().isOk());
    }

    @Test
    public void buscarPorCapacidadeMaximaTest() throws Exception {

        int capacidadeMaxima = 3;

        List<Quarto> quartosMockados = Arrays.asList(quarto1);
        when(quartoRepository.findByCapacidadeMaximaDePessoas(capacidadeMaxima)).thenReturn(quartosMockados);

        //Realizando requisição pro endpoint
        mockMvc.perform(get("/quarto")
                        .param("capacidadeMaximaDePessoas", String.valueOf(capacidadeMaxima)))
                .andExpect(status().isOk());

        //Verificando se o método do repositório foi chamado ao menos 1 vez
        verify(quartoRepository, times(1)).findByCapacidadeMaximaDePessoas(capacidadeMaxima);
    }


    @Test
    public void buscarPorCapacidadeMaximaTest2() throws Exception {

        int capacidadeMaxima = 3;

        //Alterando a capacidade máxima retornada pelo mock
        List<Quarto> quartosMockados = Arrays.asList(quarto2); // Quarto 2 tem capacidade 2
        when(quartoRepository.findByCapacidadeMaximaDePessoas(capacidadeMaxima)).thenReturn(quartosMockados);

        MvcResult result = mockMvc.perform(get("/quarto")
                        .param("capacidadeMaximaDePessoas", String.valueOf(capacidadeMaxima)))
                .andReturn();

        verify(quartoRepository, times(1)).findByCapacidadeMaximaDePessoas(capacidadeMaxima);

        //Verificando se a resposta contém quartos com capacidade maior que a capacidade máxima
        String responseContent = result.getResponse().getContentAsString();
        assertFalse(responseContent.contains("\"capacidadeMaximaDePessoas\":3"));
    }

    @Test
    public void buscarPorPrecoPorNoiteTest() throws Exception {

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
    public void buscarPorPrecoPorNoiteTest_2() throws Exception{
        BigDecimal precoPorNoite = new BigDecimal("400");

        when(quartoRepository.findByPrecoPorNoite(precoPorNoite)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/quarto")
                        .param("precoPorNoite", String.valueOf(precoPorNoite)))
                .andExpect(status().isNotFound());

        verify(quartoRepository, times(1)).findByPrecoPorNoite(precoPorNoite);
    }

    @Test
    public void alterarValorQuartoTest() throws Exception {
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

}


