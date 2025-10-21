# Como funcionam as aplicações web?

## Introdução
Nesta aula, exploraremos detalhadamente como funcionam as aplicações web, incluindo o modelo cliente-servidor, o protocolo HTTP e as APIs REST. Vamos entender como esses elementos trabalham juntos para garantir uma comunicação eficiente na web, destacando também características das aplicações distribuídas e sua importância no desenvolvimento moderno.

As aplicações web estão na base de muitos serviços digitais que utilizamos diariamente, desde redes sociais até sistemas bancários e plataformas de e-commerce. Compreender como essas aplicações funcionam é essencial para quem deseja construir soluções robustas e escaláveis.

## Programação Distribuída
Programação distribuída envolve a execução de diferentes partes de uma aplicação em máquinas distintas conectadas em rede. Essa abordagem permite a comunicação entre clientes e servidores, viabilizando uma diversidade abrangente de serviços e recursos.

- **Cliente:** Software ou dispositivo que realiza solicitações e consome serviços fornecidos por servidores.
- **Servidor:** Sistema ou serviço que processa solicitações, executa tarefas e retorna respostas apropriadas.

### Características
- **Distribuição de carga:** melhor desempenho e escalabilidade.
- **Tolerância a falhas:** operações continuam mesmo se um servidor falhar.
- **Localização remota:** acesso a serviços de qualquer lugar do mundo.

### Exemplo
Ao acessar um site no navegador, este atua como cliente ao enviar uma solicitação ao servidor, que responde com a página web correspondente. Essa interação exemplifica como a programação distribuída é aplicada nas aplicações web modernas.

## Protocolo HTTP
HTTP (Hypertext Transfer Protocol) é o principal protocolo de comunicação na web. Ele especifica como solicitações e respostas devem ser estruturadas para garantir um intercâmbio de informações eficiente entre clientes e servidores.

### Estrutura de uma requisição HTTP
```
GET /index.html HTTP/1.1
Host: www.exemplo.com
```

- **Método:** determina a ação desejada (ex.: GET, POST, PUT, DELETE).
- **URL:** indica o recurso solicitado no servidor.
- **Versão:** versão do protocolo utilizado.
- **Cabeçalhos:** informações adicionais para a requisição (opcionais).

### Estrutura de uma resposta HTTP

Considerando a requisição enviada anteriormente para exibir "index.html", vamos examinar uma possível resposta.

```
HTTP/1.1 200 OK
Content-Type: text/html

<html>...conteúdo...</html>
```
- **Status Code:** indica o resultado do processamento (ex.: 200 OK, 404 Not Found).
- **Cabeçalhos:** metadados sobre a resposta.
- **Corpo:** conteúdo retornado pelo servidor.

### Métodos HTTP mais comuns
- **GET:** recupera informações de um recurso.
- **POST:** envia dados para criação de novos recursos.
- **PUT:** atualiza recursos existentes.
- **DELETE:** remove recursos especificados.
- **PATCH**: atualiza parte de um recurso existente.

### Códigos de status comuns
- **2xx:** Sucesso (ex.: 200 OK, 201 Created).
- **3xx:** Redirecionamento (ex.: 301 Moved Permanently).
- **4xx:** Erros do cliente (ex.: 404 Not Found).
- **5xx:** Erros do servidor (ex.: 500 Internal Server Error).

## API REST
REST (Representational State Transfer) é um estilo arquitetural para comunicação web, estabelecendo princípios para facilitar a interoperabilidade entre sistemas distribuídos.

### Princípios REST
- **Stateless:** cada requisição é processada de forma independente.
- **Recursos identificáveis:** urls únicas representam recursos acessíveis.
- **Operações padronizadas:** baseado nos métodos http padronizados.

### Exemplos de Boas Práticas para Definição de URIs

#### Boas Práticas

1. **Clareza e Coerência**: Use nomes descritivos e coesos para recursos.
   ```
   GET /users           # Obtém a lista de todos os usuários
   GET /users/{id}      # Obtém detalhes de um usuário específico
   POST /users          # Cria um usuário
   PUT /users/{id}      # Atualiza completamente um usuário específico
   PATCH /users/{id}    # Atualiza parcialmente um usuário
   DELETE /users/{id}   # Remove um usuário
   ```
