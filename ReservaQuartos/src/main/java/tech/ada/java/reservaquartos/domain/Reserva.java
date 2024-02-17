package tech.ada.java.reservaquartos.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {
    private Integer identificadorReserva;
    private LocalDateTime dataRealizacaoReserva;
    private LocalDateTime dataAtualizacaoReserva;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private Integer numeroHospedes;
    private Quarto quarto;
    private Cliente cliente;
    private Boolean statusConfirmada;
    private BigDecimal valorTotalReserva;
    private enum formaPagamento {CARTAO_DE_CREDITO, DINHEIRO, PIX};

}
