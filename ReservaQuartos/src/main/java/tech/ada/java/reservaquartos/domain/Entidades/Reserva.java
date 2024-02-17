package tech.ada.java.reservaquartos.domain.Entidades;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.ada.java.reservaquartos.domain.Entidades.Cliente;
import tech.ada.java.reservaquartos.domain.Entidades.Quarto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
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
