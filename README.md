# API Reserva de Quartos

O sistema de Reserva de Quartos é uma aplicação projetada para facilitar o gerenciamento de um hotel, permitindo o controle eficiente de clientes, quartos e reservas. Com essa aplicação, os hotéis podem manter registros organizados e otimizar suas operações.

---

## Funcionalidades

### Gerenciamento de Clientes

O módulo de gerenciamento de clientes permite realizar as seguintes operações:

Cadastro de Clientes: Permite o cadastro de novos clientes com informações detalhadas, incluindo nome completo, CPF, endereço, telefone e email.

Busca de Clientes: Possibilita a busca de clientes pelo número de CPF.

Atualização de Clientes: Permite a atualização das informações dos clientes, incluindo nome, CPF, endereço, telefone e email.

Exclusão de Clientes: Permite a exclusão de clientes do sistema, juntamente com todas as suas reservas associadas.

### Gerenciamento de Quartos
O módulo de gerenciamento de quartos oferece as seguintes funcionalidades:

Cadastro de Quartos: Permite o cadastro de novos quartos com detalhes sobre capacidade máxima de pessoas, preço por noite, tipo de quarto e uma descrição.

Busca de Quartos: Permite a busca de quartos pelo seu identificador único (ID) ou por meio de filtros, como tipo de quarto, capacidade máxima de pessoas e preço por noite. Também é possível buscar por todos os quartos disponíveis.

Atualização de Quartos: Possibilita a atualização das informações dos quartos, incluindo número, capacidade, preço, descrição e tipo.

Exclusão de Quartos: Permite a exclusão de quartos do sistema, juntamente com todas as suas reservas associadas.

### Gerenciamento de Reservas
O módulo de realização de reservas oferece as seguintes funcionalidades:

Cadastro de Reservas: Permite o agendamento de novas reservas, associando clientes aos quartos disponíveis em determinadas datas.

Busca de Reservas: Permite a busca de reservas pelo seu identificador único (ID).

Atualização de Reservas: Possibilita a atualização das informações das reservas, incluindo datas de entrada e saída, número de hóspedes, status de confirmação e forma de pagamento.

Exclusão de Reservas: Permite a exclusão de reservas do sistema.

---

## Tecnologias utilizadas:

- Java
- Maven
- Spring Boot
- Lombok

---
## Entidades:

### Cliente
idCliente: Identificador único do cliente.
nomeCompleto: Nome completo do cliente.
cpf: Número do CPF do cliente.
endereco: Endereço do cliente.
telefone: Número de telefone do cliente.
email: Endereço de e-mail do cliente.
### Quarto
idQuarto: Identificador único do quarto.
numeroQuarto: Número que identifica o quarto.
capacidadeMaximaDePessoas: Quantidade máxima de pessoas permitidas no quarto.
precoPorNoite: Preço por noite para reservar o quarto.
descricao: Descrição do quarto.
tipoQuarto: Tipo do quarto (ex: suíte, standard, etc.).
### Reserva
idReserva: Identificador único da reserva.
cliente: Cliente associado à reserva.
quarto: Quarto reservado.
dataEntrada: Data de entrada na reserva.
dataSaida: Data de saída da reserva.
numeroHospedes: Número de hóspedes na reserva.
valorTotalReserva: Valor total a ser pago pela reserva.
statusConfirmada: Indica se a reserva foi confirmada.
formaPagamento: Método de pagamento da reserva.
dataRealizacaoReserva: Data em que a reserva foi realizada.
dataAtualizacaoReserva: Data em que a reserva foi atualizada.

---

## Documentação da API

Todos os caminhos começam no localhost porta 8080

| Métodos HTTP | Caminho        | Descrição do uso                           |
| :----------- | :------------- | :---------------------------------------- |
| POST         | /cliente       | Cadastra um novo cliente.                 |
| GET          | /cliente       | Retorna todos os clientes cadastrados.     |
| GET          | /cliente/{cpf} | Retorna o cliente com o CPF especificado. |
| PATCH        | /cliente/{id}  | Altera as informações de um cliente pelo ID. |
| DELETE       | /cliente/{id}  | Deleta um cliente pelo ID e todas as suas reservas associadas. |

