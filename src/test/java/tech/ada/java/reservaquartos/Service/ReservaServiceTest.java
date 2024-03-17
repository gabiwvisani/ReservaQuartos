package tech.ada.java.reservaquartos.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Domain.Quarto;
import tech.ada.java.reservaquartos.Domain.Reserva;
import tech.ada.java.reservaquartos.Repository.ReservaRepository;
import tech.ada.java.reservaquartos.Request.ReservaRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {
    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;


    private Quarto quarto1;
    private Cliente cliente1;
    private Cliente cliente2;
    private Reserva reserva1;
    private Reserva reserva2;

    @BeforeEach
    public void setup() {
        quarto1 = new Quarto(
                1,
                3,
                "Quarto com vista para a piscina",
                new BigDecimal("400"),
                Quarto.TipoQuarto.SUPERIOR);
        quarto1.setIdentificadorQuarto(1);


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
        cliente2.setIdentificadorCliente(2);

        reserva1 = new Reserva(
                LocalDate.of(2024, 4, 14),
                LocalDate.of(2024, 4, 15),
                3,
                quarto1,
                cliente1,
                true,
                Reserva.FormaPagamento.CARTAO_DE_CREDITO);

        reserva2 = new Reserva(
                LocalDate.of(2024, 4, 15),
                LocalDate.of(2024, 4, 17),
                2,
                quarto1,
                cliente2,
                true,
                Reserva.FormaPagamento.DINHEIRO);
    }

    @Test
    void verificaConflitosReservaTest() {
        List<Reserva> conflitos = new ArrayList<>();
        conflitos.add(reserva1);
        when(reservaRepository.encontraConflitosReserva(any(), any(), any())).thenReturn(conflitos);

        boolean result = reservaService.verificaConflitosReserva(1,LocalDate.of(2024, 4, 15),
                LocalDate.of(2024, 4, 17),reserva2);

        assertTrue(result);
    }
    @Test
    void verificaSemConflitosReservaTest() {
        List<Reserva> conflitos = new ArrayList<>();
        conflitos.add(reserva1);
        when(reservaRepository.encontraConflitosReserva(any(), any(), any())).thenReturn(conflitos);

        boolean result = reservaService.verificaConflitosReserva(1,LocalDate.of(2024, 4, 18),
                LocalDate.of(2024, 4, 20),reserva2);

        assertTrue(result);

    }


    @Test
    void validaDataTest() {
        LocalDate hoje = LocalDate.now();

    }
}