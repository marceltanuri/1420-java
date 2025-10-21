# Criando Aplicações Web Robustas com Spring Boot

## Introdução
Nesta aula, vamos explorar como construir uma aplicação web robusta usando Spring Boot, focando em APIs REST e no padrão MVC. Vamos criar um projeto simples que exibe uma lista de produtos por meio de um endpoint REST.

## Spring e Spring Boot

> Leia mais: [Site Spring](https://spring.io/)

### Spring Framework

O **Spring Framework** é um dos frameworks mais populares para o desenvolvimento de aplicações Java. É conhecido por sua flexibilidade, modularidade e extensa coleção de funcionalidades que facilitam a implementação de soluções complexas, como injeção de dependência, gerenciamento de transações, segurança e envio de mensagens. 

Essa estrutura oferece uma base sólida para criação de aplicações em qualquer camada de desenvolvimento, incluindo aplicações web, sistemas de backend e microsserviços.

### Spring Boot

O [**Spring Boot**](https://spring.io/projects/spring-boot), por sua vez, é uma extensão do Spring Framework que visa simplificar ao máximo o processo de desenvolvimento. Ele reduz as configurações e _boilerplate_, permitindo que os desenvolvedores inicializem rapidamente suas aplicações Spring. As principais características do Spring Boot incluem:

- **Configuração automática:** detecta automaticamente as configurações necessárias com base nas dependências incluídas no projeto.
- **Servidor embutido:** possui suporte a servidores como [Tomcat](https://tomcat.apache.org/) e [Jetty](https://jetty.org/index.html) embutidos, eliminando a necessidade de configuração manual.
- **Gestão simples de dependências:** integração facilitada com o Maven ou Gradle, com um modelo de dependências preparado para uso.
- **Alta produtividade:** foco em inicializar aplicativos funcionais com código mínimo.
- **Execução simples:** você pode criar aplicações executáveis com um único comando (`java -jar`).

Por todas essas razões, o Spring Boot revolucionou o desenvolvimento Java, sendo amplamente usado para criar APIs RESTful, microsserviços e muito
mais.

## Configuração Inicial do Projeto

### Criando um projeto Spring Boot - Site Spring

1. Acesse [Spring Initializr](https://start.spring.io/)
2. Preencha os campos principais:
    - **Project:** Maven
    - **Language:** Java
    - **Spring Boot:** Última versão estável
    - **Dependencies:** Spring Web
3. Clique em **Generate** e extraia o arquivo ZIP.

#### Configurando o projeto no IntelliJ IDEA

1. Importe o projeto Maven.
2. Certifique-se de que o SDK Java está configurado corretamente.

### Criando um projeto Spring Boot - IntelliJ IDEA Ultimate

Você também pode criar seu projeto Spring Boot direto no IntelliJ IDEA se você tem a versão Ultimate. Siga as orientações do tutorial e lembre de selecionar corretamente as dependências como no Spring Initializer.

[Tutorial da primeira aplicação Spring Boot - Site Jetbrains](https://www.jetbrains.com/help/idea/your-first-spring-application.html#create-new-spring-boot-project)

<img src="https://resources.jetbrains.com/help/img/idea/2024.3/spring-new-project-initializr_dark.png" width="600" alt="Tela de criação de projetos Spring no IntelliJ" />

## Criando Controladores REST

### Controlador simples
```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @GetMapping
    public String listarProdutos() {
        return "Lista de produtos";
    }
}
```

Vamos analisar as anotações que foram usadas nesse controlador.

* **@RestController**: indica que a classe é um controlador REST, combinando @Controller e @ResponseBody. Isso significa que o retorno dos métodos será enviado como resposta HTTP, geralmente em formato JSON.
* **@RequestMapping**: define a URL base para todos os endpoints desta classe. Qualquer método dentro da classe responderá às URLs que começam com `/api/produtos`.
* **@GetMapping**: especifica que o método associado responde a requisições HTTP GET. No exemplo, o método `listarProdutos()` será acessível por uma chamada GET para a URL definida na anotação @RequestMapping.

### Executando a aplicação

1. Execute a classe principal gerada `Application.java`.
2. Acesse `http://localhost:8080/api/produtos` no navegador.
3. Resultado esperado: uma resposta de texto simples "Lista de produtos" exibida no navegador.

## Aplicando o Padrão MVC

O padrão MVC (Model-View-Controller) é uma arquitetura de software que separa uma aplicação em três componentes principais:

* **Model (Modelo)**: representa os dados e a lógica de negócios. Ele gerencia o estado e as regras de negócio da aplicação.
* **View (Visão)**: define a interface com o usuário, exibindo as informações e interagindo com ele.
* **Controller (Controlador)**: atua como intermediário entre o modelo e a visão. Ele processa as solicitações dos usuários, atualiza o modelo e determina qual visão deve ser apresentada.

Essa separação torna o código mais organizado, facilitando a manutenção, a escalabilidade e a testabilidade da aplicação.

### Criando a classe modelo

Observe a seguir que a classe modelo é um POJO (Plain Old Java Object), ou seja, uma simples classe java que define os atributos, `getters`, `setters`, `toString`, `equals`, `hashCode` e os construtores padrão e canônico. Veja como tudo fica simples usando Lombok.

```java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto {
    private String nome;
    private double preco;
}
```

### Criando o serviço

A anotação `@Service` indica que a classe é um componente (bean) de serviço do Spring. Essa anotação permite que o Spring gerencie automaticamente sua instância e a injete em outras classes, promovendo o uso de boas práticas de injeção de dependência e facilitando a separação de responsabilidades.

```java
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class ProdutoService {

    public List<Produto> listarProdutos() {
        return Arrays.asList(
                new Produto("Notebook", 2500.0),
                new Produto("Smartphone", 1500.0),
                new Produto("Monitor", 800.0)
        );
    }
}
```

### Atualizando o controlador

Inicialmente, o controlador devolvia uma resposta de texto simples. Após a criação do modelo `Produto` e do serviço `ProdutoService`, é necessário atualizar o controlador para que ele retorne dados armazenados na lista em memória.

Com a atualização, o controlador agora usa o serviço para buscar todos os produtos armazenados na lista em memória e retornar em formato JSON automaticamente.

```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }
}
```

### Injeção da classe de serviço no controlador

Neste código, a classe de serviço (`ProdutoService`) é injetada no controlador (`ProdutoController`) através de um construtor. Essa abordagem,
conhecida como **injeção de dependência baseada em construtor**, é uma prática recomendada por várias razões:

* **Imutabilidade**: com essa abordagem, o objeto criado se torna imutável, já que todas as dependências necessárias são fornecidas no momento da
  construção do objeto. Isso reduz a possibilidade de erros relacionados a alterações inesperadas nas dependências.
* **Testabilidade**: a injeção de dependências via construtor facilita a criação de testes unitários, permitindo que dependências simuladas (mocks)
  sejam injetadas facilmente durante os testes.
* **Clareza**: a necessidade de dependências é explícita no construtor, deixando claro quais objetos o controlador precisa para funcionar
  corretamente.
* **Evitar dependências circulares**: como as dependências são verificadas no momento da criação do objeto, é mais fácil identificar problemas como
  dependências circulares no código.

#### Por que evitar o uso de `@Autowired` diretamente nos campos (Field Injection)?

A prática de usar `@Autowired` diretamente nos campos, conhecida como **field injection**, é desencorajada principalmente pelos seguintes motivos:

* **Dificulta testes**: com field injection, não é possível criar instâncias do controlador com dependências simuladas (mocks) facilmente nos testes
  unitários, a menos que sejam utilizadas ferramentas de reflexão, o que aumenta a complexidade.
* **Fragilidade**: a injeção diretamente nos campos viola o princípio de imutabilidade, já que as dependências podem ser alteradas após a criação do
  objeto.
* **Problemas com frameworks**: alguns frameworks de injeção de dependência podem não funcionar bem com field injection, especialmente em caso de
  classes proxy ou cenários avançados.
  
Portanto, a injeção por meio de construtor ou, em casos menos comuns, de métodos `setter` é preferida em projetos Java, garantindo um código mais
  sustentável e alinhado às boas práticas de desenvolvimento.

### Completando os demais verbos HTTP

Agora que conectamos o controlador ao serviço, estamos prontos para adicionar ao controlador os demais verbos HTTP de maneira que a aplicação consiga fazer um CRUD completo para o modelo `Produto`.

```java
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    @PostMapping
    public Produto adicionarProduto(@RequestBody Produto produto) {
        return produtoService.adicionarProduto(produto);
    }

    @PutMapping("/{nome}")
    public Produto atualizarProduto(@PathVariable String nome, @RequestBody Produto produtoAtualizado) {
        return produtoService.atualizarProduto(nome, produtoAtualizado);
    }

    @DeleteMapping("/{nome}")
    public String deletarProduto(@PathVariable String nome) {
        return produtoService.deletarProduto(nome);
    }
}
```

Além das anotações Spring associadas a cada verbo HTTP, você pode observar algumas outras novas anotações. 

* `@RequestBody`: liga automaticamente o corpo da requisição HTTP a um objeto Java.
* `@PathVariable`: extrai variáveis da URL e as atribui a parâmetros do método.
* `@RequestParam`: lê parâmetros de consulta na URL, úteis para filtros e buscas simples.

## Pesquisa Individual

- Como o Spring Boot facilita a configuração e o gerenciamento de dependências?
- Qual a diferença entre `@Controller` e `@RestController` no Spring?

## Desafio

- Organize o projeto seguindo o padrão MVC, separando controlador, serviço e modelo.
- Faça todas as modificações necessárias no serviço para atender as mudanças feitas no controlador.

## Conclusão
Nesta aula, aprendemos a criar uma aplicação web simples usando Spring Boot e APIs REST. Na próxima aula, exploraremos como adicionar funcionalidades mais avançadas, como autenticação e manipulação de dados persistentes.
