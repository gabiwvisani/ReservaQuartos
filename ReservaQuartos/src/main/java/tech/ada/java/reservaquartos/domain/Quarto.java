package tech.ada.java.reservaquartos.domain;

import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Quarto {
    private Integer identificadorQuarto;
    private Integer numeroQuarto;
    private enum tipoQuarto {STANDART, SUPERIOR, DELUXE};
    private Integer capacidadeMaximaDePessoas;
    private BigDecimal precoPorNoite;
    private String descricao;


}
