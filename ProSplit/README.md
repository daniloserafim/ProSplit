# ProSplit - Backend

ProSplit é uma aplicação desenvolvida para facilitar a divisão de contas de almoços ou lanches entre amigos de equipe de trabalho. A aplicação resolve um problema comum ao lidar com descontos, acréscimos e entrega proporcional aos itens solicitados por cada pessoa.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Maven 4**
- **JUnit 5** para testes unitários
- **Mockito** para mock de objetos nos testes

## Configuração do Ambiente de Desenvolvimento

1. Certifique-se de ter o Java 17 instalado e configurado em sua máquina assim como as demais tecnologias citadas acima.
2. Clone este repositório: `git clone https://github.com/daniloserafim/ProSplit.git`
3. Navegue até o diretório do backend: `cd ProSplit`
4. Execute o comando Maven para compilar o projeto: `mvn clean install`
5. Execute o backend: `mvn spring-boot:run`

## Testes

Este projeto inclui testes unitários e de integração para garantir a qualidade e a confiabilidade do código. Abaixo estão os principais testes realizados:

### Testes Unitários

Os testes unitários garantem que cada unidade individual de código (como métodos e classes) funcione conforme o esperado. Eles são executados isoladamente para verificar se cada parte do código funciona corretamente. Aqui estão alguns exemplos de testes unitários realizados:

- **shouldCalculateCorrectlyWhenBillHasNormalValues**: Verifica se o cálculo da conta é realizado corretamente quando os valores da conta são como o esperado.
- **shouldThrowExceptionWhenBillHasNoFriendBills**: Garante que uma exceção seja lançada quando a service lida com uma conta que não possui os dados necessários.
- **shouldThrowExceptionWhenAnIncomingExpenseIsNegative**: Garante que uma exceção seja lançada quando a service lida com uma conta que possui dados inválidos.

### Testes de Integração

Os testes de integração verificam se diferentes partes do sistema funcionam corretamente juntas. Eles testam a integração entre componentes e serviços. Aqui estão alguns exemplos de testes de integração realizados:

- **shouldCalculateBillSuccessfully**: Verifica se o cálculo da conta é realizado com sucesso quando uma solicitação válida é enviada para o endpoint.
- **shouldReturnBadRequestWhenValidationFails**: Garante que uma resposta de erro seja retornada quando a validação falha durante o processamento da solicitação.
- **shouldReturnInternalServerErrorOnException**: Verifica se uma resposta de erro é retornada quando ocorre uma exceção inesperada durante o processamento da solicitação.

### Executando os Testes

Para executar todos os testes unitários e de integração no projeto utilize o Maven com o comando abaixo:

```bash
mvn test
```