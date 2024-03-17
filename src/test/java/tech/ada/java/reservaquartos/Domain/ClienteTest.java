package tech.ada.java.reservaquartos.Domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.ada.java.reservaquartos.Request.ClienteRequest;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.any;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClienteTest {

    Cliente cliente1;
    Cliente cliente2;
    Cliente cliente3;
    ClienteRequest clienteRequestEmailIncorreto;
    ClienteRequest clienteRequestVazio;
    ClienteRequest clienteRequestCepIncorreto;
    ClienteRequest clienteRequestTelefoneIncorreto;

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

        clienteRequestEmailIncorreto = new ClienteRequest();
        clienteRequestEmailIncorreto.setNomeCompleto("Maria Silva");
        clienteRequestEmailIncorreto.setCpf("12545458954");
        clienteRequestEmailIncorreto.setCep("27150-574");
        clienteRequestEmailIncorreto.setEndereco("Rua Prefeito Alguma Coisa");
        clienteRequestEmailIncorreto.setTelefone("(52) 99885-4612");
        clienteRequestEmailIncorreto.setEmail("mariasilvagmail.com");

        clienteRequestVazio = new ClienteRequest();

        clienteRequestCepIncorreto = new ClienteRequest();
        clienteRequestCepIncorreto.setNomeCompleto("Maria Silva");
        clienteRequestCepIncorreto.setCpf("12545458954");
        clienteRequestCepIncorreto.setCep("27150");
        clienteRequestCepIncorreto.setEndereco("Rua Prefeito Alguma Coisa");
        clienteRequestCepIncorreto.setTelefone("(52) 99885-4612");
        clienteRequestCepIncorreto.setEmail("mariasilva@gmail.com");

        clienteRequestTelefoneIncorreto = new ClienteRequest();
        clienteRequestTelefoneIncorreto.setNomeCompleto("Maria Silva");
        clienteRequestTelefoneIncorreto.setCpf("12545458954");
        clienteRequestTelefoneIncorreto.setCep("27150-574");
        clienteRequestTelefoneIncorreto.setEndereco("Rua Prefeito Alguma Coisa");
        clienteRequestTelefoneIncorreto.setTelefone("99885-4612");
        clienteRequestTelefoneIncorreto.setEmail("mariasilva@gmail.com");

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
    @Test
    public void testValidarCPFNull() {
        assertEquals("O CPF deve conter exatamente 11 dígitos numéricos.", Cliente.validarCPF(null));
    }
    @Test
    public void testEquals() {
        assertEquals(cliente1, cliente2);
        assertNotEquals(cliente1, cliente3);
    }

    @Test
    public void testHashCode() {
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
        assertNotEquals(cliente1.hashCode(), cliente3.hashCode());
    }

    @Test
    public void validarAnotacoesCliente() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente1);
        assertEquals(0, violations.size());
    }

    @Test
    public void validarClienteRequestEmailIncorreto() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(clienteRequestEmailIncorreto);
        assertEquals(1, violations.size());
    }

    @Test
    public void validarClienteRequestVazio() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(clienteRequestVazio);
        assertEquals(6, violations.size());
    }
    @Test
    public void validarClienteRequestCepIncorreto() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(clienteRequestCepIncorreto);
        assertEquals(1, violations.size());
    }

    @Test
    public void validarClienteRequestTelefoneIncorreto() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ClienteRequest>> violations = validator.validate(clienteRequestTelefoneIncorreto);
        assertEquals(1, violations.size());
    }

}
