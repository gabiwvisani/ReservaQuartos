package tech.ada.java.reservaquartos.Controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Domain.Quarto;
import tech.ada.java.reservaquartos.Repository.QuartoRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
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
    public void buscarQuartoPorID() throws Exception {
        when(quartoRepository.findById(1)).thenReturn(optionalQuarto);
        mockMvc.perform(MockMvcRequestBuilders.get("/quarto/{id}", 1).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
        verify(quartoRepository,times(1)).findById(1);
    }

    @Test
    public void buscarPorTipoQuartoTest() throws Exception {
        when(quartoRepository.findByTipoQuarto(Quarto.TipoQuarto.valueOf("SUPERIOR"))).thenReturn(quartos);
        mockMvc.perform(get("/quarto").param("tipoQuarto", tipoQuarto))
                .andExpect(status().isOk());
    }

    }


