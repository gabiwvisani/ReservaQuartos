package tech.ada.java.reservaquartos.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Quarto {
    private Integer identificadorQuarto;
    private Integer numeroQuarto;
    private enum tipoQuarto {STANDART, SUPERIOR, DELUXE};
    private Integer capacidadeMaximaDePessoas;
    private BigDecimal precoPorNoite;
    private String descricao;


}
