package tech.ada.java.reservaquartos.Domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.ada.java.reservaquartos.Domain.Quarto;
import tech.ada.java.reservaquartos.Domain.Cliente;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identificadorReserva;
    private LocalDateTime dataRealizacaoReserva;
    private LocalDateTime dataAtualizacaoReserva;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private Integer numeroHospedes;
    @ManyToOne
    private Quarto quarto;
    @ManyToOne
    private Cliente cliente;
    private Boolean statusConfirmada;
    private BigDecimal valorTotalReserva;
    private  FormaPagamento formaPagamento;
    public enum FormaPagamento {CARTAO_DE_CREDITO, DINHEIRO, PIX};
    public Reserva(LocalDateTime dataRealizacaoReserva, LocalDateTime dataAtualizacaoReserva, LocalDate dataEntrada, LocalDate dataSaida, Integer numeroHospedes, Quarto quarto, Cliente cliente, Boolean statusConfirmada,  FormaPagamento formaPagamento) {
        this.dataRealizacaoReserva = dataRealizacaoReserva;
        this.dataAtualizacaoReserva = dataAtualizacaoReserva;
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
    public void setValorTotalReserva(){
        BigDecimal diferencaDias = BigDecimal.valueOf(dataSaida.toEpochDay() - dataEntrada.toEpochDay());
        BigDecimal precoPorNoite = this.quarto.getPrecoPorNoite();
        this.valorTotalReserva = diferencaDias.multiply(precoPorNoite);
    }


}
