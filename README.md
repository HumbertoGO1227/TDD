# Gerenciador de Versículos

Este é um projeto Spring Boot que permite criar, listar, buscar por data, atualizar e deletar versículos bíblicos.

## Tecnologias
- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- H2 Database (memória)
- Maven
- JUnit + Mockito
- Cucumber (Testes BDD)
- Jackson (suporte a datas `LocalDate`)
- RESTful API

## Endpoints

| Método | Endpoint                  | Descrição                               |
|--------|---------------------------|-----------------------------------------|
| POST   | `/versiculos`             | Cria um novo versículo                  |
| GET    | `/versiculos`             | Lista todos os versículos               |
| GET    | `/versiculos/data?data=...` | Busca versículos por data (yyyy-MM-dd)  |
| PUT    | `/versiculos/{id}`        | Atualiza um versículo existente         |
| DELETE | `/versiculos/{id}`        | Remove um versículo                     |

## Como rodar localmente

```bash
git clone https://github.com/seu-usuario/versiculos.git
cd versiculos
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## Executar Testes

```bash
mvn test
```

Inclui testes:
- Unitários (`VersiculoServiceTest`)
- Integração REST (`VersiculoControllerTest`)
- BDD (`CucumberTest` + `VersiculosSteps.feature`)

## Banco de Dados

- Utiliza H2 em memória para testes e desenvolvimento
- Console acessível via: `http://localhost:8080/h2-console`
