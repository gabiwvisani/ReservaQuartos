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
    public enum formaPagamento {CARTAO_DE_CREDITO, DINHEIRO, PIX};

}
