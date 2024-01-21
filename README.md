# Transações bancárias

## Sobre o projeto

Este projeto foi desenvolvido com intuito de estudos, para aprimorar conhecimentos em Java e Quarkus e também em testes 
unitarios e integração utilizando JUnit e Mockito.

## Tecnologias utilizadas:
- Java 17
- Framework Quarkus
- JUnit e Mockito
- Docker
- Maven
- Banco de dados H2 - Em memória

## Como executar o projeto

Pré-requisitos: Java 17, Maven e Docker caso queira rodar em container



### Rodando a aplicação

### Em modo DEV:
```bash
Somente rodar o comando:

./mvnw compile quarkus:dev
```
### Em container docker:
```bash
# Clone este repositório
git clone git@github.com:ViniciusG1403/transactions.git

# Acesse a pasta do projeto no terminal/cmd e rode o seguinte comando estando na pasta raiz do projeto
 ./mvnw install -Dquarkus.container-image.build=true 

Após

docker run -p 8080:8080 nexuscloud/bank-transactions:1.0.0-SNAPSHOT
```