2. **Identificação Hierárquica de Sub-recursos**:
   ```
   GET /users/{id}/orders          # Obtém todos os pedidos de um usuário específico
   GET /users/{id}/orders/{orderId} # Obtém detalhes de um pedido específico de um usuário
   ```
3. **Uso de Pluralidade para Representação de Coleções**:
   ```
   GET /products                  # Correto: usa plural para coleção de produtos
   GET /products/{productId}      # Obtém um único produto específico
   ```
4. **Evitar Verbos na URI (Ação é Definida pelo Método HTTP)**:
   ```
   POST /books                    # Correto: ação de criação implícita no POST
   ```

5. **Utilização de Parâmetros para Filtragem (Query Parameters)**:
   ```
   GET /books?author=John          # Pesquisa livros específicos pelo autor
   GET /books?genre=fiction&year=2021 # Filtros combinados para melhor controle
   ```

#### Evite

1. **URI Excessivamente Descritiva**:
   ```
   GET /getAllUsers                      # Errado: verbo desnecessário na URI
   DELETE /removeUser/{id}               # Errado: verbo redundante, ação é implícita no DELETE
   ```

2. **Falta de Clareza ou Estrutura**:
   ```
   GET /users/123/order/456             # Errado: inconsistente (singular/plural misturados)
   ```

3. **Uso de Verbos na Identificação de Recursos**:
   ```
   /createUser                           # Errado: a ação deve ser definida pelo método HTTP
   /deleteProduct/{productId}            # Errado pelo mesmo motivo
   ```

4. **Incluindo Filtragem como Parte da URI (Evitar Combinações Múltiplas)**:
   ```
   GET /products/fiction/2021           # Errado: deve usar parâmetros de consulta
   ```

5. **Uso de IDs Ambíguos ou Não Definidos**:
   ```
   GET /items/123XYZ890ABC              # Errado: dificuldade de compreensão do recurso
   ```

Esses exemplos mostram como URIs devem ser desenhadas de forma robusta, aderindo às boas práticas, para facilitar identificação e acesso aos recursos, ao mesmo tempo que tornam a API mais legível e fácil de usar.

### Modelo de maturidade de Richardson (nível 2)
- **Nível 0:** Chamadas simples sem estrutura definida.
- **Nível 1:** Recursos identificados por URLs.
- **Nível 2:** Uso correto de métodos HTTP e códigos de status para ações específicas.
- **Nível 3:** HATEOAS (Hypermedia as the Engine of Application State).

### Modelo de Maturidade de Richardson - Exemplos em cada nível

1. **Nível 0: Chamadas simples sem estrutura definida**  
   Neste nível, as APIs apenas utilizam um único endpoint sem diferenciar recursos ou métodos HTTP. Todas as requisições são feitas para a mesma URL e, normalmente, a ação ou tipo de operação é passada via query parameters, ou no corpo da requisição.

   **Exemplo**:
   ```bash
   curl -X POST http://api.example.com/api \
        -H "Content-Type: application/json" \
        -d '{"action": "getAllUsers"}'
   ```
   - O endpoint `/api` é genérico.
   - A operação é identificada pelo atributo `"action"` no corpo da requisição.
   - Sem uso claro dos métodos HTTP para determinar a ação.


2. **Nível 1: Recursos identificados por URLs**  
   Agora, recursos são organizados por URLs específicas que identificam os itens acessados. Porém, ainda sem usar adequadamente os métodos HTTP.

   **Exemplo**:
   ```bash
   curl http://api.example.com/users
   ```

    - Recursos como `users` estão claramente definidos por sua URL (`/users` para listar os usuários).
    - No entanto, todas as operações ainda podem ser feitas com o mesmo método HTTP, sem distinção.


