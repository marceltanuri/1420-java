# Projeto de Demonstração Spring Boot

Este é um projeto de demonstração simples usando Spring Boot.

## Descrição

O projeto consiste em uma aplicação web básica com um único endpoint que retorna uma saudação.

## Endpoints

### GET /hello

Este endpoint recebe um parâmetro `name` e retorna uma saudação personalizada.

**Exemplo de requisição:**

```bash
curl "http://localhost:8080/hello?name=Mundo"
```

**Exemplo de resposta:**

```json
{
  "content": "Hello, Mundo!"
}
```

## Como executar

Para executar o projeto, você pode usar o Maven wrapper incluído.

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.