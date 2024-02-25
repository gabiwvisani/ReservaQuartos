package tech.ada.java.reservaquartos.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@Getter
@Setter
@Validated
public class ClienteRequest {
 @NotBlank(message = "O nome é obrigatório.")
 private String nomeCompleto;
 @NotBlank(message = "O CPF é obrigatório.")
 private String cpf;
 @NotBlank(message = "O CEP é obrigatório.")
 @Pattern(regexp = "\\d{5}-\\d{3}", message = "Formato de CEP inválido. Utilize o formato: XXXXX-XXX.")
 private String cep;
 @NotBlank(message = "O endereço é obrigatório.")
 private String endereco;
 @NotBlank(message = "O telefone é obrigatório.")
 @Pattern(regexp = "\\(\\d{2}\\)\\s\\d{5}-\\d{4}", message = "Formato de telefone inválido. Utilize o formato: (XX) XXXXX-XXXX")
 private String telefone;
 @NotBlank(message = "O e-mail é obrigatório.")
 @Email(message = "E-mail inválido. Utilize um formato de e-mail válido.")
 private String email;

}
