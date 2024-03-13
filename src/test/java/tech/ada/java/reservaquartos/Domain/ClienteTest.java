package tech.ada.java.reservaquartos.Domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteTest {

    Cliente cliente1;

    @BeforeEach
    public void setup() {

        cliente1 = new Cliente(
                "Maria Silva",
                "12545458954",
                "27150-574",
                "Rua Prefeito Alguma Coisa",
                "(52) 99885-4612",
                "mariasilva@gmail.com");
    }

    @Test
    public void validarCPFTest(){
        assertEquals(Cliente.validarCPF(cliente1.getCpf()), null);
    }

    @Test
    public void validarCPFTest2(){
        assertEquals(Cliente.validarCPF("152125"), "O CPF deve conter exatamente 11 dígitos numéricos.");
    }
}