3. **Nível 2: Uso correto de métodos HTTP e códigos de status**  
   Neste nível, existe uma diferenciação clara das operações com base nos métodos HTTP usados (como `GET`, `POST`, `PUT`, `DELETE`), além da adoção de códigos de status apropriados para as respostas.

   **Exemplo**:
    - Obter lista de usuários:
      ```bash
      curl -X GET http://api.example.com/users
      ```
    - Criar novo usuário:
      ```bash
      curl -X POST http://api.example.com/users \
           -H "Content-Type: application/json" \
           -d '{"name": "John Doe", "email": "john.doe@email.com"}'
      ```
    - Atualizar usuário:
      ```bash
      curl -X PUT http://api.example.com/users/1 \
           -H "Content-Type: application/json" \
           -d '{"name": "Jane Doe"}'
      ```
    - Excluir um usuário:
      ```bash
      curl -X DELETE http://api.example.com/users/1
      ```

    - Uso integrado de códigos de status:
        - `200 (OK)` para sucesso ao obter informações,
        - `201 (Created)` ao criar um novo recurso,
        - `404 (Not Found)` se o recurso não for encontrado,
        - etc.


4. **Nível 3: HATEOAS (Hypermedia as the Engine of Application State)**  
   No último nível, além de organizar os recursos e métodos, adiciona-se a navegação hipertextual (HATEOAS). As respostas do servidor contêm links que
   guiam o cliente para outras interações possíveis com a API.

   **Exemplo**:  
   Resposta para `GET /users/1`:
   ```json
   {
     "id": 1,
     "name": "John Doe",
     "email": "john.doe@email.com",
     "_links": {
       "self": {
         "href": "http://api.example.com/users/1"
       },
       "update": {
         "href": "http://api.example.com/users/1",
         "method": "PUT"
       },
       "delete": {
         "href": "http://api.example.com/users/1",
         "method": "DELETE"
       }
     }
   }
   ```

    - A resposta inclui links no atributo `_links`, indicando claramente os próximos passos possíveis:
        - Atualizar (`PUT`),
        - Deletar (`DELETE`),
        - Ou consultar o próprio recurso adicionalmente (`self`).
    - Esse modelo simplifica a interação entre cliente e servidor, pois o cliente conhece as ações disponíveis sem a necessidade de documentação
      extra.


Esses exemplos mostram como uma API pode evoluir rumo à maturidade de Richardson, tornando-se mais robusta, escalável e padronizada. Cada nível
representa uma melhora na organização, clareza e usabilidade da API.

### Benefícios de APIs REST
- Simplicidade de implementação.
- Alta escalabilidade.
- Compatibilidade com diferentes plataformas.
- Facilidade de integração com microsserviços.

## Estudo de Caso
**Exploração de uma API Pública**
- Acesse: [DummyJSON API](https://dummyjson.com/docs)
- Investigue endpoints como `/products` e `/users` usando navegador, Postman ou cURL.

Explore diferentes métodos e parâmetros para observar como as respostas variam de acordo com a solicitação.

### Tarefas propostas
- Realize solicitações de diferentes tipos.
- Inspecione as respostas e seus códigos de status.
- Identifique possíveis melhorias na API.

## Pesquisa Individual
- Investigue como desenvolver um cliente HTTP nativo em Java sem bibliotecas de terceiros.
- Pesquise alternativas modernas como a biblioteca `HttpClient` introduzida no Java 11.
- Explore boas práticas para implementar serviços RESTful seguros e escaláveis.

## Desafio
**Tarefa:** Desenvolva um cliente Java nativo que faça chamadas à API pública [ViaCEP](https://viacep.com.br/).

**Exemplo:**
```java
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ClienteViaCEP {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://viacep.com.br/ws/01001000/json/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

Inclua melhorias como tratamento de erros, leitura de parâmetros do usuário e exibição mais amigável das respostas.

## Conclusão
Você adquiriu uma compreensão inicial de como as aplicações web funcionam, desde o protocolo HTTP até o design de APIs REST. Na próxima aula, discutiremos como gerenciar dependências e manipular dados usando bibliotecas Java como Maven e Jackson. Explore os tópicos discutidos para reforçar seu aprendizado e prepare-se para construir aplicações web robustas e escaláveis.
