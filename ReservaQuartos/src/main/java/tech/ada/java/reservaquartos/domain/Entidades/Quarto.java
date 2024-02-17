package tech.ada.java.reservaquartos.domain.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identificadorQuarto;
    private Integer numeroQuarto;
    private Integer capacidadeMaximaDePessoas;
    private BigDecimal precoPorNoite;
    private String descricao;
    private enum tipoQuarto {STANDARD, SUPERIOR, DELUXE}

}
