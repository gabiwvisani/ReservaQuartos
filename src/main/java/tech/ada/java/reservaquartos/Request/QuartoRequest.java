package tech.ada.java.reservaquartos.Request;

import lombok.Getter;
import lombok.Setter;
import tech.ada.java.reservaquartos.Domain.Quarto;

import java.math.BigDecimal;

@Getter
@Setter
public class QuartoRequest{

    private Integer numeroQuarto;
    private Integer capacidadeMaximaDePessoas;
    private BigDecimal precoPorNoite;
    private String descricao;
    private Quarto.TipoQuarto tipoQuarto;

}
