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

## Testes Unitários

Este projeto inclui testes unitários para garantir a qualidade e a confiabilidade do código.
- Para executar todos os testes unitários e de integração no projeto utilize o Maven com o comando abaixo:

```bash
mvn test
```