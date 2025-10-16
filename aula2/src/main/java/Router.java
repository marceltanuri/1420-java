
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Router {

    private final CepRestController cepRestController;

    public Router(CepRestController cepRestController) {
        this.cepRestController = cepRestController;
    }

    public void handleRequest(Socket clientSocket, String requestLine) throws IOException {
        if (requestLine != null) {
            System.out.println("=============================");
            System.out.println("Received request: " + requestLine);
            System.out.println("=============================");

            String[] requestParts = requestLine.split(" ");
            if (requestParts.length >= 2 && requestParts[0].equals("GET")) {
                String path = requestParts[1];
                if (path.startsWith("/cep/")) {
                    String cep = path.substring(5);
                    System.out.println("Received request for CEP: " + cep);
                    String response = cepRestController.getEnderecoByCep(cep);
                    System.out.println("Response: " + response);
                    sendResponse(clientSocket, response, "200 OK");
                } else {
                    sendResponse(clientSocket, "{\"error\": \"Invalid path\"}", "404 Not Found");
                }
            } else {
                sendResponse(clientSocket, "{\"error\": \"Invalid request\"}", "400 Bad Request");
            }
        }
    }

    private void sendResponse(Socket clientSocket, String response, String status) throws IOException {
        OutputStream out = clientSocket.getOutputStream();
        String httpResponse = "HTTP/1.1 " + status + "\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: " + response.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                "\r\n" +
                response;
        out.write(httpResponse.getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
}
