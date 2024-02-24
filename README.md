# API Reserva de Quartos

O sistema de Reserva de Quartos é uma aplicação projetada para facilitar o gerenciamento de um hotel, permitindo o controle eficiente de clientes, quartos e reservas. Com essa aplicação, os hotéis podem manter registros organizados e otimizar suas operações.

---

### Principais Funcionalidades

- Gerenciamento de Clientes: Cadastar informações dos clientes incluindo nome, endereço e contato.

- Gerenciamento de Quartos: Registrar e administrar os quartos disponíveis no hotel, suas características e tarifas. 

- Realização de Reservas: Permite que o usuário realize a reservas de quartos, fornecendo datas de check-in e check-out.


---

### Tecnologias utilizadas:

- Java
- Maven
- Spring Boot
- Lombok

---

## Documentação da API

Todos os caminhos começam no localhost porta 8080


| Métodos HTTP   | Caminho       | Descrição do uso                           |
| :---------- | :--------- | :---------------------------------- |
| GET | /cliente | Retorna a lista com todos os clientes cadastrados |
| GET | /cliente/{cpf} | Retorna o cliente com o cpf informado|
| GET | /quarto | Retorna a lista de todos os quartos cadastrados |
| GET | /quarto/{id} | Retorna o quarto com o id informado|
| GET | /reserva | Retorna a lista de todas as reservas realizadas|
| GET | /reservas/{id} | Retorna uma reserva com um id específico |
| POST | /cliente | Adiciona um cliente |
| POST | /quarto | Adiciona um quarto |
| POST | /reserva | Adiciona uma reserva |
| PATCH | /cliente/{id} | Altera as informações de um cliente por id |
| PATCH | /quarto/{id} | Altera as informações de um quarto por id |
| PATCH | /reservas/{id} | Altera as informações de uma reserva por id |
| PUT | /reservas/{id} | Altera todas a informações de uma reserva por id |


#### Filtros

A utilização dos filtros é realizada através dos params

A consulta de quarto pode ser feita por tipo de quarto, capacidade e preço.

Exemplo de consulta por preço

```
http://localhost:8080/quarto?precoPorNoite=230 
```

## Autores

- [@fonseca-bianca](https://github.com/fonseca-bianca)
- [@gabiwvisani](https://github.com/gabiwvisani)
- [@isabelkodama](https://github.com/isabelkodama)
- [@vivianpanizzi](https://github.com/vivianpanizzi)
