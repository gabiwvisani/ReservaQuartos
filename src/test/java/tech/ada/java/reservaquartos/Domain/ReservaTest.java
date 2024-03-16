package tech.ada.java.reservaquartos.Domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaTest {

    private Reserva reserva;
    private Quarto quarto;
    private Cliente cliente;
    private Reserva outraReserva;

    @BeforeEach
    public void setup() {
        quarto = new Quarto();
        quarto.setPrecoPorNoite(new BigDecimal("100.00"));

        cliente = new Cliente();

        reserva = new Reserva(
                LocalDate.of(2024, 3, 17),
                LocalDate.of(2024, 3, 19),
                2,
                quarto,
                cliente,
                true,
                Reserva.FormaPagamento.CARTAO_DE_CREDITO);
        outraReserva = new Reserva(
                LocalDate.of(2024, 3, 17),
                LocalDate.of(2024, 3, 19),
                2,
                quarto,
                cliente,
                true,
                Reserva.FormaPagamento.CARTAO_DE_CREDITO);
        outraReserva.setIdentificadorReserva(1);
        reserva.setIdentificadorReserva(1);
    }

    @Test
    public void testCalcularValorTotalReserva() {
        reserva.setValorTotalReserva();

        assertEquals(new BigDecimal("200.00"), reserva.getValorTotalReserva());
    }
    @Test
    public void testRealizarReserva() {
        reserva.setDataRealizacaoReserva();
        LocalDateTime dataRealizacao = reserva.getDataRealizacaoReserva();

        assertTrue(dataRealizacao.isEqual(LocalDateTime.now()));
    }

    @Test
    public void testEqualsComIdentificadorReservaDiferente() {
        Reserva outraReserva = new Reserva();
        outraReserva.setIdentificadorReserva(2);

        assertTrue(!reserva.equals(outraReserva));
    }
    @Test
    public void testEqualsComMesmoIdentificadorReserva() {
        assertTrue(reserva.equals(outraReserva));
    }

    @Test
    public void testEqualsComOutroTipo() {
        assertFalse(reserva.equals(new Object()));
    }

    @Test
    public void testEqualsComReservaNull() {
        assertFalse(reserva.equals(null));
    }
    @Test
    public void testAtualizarDataAtualizacaoReserva() throws InterruptedException {
        LocalDateTime dataAntiga = reserva.getDataAtualizacaoReserva();
        Thread.sleep(3000);
        reserva.setDataAtualizacaoReserva();
        LocalDateTime dataNova = reserva.getDataAtualizacaoReserva();
        assertTrue(dataNova.isAfter(dataAntiga));
    }
    @Test
    public void testSetValorTotalReservaZeroDias() {
        reserva.setDataEntrada(LocalDate.of(2024, 3, 17));
        reserva.setDataSaida(LocalDate.of(2024, 3, 17));
        reserva.setValorTotalReserva();

        assertEquals(new BigDecimal("100.00"), reserva.getValorTotalReserva());
    }

    @Test
    public void testSetValorTotalReservaUmaNoite() {
        reserva.setDataEntrada(LocalDate.of(2024, 3, 17));
        reserva.setDataSaida(LocalDate.of(2024, 3, 18));
        reserva.setValorTotalReserva();

        assertEquals(new BigDecimal("100.00"), reserva.getValorTotalReserva());
    }

    @Test
    public void testSetValorTotalReservaDiferentesDias() {
        reserva.setDataEntrada(LocalDate.of(2024, 3, 17));
        reserva.setDataSaida(LocalDate.of(2024, 3, 20));
        reserva.setValorTotalReserva();

        assertEquals(new BigDecimal("300.00"), reserva.getValorTotalReserva());
    }

    @Test
    public void testSetValorTotalReservaUmaSemana() {
        reserva.setDataEntrada(LocalDate.of(2024, 3, 17));
        reserva.setDataSaida(LocalDate.of(2024, 3, 24));
        reserva.setValorTotalReserva();

        assertEquals(new BigDecimal("700.00"), reserva.getValorTotalReserva());
    }

    @Test
    public void testSetValorTotalReservaPrecoPorNoiteZero() {
        reserva.getQuarto().setPrecoPorNoite(BigDecimal.ZERO);
        reserva.setDataEntrada(LocalDate.of(2024, 3, 17));
        reserva.setDataSaida(LocalDate.of(2024, 3, 19));
        reserva.setValorTotalReserva();

        assertEquals(BigDecimal.ZERO, reserva.getValorTotalReserva());
    }

    @Test
    public void testEqualsComIdentificadorReservaNull() {
        reserva.setIdentificadorReserva(null);
        outraReserva.setIdentificadorReserva(null);

        assertTrue(reserva.equals(outraReserva));
    }

    @Test
    public void testEqualsComReservasNull() {
        reserva.setIdentificadorReserva(null);

        assertFalse(reserva.equals(outraReserva));
    }

    @Test
    public void testHashCode() {
        Reserva reservaComIdentificadorNulo = new Reserva();
        reservaComIdentificadorNulo.setIdentificadorReserva(null);

        assertEquals(reserva.hashCode(), outraReserva.hashCode());
        assertNotEquals(reserva.hashCode(), reservaComIdentificadorNulo.hashCode());
    }
}
