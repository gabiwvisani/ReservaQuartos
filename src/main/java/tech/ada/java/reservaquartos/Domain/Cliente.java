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
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos.")
    private String cpf ;
    @NotBlank
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato XXXXX-XXX")
    private String cep ;
    @NotBlank
    private String endereco;
    @NotBlank
    @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}-\\d{4}", message = "O telefone deve estar no formato (XX) XXXXX-XXXX")
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



}
