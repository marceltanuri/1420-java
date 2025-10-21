import io.github.marceltanuri.frameworks.restam3.HttpServer;
import io.github.marceltanuri.frameworks.restam3.Router;
import io.github.marceltanuri.frameworks.restam3.json.JsonParser;
import io.github.marceltanuri.frameworks.restam3.json.ConfigurableJacksonParser;

public class Main {
    public static void main(String[] args) {
        // 1. Configura o JSON Parser (usa Jackson com configurações padrão)
        JsonParser jsonParser = new ConfigurableJacksonParser();

        // 2. Cria o Router e registra as rotas
        Router router = 
            Router
                .create()
                .addRoute("/hello", new HelloController(jsonParser));

        // 3. Inicia o Servidor na porta 8080
        HttpServer
            .create(router)
            .start(8080);
    }
}