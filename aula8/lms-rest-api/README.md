# LMS Simples (Sistema de Gerenciamento de Aprendizagem)

Este é um projeto de demonstração construído com Spring Boot, Spring Data JPA e Spring Data REST para criar um sistema de gerenciamento de aprendizagem básico. Ele permite o gerenciamento de Usuários (Administradores, Professores e Alunos) e Cursos.

## 🚀 Tecnologias

- **Java 21+**
- **Spring Boot 3.x**
- **Maven**
- **Spring Data JPA:** Para persistência de dados.
- **Spring Data REST:** Para exposição automática das entidades como APIs RESTful.
- **H2 Database (Padrão):** Banco de dados em memória para desenvolvimento e testes rápidos.
- **MySQL Connector/J:** Driver para conexão com o MySQL 8+ (configuração opcional).

## ⚙️ Configuração e Execução

### Pré-requisitos

Certifique-se de ter o seguinte instalado:

- JDK 21 ou superior
- Maven 3.x

### Configuração do Banco de Dados (MySQL com Docker)

Este projeto está configurado para usar MySQL como banco de dados. A maneira mais fácil de iniciar um ambiente local é usando Docker.

**1. Inicie o Container MySQL**

Execute o comando abaixo no seu terminal. Ele irá baixar a imagem do MySQL 8, iniciar um container chamado `lms-mysql`, e já criar o banco de dados `lms` com as credenciais corretas definidas no `application.properties`.

```bash
docker run --name lms-mysql -e MYSQL_ROOT_PASSWORD=admin123 -e MYSQL_DATABASE=lms -p 3306:3306 -d mysql:8
```

**Para verificar se o container está rodando:**
```bash
docker ps
```

**2. Verifique a Configuração**

O arquivo `src/main/resources/application.properties` já deve estar configurado para se conectar a esta instância do Docker. As linhas relevantes são:

```properties
# --- Configuração MySQL ---
 spring.datasource.url=jdbc:mysql://localhost:3306/lms?serverTimezone=UTC
 spring.datasource.username=root
 spring.datasource.password=admin123
 spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
 spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
 spring.jpa.hibernate.ddl-auto=update
```

### Executando a Aplicação

Com o banco de dados rodando no Docker, você pode compilar e executar a aplicação.

**Compile o Projeto:**

```bash
mvn clean install
```

**Execute a Aplicação:**

```bash
mvn spring-boot:run
```

A aplicação será iniciada na porta `8080` e se conectará automaticamente ao banco de dados MySQL no Docker.

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