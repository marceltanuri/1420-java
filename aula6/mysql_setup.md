
# Como subir um servidor MySQL com Docker e acessá-lo

Este guia explica como iniciar um servidor MySQL usando Docker e como se conectar a ele tanto por linha de comando quanto por uma interface gráfica.

## 1. Subindo o servidor MySQL com Docker

Para iniciar um container Docker com uma instância do MySQL, execute o seguinte comando no seu terminal:

```bash
docker run --name mysql-local -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:8.0.28
```

**Detalhes do comando:**

*   `docker run`: Inicia um container Docker.
*   `--name mysql-local`: Define um nome para o container, facilitando sua identificação.
*   `-e MYSQL_ROOT_PASSWORD=root`: Define a senha do usuário `root` do MySQL como `root`. **Importante:** Use senhas seguras em ambientes de produção.
*   `-p 3306:3306`: Mapeia a porta 3306 do seu computador para a porta 3306 do container, permitindo a conexão ao banco de dados.
*   `-d`: Executa o container em modo "detached" (em segundo plano).
*   `mysql:8.0.28`: Especifica a imagem e a versão do MySQL a ser utilizada.

## 2. Acessando via Cliente de Linha de Comando (CLI)

Após o container estar em execução, você pode se conectar ao servidor MySQL usando o cliente de linha de comando.

**Comando para conectar:**

```bash
mysql -h 127.0.0.1 -u root -p
```

**Detalhes:**

*   `mysql`: Comando para iniciar o cliente MySQL.
*   `-h 127.0.0.1`: Especifica o endereço do servidor (localhost).
*   `-u root`: Define o usuário para a conexão.
*   `-p`: Solicita a senha. Ao ser solicitado, digite a senha definida no comando `docker run` (neste caso, `root`).

## 3. Acessando via Cliente com Interface Gráfica (GUI)

Você pode usar diversas ferramentas com interface gráfica para se conectar ao banco de dados. Abaixo estão as configurações para duas opções populares: DBeaver e SQuirreL SQL Client.

**Configurações de Conexão:**

*   **Host / Endereço do Servidor:** `127.0.0.1` ou `localhost`
*   **Porta:** `3306`
*   **Usuário:** `root`
*   **Senha:** `root` (a senha que você definiu)
*   **Database/Schema:** (Opcional) Você pode deixar em branco ou especificar um banco de dados se já tiver criado um.

### Exemplo com DBeaver:

1.  Abra o DBeaver e clique em "Nova Conexão".
2.  Selecione "MySQL".
3.  Na aba "Principal", preencha os campos com as informações acima.
4.  Clique em "Testar Conexão" para verificar se tudo está correto.
5.  Clique em "Finalizar" para salvar a conexão.

### Exemplo com SQuirreL SQL Client:

1.  Certifique-se de que o Driver JDBC do MySQL está configurado no SQuirreL.
2.  Crie um novo "Alias" (Conexão).
3.  Selecione o Driver do MySQL.
4.  Preencha a URL de conexão: `jdbc:mysql://127.0.0.1:3306/`
5.  Preencha o nome de usuário (`root`) e a senha (`root`).
6.  Clique em "Testar" e depois em "OK" para salvar.
