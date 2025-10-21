import io.github.marceltanuri.frameworks.restam3.controller.RestController;
import io.github.marceltanuri.frameworks.restam3.http.HttpRequest;
import io.github.marceltanuri.frameworks.restam3.http.HttpResponse;
import io.github.marceltanuri.frameworks.restam3.http.HttpStatus;
import io.github.marceltanuri.frameworks.restam3.json.JsonParser;

public class HelloController extends RestController {
    public HelloController(JsonParser jsonParser) {
        super(jsonParser);
    }

    @Override
    public HttpResponse handleGet(HttpRequest request) {
        // Objeto que será serializado para JSON na resposta
        class SimpleResponse {
            public String status = "OK";
            public String message = "Hello from restam3!";
        }
        
        // Serializa o objeto e retorna a resposta 200 OK
        String jsonBody = _toJson(new SimpleResponse()).orElse(
            "{\"error\": \"Failed to serialize response\"}"
        );
        
        return new HttpResponse(jsonBody, HttpStatus.OK);
    }
}
