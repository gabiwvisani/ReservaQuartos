package tech.ada.java.reservaquartos.Domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteTest {

    Cliente cliente1;
    Cliente cliente2;
    Cliente cliente3;

    @BeforeEach
    public void setup() {

        cliente1 = new Cliente(
                "Maria Silva",
                "12545458954",
                "27150-574",
                "Rua Prefeito Alguma Coisa",
                "(52) 99885-4612",
                "mariasilva@gmail.com");
        cliente2 = new Cliente(
                "Maria Silva",
                "12545458954",
                "27150-574",
                "Rua Prefeito Alguma Coisa",
                "(52) 99885-4612",
                "mariasilva@gmail.com");

        cliente3 = new Cliente(
                "João Souza",
                "98765432100",
                "27150-123",
                "Rua dos Testes",
                "(52) 12345-6789",
                "joaosouza@gmail.com");
    }
    @Test
    public void testConstructor() {
        assertNotNull(cliente1);
        assertEquals("Maria Silva", cliente1.getNomeCompleto());
        assertEquals("12545458954", cliente1.getCpf());
        assertEquals("27150-574", cliente1.getCep());
        assertEquals("Rua Prefeito Alguma Coisa", cliente1.getEndereco());
        assertEquals("(52) 99885-4612", cliente1.getTelefone());
        assertEquals("mariasilva@gmail.com", cliente1.getEmail());
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
