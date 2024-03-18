package tech.ada.java.reservaquartos.Request;

import lombok.Getter;
import lombok.Setter;
import tech.ada.java.reservaquartos.Domain.Cliente;
import tech.ada.java.reservaquartos.Domain.Quarto;
import tech.ada.java.reservaquartos.Domain.Reserva;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class ReservaRequest {
    private Integer identificadorReserva;
    private  LocalDateTime dataRealizacaoReserva;
    private  LocalDateTime dataAtualizacaoReserva;
    private   LocalDate dataEntrada;
    private   LocalDate dataSaida;
    private Integer numeroHospedes;
    private Quarto quarto;
    private Cliente cliente;
    private  Boolean statusConfirmada;
    private  BigDecimal valorTotalReserva;
    private Reserva.FormaPagamento formaPagamento;

}
