package tech.ada.java.reservaquartos.Request;

import java.math.BigDecimal;

public record QuartoRequest(
         Integer numeroQuarto,
         Integer capacidadeMaximaDePessoas,
         BigDecimal precoPorNoite,
         String descricao){
}
