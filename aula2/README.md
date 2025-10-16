# API de Consulta de CEP

Este projeto é um servidor HTTP simples escrito em Java que fornece uma API REST para consultar endereços brasileiros pelo CEP.

## Arquitetura

A aplicação segue uma arquitetura em camadas para separar as responsabilidades:

-   **`HttpServer.java`**: A classe principal que inicia o servidor e gerencia as conexões de rede. Ele delega o processamento de requisições para o `Router`.
-   **`Router.java`**: Atua como um roteador, analisando as requisições recebidas e encaminhando-as para o controller apropriado.
-   **`CepRestController.java`**: O controller da aplicação. Ele contém a lógica para manipular as requisições relacionadas a CEPs, interagindo com o `CepRepository`.
-   **`CepRepository.java`**: A camada de acesso a dados, responsável por ler e buscar as informações de CEP do arquivo `cep.csv`.

## Como Executar

1.  **Compile os arquivos Java:**
    ```bash
    javac -d out src/main/java/*.java
    ```
2.  **Execute o servidor HTTP:**
    ```bash
    java -cp out HttpServer
    ```
3.  O servidor estará escutando na porta 8081.

## Endpoints da API

### GET /cep/{cep}

Recupera as informações de endereço para um determinado CEP.

-   **Parâmetros da URL:** `cep=[string]` (ex: `12210131`)
-   **Resposta de Sucesso:**
    -   **Código:** 200 OK
    -   **Conteúdo:**
        ```json
        {
          "cep": "12210131",
          "logradouro": "Rua Siqueira Campos",
          "complemento": "",
          "unidade": "",
          "bairro": "Centro",
          "localidade": "São José dos Campos",
          "uf": "SP",
          "estado": "São Paulo",
          "regiao": "Sudeste",
          "ibge": "3549904",
          "gia": "6455",
          "ddd": "12",
          "siafi": "7097",
          "geolocalizacao": {
            "lat": "-23.1802",
            "lng": "-45.882"
          }
        }
        ```
-   **Resposta de Erro:**
    -   **Código:** 404 Not Found
    -   **Conteúdo:**
        ```json
        {
          "error": "CEP não encontrado"
        }
        ```

## Exemplos de Uso

Você pode usar o `curl` para testar a API a partir da linha de comando.

**Exemplo de sucesso:**

```bash
curl http://localhost:8081/cep/12210131
```

**Exemplo de CEP não encontrado:**

```bash
curl http://localhost:8081/cep/99999999
```

## Fonte de Dados

Os dados de CEP são carregados do arquivo `src/main/resources/cep.csv`. Este arquivo contém uma lista de CEPs e suas informações de endereço correspondentes.

## Estrutura do Projeto

```
.
├── out/
└── src/
    ├── main/
    │   ├── java/
    │   │   ├── CepRepository.java
    │   │   ├── CepRestController.java
    │   │   ├── Endereco.java
    │   │   ├── Geolocalizacao.java
    │   │   ├── HttpServer.java
    │   │   └── Router.java
    │   └── resources/
    │       └── cep.csv
    └── test/
```