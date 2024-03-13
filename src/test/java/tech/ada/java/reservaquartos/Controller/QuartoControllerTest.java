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
import tech.ada.java.reservaquartos.Domain.Quarto;
import tech.ada.java.reservaquartos.Repository.QuartoRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
public class QuartoControllerTest {
    @InjectMocks
    private QuartoController quartoController;
    @Mock
    private QuartoRepository quartoRepository;
    Quarto quarto;
    Optional<Quarto> optionalQuarto;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        quarto = new Quarto();
        optionalQuarto = Optional.of(quarto);
        mockMvc = MockMvcBuilders.standaloneSetup(quartoController).build();
    }

    @Test
    public void buscarQuartoPorID() throws Exception {
        when(quartoRepository.findById(1)).thenReturn(optionalQuarto);
        mockMvc.perform(MockMvcRequestBuilders.get("/quarto/{id}", 1).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
        verify(quartoRepository,times(1)).findById(1);
    }
}
