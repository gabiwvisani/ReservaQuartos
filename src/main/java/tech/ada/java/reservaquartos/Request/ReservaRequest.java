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
    public ReservaRequest( LocalDate dataEntrada, LocalDate dataSaida, Integer numeroHospedes, Quarto quarto, Cliente cliente, Boolean statusConfirmada,  Reserva.FormaPagamento formaPagamento) {
        this.dataRealizacaoReserva = LocalDateTime.now();
        this.dataAtualizacaoReserva = LocalDateTime.now();
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.numeroHospedes = numeroHospedes;
        this.quarto = quarto;
        this.cliente = cliente;
        this.statusConfirmada = statusConfirmada;
        BigDecimal diferencaDias = BigDecimal.valueOf(dataSaida.toEpochDay() - dataEntrada.toEpochDay());
        BigDecimal precoPorNoite = quarto.getPrecoPorNoite();
        this.valorTotalReserva = diferencaDias.multiply(precoPorNoite);
        this.formaPagamento= formaPagamento;
    }

}
