package tech.ada.java.reservaquartos.Request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class QuartoRequest{

    private Integer numeroQuarto;
    private Integer capacidadeMaximaDePessoas;
    private BigDecimal precoPorNoite;
    private String descricao;
    private TipoQuarto tipoQuarto;
    public enum TipoQuarto {STANDARD, SUPERIOR, DELUXE};
}
