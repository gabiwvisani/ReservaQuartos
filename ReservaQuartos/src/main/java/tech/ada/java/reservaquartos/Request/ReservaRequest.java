package tech.ada.java.reservaquartos.Request;

import lombok.Getter;
import lombok.Setter;
import tech.ada.java.reservaquartos.domain.Entidades.Cliente;
import tech.ada.java.reservaquartos.domain.Entidades.Quarto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
//@Getter
//@Setter
public record ReservaRequest (Integer identificadorReserva,
                              LocalDateTime dataRealizacaoReserva,
                              LocalDateTime dataAtualizacaoReserva,
                              LocalDate dataEntrada,
                              LocalDate dataSaida,
                              Integer numeroHospedes,
                              Quarto quarto,
                              Cliente cliente,
                              Boolean statusConfirmada,
                              BigDecimal valorTotalReserva) {

    public enum FormaPagamento {
        CARTAO_DE_CREDITO, DINHEIRO, PIX
    }
}
