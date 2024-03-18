package tech.ada.java.reservaquartos.Domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class QuartoTest {

    @InjectMocks
    private Quarto quarto;
    int numeroQuarto;
    int capacidadeMaxima;
    String descricao;
    BigDecimal precoPorNoite;
    Quarto.TipoQuarto tipoQuarto;
    BigDecimal precoEsperado;
    Quarto quarto1;
    Quarto quarto2;
    @BeforeEach
    public void setup() {
        numeroQuarto = 101;
        capacidadeMaxima = 2;
        descricao = "Quarto com vista para o mar";
        precoPorNoite = new BigDecimal("200.00");
        tipoQuarto = Quarto.TipoQuarto.SUPERIOR;

        precoEsperado = new BigDecimal("150.00");

        quarto1 = new Quarto();
        quarto1.setIdentificadorQuarto(1);
        quarto1.setNumeroQuarto(numeroQuarto);
        quarto1.setCapacidadeMaximaDePessoas(capacidadeMaxima);
        quarto1.setDescricao(descricao);
        quarto1.setPrecoPorNoite(precoPorNoite);
        quarto1.setTipoQuarto(tipoQuarto);

        quarto2 = new Quarto();
        quarto2.setIdentificadorQuarto(1);
        quarto2.setNumeroQuarto(numeroQuarto);
        quarto2.setCapacidadeMaximaDePessoas(capacidadeMaxima);
        quarto2.setDescricao(descricao);
        quarto2.setPrecoPorNoite(precoPorNoite);
        quarto2.setTipoQuarto(tipoQuarto);
    }
    @Test
    public void testConstrutorComParametros() {
        Quarto quarto = new Quarto(numeroQuarto, capacidadeMaxima, descricao, precoPorNoite, tipoQuarto);
        assertEquals(numeroQuarto, quarto.getNumeroQuarto());
        assertEquals(capacidadeMaxima, quarto.getCapacidadeMaximaDePessoas());
        assertEquals(descricao, quarto.getDescricao());
        assertEquals(precoPorNoite, quarto.getPrecoPorNoite());
        assertEquals(tipoQuarto, quarto.getTipoQuarto());
    }

    @Test
    public void testHashCode() {
        int hashCode1 = quarto1.hashCode();
        int hashCode2 = quarto2.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testConstrutorComPrecoEsperado() {
        Quarto quartoComPreco = new Quarto(precoEsperado);
        assertEquals(precoEsperado, quartoComPreco.getPrecoPorNoite(),
                "O preço por noite deve ser igual ao valor passado no construtor");
    }

    @Test
    public void testSetNumeroQuarto() {
        quarto.setNumeroQuarto(numeroQuarto);
        assertEquals(numeroQuarto, quarto.getNumeroQuarto(),
                "O número do quarto deve ser igual ao valor esperado");
    }

    @Test
    public void testSetCapacidadeMaximaDePessoas() {
        quarto.setCapacidadeMaximaDePessoas(capacidadeMaxima);
        assertEquals(capacidadeMaxima, quarto.getCapacidadeMaximaDePessoas(),
                "A capacidade máxima de pessoas deve ser igual ao valor esperado");
    }
    @Test
    public void testEquals() {
        boolean result = quarto1.equals(quarto2);
        assertEquals(true, result);
    }
    @Test
    public void testNotEquals() {
        quarto1.setPrecoPorNoite(precoEsperado);
        boolean result = quarto1.equals(quarto2);
        assertEquals(false, result);
    }

}
