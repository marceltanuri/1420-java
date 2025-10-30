# LMS Simples (Sistema de Gerenciamento de Aprendizagem)

Este é um projeto de demonstração construído com Spring Boot, Spring Data JPA e Spring Data REST para criar um sistema de gerenciamento de aprendizagem básico. Ele permite o gerenciamento de Usuários (Administradores, Professores e Alunos) e Cursos.

## 🚀 Tecnologias

- **Java 17+**
- **Spring Boot 3.x**
- **Maven**
- **Spring Data JPA:** Para persistência de dados.
- **Spring Data REST:** Para exposição automática das entidades como APIs RESTful.
- **H2 Database (Padrão):** Banco de dados em memória para desenvolvimento e testes rápidos.
- **MySQL Connector/J:** Driver para conexão com o MySQL 8+ (configuração opcional).

## ⚙️ Configuração e Execução

### Pré-requisitos

Certifique-se de ter o seguinte instalado:

- JDK 17 ou superior
- Maven 3.x

### Execução Padrão (Usando H2 em Memória)

Por padrão, a aplicação está configurada para usar o H2 Database. Isso significa que ela pode ser iniciada imediatamente sem a necessidade de um servidor de banco de dados externo.

**Compile o Projeto:**

```bash
mvn clean install
```

**Execute a Aplicação:**

```bash
mvn spring-boot:run
```

A aplicação será iniciada na porta `8080`.

### 🔑 Configuração para MySQL

Se você deseja usar o MySQL como banco de dados:

1. Crie o banco de dados (ex: `lmsdb`) no seu servidor MySQL.
2. Edite o arquivo `src/main/resources/application.properties`.
3. Comente as configurações do H2 e descomente e ajuste as propriedades do MySQL:

```properties
# Exemplo de configuração para MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/lmsdb?serverTimezone=UTC
spring.datasource.username=seu_usuario_mysql
spring.datasource.password=sua_senha_mysql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
```

## 🗺️ Modelo de Dados

### Entidades Principais

| Entidade | Descrição                                     | Relacionamentos                                                  |
|----------|-------------------------------------------------|------------------------------------------------------------------|
| `User`   | Usuários do sistema (Admin, Professor, Aluno).  | -                                                                |
| `Course` | Cursos oferecidos.                              | `ManyToOne` com `Teacher` (Professor), `ManyToMany` com `Students` (Alunos). |

### Enum: `Role`

Define o papel do usuário: `ADMIN`, `TEACHER`, `STUDENT`.

## 💻 Endpoints da API REST

Graças ao Spring Data REST, os seguintes endpoints são automaticamente criados. O prefixo da API é `/api`.

| Recurso | Método      | Path                | Descrição                       |
|---------|-------------|---------------------|---------------------------------|
| Users   | `GET`       | `/api/users`        | Lista todos os usuários.        |
|         | `GET`       | `/api/users/{id}`   | Recupera um usuário específico. |
|         | `POST`      | `/api/users`        | Cria um novo usuário (JSON body). |
|         | `PUT`/`PATCH` | `/api/users/{id}`   | Atualiza um usuário.            |
|         | `DELETE`    | `/api/users/{id}`   | Deleta um usuário.              |
| Courses | `GET`       | `/api/courses`      | Lista todos os cursos.          |
|         | `GET`       | `/api/courses/{id}` | Recupera um curso específico.   |
|         | `POST`      | `/api/courses`      | Cria um novo curso (JSON body). |
|         | `PUT`/`PATCH` | `/api/courses/{id}` | Atualiza um curso.              |
|         | `DELETE`    | `/api/courses/{id}` | Deleta um curso.                |

### Relacionamentos e Links HATEOAS

Os relacionamentos são expostos via links HATEOAS.

**Exemplo de Acesso aos Alunos de um Curso:**

```http
GET http://localhost:8080/api/courses/{courseId}/students
```

**Exemplo de Atribuição de Professor a um Curso (PUT/PATCH):**

O corpo da requisição deve conter o URI do professor:

```http
// PATCH http://localhost:8080/api/courses/1
{
  "teacher": "/api/users/2" // Supondo que o professor tenha ID 2
}
```