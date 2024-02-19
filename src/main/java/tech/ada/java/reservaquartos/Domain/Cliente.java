package tech.ada.java.reservaquartos.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identificadorCliente;
    private String nomeCompleto;
    private String cpf ;
    private String cep ;
    private String endereco;
    private String telefone;
    private String email;

    public Cliente(String nomeCompleto, String cpf, String cep, String endereco, String telefone, String email) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.cep = cep;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }



}
