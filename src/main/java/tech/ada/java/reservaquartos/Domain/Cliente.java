package tech.ada.java.reservaquartos.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer identificadorCliente;
    @NotBlank
    private String nomeCompleto;
    @NotBlank
    private String cpf ;
    @NotBlank
    private String cep ;
    @NotBlank
    private String endereco;
    @NotBlank
    private String telefone;
    @NotBlank
    @Email
    private String email;

    public Cliente(String nomeCompleto, String cpf, String cep, String endereco, String telefone, String email) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.cep = cep;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public static String validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.chars().allMatch(Character::isDigit)) {
            return "O CPF deve conter exatamente 11 dígitos numéricos.";
        }
        return null;
    }

}