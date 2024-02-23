package tech.ada.java.reservaquartos.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {
 private String nomeCompleto;
 private String cpf;
 private String cep;
 private String endereco;
 private String telefone;
 private String email;

}