| Métodos HTTP | Caminho        | Descrição do uso                                                                                                        |
| :----------- | :------------- |:------------------------------------------------------------------------------------------------------------------------|
| POST         | /quarto        | Cadastra um novo quarto.                                                                                                |
| GET          | /quarto        | Retorna todos os quartos cadastrados.                                                                                   |
| GET          | /quarto/{id}   | Retorna o quarto com o ID especificado.                                                                                 |
| GET          | /quarto        | (com filtros): Retorna os quartos filtrados por tipo de quarto, capacidade máxima de pessoas ou preço por noite.        |
| GET          | /disponiveis   | Retorna os quartos disponíveis para reserva com base em datas de entrada e saída. Formato para passar datas yyyy-MM-dd. |
| PATCH        | /quarto/{id}   | Altera o valor do quarto pelo ID.                                                                                       |
| PUT          | /quarto/{id}   | Atualiza as informações de um quarto pelo ID.                                                                           |
| DELETE       | /quarto/{id}   | Deleta um quarto pelo ID e todas as reservas associadas.                                                                |

| Métodos HTTP | Caminho        | Descrição do uso                           |
| :----------- | :------------- | :---------------------------------------- |
| POST         | /reserva       | Cadastra uma nova reserva.                |
| GET          | /reserva       | Retorna todas as reservas cadastradas.    |
| GET          | /reserva/{id}  | Retorna a reserva com o ID especificado.  |
| PATCH        | /reserva/{id}  | Altera as informações de uma reserva pelo ID. |
| DELETE       | /reserva/{id}  | Deleta uma reserva pelo ID.               |


#### Filtros

A utilização dos filtros é realizada através dos params

A consulta de quarto pode ser feita por tipo de quarto, capacidade e preço.

Exemplo de consulta por preço

```
http://localhost:8080/quarto?precoPorNoite=230 
```
## Regras de Negócio
O sistema de reserva de quartos adota as seguintes regras de negócio para garantir a integridade dos dados e o bom funcionamento das operações:

### Validação de CPF
Ao cadastrar um novo cliente, o sistema verifica se o CPF fornecido é válido, garantindo que não haja duplicação de clientes com o mesmo CPF.
### Verificação de Duplicidade de CPF
Antes de cadastrar um novo cliente, o sistema verifica se já existe algum cliente cadastrado com o mesmo CPF, evitando duplicações.
### Conflitos de Reserva
Durante o processo de reserva de quartos, o sistema verifica se o quarto solicitado já está reservado para as datas especificadas. Em caso de conflito, a reserva não é concluída, garantindo que cada quarto seja reservado apenas uma vez para um determinado período.
### Validade de Datas
Ao realizar uma reserva, o sistema valida se a data de entrada é igual ou posterior à data atual e se a data de saída é posterior à data de entrada. Essa validação evita reservas com datas inválidas ou inconsistentes.
### Exclusão de Quartos e Clientes
Ao excluir um quarto ou um cliente do sistema, todas as reservas associadas a eles também são removidas, garantindo a integridade dos dados e evitando inconsistências no sistema.
### Atualização de Reservas
Ao alterar uma reserva, o sistema verifica se há conflitos com outras reservas para o mesmo quarto nas novas datas especificadas. Além disso, valida se as datas fornecidas são consistentes e dentro dos limites permitidos.

---
## Segurança

O sistema também conta com a dependência spring-boot-starter-security, que foi configurada com as credenciais de login usuarioquarto e senha 12345. No entanto, não é necessário inserir essas credenciais para autenticação básica ao acessar a rota de get: http://localhost:8080/quarto. Isso porque essa rota pode ser posteriormente utilizada em um site, permitindo que o público geral do hotel verifique os quartos disponíveis sem a necessidade de autenticação.

---
## Autores

- [@fonseca-bianca](https://github.com/fonseca-bianca)
- [@gabiwvisani](https://github.com/gabiwvisani)
- [@irinaiwata](https://github.com/irinaiwata)
- [@isabelkodama](https://github.com/isabelkodama)
- [@vivianpanizzi](https://github.com/vivianpanizzi)
