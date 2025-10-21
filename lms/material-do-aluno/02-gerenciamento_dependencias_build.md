# Como gerenciar dependências e manipular dados em Java

## Introdução
Nesta aula, aprenderemos a gerenciar dependências e simplificar a manipulação de dados em projetos Java usando ferramentas e bibliotecas como Maven, Jackson e Lombok. Esses recursos são fundamentais para criar aplicações mais organizadas, escaláveis e fáceis de manter.

## Gerenciamento de Dependências com Maven
Maven é uma ferramenta de gerenciamento de projetos e automação de builds. Ela facilita a inclusão de bibliotecas externas e o controle de versões.

### Configuração inicial
1. Instale o Maven e configure suas variáveis de ambiente.
2. Crie um arquivo `pom.xml` na raiz do projeto.

> **Links úteis**
- [Download](https://maven.apache.org/download.cgi)
- [Instalação](https://maven.apache.org/install.html)
- [Guia para Windows](https://www.geeksforgeeks.org/how-to-install-apache-maven-on-windows/)

### Criando projetos Maven no IntelliJ

[Tutorial projetos Maven no IntelliJ - Site Jetbrains](https://www.jetbrains.com/guide/java/tutorials/working-with-maven/introduction/)

<img src="https://www.jetbrains.com/guide/assets/new-maven-project-7c0d5e35.png" width="600" alt="Tela de criação de projetos no IntelliJ"/>

### Estrutura básica do `pom.xml`
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.exemplo</groupId>
    <artifactId>meu-projeto</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.12.3</version>
        </dependency>
    </dependencies>
</project>
```

### Comandos Maven úteis
- `mvn compile`: Compila o projeto.
- `mvn package`: Gera o arquivo `.jar`.
- `mvn clean`: Remove arquivos gerados.
- `mvn install`: Instala o projeto no repositório local.

## Manipulação de dados com Jackson
Jackson é uma biblioteca popular para converter objetos Java em JSON e vice-versa. A classe `ObjectMapper` é a responsável por fazer essa conversão de maneira simples, como no exemplo a seguir.

### Conversão de objeto para JSON
```java
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExemploJackson {
    public static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Produto produto = new Produto("Notebook", 2500.0);
            String json = mapper.writeValueAsString(produto);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### Conversão de JSON para objeto
```java
String json = "{\"nome\":\"Notebook\",\"preco\":2500.0}";
Produto produto = mapper.readValue(json, Produto.class);
```

### Anotações Jackson para manipulação de JSON

Jackson fornece diversas anotações para personalizar a maneira como os campos e classes são serializados e desserializados. Aqui estão algumas das
principais:

#### `@JsonProperty`

Define o nome que será usado no JSON para um campo específico. É útil quando o nome do campo na classe Java difere daquele usado no JSON.

```java
import com.fasterxml.jackson.annotation.JsonProperty;

public class Produto {

    @JsonProperty("nome_produto")
    private String nome;

    @JsonProperty("preco_produto")
    private double preco;

    // Construtor, getters e setters
}
```

#### `@JsonIgnore`

Ignora um campo durante a serialização e desserialização. Isso é útil para excluir informações sensíveis ou desnecessárias no JSON.

```java
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Produto {

    private String nome;
    private double preco;

    @JsonIgnore
    private String codigoInterno;

    // Construtor, getters e setters
}
```

#### `@JsonFormat`

Usado para especificar o formato de datas ou outros tipos de valores durante a serialização e desserialização. Por exemplo, formatar uma data para o padrão `dd/MM/yyyy`.

```java
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class Produto {

    private String nome;
    private double preco;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataLancamento;

    // Construtor, getters e setters
}
```

#### `@JsonCreator`

Permite configurar um construtor ou método estático para ser usado na desserialização de um objeto. É especialmente útil quando a classe não possui um construtor padrão.

```java
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Produto {

    private String nome;
    private double preco;

    @JsonCreator
    public Produto(@JsonProperty("nome") String nome, @JsonProperty("preco") double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // Getters e setters
}
```

Essas anotações tornam o Jackson ainda mais flexível, possibilitando personalizações detalhadas no comportamento de serialização e desserialização. Use-as para ajustar como os dados JSON são tratados em suas aplicações.

## Redução de Código com Lombok
Lombok é uma biblioteca que reduz a necessidade de escrever métodos repetitivos como `getters`, `setters` e `toString`.

> Leia mais: [Lombok](https://projectlombok.org/)

### Configuração do Lombok
Adicione a dependência ao `pom.xml`:
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.20</version>
    <scope>provided</scope>
</dependency>
```

> Leia mais: [anotação](https://projectlombok.org/features/Data) `@Data`

### Exemplo com Lombok
```java
import lombok.Data;

@Data
public class Produto {
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }
}
```

## Estudo de Caso
Desenvolvimento de uma API simples
- Crie uma aplicação que consome uma API pública usando Maven.
- Converta a resposta JSON em objetos Java usando Jackson.
- Use Lombok para definir classes de modelo com menos código.

## Pesquisa Individual
- Investigue como o Maven gerencia repositórios externos.
- Explore outras bibliotecas de manipulação de dados como Gson e Moshi.

## Desafio
Crie uma aplicação que:
- Consome uma API pública de produtos.
- Converte a resposta em objetos Java.
- Organiza os objetos em uma lista e exibe as informações formatadas.

**Exemplo:**
```java
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiProdutos {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://dummyjson.com/products");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder json = new StringBuilder();
            while (scanner.hasNext()) {
                json.append(scanner.nextLine());
            }
            scanner.close();

            ObjectMapper mapper = new ObjectMapper();
            Produto[] produtos = mapper.readValue(json.toString(), Produto[].class);
            for (Produto p : produtos) {
                System.out.println(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## Conclusão
Você aprendeu como gerenciar dependências e manipular dados em Java usando Maven, Jackson e Lombok. Essas ferramentas são essenciais para construir aplicações modernas e escaláveis. Na próxima aula, exploraremos como estruturar uma aplicação completa usando Spring Boot.
